package com.example.server_weather.service.impl;


import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.exeption.MeasurementValidationException;
import com.example.server_weather.model.entity.Measurement;

import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.MeasurementRepository;
import com.example.server_weather.repository.SensorRepository;
import com.example.server_weather.service.MeasurementService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class MeasurementServiceImpl implements MeasurementService {

@Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
private SensorRepository sensorRepository;


    @Override
    public void addMeasurement(UUID sensorKey, MeasurementDto measurementDto) {
        Optional<Sensor> sensor = sensorRepository.findById(sensorKey);
        try {
            if (sensor.isEmpty()) {
                throw new MeasurementValidationException("Sensor not found");
            }
            if (measurementDto.getValue() < -100 || measurementDto.getValue() > 100) {
                throw new MeasurementValidationException("Value should be between -100 and 100");
            }
            sensor.get().setActive(true);
            sensorRepository.save(sensor.get());
            Measurement measurement = new Measurement();
            measurement.setValue(measurementDto.getValue());
            measurement.setRaining(measurementDto.isRaining());
            measurement.setTimestamp(LocalDateTime.now());
            measurement.setSensor(sensor.get());
            measurementRepository.save(measurement);
            System.out.println("Send data: "+measurement);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID : " + sensorKey);
        }
    }

    @Override
    public List<Measurement> getSensorMeasurements(UUID key) {
        Optional<Sensor> sensor = sensorRepository.findById(key);
        if(sensor.isPresent()){
            return measurementRepository.findMeasurementBySensor(sensor.get());
        }
        else {
            throw new NoSuchElementException(String.format("Sensor with key '%s' don't exist", key));
        }




    }


}
