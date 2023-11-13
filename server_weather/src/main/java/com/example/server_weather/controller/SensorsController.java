package com.example.server_weather.controller;

import com.example.server_weather.dto.RegistrationResponse;
import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.service.MeasurementService;
import com.example.server_weather.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    @Autowired
    private SensorService sensorService;
    @Autowired
    private MeasurementService measurementService;

    //(value = "/registration")
    @PostMapping("registration")
    public ResponseEntity<RegistrationResponse> registerSensor(@RequestBody SensorRegistrationDto sensorDTO) {
        try {
            return new ResponseEntity<>(sensorService.saveSensorToServer(sensorDTO), HttpStatus.CREATED);
        } catch (SensorRegistrationException e) {
            return new ResponseEntity<>(sensorService.saveSensorToServer(sensorDTO), HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping
//    public ResponseEntity<List<Sensor>> getAllActiveSensors() {
//        List<Sensor> activeSensors = sensorService.getAllActiveSensors();
//        return ResponseEntity.ok(activeSensors);
//    }
}
