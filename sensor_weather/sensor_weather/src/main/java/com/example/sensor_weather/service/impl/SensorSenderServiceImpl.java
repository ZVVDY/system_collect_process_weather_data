package com.example.sensor_weather.service.impl;

import com.example.sensor_weather.dto.MeasurementDto;
import com.example.sensor_weather.dto.RegistrationSensorResponseDto;
import com.example.sensor_weather.dto.SensorRegistrationDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.sensor_weather.conf.SensorNameConst.NAME_SENSOR_1;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class SensorSenderServiceImpl {

    private UUID sensorKey;

    private String serverIP = "http://localhost:8081";

    private Random random = new Random();

    @PostConstruct
    public void registerSensor() {
        SensorRegistrationDto sensorRegistrationDto = new SensorRegistrationDto();
        sensorRegistrationDto.setName(NAME_SENSOR_1);

        if (sensorRegistrationDto.getName().length() < 3 || sensorRegistrationDto.getName().length() > 30) {
            System.out.println("Error: Sensor name should be between 3 and 30 characters.");
            return;
        }
        String serverUrl = serverIP + "/sensors/registration";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            HttpEntity<SensorRegistrationDto> entity = new HttpEntity<>(sensorRegistrationDto, headers);
            ResponseEntity<RegistrationSensorResponseDto> response = restTemplate.postForEntity(serverUrl, entity, RegistrationSensorResponseDto.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                RegistrationSensorResponseDto responseBody = response.getBody();
                sensorKey = responseBody.getSensorKey();
                System.out.println("Sensor registered. Key: " + sensorKey);
            } else {
                System.out.println("Failed to register sensor.");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                System.out.println("Failed to register sensor: Bad Request.");
            } else {
                System.out.println("Failed to register sensor: Server Error.");
            }
        }
    }

    private void sendWeatherData() {
        if (sensorKey != null) {
            RestTemplate restTemplate = new RestTemplate();
            String serverUrl = serverIP + "/sensors/" + sensorKey + "/measurements";

            double temperature = generateRandomTemperature();
            boolean raining = generateRandomRaining();
            MeasurementDto measurementDto = new MeasurementDto();
            measurementDto.setValue(temperature);
            measurementDto.setRaining(raining);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            try {
                HttpEntity<MeasurementDto> entity = new HttpEntity<>(measurementDto, headers);
                ResponseEntity<MeasurementDto> response = restTemplate.postForEntity(serverUrl, entity, MeasurementDto.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Data sent to server: " + measurementDto);
                } else {
                    System.out.println("Failed to send data to the server.");
                }
            } catch (HttpServerErrorException e) {
                throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
            }

        }
    }

    @PostConstruct
    public void simulateSensorData() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::sendWeatherData, 0, getRandomInterval(), TimeUnit.SECONDS);
    }

    private int getRandomInterval() {
        return random.nextInt(12) + 3;
    }

    private double generateRandomTemperature() {
        return Math.random() * 200 - 100;
    }

    private boolean generateRandomRaining() {
        return Math.random() < 0.5;
    }
}
