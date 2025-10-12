package com.fraxen.fraxen.repositories;

import com.fraxen.fraxen.entities.ExecutiveStrength;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutiveStrengthRepository extends JpaRepository<ExecutiveStrength, Long> {

    // Find all strengths for a specific executive, ordered by display order
    List<ExecutiveStrength> findByExecutiveIdOrderByDisplayOrderAsc(Long executiveId);
}