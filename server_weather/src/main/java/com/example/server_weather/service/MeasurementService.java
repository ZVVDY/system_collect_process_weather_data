package com.example.server_weather.service;

import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.model.entity.Measurement;

import java.util.List;
import java.util.UUID;

public interface MeasurementService {

   void addMeasurement(UUID key, MeasurementDto measurementDto);

    List<Measurement> getSensorMeasurements(UUID key);

    List<Measurement> getCurrentMeasurementFromSensor();

}