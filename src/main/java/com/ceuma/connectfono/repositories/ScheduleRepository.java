package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Hour;
import com.ceuma.connectfono.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


}
