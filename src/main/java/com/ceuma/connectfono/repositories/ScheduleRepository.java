package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Hour;
import com.ceuma.connectfono.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT * FROM schedule WHERE DATE(date) = :dateToFind", nativeQuery = true)
    List<Schedule> findByDate(@Param("dateToFind") LocalDate dateToFind);

    @Query(value = "SELECT * FROM schedule WHERE DATE(date) BETWEEN :beginDate AND :endDate", nativeQuery = true)
    List<Schedule> findByDateWithBeginAndEnd(@Param("beginDate") LocalDate beginDate, @Param("endDate") LocalDate endDate);

}
