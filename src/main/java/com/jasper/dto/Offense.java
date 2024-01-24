package com.jasper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offense {
    @JsonProperty("docketNumber")
    private String docketNumber;
    @JsonProperty("country")
    private String country;
    @JsonProperty("crime")
    private String crime;
    @JsonProperty("sentenceType")
    private String sentenceType;
    @JsonProperty("beginDate")
    private String beginDate;
    @JsonProperty("terminationDate")
    private String terminationDate;
}
