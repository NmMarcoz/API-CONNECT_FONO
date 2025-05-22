package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionsRepository  extends JpaRepository<Questions, Integer> {
}
