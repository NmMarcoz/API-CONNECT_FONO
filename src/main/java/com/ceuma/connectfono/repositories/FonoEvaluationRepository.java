package com.ceuma.connectfono.repositories;

import com.ceuma.connectfono.models.FonoEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FonoEvaluationRepository extends JpaRepository<FonoEvaluation, UUID> {
}
