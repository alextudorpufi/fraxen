package com.fraxen.fraxen.dto;

import lombok.Data;

@Data
public class CreateStrengthRequest {
    private String strengthDescription;
    private Integer displayOrder;
}
