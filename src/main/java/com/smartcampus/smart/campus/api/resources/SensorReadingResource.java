package com.smartcampus.smart.campus.api.resources;

import com.smartcampus.smart.campus.api.SensorUnavailableException;
import com.smartcampus.smart.campus.api.Sensor;
import com.smartcampus.smart.campus.api.SensorReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorReadingResource {

    private String sensorId;
    private static Map<String, List<SensorReading>> readingsMap = new HashMap<>();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        List<SensorReading> readings = readingsMap.getOrDefault(sensorId, new ArrayList<>());
        return Response.ok(readings).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Map<String, Sensor> sensors = SensorResource.getSensors();
        Sensor sensor = sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}").build();
        }

        if (sensor.getStatus().equals("MAINTENANCE")) {
    throw new SensorUnavailableException(sensorId);
}

        if (reading.getId() == null) {
            reading.setId(java.util.UUID.randomUUID().toString());
        }
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        readingsMap.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
