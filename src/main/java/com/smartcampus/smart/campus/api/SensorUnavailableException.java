package com.smartcampus.smart.campus.api;

public class SensorUnavailableException extends RuntimeException {
    public SensorUnavailableException(String sensorId) {
        super("Sensor " + sensorId + " is under maintenance and cannot accept readings.");
    }
}
