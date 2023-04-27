package com.borabogdan.javaapi.repository;

import com.borabogdan.javaapi.entity.SensorDht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface SensorDhtRepository extends JpaRepository<SensorDht, Integer> {

    @Query("SELECT s FROM SensorDht s WHERE EXTRACT(DAY FROM s.timestamp) = :day")
    Optional<List<SensorDht>> findByDay(@Param("day") int day);

    @Query("SELECT EXTRACT(DAY FROM s.timestamp) FROM SensorDht s GROUP BY  EXTRACT(DAY FROM s.timestamp)")
    List<Integer> getAllTimestamps ();
}
