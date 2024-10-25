package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Logs;
import com.ceuma.connectfono.responses.GenericResponse;
import com.ceuma.connectfono.services.LogsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("logs")
public class LogsController {
    @Autowired
    LogsService logsService;

    @GetMapping("")
    public ResponseEntity<List<Logs>> getAllLogs(){
        List<Logs> logs = logsService.getAll();
        return ResponseEntity.ok().body(logs);
    }

    @GetMapping("/bycpf/{cpf}")
    public ResponseEntity<List<Logs>> getLogsByCpf(@PathVariable String cpf){
        List<Logs> logs = logsService.getByCpf(cpf);
        return ResponseEntity.ok().body(logs);
    }

    @GetMapping("/bydate/{date}")
    public ResponseEntity<List<Logs>> getLogsByDate(@PathVariable LocalDate date){
        List<Logs> logs = logsService.getByDate(date);
        return ResponseEntity.ok().body(logs);
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody Logs logs){
        if(logs.getMessage() == null || logs.getCpf() == null ){
            throw new BadRequestException("Campos obrigatórios: message, cpf");
        }
        logs.setDate(LocalDate.now());
        logs.setHour(LocalTime.now());
        logsService.create(logs);
        return ResponseEntity.ok().body(buildSuccessResponse(200, "Log criado com sucesso"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody Logs logs){
        Logs logsUpdated = logsService.update(id, logs);
        return ResponseEntity.ok().body(buildSuccessResponse(200, "Log atualizado com sucesso"));
    }



    public GenericResponse buildSuccessResponse(int status, String message) {
        GenericResponse genericResponse = new GenericResponse(status, message);
        return genericResponse;
    }

}
