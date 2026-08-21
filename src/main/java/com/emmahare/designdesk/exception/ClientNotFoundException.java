package com.emmahare.designdesk.exception;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(Long id) {
        super("Client with ID " + id + " was not found.");
    }
}
