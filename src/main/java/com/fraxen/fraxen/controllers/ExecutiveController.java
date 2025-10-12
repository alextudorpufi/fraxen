package com.fraxen.fraxen.controllers;

import com.fraxen.fraxen.dto.CreateExecutiveRequest;
import com.fraxen.fraxen.dto.ExecutiveDTO;
import com.fraxen.fraxen.services.ExecutiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/executives")
@CrossOrigin(origins = "*") // Configure appropriately for production
@RequiredArgsConstructor
public class ExecutiveController {

    private final ExecutiveService executiveService;

    /**
     * GET /api/executives
     * Get all executives with their details
     */
    @GetMapping
    public ResponseEntity<List<ExecutiveDTO>> getAllExecutives() {
        try {
            List<ExecutiveDTO> executives = executiveService.getAllExecutives();
            return ResponseEntity.ok(executives);
        } catch (Exception e) {
            e.printStackTrace(); // add this
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    /**
     * GET /api/executives/{id}
     * Get single executive by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExecutiveDTO> getExecutiveById(@PathVariable Long id) {
        try {
            ExecutiveDTO executive = executiveService.getExecutiveById(id);
            return ResponseEntity.ok(executive);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/executives/gender/{gender}
     * Get executives by gender
     */
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<ExecutiveDTO>> getExecutivesByGender(@PathVariable String gender) {
        try {
            List<ExecutiveDTO> executives = executiveService.getExecutivesByGender(gender);
            return ResponseEntity.ok(executives);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/executives
     * Create new executive
     */
    @PostMapping
    public ResponseEntity<ExecutiveDTO> createExecutive(@RequestBody CreateExecutiveRequest request) {
        try {
            ExecutiveDTO executive = executiveService.createExecutive(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(executive);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/executives/{id}
     * Update existing executive
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExecutiveDTO> updateExecutive(
            @PathVariable Long id,
            @RequestBody CreateExecutiveRequest request) {
        try {
            ExecutiveDTO executive = executiveService.updateExecutive(id, request);
            return ResponseEntity.ok(executive);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/executives/{id}
     * Delete executive by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExecutive(@PathVariable Long id) {
        try {
            executiveService.deleteExecutive(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * HEAD /api/executives/{id}
     * Check if executive exists
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkExecutiveExists(@PathVariable Long id) {
        if (executiveService.existsById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
