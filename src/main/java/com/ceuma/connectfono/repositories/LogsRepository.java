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
}
