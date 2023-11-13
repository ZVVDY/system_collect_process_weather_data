package com.example.server_weather.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class RegistrationResponse {

    private UUID sensorKey;

    public RegistrationResponse(UUID sensorKey){
        this.sensorKey = sensorKey;
    }

    public RegistrationResponse(){

    }
}
