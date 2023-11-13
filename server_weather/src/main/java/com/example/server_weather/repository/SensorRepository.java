package com.example.server_weather.repository;

import com.example.server_weather.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, UUID> {
    boolean existsByActive(boolean active);
    boolean existsByName(String name);
}
