package com.example.server_weather.service.impl;


import com.example.server_weather.dto.MeasurementDto;
import com.example.server_weather.model.entity.Measurement;
import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.MeasurementRepository;
import com.example.server_weather.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private MeasurementServiceImpl measurementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddMeasurement() {
        UUID sensorKey = UUID.randomUUID();
        MeasurementDto measurementDto = new MeasurementDto();
        measurementDto.setValue(50);
        measurementDto.setRaining(false);

        Sensor sensor = new Sensor();
        sensor.setActive(false);

        when(sensorRepository.findById(sensorKey)).thenReturn(Optional.of(sensor));
        when(measurementRepository.save(any(Measurement.class))).thenReturn(new Measurement());

        Measurement result = measurementService.addMeasurement(sensorKey, measurementDto);

        assertNotNull(result);
        assertTrue(sensor.isActive());
        verify(sensorRepository, times(1)).save(sensor);
        verify(measurementRepository, times(1)).save(any(Measurement.class));
    }

    @Test
    void testGetSensorMeasurements() {
        UUID sensorKey = UUID.randomUUID();
        Sensor sensor = new Sensor();

        when(sensorRepository.findById(sensorKey)).thenReturn(Optional.of(sensor));
        when(measurementRepository.findMeasurementBySensor(sensor)).thenReturn(Collections.emptyList());

        List<Measurement> result = measurementService.getSensorMeasurements(sensorKey);

        assertNotNull(result);
        verify(sensorRepository, times(1)).findById(sensorKey);
        verify(measurementRepository, times(1)).findMeasurementBySensor(sensor);
    }

    @Test
    void testGetCurrentMeasurementFromSensor() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime oneMinuteAgo = currentTime.minusMinutes(1);

        when(measurementRepository.findMeasurementLaterDateTime(
                ArgumentMatchers.argThat(arg -> arg.isAfter(oneMinuteAgo))))
                .thenReturn(Collections.emptyList());

        List<Measurement> result = measurementService.getCurrentMeasurementFromSensor();

        assertNotNull(result);
        verify(measurementRepository, times(1)).findMeasurementLaterDateTime(
                ArgumentMatchers.argThat(arg -> arg.isAfter(oneMinuteAgo)));
    }
}