package com.example.server_weather.service.impl;

import com.example.server_weather.model.entity.Sensor;
import com.example.server_weather.repository.MeasurementRepository;
import com.example.server_weather.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ActivityCheckingServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private ActivityCheckingService activityCheckingService;

    @Test
    void testScheduleFixedRateTask() {
        Sensor testSensor = new Sensor();
        testSensor.setActive(true);
        when(measurementRepository.findWeatherSensorWitchNeedDeactivated(any(LocalDateTime.class)))
                .thenReturn(Collections.singleton(testSensor));
        activityCheckingService.scheduleFixedRateTask();
        verify(measurementRepository, times(1)).findWeatherSensorWitchNeedDeactivated(any(LocalDateTime.class));
        verify(sensorRepository, times(1)).save(testSensor);
        assertFalse(testSensor.isActive());
    }
}
