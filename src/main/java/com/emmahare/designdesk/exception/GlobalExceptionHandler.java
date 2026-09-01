package com.emmahare.designdesk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles custom application exceptions and displays user-friendly error pages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleClientNotFound(
            ClientNotFoundException exception,
            Model model
    ) {
        model.addAttribute("errorMessage", exception.getMessage());

        return "error/404";
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProjectNotFound(
            ProjectNotFoundException exception,
            Model model
    ) {
        model.addAttribute("errorMessage", exception.getMessage());

        return "error/404";
    }

    @ExceptionHandler(RevisionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRevisionNotFound(
            RevisionNotFoundException exception,
            Model model
    ) {
        model.addAttribute("errorMessage", exception.getMessage());

        return "Error/404";
    }
}
