package com.hr.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class CountryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String countryId;
    private String countryName;
    private String region;
}
