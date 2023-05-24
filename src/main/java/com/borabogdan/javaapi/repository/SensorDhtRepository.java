package com.borabogdan.javaapi.repository;

import com.borabogdan.javaapi.dto.GetSensorDhtDataAvgDTO;
import com.borabogdan.javaapi.entity.SensorDhtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface SensorDhtRepository extends JpaRepository<SensorDhtEntity, Integer> {

    @Query("SELECT s FROM SensorDhtEntity s WHERE EXTRACT(DAY FROM s.timestamp) = :day")
    Optional<List<SensorDhtEntity>> findByDay(@Param("day") int day);

    @Query("SELECT EXTRACT(DAY FROM s.timestamp) FROM SensorDhtEntity s GROUP BY  EXTRACT(DAY FROM s.timestamp)")
    List<Integer> getAllTimestamps ();


    @Query("SELECT new com.borabogdan.javaapi.dto.GetSensorDhtDataAvgDTO( EXTRACT(HOUR FROM s.timestamp), AVG(s.temperature), AVG(s.airHumidity) ) FROM SensorDhtEntity s WHERE s.timestamp >= :fromTimestamp GROUP BY 1")
    List<GetSensorDhtDataAvgDTO> getLast24HoursData(@Param("fromTimestamp") Timestamp fromTimestamp);

    @Query("SELECT new com.borabogdan.javaapi.dto.GetSensorDhtDataAvgDTO( EXTRACT(DAY FROM s.timestamp), AVG(s.temperature), AVG(s.airHumidity) ) FROM SensorDhtEntity s WHERE s.timestamp >= :fromTimestamp GROUP BY 1")
    List<GetSensorDhtDataAvgDTO> getLast7DaysData(@Param("fromTimestamp") Timestamp fromTimestamp);
}
