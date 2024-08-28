package com.hr.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegionDTO {
    private Integer regionId;
    private String regionName;
    private List<CountryDTO> countries;
}

