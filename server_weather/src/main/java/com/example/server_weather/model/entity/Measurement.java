package com.example.server_weather.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table
@Schema(description = "Сущность погоды")
public class Measurement {
    @Schema(description = "Идентификатор")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Сущность сенсора")
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;
    @Schema(description = "Значение температуры")
    private double value;
    @Schema(description = "Значение наличия дождя")
    private boolean raining;
    @Schema(description = "Значение текущего мремени измерения")
    private LocalDateTime timestamp;
}
