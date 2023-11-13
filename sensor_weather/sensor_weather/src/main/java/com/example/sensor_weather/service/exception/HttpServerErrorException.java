package com.example.sensor_weather.service.exception;

public class HttpServerErrorException extends RuntimeException{

    public HttpServerErrorException(String message){
        super(message);
    }
}
