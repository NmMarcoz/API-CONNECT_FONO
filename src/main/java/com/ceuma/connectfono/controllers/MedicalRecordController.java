package com.ceuma.connectfono.controllers;

import com.aspose.pdf.Document;
import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.UUID;

@Controller
@RequestMapping("/medical_record")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    private MedicalRecordPdfController pdfController = new MedicalRecordPdfController();

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id){
        MedicalRecordDTO medicalRecordDTO = medicalRecordService.getById(id);
        return ResponseEntity.ok().body(medicalRecordDTO);
    }

    @GetMapping("pdf/{id}")
    public ResponseEntity<Resource> generatePdf(@PathVariable UUID id) {
        try {
            MedicalRecordDTO medicalRecordDTO = medicalRecordService.getById(id);
            System.out.println("achou o medicalRecord");
            String docPath = pdfController.generatePdf(medicalRecordDTO);
            File pdfFile = new File(docPath);
            if(!pdfFile.exists()){
                throw new BadRequestException("nao achei o pdf");
            }
            System.out.println(pdfFile.getName());

            Resource pdfForDownload = new FileSystemResource(pdfFile.getAbsolutePath());
            if(!pdfForDownload.exists()){
                throw new BadRequestException("nao achei o pdf");
            }
            System.out.println("nome do pdf: " + pdfForDownload.getFilename());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", pdfFile.getName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfFile.length())
                    .body(pdfForDownload);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
