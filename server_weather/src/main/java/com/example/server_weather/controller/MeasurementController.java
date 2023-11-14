package com.example.server_weather.controller;

import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.exeption.MeasurementValidationException;
import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.service.MeasurementService;
import jakarta.validation.Valid;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sensors")
public class MeasurementController {
    @Autowired
    private  MeasurementService measurementService;

    @PostMapping("{key}/measurements")
    public ResponseEntity addMeasurement(@PathVariable (name = "key") UUID sensorKey, @Valid @RequestBody MeasurementDto measurementDto) {
        try {
            UUID uuid = sensorKey;
            measurementService.addMeasurement(uuid,measurementDto);
            return ResponseEntity.status(HttpStatus.OK).body(measurementDto);
        } catch (MeasurementValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(measurementDto);
        }
    }
    @GetMapping("{key}/measurements")
    public ResponseEntity<List<Measurement>> getSensorMeasurements(@PathVariable UUID key) {
        List<Measurement> sensorMeasurements = measurementService.getSensorMeasurements(key);
        return ResponseEntity.ok(sensorMeasurements);
    }


}