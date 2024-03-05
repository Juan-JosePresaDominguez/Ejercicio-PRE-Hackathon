package com.banana.proyectostareas.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
//@Sql(value = "classpath:testing.sql")
//@Sql(value = "classpath:testing_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TareaServiceControllerTest {

    @Autowired
    private TareaServiceController controllerTarea;        // Bean @RestController

    @Test
    void createTareaEnProyecto() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    void getTareasProyectoOrdenadas() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    void getTareasProyecto() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    void marcarTareaCompletada() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }
}