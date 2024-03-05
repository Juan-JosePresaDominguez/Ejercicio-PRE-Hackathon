package com.banana.proyectostareas.persistence;

import com.banana.proyectostareas.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaJPARepositoryData extends JpaRepository<Tarea, Long> {

    /* Este método está correctamente definido */
    List<Tarea> findAllById(Long idTarea) throws RuntimeException;

    // Este método habría que definirlo con una query nativa o con un nombre que siga la convención de Spring-Data
    // Si no provoca un Exception al compilar.
    // List<Tarea> findByProject(Long idProyecto) throws RuntimeException;
    List<Tarea> findAllByMiProyecto_IdOrderByOrden(Long idProyecto) throws RuntimeException;

    /// Este método no haría falta definirlo/declararlo obligatoriamente, podriamos utilizar el 'save'.
    //Tarea update(Tarea tarea) throws RuntimeException;
}
