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

    @Query("select w from Measurement w where w.sensor = ?1 order by w.timestamp desc limit 20")
    List<Measurement> findMeasurementBySensor(Sensor sensor);

    @Query("select w from Measurement w where w.timestamp >= ?1")
    List<Measurement> findMeasurementLaterDateTime(LocalDateTime time);

    Optional<Measurement> findMeasurementBySensorOrderByTimestampDesc(Sensor sensor);

    @Query("select w.sensor from Measurement w where w.sensor not in (select w1.sensor from Measurement w1 where w1.sensor.active = false or w1.timestamp >= ?1)")
    Set<Sensor> findWeatherSensorWitchNeedDeactivated(LocalDateTime time);
}
