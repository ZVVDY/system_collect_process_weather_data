package com.example.server_weather.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

    @Entity
    @Setter
    @Getter
    @Table
    public class Sensor {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID sensorKey;

        @Column(unique = true)
        private String name;

        private boolean active;
    }

