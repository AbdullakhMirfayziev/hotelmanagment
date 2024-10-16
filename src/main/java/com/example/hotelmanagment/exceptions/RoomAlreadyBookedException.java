package com.example.hotelmanagment.exceptions;

public class RoomAlreadyBookedException extends RuntimeException{
    public RoomAlreadyBookedException() {super();}

    public RoomAlreadyBookedException(String message) {super(message);}
}
