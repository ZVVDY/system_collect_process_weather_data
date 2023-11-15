package com.example.server_weather.controller;

import com.example.server_weather.dto.RegistrationSensorResponseDto;
import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.service.MeasurementService;
import com.example.server_weather.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@Tag(name = "Контроллер сенсора", description = "Позволяет зарегистрировать сенсор и получить уникальный ключ, " +
        "а так же вернуть все активные сенсоры на пользовательский интерфейс")
public class SensorsController {

    @Autowired
    private SensorService sensorService;
    @Operation(
            summary = "Прием данных для регистрации сенсора",
            description = "Позволяет зарегистрировать сенсор на сервере"
    )
    @PostMapping("registration")
    public ResponseEntity<RegistrationSensorResponseDto> registerSensor(@Valid @RequestBody @Parameter(description = "Имя сенсора" +
            " для регистрации") SensorRegistrationDto sensorDTO) {
        try {
           RegistrationSensorResponseDto response = new RegistrationSensorResponseDto();
            response.setSensorKey(sensorService.saveSensorToServer(sensorDTO));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (SensorRegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @Operation(
            summary = "Отправка  данных об активных сенсорах",
            description = "Позволяет отправить данные с сервера на сторону пользователя"
    )
    @GetMapping
    public ResponseEntity<List<Sensor>> getAllActiveSensors() {
        List<Sensor> activeSensors = sensorService.getAllActiveSensors();
        return ResponseEntity.ok(activeSensors);
    }
}
