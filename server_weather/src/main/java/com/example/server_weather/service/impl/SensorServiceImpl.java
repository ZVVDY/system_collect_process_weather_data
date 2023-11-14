package com.example.server_weather.service.impl;


import com.example.server_weather.dto.RegistrationSensorResponseDto;
import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.SensorRepository;
import com.example.server_weather.service.SensorService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public UUID saveSensorToServer(SensorRegistrationDto dto) {
        String name = dto.getName();
        if (name == null || name.length() < 3 || name.length() > 30) {
            throw new SensorRegistrationException("Sensor name should be between 3 and 30 characters");
        }
        if (sensorRepository.existsByName(name)) {
            throw new SensorRegistrationException("Sensor with this name already exists");
        }
        Sensor sensor = new Sensor();
        sensor.setName(name);
        sensor.setActive(true);
        sensorRepository.save(sensor);
        RegistrationSensorResponseDto response = new RegistrationSensorResponseDto();
        response.setSensorKey(sensor.getSensorKey());
        return response.getSensorKey();

    }

    @Override
    public List<Sensor> getAllActiveSensors() {
        return sensorRepository.findByActiveIsTrue();
    }

}
