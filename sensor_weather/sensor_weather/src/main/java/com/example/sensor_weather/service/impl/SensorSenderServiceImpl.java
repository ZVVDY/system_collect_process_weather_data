package com.example.sensor_weather.service.impl;


import com.example.sensor_module.dto.SensorRegistrationDto;
import com.example.sensor_weather.dto.RegistrationSensorResponseDto;
import com.example.sensor_weather.service.SensorSenderService;
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

import static com.example.sensor_module.sensors.SensorNameConst.NAME_SENSOR_1;

@Setter
@Getter
@RequiredArgsConstructor
@Service
public class SensorSenderServiceImpl implements SensorSenderService {

    private SensorSenderService sensorSenderService;
    private double value;
    private boolean raining;
    private boolean activated;
    private UUID sensorKey;

    private String serverIP = "http://localhost:8081";

    private Random random = new Random();

    public SensorSenderServiceImpl(SensorSenderService sensorSenderService) {
        this.sensorSenderService = sensorSenderService;

    }

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

            if (response.getStatusCode() == HttpStatus.OK) {
                RegistrationSensorResponseDto responseBody = response.getBody();
                sensorKey = responseBody.getKey(); // Получаем ключ с сервера
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
        RestTemplate restTemplate = new RestTemplate();
        String serverUrl = serverIP + "/sensors/" + sensorKey + "/measurements";

        double temperature = generateRandomTemperature();
        boolean raining = generateRandomRaining();

        String jsonData = "{ \"value\": " + temperature + ", \"raining\": " + raining + " }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonData, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Data sent to server: " + jsonData);
            } else {
                System.out.println("Failed to send data to the server.");
            }
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }

    }

    @PostConstruct
    public void simulateSensorData() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::sendWeatherData, 0, getRandomInterval(), TimeUnit.SECONDS);
    }

    private int getRandomInterval() {
        return random.nextInt(12) + 3; // Рандомный интервал от 3 до 15
    }


//    public void sendDataToServer() {
//        if (activated) {
//            String serverUrl = serverIP + "/sensors/" + sensorKey + "/measurements";
//
//            double temperatureValue = generateRandomTemperature(); // Генерация случайной температуры
//            boolean isRaining = generateRandomRaining(); // Генерация случайного значения дождя
//
//            // Создание JSON
//            String jsonData = "{ \"value\": " + temperatureValue + ", \"raining\": " + isRaining + " }";
//
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            // Создание запроса
//            HttpEntity<String> entity = new HttpEntity<>(jsonData, headers);
//
//            // Отправка POST-запроса на сервер
//            ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, entity, String.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                System.out.println("Data sent to the server.");
//            } else {
//                System.out.println("Failed to send data to the server.");
//            }
//        } else {
//            System.out.println("Sensor is not activated.");
//        }
//    }

    // Генерация случайного значения температуры
    private double generateRandomTemperature() {
        return Math.random() * 200 - 100; // Возвращает значение в диапазоне от -100 до 100
    }

    // Генерация случайного значения дождя
    private boolean generateRandomRaining() {
        return Math.random() < 0.5; // Возвращает случайное булево значение
    }

    public double getTemperature() {
        if (activated) {
            value = Math.random() * 100;
        } else {
            System.out.println("Sensor is not activated.");
        }
        return value;
    }

    public void activate() {
        this.activated = true;
        System.out.println("Sensor activated.");
    }

    public void deactivate() {
        this.activated = false;
        System.out.println("Sensor deactivated.");
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
