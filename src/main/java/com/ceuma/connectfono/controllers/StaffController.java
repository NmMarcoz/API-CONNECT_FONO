package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.AuthenticateResponseDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Logs;
import com.ceuma.connectfono.models.Staff;
import com.ceuma.connectfono.repositories.StaffRepository;
import com.ceuma.connectfono.responses.GenericResponse;
import com.ceuma.connectfono.responses.StaffResponse;
import com.ceuma.connectfono.services.LogsService;
import com.ceuma.connectfono.services.StaffService;

import com.ceuma.connectfono.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ch.qos.logback.core.util.StringUtil.isNullOrEmpty;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    StaffService staffService;

    StringUtils stringUtils = new StringUtils();
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private LogsService logsService;

    @GetMapping("")
    public ResponseEntity<List<Staff>> getAll(){
        List<Staff> staffs = staffService.getAll();
        if(staffs == null || staffs.isEmpty()){
            throw new BadRequestException("Nenhum staff encontrado");
        }
        return ResponseEntity.ok().body(staffs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getById(@PathVariable Integer id){
        Staff staff = staffService.getById(id);
        return ResponseEntity.ok().body(staff);
    }

    @GetMapping("cpf/{cpf}")
    public ResponseEntity<Staff> getByCpf(@PathVariable String cpf){
        System.out.println(cpf);
        if(cpf.isEmpty() || cpf.isBlank() || cpf.equals(":cpf")){
            throw new BadRequestException("Insira um cpf válido");
        }
        Staff staff = staffService.getByCpf(cpf);
        return ResponseEntity.ok().body(staff);
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Staff staff){
        if(Objects.isNull(staff)){
            throw new BadRequestException("Requisição inválida: Nenhum campo informado");
        }
        if(stringUtils.isNullOrEmpty(staff.getEmail()) ||
                stringUtils.isNullOrEmpty(staff.getPassword()) ||
                stringUtils.isNullOrEmpty(staff.getName())){
            throw new BadRequestException("Requisição inválida: Campos obrigatórios não informados (cpf, email, password, name)");
        }

        Staff staffCreated = staffService.create(staff);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(staff.getId()).toUri();

        return ResponseEntity.created(uri).body(buildSuccessResponse(201, "Staff cadastrado com sucesso"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Staff staff){
        if(Objects.isNull(staff)){
            throw new BadRequestException("Insira ao menos um campo");
        }
        Staff  dbStaff = staffService.getById(id);
        if(staff.getLevel() != null){
            dbStaff.setLevel(staff.getLevel());
        }
        if(staff.getName() != null){
            dbStaff.setName(staff.getName());
        }
        if(staff.getEmail() != null){
            dbStaff.setEmail(staff.getEmail());
        }
        if(staff.getPassword() != null){
            dbStaff.setPassword(staff.getPassword());
        }
        if(staff.getCpf()!= null){
            dbStaff.setCpf(staff.getCpf());
        }
        if(staff.getPhone_number() != null){
            dbStaff.setPhone_number(staff.getPhone_number());
        }
        Staff newStaff = staffService.update(id, dbStaff);
        return ResponseEntity.ok().body(buildSuccessResponse(200, "Atualizado com sucesso"));
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody Staff staff){
        if(isNullOrEmpty(staff.getEmail()) || isNullOrEmpty(staff.getPassword())){
            throw new BadRequestException("Os dois campos são obrigatórios");
        }
        Staff staffFound = staffService.login(staff.getEmail(), staff.getPassword());
        if(staffFound == null){
            throw new BadRequestException("Email ou senha inválidos");
        }
        String message = "O staff " + staffFound.getCpf() + " efetuou login";
        Logs log = new Logs(staffFound.getId(),message, staffFound.getCpf(), String.valueOf(LocalDate.now()), String.valueOf(LocalTime.now()));
        logsService.create(log);
        AuthenticateResponseDTO authenticateResponseDTO =
                new AuthenticateResponseDTO(
                        null,
                        "Autenticação com sucesso",
                        true,
                        staffFound
                        );

        return ResponseEntity.ok().body(authenticateResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        Staff staff = staffRepository.findById(id).orElse(null);
        if(staff == null){
            throw new BadRequestException("nenhum staff encontrado");
        }
        staffRepository.delete(staff);
        return ResponseEntity.ok().body(buildSuccessResponse(200, "Deletado com sucesso"));
    }

    public Object buildSuccessResponse(int status, String message){
        GenericResponse genericResponse = new GenericResponse(status, message);
        return genericResponse;
    }

    public Object buildSuccessResponse(int status, String message, Staff staff){
        return new StaffResponse(status, message, staff);
    }


}
