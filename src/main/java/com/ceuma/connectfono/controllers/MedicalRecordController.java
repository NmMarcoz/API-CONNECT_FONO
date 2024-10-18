package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/medical_record")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        System.out.println("ta aqui");
        if(medicalRecordDTO.getMedicalRecord() == null){
            throw new BadRequestException("Medical Record cannot be null");
        }

        MedicalRecordDTO medicalRecordDTOSaved = medicalRecordService.create(medicalRecordDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(medicalRecordDTOSaved.getMedicalRecord().getId()).toUri();
        return ResponseEntity.created(uri).body(medicalRecordDTOSaved);

    }
}
