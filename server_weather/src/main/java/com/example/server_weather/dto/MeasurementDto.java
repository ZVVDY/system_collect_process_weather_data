package com.example.server_weather.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@Data
@Schema(description = "Сущность показаний погоды")
public class MeasurementDto {
    @Schema(description = "Значение температуры")
    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "-100", message = "Value must be greater than or equal to -100")
    @DecimalMax(value = "100", message = "Value must be less than or equal to 100")
    private double value;
    @Schema(description = "Значение наличия дождя")
    @NotNull(message = "Raining cannot be null")
    private boolean raining;
}
