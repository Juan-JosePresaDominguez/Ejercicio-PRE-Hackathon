package com.banana.proyectostareas.persistence;

import com.banana.proyectostareas.exception.ProyectoNotFoundException;
import com.banana.proyectostareas.model.Proyecto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProyectoJPARepository implements IProyectoJPARepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Proyecto> findAll() throws RuntimeException {
        Query query = em.createQuery("SELECT p FROM Proyecto p");
        return (List<Proyecto>) query.getResultList();
    }

    @Override
    public Proyecto findById(Long id) throws RuntimeException {
//        Query query = em.createQuery("SELECT p FROM Proyecto p WHERE p.id = id");
//        return (Proyecto) query.getSingleResult();
        try {
            if (em.find(Proyecto.class, id) == null) {
                throw new ProyectoNotFoundException(id);
            } else {
                return em.find(Proyecto.class, id);
            }
        } catch (RuntimeException e) {
            //e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public Proyecto save(Proyecto unProyecto) throws RuntimeException {
        em.persist(unProyecto);
        return unProyecto;
    }

    @Override
    public Proyecto update(Proyecto proyecto) throws RuntimeException {
        try {
            Proyecto proyectoActual = em.find(Proyecto.class, proyecto.getId());
            proyectoActual.setId(proyecto.getId());
            proyectoActual.setNombre(proyecto.getNombre());
            proyectoActual.setFechaCreacion(proyecto.getFechaCreacion());
            //proyectoActual.setTareas(proyecto.getTareas());
            return proyectoActual;
        } catch (ProyectoNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Proyecto proyecto) throws RuntimeException {
        em.remove(proyecto);
    }
}
