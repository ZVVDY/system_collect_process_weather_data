package com.example.server_weather.repository;

import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findMeasurementBySensor(Sensor sensor);
}
