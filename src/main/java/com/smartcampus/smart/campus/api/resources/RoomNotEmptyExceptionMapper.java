package com.smartcampus.smart.campus.api.resources;

import com.smartcampus.smart.campus.api.RoomNotEmptyException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {
    @Override
    public Response toResponse(RoomNotEmptyException e) {
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
    }
}