package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public interface HourRepository extends JpaRepository <Hour, Long>{
    @Query(value = "SELECT hour FROM hour WHERE hour NOT IN :hours",nativeQuery = true)
    List<Time> getAvailableHours(@Param("hours") List<LocalTime> hours);

    @Query(value = "SELECT hour FROM hour", nativeQuery = true)
    List<Time> getAllHours();
}
