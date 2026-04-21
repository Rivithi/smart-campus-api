package com.smartcampus.smart.campus.api.resources;

import com.smartcampus.smart.campus.api.LinkedResourceNotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {
    @Override
    public Response toResponse(LinkedResourceNotFoundException e) {
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity("{\"error\": \"" + e.getMessage() + "\"}")
                .build();
    }
}