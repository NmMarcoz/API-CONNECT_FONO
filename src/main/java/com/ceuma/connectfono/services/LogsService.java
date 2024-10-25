package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Logs;
import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.repositories.LogsRepository;
import com.ceuma.connectfono.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private PatientRepository patientRepository;

    public List<Logs> getAll(){
        List<Logs> logs = logsRepository.findAll();
        if(logs.isEmpty()){
            throw new BadRequestException("Não há logs cadastrados");
        }
        return logs;
    }
    public Logs getById(UUID id){
        return logsRepository.findById(id).orElseThrow(
                ()-> new BadRequestException("Não existe log cadastrado com esse id")
        );
    }

    public List<Logs> getByCpf(String cpf){
        List<Logs> logs = logsRepository.getLogsByUserCpf(cpf);
        if(logs.isEmpty()){
            throw new BadRequestException("Não há logs cadastrados para esse cpf");
        }
        return logs;
    }

    public List<Logs> getByDate(LocalDate date){
        List<Logs> logs = logsRepository.getLogsByDate(date);
        if(logs.isEmpty()){
            throw new BadRequestException("Não há logs cadastrados nessa data");
        }
        return logs;
    }

    public Logs create(Logs logs){
        if(patientRepository.findByCpf(logs.getCpf()).isEmpty()){
            throw new BadRequestException("Não existe paciente com esse cpf");
        }
        Logs logSaved = logsRepository.save(logs);
        return logSaved;
    }

    public Logs update(UUID id, Logs logs){
        Logs newLog = getById(id);
        if(logs.getCpf() != null){
            newLog.setCpf(logs.getCpf());
        }
        if(logs.getTime() != null){
            newLog.setTime(logs.getTime());
        }
        if(logs.getDate() != null){
            newLog.setDate(logs.getDate());
        }
        if(logs.getMessage() != null){
            newLog.setMessage(logs.getMessage());
        }
        return logsRepository.save(newLog);
    }

}
