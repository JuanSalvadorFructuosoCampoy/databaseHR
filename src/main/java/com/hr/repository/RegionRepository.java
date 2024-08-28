package com.hr.repository;

import com.hr.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegionRepository extends JpaRepository<Region, Integer> {

    @Query(value = "SELECT MAX(region_id) FROM regions", nativeQuery = true)
    Integer findMaxRegionId();}
