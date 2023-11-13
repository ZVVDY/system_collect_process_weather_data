package com.example.server_weather.service.impl;


import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.dto.RegistrationResponse;
import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.SensorRepository;
import com.example.server_weather.service.MeasurementService;
import com.example.server_weather.service.SensorService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Setter
@Getter
@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    private SensorService sensorService;

    private MeasurementService measurementService;

    private boolean activated;

    public RegistrationResponse saveSensorToServer(SensorRegistrationDto dto) {
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
        UUID uuid = UUID.randomUUID();
        sensor.setSensorKey(uuid);
        sensorRepository.save(sensor);
        return new RegistrationResponse(sensor.getSensorKey());
    }

//    @Override
//    public List<Sensor> getAllActiveSensors() {
//        return null;
//    }

//    @Override
//    public boolean isSensorActive(Long id) {
//        try {
//            Sensor sensor = sensorRepository.getReferenceById(id);
//
//            sensor.setActive(sensorRepository.existsByActive());
///**
// *
// */
//            if (true){
//                return true;
//            }
//            else {
//                return false;
//
//
//            }
//
//        }
//        catch (Exception e){
//
//        }
//
//        return false;
//    }


}
