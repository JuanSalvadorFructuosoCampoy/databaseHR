package com.hr.repository;

import com.hr.entity.Country;
import com.hr.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends JpaRepository<Country, String> {
    @Query("SELECT r FROM Region r WHERE r.regionName = :regionName")
    Region findByRegionName(String regionName);
}
