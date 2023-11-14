package com.example.server_weather.controller;

import com.example.server_weather.dto.RegistrationSensorResponseDto;
import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.service.MeasurementService;
import com.example.server_weather.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    @Autowired
    private SensorService sensorService;

    @PostMapping("registration")
    public ResponseEntity<RegistrationSensorResponseDto> registerSensor(@Valid @RequestBody SensorRegistrationDto sensorDTO) {
        try {
           RegistrationSensorResponseDto response = new RegistrationSensorResponseDto();
            response.setSensorKey(sensorService.saveSensorToServer(sensorDTO));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (SensorRegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @GetMapping
    public ResponseEntity<List<Sensor>> getAllActiveSensors() {
        List<Sensor> activeSensors = sensorService.getAllActiveSensors();
        return ResponseEntity.ok(activeSensors);
    }
}
