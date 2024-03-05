package com.banana.proyectostareas.persistence;

import com.banana.proyectostareas.model.Proyecto;

import java.util.List;

public interface IProyectoJPARepository {

    public List<Proyecto> findAll() throws RuntimeException;

    public Proyecto findById(Long id) throws RuntimeException;

    public Proyecto save(Proyecto proyecto) throws RuntimeException;

    public Proyecto update(Proyecto proyecto) throws RuntimeException;

    public void delete(Proyecto proyecto) throws RuntimeException;

}
