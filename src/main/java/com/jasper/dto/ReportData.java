package com.jasper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportData {
    @JsonProperty("userDetails")
    private UserDetails userDetails;
    @JsonProperty("offenses")
    private List<Offense> offenses;
}
