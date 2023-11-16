package com.example.server_weather.repository;

import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    @Query("SELECT w FROM Measurement w WHERE w.sensor = ?1 ORDER BY w.timestamp DESC limit 20")
    List<Measurement> findMeasurementBySensor(Sensor sensor);

    @Query("SELECT w FROM Measurement w WHERE w.timestamp >= ?1")
    List<Measurement> findMeasurementLaterDateTime(LocalDateTime time);

    Optional<Measurement> findMeasurementBySensorOrderByTimestampDesc(Sensor sensor);

    @Query("SELECT DISTINCT w.sensor FROM Measurement w LEFT JOIN Measurement w1 ON w.sensor = w1.sensor AND " +
            "(w1.sensor.active = false OR w1.timestamp >= ?1) WHERE w1.sensor IS NULL")
    Set<Sensor> findWeatherSensorWitchNeedDeactivated(LocalDateTime time);
}
