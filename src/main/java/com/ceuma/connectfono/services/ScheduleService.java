package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Schedule;
import com.ceuma.connectfono.repositories.HourRepository;
import com.ceuma.connectfono.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    HourRepository hourRepository;


    public Schedule create(Schedule obj){
        obj.setId(null);
        this.scheduleRepository.save(obj);
        return obj;
    }

    public List<Schedule> findAll(){
        List<Schedule> objs = this.scheduleRepository.findAll();
        if(objs == null){
            throw new BadRequestException("Nenhum agendamento cadastrado");
        }
        return objs;
    }

    public Schedule findById(Long id){
        Optional<Schedule> obj = this.scheduleRepository.findById(id);

        return obj.orElseThrow(() -> new BadRequestException("Nenhum agendamento cadastrado com esse id"));
    }

    public List<String> getAvailableHours(List<String> hour){
        return this.hourRepository.getAvailableHours(hour);
    }


}
