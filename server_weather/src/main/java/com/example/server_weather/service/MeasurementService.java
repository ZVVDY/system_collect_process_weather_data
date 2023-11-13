package com.example.server_weather.service;

import com.example.server_weather.model.entity.Measurement;

import java.util.List;
import java.util.UUID;

public interface MeasurementService {

   void addMeasurement(UUID key, double value, boolean raining);

    List<Measurement> getSensorMeasurements(String key);

}