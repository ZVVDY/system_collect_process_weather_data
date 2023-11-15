package com.example.server_weather.controller;

import com.example.server_weather.dto.RegistrationSensorResponseDto;
import com.example.server_weather.dto.SensorRegistrationDto;
import com.example.server_weather.exeption.SensorRegistrationException;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.service.SensorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorsControllerTest {

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorsController sensorsController;

    @Test
    void testRegisterSensorSuccess() throws SensorRegistrationException {
        SensorRegistrationDto sensorDTO = new SensorRegistrationDto();
        UUID mockedSensorKey = UUID.randomUUID();
        when(sensorService.saveSensorToServer(any(SensorRegistrationDto.class)))
                .thenReturn(mockedSensorKey);
        ResponseEntity<RegistrationSensorResponseDto> responseEntity = sensorsController.registerSensor(sensorDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        String expectedSensorKey = mockedSensorKey.toString();
        UUID getKey = responseEntity.getBody().getSensorKey();
        String key = getKey.toString();
        assertEquals(expectedSensorKey, key);
    }

    @Test
    void testRegisterSensorBadRequest() throws SensorRegistrationException {
        SensorRegistrationDto sensorDTO = new SensorRegistrationDto();
        doThrow(new SensorRegistrationException("Registration failed"))
                .when(sensorService).saveSensorToServer(any(SensorRegistrationDto.class));
        ResponseEntity<RegistrationSensorResponseDto> responseEntity = sensorsController.registerSensor(sensorDTO);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void testGetAllActiveSensors() {
        List<Sensor> expectedSensors = Collections.singletonList(new Sensor());
        when(sensorService.getAllActiveSensors()).thenReturn(expectedSensors);
        ResponseEntity<List<Sensor>> responseEntity = sensorsController.getAllActiveSensors();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedSensors, responseEntity.getBody());
    }
}
