/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.smart.campus.api.resources;

import com.smartcampus.smart.campus.api.Room;
import com.smartcampus.smart.campus.api.Sensor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/sensors")
public class SensorResource {

    private static Map<String, Sensor> sensors = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSensors(@QueryParam("type") String type) {
        List<Sensor> sensorList = new ArrayList<>(sensors.values());
        if (type != null) {
            sensorList.removeIf(s -> !s.getType().equalsIgnoreCase(type));
        }
        return Response.ok(sensorList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        Map<String, Room> rooms = RoomResource.getRooms();
        if (!rooms.containsKey(sensor.getRoomId())) {
            return Response.status(422)
                    .entity("{\"error\":\"Room not found with id: " + sensor.getRoomId() + "\"}")
                    .build();
        }
        sensors.put(sensor.getId(), sensor);
        rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensor(@PathParam("sensorId") String sensorId) {
        Sensor sensor = sensors.get(sensorId);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}").build();
        }
        return Response.ok(sensor).build();
    }

    public static Map<String, Sensor> getSensors() {
        return sensors;
    }
}
