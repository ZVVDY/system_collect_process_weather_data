package com.example.sensor_weather.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class MeasurementDto {
    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "-100", message = "Value must be greater than or equal to -100")
    @DecimalMax(value = "100", message = "Value must be less than or equal to 100")
    private double value;

    @NotNull(message = "Raining cannot be null")
    private boolean raining;
}
