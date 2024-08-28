package com.hr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country {
    @Id
    private String countryId;
    private String countryName;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @JsonBackReference
    private Region region;

    @OneToMany(mappedBy = "country",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Location> locations;
}
