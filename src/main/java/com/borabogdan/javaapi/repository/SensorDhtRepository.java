package com.borabogdan.javaapi.repository;

import com.borabogdan.javaapi.entity.SensorDht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SensorDhtRepository extends JpaRepository<SensorDht, Integer> {

}
