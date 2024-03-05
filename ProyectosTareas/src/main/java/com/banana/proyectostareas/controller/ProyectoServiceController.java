package com.banana.proyectostareas.controller;

import com.banana.proyectostareas.exception.ProyectoNotFoundException;
import com.banana.proyectostareas.model.Proyecto;
import com.banana.proyectostareas.model.Tarea;
import com.banana.proyectostareas.service.ProyectoTareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**************************************************************************************************/
/* DOCUMENTACIÓN SWAGGER: http://localhost:9900/swagger-ui/index.html                             */
/* ~~~~~~~~~~~~~~~~~~~~~                                                                          */
/* HISTORIA DE USUARIO 1 >>> Realizada X                                                          */
/* Como usuario anónimo quiero poder crear proyectos, para poder gestionar una lista asociada de  */
/* tareas. POST                                                                                   */
/*                                                                                                */
/* HISTORIA DE USUARIO 2 >>> Realizada X                                                          */
/* Como usuario anónimo quiero poder añadir tareas a uno de mis proyectos, para tener una lista   */
/* ORDENADA de trabajos.  PUT                                                                     */
/*                                                                                                */
/* HISTORIA DE USUARIO 3 >>> Realizada X                                                          */
/* Como usuario anónimo quiero poder mostrar la lista de mis proyectos, para decidir cuál         */
/* gestionar. GET                                                                                 */
/**************************************************************************************************/
@RestController
@RequestMapping("/proyectos")
@Validated
@Tag(name = "ProyectosTareasAPI", description = "ProyectosTareas API - ProyectoServiceController")
public class ProyectoServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoServiceController.class);

    @Autowired
    private ProyectoTareaService proyectoService;

    // Método POST (Crear Proyecto 'createProyecto')
    // HISTORIA DE USUARIO 1
    // Como usuario anónimo quiero poder crear proyectos, para poder gestionar una lista asociada de tareas.
    @Operation(summary = "Create a new Proyecto client", description = "Create a new Proyecto client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "412", description = "Precondition Failed"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Proyecto> createProyecto(@RequestBody @Valid Proyecto newProyecto) {
        logger.info("newProyecto:" + newProyecto);
        return new ResponseEntity<>(proyectoService.crearProyecto(newProyecto), HttpStatus.CREATED); // HTTP 201
        // Equivalente:
        // return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.crearProyecto(newProyecto)); // HTTP 201
    }

    // Método PUT (Actualizar proyecto añadiendo una tarea nueva 'addTareaAProyecto')
    // HISTORIA DE USUARIO 2
    // Como usuario anónimo quiero poder añadir tareas a uno de mis proyectos, para tener una lista ORDENADA de trabajos.  PUT
    @Operation(summary = "Update add new task to project mark by id", description = "Update add new task to project selected by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "404", description = "Not Found - The task was not found"),
            @ApiResponse(responseCode = "412", description = "Precondition Failed"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> addTareaAProyecto(
            @PathVariable @Min(1) Long id,
            @RequestBody Tarea newTarea // Define el tipo de contenido del cuerpo de la solicitud.
    ) throws ProyectoNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(proyectoService.anadeTareaAProyecto(id, newTarea)); // HTTP 202
    }

    // Método GET (Obtener Proyecto 'getProyectos')
    // HISTORIA DE USUARIO 3
    // Como usuario anónimo quiero poder mostrar la lista de mis proyectos, para decidir cuál gestionar.
    @GetMapping("")
    public ResponseEntity getProyectos() {
        List<Proyecto> listaProyectos = proyectoService.obtenerProyectos();
        if (listaProyectos != null && listaProyectos.size() > 0) return ResponseEntity.status(HttpStatus.OK).body(listaProyectos);
        else throw new ProyectoNotFoundException("La lista de proyectos está vacía: " + listaProyectos);
    }

}
