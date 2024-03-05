package com.banana.proyectostareas.service;

import com.banana.proyectostareas.exception.ProyectoNotFoundException;
import com.banana.proyectostareas.exception.TareaNotFoundException;
import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.model.Tarea;

import java.util.List;

public interface IProyectoTareaService {

    public Proyecto crearProyecto(Proyecto proyecto) throws RuntimeException;

    public Tarea crearTareaEnProyecto(Tarea tarea, Long idProyecto) throws RuntimeException;

    public Proyecto anadeTareaAProyecto(Long idProyecto, Tarea tarea) throws ProyectoNotFoundException, RuntimeException;

    public List<Proyecto> obtenerProyectos() throws ProyectoNotFoundException, RuntimeException;

    public List<Tarea> obtenerTareasDelProyecto(Long idProyecto) throws ProyectoNotFoundException, RuntimeException;

    public Tarea marcarTareaHecha(Long idTarea) throws TareaNotFoundException, RuntimeException;

}
