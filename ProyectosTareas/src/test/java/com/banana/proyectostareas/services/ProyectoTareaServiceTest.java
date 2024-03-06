package com.banana.proyectostareas.services;

import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.model.Tarea;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/*@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ComponentScan(basePackages = {"com.banana.proyectostareas.services"})
@AutoConfigureTestEntityManager*/
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProyectoTareaServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoTareaServiceTest.class);

    @Autowired
    private IProyectoTareaService repoServicio;

//    @Autowired
//    private IProyectoJPARepository proyectoRepository;
//
//    @Autowired
//    private TareaJPARepositoryData tareaRepository;

    @Test
    @Order(1)
    void testBeans() {
        MatcherAssert.assertThat(repoServicio, notNullValue());
    }

    @Test
    @Order(2)
    void crearProyecto() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    @Order(3)
    void crearTareaEnProyecto() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    @Order(4)
    void anadeTareaAProyecto() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    @Order(5)
    void obtenerProyectos() {
        //Dado (Given)
        Proyecto unProyecto1 = new Proyecto(null, "Proyecto 1", LocalDate.now(), null);
        Proyecto unProyecto2 = new Proyecto(null, "Proyecto 2", LocalDate.now(), null);
        //Cuando (When)
        List<Proyecto> proyectos = repoServicio.obtenerProyectos();
        logger.info("Proyectos: " + proyectos + "(size = " + proyectos.size() + ")");
        //Entonces (Then)
        assertThat(proyectos.size()).isGreaterThan(0);
        assertNotNull(proyectos);
    }

    @Test
    @Order(6)
    void obtenerTareasDelProyecto() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    @Order(6)
    void marcarTareaHecha() {
        //Dado (Given)
        Tarea unaTarea = new Tarea(2L, "Tarea Proyecto 1", LocalDate.now(), 1, FALSE, null);
        //Cuando (When)
        Tarea tarea = repoServicio.marcarTareaHecha(unaTarea.getId());
        logger.info("Tarea: " + tarea);
        //Entonces (Then)
        assertTrue(tarea.isCompletada());
        assertNotNull(tarea);
    }
}