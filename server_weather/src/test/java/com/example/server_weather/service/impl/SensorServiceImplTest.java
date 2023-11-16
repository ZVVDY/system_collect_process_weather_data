package com.example.server_weather.service.impl;

import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Test
    void testSaveSensorToServerSuccess() {
        SensorRegistrationDto sensorDTO = new SensorRegistrationDto();
        sensorDTO.setName("TestSensor");
        UUID expectedSensorKey = UUID.randomUUID();

        when(sensorRepository.existsByName(sensorDTO.getName())).thenReturn(false);
        when(sensorRepository.save(any(Sensor.class))).thenAnswer(invocation -> {
            Sensor savedSensor = invocation.getArgument(0);
            savedSensor.setSensorKey(expectedSensorKey);
            return savedSensor;
        });
        UUID result = sensorService.saveSensorToServer(sensorDTO);
        assertNotNull(result);
        assertEquals(expectedSensorKey, result);
        verify(sensorRepository, times(1)).existsByName(sensorDTO.getName());
        verify(sensorRepository, times(1)).save(any(Sensor.class));
    }

    @Test
    void testSaveSensorToServerNameTooShort() {
        SensorRegistrationDto sensorDTO = new SensorRegistrationDto();
        sensorDTO.setName("12"); // Name is too short
        assertThrows(SensorRegistrationException.class, () -> sensorService.saveSensorToServer(sensorDTO));
        verify(sensorRepository, never()).existsByName(any());
        verify(sensorRepository, never()).save(any(Sensor.class));
    }

    @Test
    void testSaveSensorToServerNameTooLong() {
        // Arrange
        SensorRegistrationDto sensorDTO = new SensorRegistrationDto();
        sensorDTO.setName("ThisSensorNameIsTooLongAndExceedsTheMaximumLength"); // Name is too long
        assertThrows(SensorRegistrationException.class, () -> sensorService.saveSensorToServer(sensorDTO));
        verify(sensorRepository, never()).existsByName(any());
        verify(sensorRepository, never()).save(any(Sensor.class));
    }

    @Test
    void testSaveSensorToServerDuplicateName() {
        SensorRegistrationDto sensorDTO = new SensorRegistrationDto();
        sensorDTO.setName("TestSensor");
        when(sensorRepository.existsByName(sensorDTO.getName())).thenReturn(true);
        assertThrows(SensorRegistrationException.class, () -> sensorService.saveSensorToServer(sensorDTO));
        verify(sensorRepository, times(1)).existsByName(sensorDTO.getName());
        verify(sensorRepository, never()).save(any(Sensor.class));
    }

    @Test
    void testGetAllActiveSensors() {
        Sensor sensor1 = new Sensor();
        sensor1.setActive(true);
        Sensor sensor2 = new Sensor();
        sensor2.setActive(true);
        List<Sensor> sensorList = new ArrayList<>();
        sensorList.add(sensor1);
        sensorList.add(sensor2);
        when(sensorRepository.findByActiveIsTrue()).thenReturn(sensorList);
        List<Sensor> result = sensorService.getAllActiveSensors();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(sensorRepository, times(1)).findByActiveIsTrue();
    }
}
