package com.example.server_weather.service;
import com.example.server_weather.dto.RegistrationResponse;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.dto.SensorRegistrationDto;

import java.util.List;

public interface SensorService {
//        List<Sensor> getAllActiveSensors();
RegistrationResponse saveSensorToServer(SensorRegistrationDto dto);
        //public boolean isSensorActive(Long id);
        //void sendDataToServer();
    }

