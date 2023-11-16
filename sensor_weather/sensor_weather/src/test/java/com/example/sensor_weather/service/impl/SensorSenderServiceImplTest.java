package com.example.sensor_weather.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SensorSenderServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SensorSenderServiceImpl sensorSenderService;

    @Test
    void testRegisterSensor_Success() {

    }

    @Test
    void testRegisterSensor_Failure() {


    }
}