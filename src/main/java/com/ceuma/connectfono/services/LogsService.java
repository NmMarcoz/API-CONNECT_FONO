package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Logs;
import com.ceuma.connectfono.repositories.LogsRepository;
import com.ceuma.connectfono.repositories.PatientRepository;
import com.ceuma.connectfono.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LogsService {
    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private StaffRepository staffRepository;

    public List<Logs> getAll(){
        List<Logs> logs = logsRepository.findAll();
        if(logs.isEmpty()){
            throw new BadRequestException("Não há logs cadastrados");
        }
        return logs;
    }

    public Logs getById(Long id){
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

    public List<Logs> getByDateAndCpf(LocalDate date, String cpf){
        List<Logs> logs = logsRepository.getLogsByDateAndCpf(date, cpf);
        if(logs.isEmpty()){
            throw new BadRequestException("Nenhum log foi encontrado");
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

    public List<Logs> getByDateInterval(LocalDate beginDate, LocalDate endDate){
        List<Logs> logs = logsRepository.getLogsByIntervalDate(beginDate, endDate);
        if(logs.isEmpty()){
            throw new BadRequestException("Não há logs nesse intervalo de data");
        }
        return logs;
    }


    public Logs create(Logs logs){
        if(staffRepository.findByCpf(logs.getCpf()).isEmpty()){
            throw new BadRequestException("Não existe paciente com esse cpf");
        }
        Logs logSaved = logsRepository.save(logs);
        return logSaved;
    }

    public Logs update(Long id, Logs logs){
        Logs newLog = getById(id);
        if(logs.getCpf() != null){
            newLog.setCpf(logs.getCpf());
        }
        if(logs.getHour() != null){
            newLog.setHour(logs.getHour());
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