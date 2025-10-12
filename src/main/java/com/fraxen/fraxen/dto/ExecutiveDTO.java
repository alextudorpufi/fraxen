package com.fraxen.fraxen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutiveDTO {
    private Long id;
    private String gender;
    private String title;
    private String experience;
    private String sectorFocus;
    private String location;
    private List<HighlightDTO> highlights;
    private List<StrengthDTO> strengths;
}