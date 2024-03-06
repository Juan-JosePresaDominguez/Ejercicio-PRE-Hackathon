package com.banana.proyectostareas.controller;

import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.persistence.ProyectoJPARepository;
import com.banana.proyectostareas.persistence.ProyectoJPARepositoryData;
import com.banana.proyectostareas.services.ProyectoTareaService;
import com.banana.proyectostareas.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// No tiene @SpringBootTest --> No carga el contexto de Spring al completo
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProyectoServiceController.class)
//@ActiveProfiles("tes_sin_db")
public class ProyectoServiceControllerTest_WebMvcTest {

    @BeforeEach
    public void setUp() {

        List<Proyecto> proyectos = Arrays.asList(
                new Proyecto(null, "Fake project", LocalDate.now(), null)
        );

        Mockito.when(service.getProyectosByText("Fake"))
                .thenReturn(proyectos);

        Mockito.when(repositoryData.findByNombreContaining("Fake"))
                .thenReturn(proyectos);

        Mockito.when(repository.findAll())
                .thenReturn(proyectos);

        // Devuelvo un objeto concreto de una lista de productos.
        Mockito.when(repository.save(Mockito.any(Proyecto.class)))
                .thenAnswer(elem -> {
                    Proyecto ap = (Proyecto) elem.getArguments()[0];
                    ap.setId(100L);
                    return ap;
                });
    }

    // Seguimos utilizando el bean MockMvc. Por lo tanto, el test es exactamente igual que el test con MockMva 'ProyectoServiceControllerTest_MockMvc.java' solo que esta vez no utilizamos Spring Boot Test
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProyectoTareaService service;

    @MockBean
    private ProyectoJPARepository repository;

    @MockBean
    private ProyectoJPARepositoryData repositoryData;

    @Test
    public void givenProyectos_whenGetProyectos_thenReturnJsonArray() throws Exception {
        Proyecto nuevoProyecto = new Proyecto(null, "Fake project", LocalDate.now(), null);

        List<Proyecto> allProyectos = Arrays.asList(nuevoProyecto);

        given(repository.findAll()).willReturn(allProyectos);
        //given(repositoryData.findAll()).willReturn(allProyectos);

        mvc.perform(get("/proyectos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print()) // Imprime ejecución
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is(nuevoProyecto.getNombre())));
    }

    @Test
    void givenProyectos_whenVaildCreateProyecto_thenIsCreatedAndHaveId() throws Exception {
       // Proyecto newProyecto = new Proyecto(null, "Nuevo producto", "123-123-1234");
        Proyecto newProyecto = new Proyecto(null, "Nuevo proyecto", LocalDate.now(), null);

        mvc.perform(post("/proyectos")
                        .content(JsonUtil.asJsonString(newProyecto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print()) // Imprime ejecución
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(100)));
    }

}
