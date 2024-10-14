package com.ceuma.connectfono.controllers;

import com.ceuma.connectfono.dto.AvailableTimeAndDateDTO;
import com.ceuma.connectfono.exceptions.patient.BadRequestException;
import com.ceuma.connectfono.models.Schedule;
import com.ceuma.connectfono.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("")
    public ResponseEntity<Object> findAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();

        if (schedules == null) {
            throw new BadRequestException("Não há agendamentos cadastrados");
        }

        return ResponseEntity.ok().body(schedules);
    }

    @GetMapping("/available")
    public ResponseEntity<Object> findAllAvailableHours(  @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Schedule> schedules = scheduleService.findByDate(date);
        List<LocalTime> schedulesHours = new ArrayList<>();
        schedules.forEach(item -> schedulesHours.add(item.getHour()));

        List<LocalTime> availableHours = scheduleService.getAvailableHours(schedulesHours);

        return ResponseEntity.ok().body(availableHours);
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> findAllSchedulesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        System.out.println("data sendo passada: " + date);
        List<Schedule> schedulesByDate = scheduleService.findByDate(date);
        return ResponseEntity.ok().body(schedulesByDate);
    }

    @GetMapping("/available/filter")
    public ResponseEntity<Object> findAllAvailableDatesWithHours(
            @RequestParam("beginDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Schedule> schedules = scheduleService.findByDateWithFilters(beginDate, endDate);


        return ResponseEntity.ok().body(schedules);
    }
}
