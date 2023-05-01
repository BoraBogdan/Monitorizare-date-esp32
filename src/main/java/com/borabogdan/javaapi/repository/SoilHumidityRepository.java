package com.borabogdan.javaapi.repository;

import com.borabogdan.javaapi.entity.SoilHumidityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoilHumidityRepository extends JpaRepository<SoilHumidityEntity, Integer> {


}
