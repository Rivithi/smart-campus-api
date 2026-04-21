package com.smartcampus.smart.campus.api.resources;

import com.smartcampus.smart.campus.api.SensorUnavailableException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException> {
    @Override
    public Response toResponse(SensorUnavailableException e) {
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
    }
}
