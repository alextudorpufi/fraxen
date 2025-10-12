package com.fraxen.fraxen.repositories;

import com.fraxen.fraxen.entities.ExecutiveHighlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutiveHighlightRepository extends JpaRepository<ExecutiveHighlight, Long> {

    // Find all highlights for a specific executive, ordered by display order
    List<ExecutiveHighlight> findByExecutiveIdOrderByDisplayOrderAsc(Long executiveId);
}