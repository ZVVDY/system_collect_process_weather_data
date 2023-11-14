package com.example.server_weather.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegistrationSensorResponseDto {
    private UUID sensorKey;
}
