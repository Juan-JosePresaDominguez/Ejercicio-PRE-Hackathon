package com.banana.proyectostareas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@data - Generará automáticamente los métodos equals, hashCode, toString y getters/setters para todas las propiedades de la clase.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proyectos")
@Schema(name = "Proyecto", description = "Modelo proyecto")

public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @Schema(name = "Proyecto ID", example = "1", required = false)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(name = "Proyecto nombre", example = "Proyecto X", required = true)
    private String nombre;

    @DateTimeFormat
    @NotNull
    @Schema(name = "Proyecto fecha creación", example = "AAAA-MM-DD", required = true)
    private LocalDate fechaCreacion;

    //@Transient
    /* Si descomentamos @JsonIgnore, se embucla cuando ejecutamos getProyectos, por ejemplo. HTTP 500 */
    @JsonIgnore // Ignora campo/excluye propiedad en JSON de las peticiones RESTer
    //@OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "miProyecto")
    // Cuando un Proyecto se carga, ¿buscamos las Tareas? Si es así, es una carga Eager, si no, es una carga Lazy (diferida).
    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "miProyecto")
    @ToString.Exclude   //Evitar bucles infinitos
    @Schema(name = "Proyecto tareas", type = "List<Tarea>", required = false)
    private List<Tarea> tareas = new ArrayList<>();
}
