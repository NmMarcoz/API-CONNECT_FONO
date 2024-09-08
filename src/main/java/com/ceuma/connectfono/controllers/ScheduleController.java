package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Schedule;
import com.ceuma.connectfono.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("")
    public ResponseEntity<Object> findAllSchedules(){
        List<Schedule> schedules = scheduleService.findAll();

        if(schedules == null){
            throw new BadRequestException("Não há agendamentos cadastrados");
        }

        return ResponseEntity.ok().body(schedules);
    }

    @GetMapping("/available")
    public ResponseEntity<Object> findAllAvailableHours(){
        List<Schedule> schedules = scheduleService.findAll();
        List<Time> schedulesHours = new ArrayList<>();
        schedules.forEach(item -> schedulesHours.add(item.getHour()));

        List<Time> availableHours = scheduleService.getAvailableHours(schedulesHours);

        return ResponseEntity.ok().body(availableHours);
    }

    @GetMapping("/available/filter")
    public ResponseEntity<Object> findAllSchedulesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        System.out.println("data sendo passada: " + date );
        List<Schedule> schedulesByDate = scheduleService.findByDate(date);
        return ResponseEntity.ok().body(schedulesByDate);
    }

    public ResponseEntity<Object> findAllAvailableDatesWithHours(){

    }
}
