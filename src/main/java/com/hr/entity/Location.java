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
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "location",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Department> departments;
}
