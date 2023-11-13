package com.example.server_weather.controller;

import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    @Autowired
    private  MeasurementService measurementService;

    @PostMapping("/{key}/measurements")
    public ResponseEntity<?> addMeasurement(@PathVariable UUID key, @PathVariable double value,
                                            @PathVariable boolean raining) {
        try {
            measurementService.addMeasurement(key, value, raining);
            return ResponseEntity.ok("Measurement added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/{key}/measurements")
    public ResponseEntity<List<Measurement>> getSensorMeasurements(@PathVariable String key) {
        List<Measurement> sensorMeasurements = measurementService.getSensorMeasurements(key);
        return ResponseEntity.ok(sensorMeasurements);
    }
}