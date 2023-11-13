package com.example.server_weather.exeption;


public class MeasurementValidationException extends RuntimeException{

    public MeasurementValidationException(String message) {
        super(message);
    }
}
