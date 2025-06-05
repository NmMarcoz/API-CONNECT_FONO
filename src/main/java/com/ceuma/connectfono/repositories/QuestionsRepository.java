package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.core.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository  extends JpaRepository<Questions, Long> {
}
