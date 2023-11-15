package com.example.server_weather.controller;


import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.exeption.MeasurementValidationException;
import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.service.MeasurementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MeasurementControllerTest {
    @Mock
    private MeasurementService measurementService;

    @InjectMocks
    private MeasurementController measurementController;

    @Test
    void testAddMeasurementSuccess() throws MeasurementValidationException {
        UUID sensorKey = UUID.randomUUID();
        MeasurementDto measurementDto = new MeasurementDto();
        doReturn(UUID.randomUUID()).when(measurementService).addMeasurement(any(UUID.class),
                any(MeasurementDto.class));
        ResponseEntity responseEntity = measurementController.addMeasurement(sensorKey, measurementDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(measurementDto, responseEntity.getBody());
    }

    @Test
    void testAddMeasurementBadRequest() throws MeasurementValidationException {
        UUID sensorKey = UUID.randomUUID();
        MeasurementDto measurementDto = new MeasurementDto();
        doThrow(new MeasurementValidationException("Validation failed"))
                .when(measurementService).addMeasurement(any(UUID.class), any(MeasurementDto.class));
        ResponseEntity responseEntity = measurementController.addMeasurement(sensorKey, measurementDto);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(measurementDto, responseEntity.getBody());
    }

    @Test
    void testGetSensorMeasurements() {
        UUID sensorKey = UUID.randomUUID();
        List<Measurement> expectedMeasurements = Collections.singletonList(new Measurement());
        when(measurementService.getSensorMeasurements(sensorKey)).thenReturn(expectedMeasurements);
        ResponseEntity<List<Measurement>> responseEntity = measurementController.getSensorMeasurements(sensorKey);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedMeasurements, responseEntity.getBody());
    }
}
