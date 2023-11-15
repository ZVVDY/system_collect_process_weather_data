package com.example.server_weather.controller;

import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.exeption.MeasurementValidationException;
import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.service.MeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sensors")
@Tag(name = "Контроллер погоды", description = "Позволяет принять данные с сервера и сохранить их в БД, " +
        "а так же вернуть данные с сенсора на пользовательский интерфейс")
public class MeasurementController {
    @Autowired
    private  MeasurementService measurementService;

    @Operation(
            summary = "Прием данных о погоде, по ключу сенсора",
            description = "Позволяет получить данные с сенсора"
    )
    @PostMapping("{key}/measurements")
    public ResponseEntity addMeasurement(@PathVariable (name = "key")  UUID sensorKey, @Valid @RequestBody @Parameter(description = "Текущая погода " +
            "измеренная сенсором") MeasurementDto measurementDto) {
        try {
            UUID uuid = sensorKey;
            measurementService.addMeasurement(uuid,measurementDto);
            return ResponseEntity.status(HttpStatus.OK).body(measurementDto);
        } catch (MeasurementValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(measurementDto);
        }
    }

    @Operation(
            summary = "Отправка данных о погоде, по ключу сенсора",
            description = "Позволяет вернуть данные с сервера на пользовательский интерфейс"
    )
    @GetMapping("{key}/measurements")
    public ResponseEntity<List<Measurement>> getSensorMeasurements(@PathVariable @Parameter (description = "Уникальный " +
            "ключ сенсора") UUID key) {
        List<Measurement> sensorMeasurements = measurementService.getSensorMeasurements(key);
        return ResponseEntity.ok(sensorMeasurements);
    }


}