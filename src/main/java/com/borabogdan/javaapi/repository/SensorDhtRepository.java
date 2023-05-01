package com.borabogdan.javaapi.repository;

import com.borabogdan.javaapi.entity.SensorDhtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SensorDhtRepository extends JpaRepository<SensorDhtEntity, Integer> {

    @Query("SELECT s FROM SensorDhtEntity s WHERE EXTRACT(DAY FROM s.timestamp) = :day")
    Optional<List<SensorDhtEntity>> findByDay(@Param("day") int day);

    @Query("SELECT EXTRACT(DAY FROM s.timestamp) FROM SensorDhtEntity s GROUP BY  EXTRACT(DAY FROM s.timestamp)")
    List<Integer> getAllTimestamps ();
}
