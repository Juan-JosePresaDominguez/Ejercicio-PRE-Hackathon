<a name="readme-top"></a>

# <h1 align="center">💻 PRE-HACKATHON - ProyectosTareasAPI 💻</h1>

<!-- PROJECT LOGO 
<br />
<div align="center">
  <a href="https://github.com/Juan-JosePresaDominguez/BananaWhatsApp">
    <img src="docs/Banana Whattsapp.png" alt="Logo" width="80" height="80">
  </a>
</div> -->


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Tabla de Contenidos</summary>
  <ol>
    <li><a href="#reto-de-navidad---una-aplicación-para-chatear">RETO DE NAVIDAD - Una Aplicación para Chatear</a></li>
    <li><a href="#pautas-reto-navidad---bananawhatsapp">Pautas RETO NAVIDAD - BananaWhatsApp</a></li>
    <li>
      <a href="#backlog-historias-de-usuario">BACKLOG Historias de Usuario</a>
      <ul>
        <li><a href="#historia-de-usuario-1">Historia de Usuario 1</a></li>
	<li><a href="#historia-de-usuario-2">Historia de Usuario 2</a></li>
	<li><a href="#historia-de-usuario-3">Historia de Usuario 3</a></li>
	<li><a href="#historia-de-usuario-4">Historia de Usuario 4</a></li>
	<li><a href="#historia-de-usuario-5">Historia de Usuario 5</a></li>
	<li><a href="#historia-de-usuario-6">Historia de Usuario 6</a></li>
      </ul>
    </li>
    <li><a href="#test-de-calidad">Test de Calidad</a></li>
    <li><a href="#notas-configuración-mysql-para-ejecución-de-test-y-perfiles">NOTAS Configuración MySQL para ejecución de TEST y PERFILES</a></li>
    <li><a href="#autor">Autor</a></li>
  </ol>
</details>


<a name="reto-de-navidad---una-aplicación-para-chatear"></a>
## PRE-HACKATHON - ProyectosTareasAPI

_Se quiere construir una API libre que permita a un usuario anónimo gestionar proyectos y sus tareas asociadas._

Atendiendo la petición de la mañana, os propongo un ejercicio para practicar de cara al Hackathon.

- Os dejo adjunto, el src y sql bases en un zip.
- Aunque aparezcan 5 historias de usuario, intentad haced 2.
- Cualquier duda o pregunta, por favor, responded en este foro.

CONSEJOS

Una recomendación: No intentéis montar todo de golpe en el proyecto de ejercicio (y por extensión en el hackathon).

Os recomiendo que lo hagáis parte por parte y probéis cada parte para añadir la siguiente, tal como vimos en clase.

Por ejemplo:

1. Crear la estructura del proyecto y ver que ejecuta sin poner nada dentro con el test de humo: ApplicationTest

	1.1. Añadir el application.yaml con la configuración.

2. Añadir el src que os he compartido, sin anotar, y ver que funciona con el test de humo.
3. Añadir las anotacionees de entity en los modelos, y ver que funciona con el test de humo.
4. Añadir los JpaRepository vacíos (o implementad un repo JPA con EM), y ver que funciona con el test de humo.

	4.1. Añadir tests de persistencia según requerimientos y ver que pasan, implementando los métodos que puedan faltar.
5. Añadir el servicio.

	5.1. Añadir tests de servicio según requerimientos y ver que pasan, implementado el servicio.
6. Añadir el RestController

	6.1. Añadir tests mvc según requerimientos y ver que pasan.
   
	6.2. Añadir control de excepciones y validación
   
	6.3. Añadir documentación

<p align="right">(<a href="#readme-top">Volver arriba</a>)</p>

<a name="pautas-reto-navidad---bananawhatsapp"></a>
## PautasPRE-HACKATHON: ProyectosTareasAPI 📁

Deberás implementar la aplicación ProyectosTareasAPI siguiendo la arquitectura planteada con las siguientes pautas:
- [x] ENTIDADES:
    - [x] Proyecto: [id], nombre, fechaDeCreacion, tareas.
    - [x] Tarea: [id], descripción, fechaLimite, orden, completada.
- [x] RETO:
    - [x] Diseña e implementa la API usando Spring Boot:
    	- Crea el proyecto con Spring Initializr.
        - Usa un perfil de desarrollo (dev) con H2, en el puerto 9900.
        - En la capa de persistencia puedes implementar el repositorio JPA o usar un JPARepository (Spring Data) equivalente.
        - En la capa de vista (controllers) no olvides la gestión de excepciones, la validación y documentación.
    - [x] Sube tu solución un repositorio en GitHub.:
        - Añade cada implementación de historia a una rama "feature/[funcionalidad]".
    - [x] Asegura la calidad de la aplicación con test automatizados para todas las capas: persistencia, servicio, web.:
        - Puedes usar un perfil de testing (test).
    - [x] EXTRA: Añade un perfil de producción (prod) que use MySql y comunique en protocolo seguro (HTTPS), en el puerto 9443.
<p align="right">(<a href="#readme-top">Volver arriba</a>)</p>

<a name="backlog-historias-de-usuario"></a>
## BACKLOG Historias de Usuario 📋

### _Historia de Usuario 1_
```
Como usuario anónimo quiero poder crear proyectos, para poder gestionar una lista asociada de tareas.
	 • POST
	   • crearProyecto()
```

### _Historia de Usuario 2_
```
Como usuario anónimo quiero poder añadir tareas a uno de mis proyectos, para tener una lista ORDENADA de trabajos.
	 • PUT
	   • anadeTareaAProyecto()
```

### _Historia de Usuario 3_
```
Como usuario anónimo quiero poder mostrar la lista de mis proyectos, para decidir cuál gestionar.
	 • GET
	   • obtenerProyectos()

```

### _Historia de Usuario 4_
```
Como usuario anónimo quiero ver la lista "ORDENADA" de tareas de un proyecto, para ejecutarlas en orden.
	 • GET
	   • obtenerTareasDelProyecto()
```

### _Historia de Usuario 5_
```
Como usuario quiero poder marcar una tarea como completada, para olvidarme de ella.
	 • PUT
	   • marcarTareaHecha() - en este caso no haría falta el BODY
```

<p align="right">(<a href="#readme-top">Volver arriba</a>)</p>

<a name="test-de-calidad"></a>
## TEST de Calidad ⚙️

- `Capa PERSISTENCIA`
  - `UsuarioJDBCRepositoryTest`: OK - Passed 12 of 12 tests
  - `MensajeJDBCRepositoryTest`: OK - Passed 8 of 8 tests
- `SERVICIOS`
  - `ServicioUsuariosTest`: OK - Passed 10 of 10 tests
  - `ServicioMensajeriaTest`: OK - Passed 7 of 7 tests
- `CONTROLADORES`
  - `ControladorUsuariosTest`: OK - Passed 8 of 8 tests
  - `ControladorMensajesTest`: OK - Passed 8 of 8 tests
<p align="right">(<a href="#readme-top">Volver arriba</a>)</p>

<a name="notas-configuración-mysql-para-ejecución-de-test-y-perfiles"></a>
## NOTAS Configuración MySQL para ejecución de TEST y PERFILES 💻

- `NOTA IMPORTANTE SQL`: Para cada conjunto de Test (6 en total) debemos recargar la BB.DD. a su situación original --> Archivos _tables_structure.sql_ y _data.sql_ están en carpeta *BananaWhatsApp\sql*

- `NOTA PERFILES`: SpringConfig --> application-dev.properties
```
	   • Usuario: bananauser (Este usuario es el que viene por defecto para la BB.DD. 'bananawhatsappdb')
	   • Contraseña: banana123
```
- `NOTA PERFILES`: SpringConfig --> application-pro.properties
```
	   • Usuario: bananauserpro (Este usuario lo he creado manualmente en phpMyAdmin)
	   • Contraseña: banana456
```
<p align="right">(<a href="#readme-top">Volver arriba</a>)</p>

<a name="autor"></a>
## Autor ✒️

* **Juan José Presa Domínguez** - *Banana Whattsapp* - [GitHub Banana Whattsapp](https://github.com/Juan-JosePresaDominguez/BananaWhatsApp/tree/master)
<p align="right">(<a href="#readme-top">Volver arriba</a>)</p>
