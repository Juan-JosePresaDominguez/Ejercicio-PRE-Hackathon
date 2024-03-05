package com.banana.proyectostareas.persistence;

import com.banana.proyectostareas.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProyectoJPARepositoryData extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByNombreContaining(String name);

    @Query("select p from Proyecto p where p.nombre = ?1")
    Proyecto findByNombre(String name);

}
