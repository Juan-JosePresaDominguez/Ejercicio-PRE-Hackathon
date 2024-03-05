package com.banana.proyectostareas.exception;

public class TareaNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TareaNotFoundException() {
        super("Task not found");
    }

    public TareaNotFoundException(Long taskId) {
        super("Task with id: " + taskId + " not found");
    }

    public TareaNotFoundException(String message) {
        super(message);
    }
}
