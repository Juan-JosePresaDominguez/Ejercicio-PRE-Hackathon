package com.banana.proyectostareas.model;

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

//@data - Generará automáticamente los métodos equals, hashCode, toString y getters/setters para todas las propiedades de la clase.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tareas")
@Schema(name = "Tarea", description = "Modelo tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @Schema(name = "Tarea ID", example = "1", required = false)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(name = "Tarea descripción", example = "Tarea ... Proyecto X", required = true)
    private String descripcion;

    @DateTimeFormat
    @NotNull
    @Schema(name = "Tarea fecha límite", example = "AAAA-MM-DD", required = true)
    private LocalDate fechaLimite;

    @Min(1)
    @Schema(name = "Tarea orden", type = "Integer", required = true)
    private Integer orden;

    @NotNull
    @Schema(name = "Tarea completada", type = "Boolean", example = "false", required = true)
    private boolean completada;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "proyecto_id") // Foreign Key
    @ToString.Exclude   //Evitar bucles infinitos
    @Schema(name = "Tarea miProyecto (foreing key)", type = "Proyecto", required = true)
    private Proyecto miProyecto;
}
