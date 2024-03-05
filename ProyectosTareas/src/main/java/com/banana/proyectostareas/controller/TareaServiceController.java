package com.banana.proyectostareas.controller;

import com.banana.proyectostareas.exception.ProyectoNotFoundException;
import com.banana.proyectostareas.exception.TareaNotFoundException;
import com.banana.proyectostareas.model.Tarea;
import com.banana.proyectostareas.persistence.ProyectoJPARepository;
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
import java.util.Comparator;
import java.util.List;

/**************************************************************************************************/
/* DOCUMENTACIÓN SWAGGER: http://localhost:9900/swagger-ui/index.html                             */
/* ~~~~~~~~~~~~~~~~~~~~~                                                                          */
/* HISTORIA DE USUARIO 2 >>> Realizada X                                                          */
/* Como usuario anónimo quiero poder añadir tareas a uno de mis proyectos, para tener una lista   */
/* ORDENADA de trabajos.  POST                                                                    */
/*                                                                                                */
/* HISTORIA DE USUARIO 4 >>> Realizada X                                                          */
/* Como usuario anónimo quiero ver la lista "ORDENADA" de tareas de un proyecto, para ejecutarlas */
/* en orden. GET                                                                                  */
/*                                                                                                */
/* HISTORIA DE USUARIO 5 >>> Realizada X                                                          */
/* Como usuario quiero poder marcar una tarea como completada, para olvidarme de ella. PUT        */
/**************************************************************************************************/
@RestController
@RequestMapping("/tareas")
@Validated
@Tag(name = "ProyectosTareasAPI", description = "ProyectosTareas API - TareaServiceController")
public class TareaServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoServiceController.class);

    @Autowired
    private ProyectoTareaService tareaService;

    /*@Autowired
    private TareaJPARepositoryData tareaRepository;*/

    @Autowired
    private ProyectoJPARepository proyectoRepository;

    // Método POST (Crear Proyecto 'createProyecto')
    // HISTORIA DE USUARIO 2
    // Como usuario anónimo quiero poder añadir tareas a uno de mis proyectos, para tener una lista ORDENADA de trabajos.  PUT.
    @Operation(summary = "Create a new Task in project mark by id", description = "Create a new Task in project select by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "404", description = "Not Found - The project was not found"),
            @ApiResponse(responseCode = "412", description = "Precondition Failed"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @PostMapping(value = "{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tarea> createTareaEnProyecto(
            @RequestBody @Valid Tarea newTarea,
            @PathVariable @Min(1) Long id
    ) {
        logger.info("newTarea:" + newTarea);
        return new ResponseEntity<>(tareaService.crearTareaEnProyecto(newTarea, id), HttpStatus.CREATED); // HTTP 201
    }

    // Método GET (Obtener Tareas ORDENADAS 'getTareasProyecto' --> A través de Servicio)
    // HISTORIA DE USUARIO 4
    // Como usuario anónimo quiero ver la lista "ORDENADA" de tareas de un proyecto, para ejecutarlas en orden.
    @GetMapping("/{id}")
    public ResponseEntity<List<Tarea>> getTareasProyectoOrdenadas(@PathVariable @Min(1) Long id) {
        List<Tarea> listaTareas = tareaService.obtenerTareasDelProyecto(id);
        return ResponseEntity.status(HttpStatus.OK).body(listaTareas);
    }

    // Método GET (Obtener Tareas ORDENADAS 'getTareasProyecto' --> A través de Persistencia-Repository)
    // HISTORIA DE USUARIO 4
    // Como usuario anónimo quiero ver la lista "ORDENADA" de tareas de un proyecto, para ejecutarlas en orden.
    // Alternativa menos elegante. Mejor a través del Servicio.
    @GetMapping("/alternativo/{id}")
    public ResponseEntity<List<Tarea>> getTareasProyecto(@PathVariable @Min(1) Long id) {
        try {
            List<Tarea> listaTareas = proyectoRepository.findById(id).getTareas();
            if (listaTareas != null && listaTareas.size() > 0) {
                listaTareas.sort(Comparator.comparing(Tarea::getOrden)); // ORDENACIÓN ASCENDENTE (1-2-3)
                //Collections.reverse(listaTareas); // ORDENACIÓN DESCENDENTE (3-2-1)
                return ResponseEntity.status(HttpStatus.OK).body(listaTareas);
            }
            else throw new TareaNotFoundException("La lista de tareas está vacía [Controller]: " + listaTareas);
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

    // Método PUT (Actualizar marca de tarea completada seleccionada por ID 'marcarTareaCompletada')
    // HISTORIA DE USUARIO 5
    // Como usuario quiero poder marcar una tarea como completada, para olvidarme de ella. PUT
    // En este caso no sería necesario pasar un cuerpo (BODY) a la petición.
    @Operation(summary = "Update task completed mark by id", description = "Update task completed mark selected by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted"),
            @ApiResponse(responseCode = "404", description = "Not Found - The task was not found"),
            @ApiResponse(responseCode = "412", description = "Precondition Failed")
    })
    @PutMapping("/completada/{id}")
    public ResponseEntity<Tarea> marcarTareaCompletada(
            @PathVariable @Min(1) Long id
    ) throws TareaNotFoundException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tareaService.marcarTareaHecha(id)); // HTTP 202
    }
}
