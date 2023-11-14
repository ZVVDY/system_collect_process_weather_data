package com.example.server_weather.service.impl;

import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.MeasurementRepository;
import com.example.server_weather.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ActivityCheckingService {
    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedRateTask() {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime oneMinuteAgo = time.minusMinutes(1);
        Set<Sensor> sensors = measurementRepository.findWeatherSensorWitchNeedDeactivated(oneMinuteAgo);
        for (Sensor sensor : sensors) {
            sensor.setActive(false);
            sensorRepository.save(sensor);
        }
    }
}
