package com.hr.repository;

import com.hr.entity.Country;
import com.hr.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location,Integer> {
    @Query("SELECT c FROM Country c WHERE c.countryName = :countryName")
    Country findByCountryName(String countryName);
}
