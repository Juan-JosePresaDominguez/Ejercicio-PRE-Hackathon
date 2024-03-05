package com.banana.proyectostareas.exception;

public class ProyectoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProyectoNotFoundException() {
        super("Project not found");
    }

    public ProyectoNotFoundException(Long proyectId) {
        super("Project with id: " + proyectId + " not found");
    }


    public ProyectoNotFoundException(String message) {
        super(message);
    }
}
