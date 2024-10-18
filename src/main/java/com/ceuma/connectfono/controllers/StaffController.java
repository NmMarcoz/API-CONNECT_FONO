package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.AuthenticateResponseDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Staff;
import com.ceuma.connectfono.repositories.StaffRepository;
import com.ceuma.connectfono.services.StaffService;

import com.ceuma.connectfono.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("")
    public ResponseEntity<List<Staff>> getAll(){
        List<Staff> staffs = staffService.getAll();
        if(staffs == null || staffs.isEmpty()){
            throw new BadRequestException("Nenhum staff encontrado");
        }
        return ResponseEntity.ok().body(staffs);
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
        return ResponseEntity.created(uri).body(staffCreated);
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
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        Staff staff = staffRepository.findById(id).orElse(null);
        if(staff == null){
            throw new BadRequestException("nenhum staff encontrado");
        }
        staffRepository.delete(staff);
        return ResponseEntity.noContent().build();
    }
}
