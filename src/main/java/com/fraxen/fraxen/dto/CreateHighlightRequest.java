package com.fraxen.fraxen.dto;

import lombok.Data;

@Data
public class CreateHighlightRequest {
    private String positionTitle;
    private String companyDescription;
    private String details;
    private Integer displayOrder;
}

