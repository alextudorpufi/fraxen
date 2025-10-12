package com.fraxen.fraxen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighlightDTO {
    private Long id;
    private String positionTitle;
    private String companyDescription;
    private String details;
    private Integer displayOrder;
}
