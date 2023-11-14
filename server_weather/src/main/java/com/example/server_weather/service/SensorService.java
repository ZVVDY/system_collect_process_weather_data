package com.example.server_weather.service;
import com.example.server_weather.dto.SensorRegistrationDto;

import java.util.UUID;

public interface SensorService {
//        List<Sensor> getAllActiveSensors();
UUID saveSensorToServer(SensorRegistrationDto dto);
        //public boolean isSensorActive(Long id);
        //void sendDataToServer();
    }

