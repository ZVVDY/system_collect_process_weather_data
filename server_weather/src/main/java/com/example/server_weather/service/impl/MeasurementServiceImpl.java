package com.example.server_weather.service.impl;


import com.example.server_weather.exeption.MeasurementValidationException;
import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.MeasurementRepository;
import com.example.server_weather.repository.SensorRepository;
import com.example.server_weather.service.MeasurementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class MeasurementServiceImpl implements MeasurementService {


    private MeasurementRepository measurementRepository;
    private SensorRepository sensorRepository;


    @Override
    public void addMeasurement(UUID key, double value, boolean raining) {
        Optional<Sensor> sensor = sensorRepository.findById(key);
        try {
            if (sensor.isEmpty()) {
                throw new MeasurementValidationException("Sensor not found");
            }
            if (value < -100 || value > 100) {
                throw new MeasurementValidationException("Value should be between -100 and 100");
            }
            sensor.get().setActive(true);
            sensorRepository.save(sensor.get());
            Measurement measurement = new Measurement();
            measurement.setValue(value);
            measurement.setRaining(raining);
            measurement.setTimestamp(LocalDateTime.now());
            measurement.setSensor(sensor.get());
            measurementRepository.save(measurement);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID : " + key);
        }
    }

    @Override
    public List<Measurement> getSensorMeasurements(String key) {
        return null;
    }


}