package com.borabogdan.javaapi.repository;

import com.borabogdan.javaapi.dto.GetSoilHumidityAvgDTO;
import com.borabogdan.javaapi.entity.SoilHumidityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SoilHumidityRepository extends JpaRepository<SoilHumidityEntity, Integer> {

    @Query("SELECT new com.borabogdan.javaapi.dto.GetSoilHumidityAvgDTO( EXTRACT(HOUR FROM s.timestamp), AVG(s.soilHumidity) ) FROM SoilHumidityEntity s WHERE s.timestamp >= :fromTimestamp GROUP BY 1")
    List<GetSoilHumidityAvgDTO> getLast24HoursData(@Param("fromTimestamp") Timestamp fromTimestamp);

    @Query("SELECT new com.borabogdan.javaapi.dto.GetSoilHumidityAvgDTO(function('date_trunc', DAY, s.timestamp), AVG(s.soilHumidity)) "
        + "FROM SoilHumidityEntity s "
        + "WHERE s.timestamp >= :fromTimestamp "
        + "GROUP BY 1 "
        + "ORDER BY 1")
    List<GetSoilHumidityAvgDTO> getLast7DaysData(@Param("fromTimestamp") Timestamp fromTimestamp);


}
