
package com.smartcampus.smart.campus.api.resources;

import com.smartcampus.smart.campus.api.RoomNotEmptyException;
import com.smartcampus.smart.campus.api.Room;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/rooms")
public class RoomResource {

    private static Map<String, Room> rooms = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRooms() {
        List<Room> roomList = new ArrayList<>(rooms.values());
        return Response.ok(roomList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {
        if (rooms.containsKey(room.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Room already exists\"}").build();
        }
        rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}").build();
        }
        return Response.ok(room).build();
    }

  @DELETE
@Path("/{roomId}")
@Produces(MediaType.APPLICATION_JSON)
public Response deleteRoom(@PathParam("roomId") String roomId) {
    Room room = rooms.get(roomId);
    if (room == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Room not found\"}").build();
    }
    if (!room.getSensorIds().isEmpty()) {
        throw new RoomNotEmptyException(roomId);
    }
    rooms.remove(roomId);
    return Response.ok("{\"message\":\"Room deleted successfully\"}").build();
}

    public static Map<String, Room> getRooms() {
        return rooms;
    }
}