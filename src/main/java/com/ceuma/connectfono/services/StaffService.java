package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Staff;
import com.ceuma.connectfono.repositories.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;

    @Transactional
    public Staff create(Staff obj){
        obj.setId(null);
        this.staffRepository.save(obj);
        return obj;
    }

    @Transactional
    public Staff update(Integer id,Staff obj){
        Staff newStaff = staffRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Não existe um staff com esse id"));
        newStaff.setLevel(obj.getLevel());
        newStaff.setPassword(obj.getPassword());
        newStaff.setEmail(obj.getEmail());
        newStaff.setPhone_number(obj.getPhone_number());
        newStaff.setCpf(obj.getCpf());

        return staffRepository.save(newStaff);
    }
    public List<Staff> getAll(){
        List<Staff> staffs = this.staffRepository.findAll();
        if(staffs == null){
            throw new BadRequestException("sem staffs cadastrados");
        }
        return staffs;
    }

    public Staff getById(Integer id){
        Staff staff = this.staffRepository.findById(id).orElse(null);
        if(staff == null){
            throw new BadRequestException("sem staff cadastrado");

        }
        return staff;
    }

    public Staff getByCpf(String cpf){
        Staff staff = staffRepository.findByCpf(cpf).orElseThrow(
                ()-> new BadRequestException("Não existe um staff cadastrado com esse cpf")
        );
        return staff;
    }

    public Staff login(String email, String password){
        Staff staff = this.staffRepository.login(email, password);
        if(staff == null){
           throw new BadRequestException("Email ou senha inválidos");
        }
        return staff;
    }

}
