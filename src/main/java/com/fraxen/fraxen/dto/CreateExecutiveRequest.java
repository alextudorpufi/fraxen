package com.fraxen.fraxen.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateExecutiveRequest {
    private String gender;
    private String title;
    private String experience;
    private String sectorFocus;
    private String location;
    private List<CreateHighlightRequest> highlights;
    private List<CreateStrengthRequest> strengths;
}

