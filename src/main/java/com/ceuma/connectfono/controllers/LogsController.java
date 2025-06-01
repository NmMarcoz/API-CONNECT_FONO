package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.core.patient.BadRequestException;
import com.ceuma.connectfono.core.models.Logs;
import com.ceuma.connectfono.core.responses.GenericResponse;
import com.ceuma.connectfono.services.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @GetMapping("bydate/{beginDate}/{endDate}")
    public ResponseEntity<List<Logs>> getByDateInterval(@PathVariable LocalDate beginDate, @PathVariable LocalDate endDate){
        List<Logs> logs = logsService.getByDateInterval(beginDate, endDate);
        return ResponseEntity.ok().body(logs);
    }

    @GetMapping("/bydateAndCpf/{date}/{cpf}")
    public ResponseEntity<List<Logs>> getLogsByDateAndCpf(@PathVariable LocalDate date, @PathVariable String cpf){
        List<Logs> logs = logsService.getByDateAndCpf(date, cpf);
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
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Logs logs){
        Logs logsUpdated = logsService.update(id, logs);
        return ResponseEntity.ok().body(buildSuccessResponse(200, "Log atualizado com sucesso"));
    }



    public GenericResponse buildSuccessResponse(int status, String message) {
        GenericResponse genericResponse = new GenericResponse(status, message);
        return genericResponse;
    }

}
