package com.hr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "regions")
public class Region {
    @Id
    private Integer regionId;
    private String regionName;

    @JsonIgnore
    @OneToMany(mappedBy = "region",
            fetch= FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Country> countries;
}
