package com.fraxen.fraxen.repositories;

import com.fraxen.fraxen.entities.Executive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExecutiveRepository extends JpaRepository<Executive, Long> {

    // Fetch single executive with all highlights and strengths
    @Query("SELECT DISTINCT e FROM Executive e " +
            "LEFT JOIN FETCH e.highlights " +
            "LEFT JOIN FETCH e.strengths " +
            "WHERE e.id = :id")
    Optional<Executive> findByIdWithDetails(@Param("id") Long id);

    // Fetch all executives with all highlights and strengths
    @Query("SELECT DISTINCT e FROM Executive e " +
            "LEFT JOIN FETCH e.highlights " +
            "LEFT JOIN FETCH e.strengths ")
    List<Executive> findAllWithDetails();

    // Find executives by gender
    List<Executive> findByGender(String gender);

    // Find executives by location containing string
    List<Executive> findByLocationContainingIgnoreCase(String location);
}