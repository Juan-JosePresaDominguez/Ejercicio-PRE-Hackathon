package com.banana.proyectostareas.services;

import com.banana.proyectostareas.exception.ProyectoNotFoundException;
import com.banana.proyectostareas.exception.TareaNotFoundException;
import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.model.Tarea;
import com.banana.proyectostareas.persistence.IProyectoJPARepository;
import com.banana.proyectostareas.persistence.ProyectoJPARepositoryData;
import com.banana.proyectostareas.persistence.TareaJPARepositoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Service
public class ProyectoTareaService implements IProyectoTareaService {

    private Logger logger = LoggerFactory.getLogger(ProyectoTareaService.class);

    @Autowired
    private IProyectoJPARepository proyectoRepository;

    @Autowired
    private ProyectoJPARepositoryData proyectoRepositoryData;

    @Autowired
//    private ITareaJPARepository tareaRepository;      // Repositorio JPA
    private TareaJPARepositoryData tareaRepository;     // JpaRepository - Spring Data

    @Override
    public Proyecto crearProyecto(Proyecto proyecto) throws RuntimeException {
        Proyecto nuevoProyecto = new Proyecto();
        nuevoProyecto.setNombre(proyecto.getNombre());
        nuevoProyecto.setFechaCreacion(proyecto.getFechaCreacion());
        //nuevoProyecto.setTareas(proyecto.getTareas());
        return proyectoRepository.save(nuevoProyecto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Tarea crearTareaEnProyecto(Tarea tarea, Long idProyecto) throws RuntimeException {
        Tarea nuevoTarea = new Tarea();
        nuevoTarea.setDescripcion(tarea.getDescripcion());
        nuevoTarea.setFechaLimite(tarea.getFechaLimite());
        nuevoTarea.setOrden(tarea.getOrden());
        nuevoTarea.setCompletada(tarea.isCompletada());
        Proyecto proyecto = proyectoRepository.findById(idProyecto);
        nuevoTarea.setMiProyecto(proyecto);
        return tareaRepository.save(nuevoTarea);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Proyecto anadeTareaAProyecto(Long idProyecto, Tarea tarea) throws ProyectoNotFoundException, RuntimeException {
        try {
            Proyecto proyecto = proyectoRepository.findById(idProyecto);
            tarea.setMiProyecto(proyecto);
            List<Tarea> tareasSort = proyecto.getTareas();
            tareasSort.add(tarea); // Añadimos nueva tarea
            tareasSort.sort(Comparator.comparing(Tarea::getOrden)); // ORDENACIÓN ASCENDENTE (1-2-3)
            System.out.println("tareasSort: "+ tareasSort);
            return proyectoRepository.save(proyecto);
        } catch (ProyectoNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Proyecto> obtenerProyectos() throws ProyectoNotFoundException, RuntimeException {
        return proyectoRepository.findAll();
    }

    @Override
    public List<Tarea> obtenerTareasDelProyecto(Long idProyecto) throws ProyectoNotFoundException, RuntimeException {
        try {
            Proyecto proyecto = proyectoRepository.findById(idProyecto);
            List<Tarea> tareasSort = proyecto.getTareas();
            if (tareasSort != null && tareasSort.size() > 0) {
                tareasSort.sort(Comparator.comparing(Tarea::getOrden)); // ORDENACIÓN ASCENDENTE (1-2-3)
                //Collections.reverse(tareasSort); // ORDENACIÓN DESCENDENTE (3-2-1)
                return tareasSort;
            } else throw new TareaNotFoundException("La lista de tareas está vacía [Service]: " + tareasSort);
        } catch (TareaNotFoundException e) {
            //e.printStackTrace();
            throw e;
        } catch (ProyectoNotFoundException e) {
            //e.printStackTrace();
            throw e;
        } catch (RuntimeException e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public Tarea marcarTareaHecha(Long idTarea) throws TareaNotFoundException, RuntimeException {
        Tarea tareaCompletada = tareaRepository.findById(idTarea).orElseThrow(() -> new TareaNotFoundException(idTarea));
        tareaCompletada.setCompletada(TRUE);
        return tareaRepository.save(tareaCompletada);
    }

    public List<Proyecto> getProyectosByText(String text) {
        return proyectoRepositoryData.findByNombreContaining(text);
    }
}
