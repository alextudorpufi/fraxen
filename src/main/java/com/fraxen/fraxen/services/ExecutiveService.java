package com.fraxen.fraxen.services;

import com.fraxen.fraxen.dto.*;
import com.fraxen.fraxen.entities.Executive;
import com.fraxen.fraxen.entities.ExecutiveHighlight;
import com.fraxen.fraxen.entities.ExecutiveStrength;
import com.fraxen.fraxen.repositories.ExecutiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutiveService {

    private final ExecutiveRepository executiveRepository;

    /**
     * Get all executives with their highlights and strengths
     */
    @Transactional(readOnly = true)
    public List<ExecutiveDTO> getAllExecutives() {
        List<Executive> executives = executiveRepository.findAllWithDetails();
        return executives.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get single executive by ID with all details
     */
    @Transactional(readOnly = true)
    public ExecutiveDTO getExecutiveById(Long id) {
        Executive executive = executiveRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Executive not found with id: " + id));
        return convertToDTO(executive);
    }

    /**
     * Create new executive with highlights and strengths
     */
    @Transactional
    public ExecutiveDTO createExecutive(CreateExecutiveRequest request) {
        // Create executive entity
        Executive executive = new Executive();
        executive.setGender(request.getGender());
        executive.setTitle(request.getTitle());
        executive.setExperience(request.getExperience());
        executive.setSectorFocus(request.getSectorFocus());
        executive.setLocation(request.getLocation());

        // Initialize sets
        executive.setHighlights(new LinkedHashSet<>());
        executive.setStrengths(new LinkedHashSet<>());


        // Add highlights if provided
        if (request.getHighlights() != null && !request.getHighlights().isEmpty()) {
            Set<ExecutiveHighlight> highlights = request.getHighlights().stream()
                    .map(h -> {
                        ExecutiveHighlight highlight = new ExecutiveHighlight();
                        highlight.setExecutive(executive);
                        highlight.setPositionTitle(h.getPositionTitle());
                        highlight.setCompanyDescription(h.getCompanyDescription());
                        highlight.setDetails(h.getDetails());
                        highlight.setDisplayOrder(h.getDisplayOrder() != null ? h.getDisplayOrder() : 0);
                        return highlight;
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            executive.setHighlights(highlights);
        }

        // Add strengths if provided
        if (request.getStrengths() != null && !request.getStrengths().isEmpty()) {
            Set<ExecutiveStrength> strengths = request.getStrengths().stream()
                    .map(s -> {
                        ExecutiveStrength strength = new ExecutiveStrength();
                        strength.setExecutive(executive);
                        strength.setStrengthDescription(s.getStrengthDescription());
                        strength.setDisplayOrder(s.getDisplayOrder() != null ? s.getDisplayOrder() : 0);
                        return strength;
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            executive.setStrengths(strengths);
        }


        // Save executive (cascade will save highlights and strengths)
        Executive savedExecutive = executiveRepository.save(executive);
        return convertToDTO(savedExecutive);
    }

    /**
     * Update existing executive
     */
    @Transactional
    public ExecutiveDTO updateExecutive(Long id, CreateExecutiveRequest request) {
        Executive executive = executiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Executive not found with id: " + id));

        // Update basic fields
        executive.setGender(request.getGender());
        executive.setTitle(request.getTitle());
        executive.setExperience(request.getExperience());
        executive.setSectorFocus(request.getSectorFocus());
        executive.setLocation(request.getLocation());

        // Clear and update highlights
        executive.getHighlights().clear();
        if (request.getHighlights() != null && !request.getHighlights().isEmpty()) {
            Set<ExecutiveHighlight> highlights = request.getHighlights().stream()
                    .map(h -> {
                        ExecutiveHighlight highlight = new ExecutiveHighlight();
                        highlight.setExecutive(executive);
                        highlight.setPositionTitle(h.getPositionTitle());
                        highlight.setCompanyDescription(h.getCompanyDescription());
                        highlight.setDetails(h.getDetails());
                        highlight.setDisplayOrder(h.getDisplayOrder() != null ? h.getDisplayOrder() : 0);
                        return highlight;
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            executive.getHighlights().addAll(highlights);
        }

        // Clear and update strengths
        executive.getStrengths().clear();
        if (request.getStrengths() != null && !request.getStrengths().isEmpty()) {
            Set<ExecutiveStrength> strengths = request.getStrengths().stream()
                    .map(s -> {
                        ExecutiveStrength strength = new ExecutiveStrength();
                        strength.setExecutive(executive);
                        strength.setStrengthDescription(s.getStrengthDescription());
                        strength.setDisplayOrder(s.getDisplayOrder() != null ? s.getDisplayOrder() : 0);
                        return strength;
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            executive.getStrengths().addAll(strengths);
        }

        Executive updatedExecutive = executiveRepository.save(executive);
        return convertToDTO(updatedExecutive);
    }

    /**
     * Delete executive by ID
     */
    @Transactional
    public void deleteExecutive(Long id) {
        if (!executiveRepository.existsById(id)) {
            throw new RuntimeException("Executive not found with id: " + id);
        }
        executiveRepository.deleteById(id);
    }

    /**
     * Check if executive exists
     */
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return executiveRepository.existsById(id);
    }

    /**
     * Get executives by gender
     */
    @Transactional(readOnly = true)
    public List<ExecutiveDTO> getExecutivesByGender(String gender) {
        List<Executive> executives = executiveRepository.findByGender(gender);
        return executives.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert Entity to DTO
     */
    private ExecutiveDTO convertToDTO(Executive executive) {
        ExecutiveDTO dto = new ExecutiveDTO();
        dto.setId(executive.getId());
        dto.setGender(executive.getGender());
        dto.setTitle(executive.getTitle());
        dto.setExperience(executive.getExperience());
        dto.setSectorFocus(executive.getSectorFocus());
        dto.setLocation(executive.getLocation());

        // Convert highlights
        if (executive.getHighlights() != null) {
            List<HighlightDTO> highlights = executive.getHighlights().stream()
                    .map(h -> new HighlightDTO(
                            h.getId(),
                            h.getPositionTitle(),
                            h.getCompanyDescription(),
                            h.getDetails(),
                            h.getDisplayOrder()
                    ))
                    .collect(Collectors.toList());
            dto.setHighlights(highlights);
        }

        // Convert strengths
        if (executive.getStrengths() != null) {
            List<StrengthDTO> strengths = executive.getStrengths().stream()
                    .map(s -> new StrengthDTO(
                            s.getId(),
                            s.getStrengthDescription(),
                            s.getDisplayOrder()
                    ))
                    .collect(Collectors.toList());
            dto.setStrengths(strengths);
        }

        return dto;
    }
}
