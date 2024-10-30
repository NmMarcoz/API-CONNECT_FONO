package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LogsRepository extends JpaRepository<Logs, UUID> {
    @Query(value = " SELECT * FROM logs WHERE cpf = :cpf", nativeQuery = true)
    List<Logs> getLogsByUserCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM logs WHERE date = :date", nativeQuery = true)
    List<Logs> getLogsByDate(@Param("date")LocalDate date);

    @Query(value = "SELECT * FROM logs WHERE date =:date AND cpf = :cpf", nativeQuery = true)
    List<Logs> getLogsByDateAndCpf(@Param("date") LocalDate date, @Param("cpf") String cpf);

    @Query(value = "SELECT * FROM logs WHERE date BETWEEN :begin_date AND :end_date", nativeQuery = true)
    List<Logs> getLogsByIntervalDate(@Param("begin_date") LocalDate beginDate, @Param("end_date") LocalDate endDate);
}
