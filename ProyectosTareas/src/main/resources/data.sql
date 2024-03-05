INSERT INTO proyectos (nombre, FECHA_CREACION) VALUES
('Proyecto 1', '2024-01-01'),
('Proyecto 2', '2025-03-01'),
('Proyecto 3', '2026-06-01'),
('Proyecto 4', '2026-12-01'); --Proyecto 4 sin tareas asociadas

INSERT INTO tareas (descripcion, FECHA_LIMITE, orden, completada, proyecto_id) VALUES
('Tarea Proyecto 1', '2025-01-01', 1, FALSE, 1),
('Tarea Proyecto 1', '2025-02-01', 2, FALSE, 1),
('Tarea Proyecto 2', '2026-03-01', 3, FALSE, 2),
('Tarea Proyecto 2', '2026-04-01', 1, FALSE, 2),
('Tarea Proyecto 2', '2026-05-01', 2, FALSE, 2),
('Tarea Proyecto 3', '2027-06-01', 9, FALSE, 3),
('Tarea Proyecto 3', '2027-07-01', 7, FALSE, 3);


-- NOTAS:
-- + Si no le indicamos el id, lo genera automáticamente la BB.DD. H2 en función del @Id de la entidad.
-- + Hibernate transforma los nombres de entidades y propiedades a "snake_case" SIEMPRE.
--   Ejemplo: 'fechaCreacion' se representará en las tablas como 'fecha_Creacion'
