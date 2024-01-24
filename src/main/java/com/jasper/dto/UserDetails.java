package com.jasper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    @JsonProperty("offenderName")
    private String offenderName;
    @JsonProperty("age")
    private String age;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("race")
    private String race;
    @JsonProperty("height")
    private String height;
    @JsonProperty("hair")
    private String hair;
    @JsonProperty("sid")
    private String sid;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("eyes")
    private String eyes;
    @JsonProperty("caseload")
    private String caseload;
    @JsonProperty("location")
    private String location;
    @JsonProperty("status")
    private String status;
    @JsonProperty("fieldAdmissionDate")
    private String fieldAdmissionDate;
    @JsonProperty("earliestReleaseDate")
    private String earliestReleaseDate;
    @JsonProperty("image")
    private String image;
}
