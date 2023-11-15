package com.example.sensor_weather.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Schema(description = "Сущность уникального ключа")
public class RegistrationSensorResponseDto {
    @Schema(description = "Значение уникального ключа")
    private UUID sensorKey;
}
