package com.banana.proyectostareas.persistence;

import com.banana.proyectostareas.model.Tarea;

import java.util.List;

public interface ITareaJPARepository {

    public List<Tarea> findByProject(Long idProyecto) throws RuntimeException;

    public Tarea update(Tarea tarea) throws RuntimeException;
}
