package com.fraxen.fraxen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrengthDTO {
    private Long id;
    private String strengthDescription;
    private Integer displayOrder;
}
