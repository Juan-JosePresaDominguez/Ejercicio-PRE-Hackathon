package com.banana.proyectostareas.controller;

import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.model.Tarea;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.Assertions.assertThat;

// >>> No se suele realizar este test, porque es redundante respecto al test web MVC.
@SpringBootTest
@Sql(value = "classpath:testing.sql")
//@Sql(value = "classpath:testing_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProyectoServiceControllerTest {

    @Autowired
    private ProyectoServiceController controllerProyecto;        // Bean @RestController

    @Test
    @Order(1)
    void createProyecto() {
        //Dado (Given)
        Proyecto unProyecto = new Proyecto(null, "Proyecto 1", LocalDate.now(), null);
        //Cuando (When)
        ResponseEntity<Proyecto> response = controllerProyecto.createProyecto(unProyecto);
        System.out.println("***** response:" + response);
        //Entonces (Then)
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getBody().getId()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    void givenProyectos_whenInVaildCreateProyecto_thenException() {
        //Dado (Given)
        Proyecto newProyecto = new Proyecto(null, "Px", LocalDate.now(), null);
        //Cuando (When)
        //Entonces (Then)
        Assertions.assertThrows(RuntimeException.class, () -> {
            ResponseEntity<Proyecto> response = controllerProyecto.createProyecto(newProyecto);
        });
    }

    @Test
    @Transactional
    @Order(2)
    void addTareaAProyecto() {
        //Dado (Given)
        Tarea unaTarea = new Tarea(2L, "Tarea Proyecto 1", LocalDate.now(), 1, FALSE, null);
        //Cuando (When)
        ResponseEntity<Proyecto> response = controllerProyecto.addTareaAProyecto(2L, unaTarea);
        System.out.println("***** response:" + response);
        //Entonces (Then)
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    @Test
    @Order(3)
    void getProyectos() {
        //Dado (Given)
        ResponseEntity<List<Proyecto>> response = controllerProyecto.getProyectos();
        System.out.println("***** response:" + response.getBody());
        //Cuando (When)
        //Entonces (Then)
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isNotNull();
    }
}