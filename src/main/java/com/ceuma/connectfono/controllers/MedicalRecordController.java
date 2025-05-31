package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.MedicalRecordDTO;
import com.ceuma.connectfono.dto.SmallMedicalRecordDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.MedicalRecord;
import com.ceuma.connectfono.responses.GenericResponse;
import com.ceuma.connectfono.services.MedicalRecordService;
import com.ceuma.connectfono.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URI;
import java.util.List;

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
        MedicalRecord medicalRecord = medicalRecordDTO.getMedicalRecord();
        if(medicalRecord.getConsultName() == null){
            throw new BadRequestException("O nome da consulta é obrigatório");
        }
        if(!VerifyUtils.isValidConsultName(medicalRecord.getConsultName())){
            throw new BadRequestException("O nome da consulta deve conter um dos seguintes valores: " +
                    "AUDIOMETRIA TONAL E VOCAL, " +
                    "CONSULTA FONOAUDIOLOGIA, IMITANCIOMETRIA, " +
                    "PROCESSAMENTO AUDITIVO CENTRAL, " +
                    "TESTE DE ORELHINHA, " +
                    "VENG");
        }

        MedicalRecordDTO medicalRecordDTOSaved = medicalRecordService.create(medicalRecordDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(medicalRecordDTOSaved.getMedicalRecord().getId()).toUri();
        return ResponseEntity.created(uri).body(medicalRecordDTOSaved);

    }

    @PostMapping("/v2")
    public ResponseEntity<Object> createv2(@RequestBody MedicalRecord  medicalRecord) {
        if(medicalRecord.getMedicalHistory() == null){
            throw new BadRequestException("Anamnese não pode estar vazia");
        }
        if(medicalRecord.getConsultName() == null){
            throw new BadRequestException("nome da consulta é obrigatório");
        }

        MedicalRecord medicalRecordSaved = medicalRecordService.createv2(medicalRecord);
        return ResponseEntity.status(201).body(buildSuccessResponse(201, "prontuario registrado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id){
        MedicalRecord medicalRecord = medicalRecordService.getById(id);
        return ResponseEntity.ok().body(medicalRecord);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<List<MedicalRecord>> getByStaffId(@PathVariable Long id){
        List<MedicalRecord> medicalRecordList = medicalRecordService.getByStaffId(id);
        return ResponseEntity.ok().body(medicalRecordList);
    }

    //retorna o medicalHistory porque ele tem a referencia para o medicalRecord, e nao o contrario.
    @GetMapping("")
    public ResponseEntity<List<MedicalRecord>> getAll(){
        List<MedicalRecord> medicalRecords = medicalRecordService.getAll();
        return ResponseEntity.ok().body(medicalRecords);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<SmallMedicalRecordDTO>> getByPatientCpf(@PathVariable String cpf){
        List<SmallMedicalRecordDTO> medicalRecords = medicalRecordService.getByPatientCpf(cpf);
        return ResponseEntity.ok().body(medicalRecords);
    }

    @GetMapping("/staff/cpf/{cpf}")
    public ResponseEntity<List<SmallMedicalRecordDTO>> getByStaffCpf(@PathVariable String cpf){
        List<SmallMedicalRecordDTO> medicalRecords = medicalRecordService.getByStaffCpf(cpf);
        return ResponseEntity.ok().body(medicalRecords);
    }

    @GetMapping("pdf/{id}")
    public ResponseEntity<Resource> generatePdf(@PathVariable Long id) {
        try {
            MedicalRecord medicalRecord = medicalRecordService.getById(id);
            System.out.println("achou o medicalRecord");
            String docPath = pdfController.generatePdf(medicalRecord);
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
            ResponseEntity<Resource> resourceResponseEntity = ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfForDownload.contentLength())
                    .body(pdfForDownload);

            new Thread(()->{
                try{
                    Thread.sleep(5000);
                    if(pdfFile.exists()){
                        System.out.println("existe");
                        pdfFile.delete();
                    }
                }catch(Exception e){
                    System.out.println("vish");
                }
            }).start();

            return resourceResponseEntity;
            //pdfFile.delete();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GenericResponse buildSuccessResponse(int status, String message) {
        GenericResponse genericResponse = new GenericResponse(status, message);
        return genericResponse;
    }
}
