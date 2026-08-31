package com.emmahare.designdesk.exception;

public class RevisionNotFoundException extends RuntimeException {

    public RevisionNotFoundException(Long id) {
        super("Revision with ID" + id + " was not found.");
    }
}
