package com.smartcampus.smart.campus.api;

public class RoomNotEmptyException extends RuntimeException {
    public RoomNotEmptyException(String roomId) {
        super("Room " + roomId + " cannot be deleted as it still has active sensors.");
    }
}