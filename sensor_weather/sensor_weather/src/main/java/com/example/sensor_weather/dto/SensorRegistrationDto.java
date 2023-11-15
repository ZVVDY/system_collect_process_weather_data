package com.example.sensor_weather.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Schema(description = "Сущность сенсора для регистрации")
public class SensorRegistrationDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @Schema(description = "Значение имени сенсора")
    private String name;
}
