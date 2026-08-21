package com.emmahare.designdesk.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long id) {
        super("Project with ID " + id + " wad not found.");
    }
}
