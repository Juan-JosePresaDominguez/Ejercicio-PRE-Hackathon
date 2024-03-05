package com.banana.proyectostareas.controller;

import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.persistence.ProyectoJPARepository;
import com.banana.proyectostareas.util.JsonUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource( locations = "classpath:application-integrationtest.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProyectoServiceControllerTest_MockMvc {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProyectoJPARepository repository;

    @Test
    @Order(1)
    public void givenProyectos_whenGetProyectos_thenStatus200() throws Exception {
        Proyecto nuevoProyecto = new Proyecto(null, "Nuevo proyecto", LocalDate.now(), null);
        repository.save(nuevoProyecto);

        // Petición GET al endpoint /proyectos
        mvc.perform(get("/proyectos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // .andExpect(jsonPath("$[5].name", is("Nuevo proyecto")));
                .andExpect(jsonPath("$[*].nombre", hasItem("Nuevo proyecto")));
                // En lenguaje jsonPatch $ es el objeto que he recibido. Para cualquiera de los elementos del array que he recibido, traigo el nombre de cualquiera de esos elementos y verifico que alguno de ellos tenga el texto "Nuevo proyecto".
    }

    @Test
    @Order(2)
    void givenProyectos_whenValidCreateProyecto_thenIsCreatedAndHaveId() throws Exception {
        Proyecto newProyecto = new Proyecto(null, "Proyecto 2", LocalDate.now(), null);
        // Proyecto newProyecto = repository.findById(2L);
        // Si ponemos un @JsonIgnore en fechaCreacion, da error de validación 412 - "fechaCreacion": "no debe ser nulo"

        mvc.perform(post("/proyectos")
                        .content(JsonUtil.asJsonString(newProyecto)) // ERROR: LocalDate vs. JsonUtil
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                //.andExpect(status().isOk()) // Daría una situación errónea (código de estado esperado es 201, no 200.
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id", is(greaterThanOrEqualTo(1))));
    }

    @Test
    @Order(3)
    void givenProyectos_whenInValidCreateProyecto_thenPreconditionFailed() throws Exception {
        Proyecto newProyecto = new Proyecto(null, "Px", LocalDate.now(), null);
        // Poner un @JsonIgnore en fechaCreacion, porque sino dará un error

        // En este test no hay excepción sino código de respuesta (exception handler 412 o si no, 500)
        mvc.perform(post("/proyectos")
                        .content(JsonUtil.asJsonString(newProyecto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                //.andExpect(status().isBadRequest()); // HTTP 400 - Bad Request (Petición incorrecta)
                .andExpect(status().isPreconditionFailed()); // HTTP 412 - Precondition Failed
    }

}