package com.ceuma.connectfono.services;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Hour;
import com.ceuma.connectfono.models.Schedule;
import com.ceuma.connectfono.repositories.HourRepository;
import com.ceuma.connectfono.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Schedule> findByDate(LocalDate date){
        List<Schedule> schedules = scheduleRepository.findByDate(date);


        return schedules;
    }

    public List<Schedule> findByDateWithFilters(LocalDate beginDate, LocalDate endDate){
        List<Schedule> schedules = scheduleRepository.findByDateWithBeginAndEnd(beginDate, endDate);

        return schedules;
    }

    public List<LocalTime> getAvailableHours(List<LocalTime> hour){
        if(hour == null || hour.isEmpty()){
            List<Time> hours = this.hourRepository.getAllHours();
            List<LocalTime> localTimes = hours.stream()
                    .map(Time::toLocalTime)
                    .collect(Collectors.toList());
            return localTimes;
        }
        List<Time> hours = this.hourRepository.getAvailableHours(hour);
        List<LocalTime> localTimes = hours.stream()
                .map(Time::toLocalTime)
                .collect(Collectors.toList());
        return localTimes;
    }
}