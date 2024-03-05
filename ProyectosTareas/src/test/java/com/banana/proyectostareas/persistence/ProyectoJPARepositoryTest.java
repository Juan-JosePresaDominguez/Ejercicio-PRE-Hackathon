package com.banana.proyectostareas.persistence;

import com.banana.proyectostareas.model.Proyecto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// NOTAS
// Dado que estamos usando SpringBoot, la configuración definida en SpringConfig.class no es necesaria y además puede generar confusión, ya que es SpringBootApplication el que lo hace.
// @DataJpaTest ya hace las veces de preconfigurar la app y cargar propiedades, pero solo carga los componentes de persistencia.
// En este caso estamos probando el repositorio, con lo cual, para hacer comprobaciones, cuando haga falta acceder a BB.DD. es recomendable usar un Entity Manager.
/* ANOTACIONES:
   · @ExtendWith(SpringExtension.class) - Conectar el contexto de Spring Boot con el contexto de JUnit
   · @DataJpaTest() - Carga solo los componentes de persistencia (los marcados con @Repository)
   · @ComponentScan(basePackages = {"com.banana.proyectostareas.persistence"}) - Escanear un paquete
   · @AutoConfigureTestEntityManager - Genera un EntityManger para realizar nuestras pruebas
   ANOTACIÓN EQUIVALENTE (consume muchos recursos, mejor utilizar las 4 anotaciones anteriores para test de persistencia):
   · @SpringBootTest - Carga el contexto completo de SpringBoot = beans de todas las capas (está en el Test de humo que ejecutamos al comienzo del proyecto)
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ComponentScan(basePackages = {"com.banana.proyectostareas.persistence"})
@AutoConfigureTestEntityManager
//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProyectoJPARepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoJPARepositoryTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProyectoJPARepository jpaRepo;

    @Autowired
    private ProyectoJPARepositoryData jpaRepoData;

    @BeforeAll
    static void setupAll() {
        // setup all tests
    }

    @BeforeEach
    void setup() {
        // setup each test
    }

    @Test
    @Order(1)
    void findAll() {
        //Dado (Given)
        Proyecto unProyecto1 = new Proyecto(null, "Proyecto 1", LocalDate.now(), null);
        entityManager.persist(unProyecto1);
        Proyecto unProyecto2 = new Proyecto(null, "Proyecto 2", LocalDate.now(), null);
        entityManager.persist(unProyecto2);
        entityManager.flush();
        //Cuando (When)
        List<Proyecto> proyectos = jpaRepo.findAll();
        logger.info("Proyectos: " + proyectos + "(size = " + proyectos.size() + ")");
        //Entonces (Then)
        assertThat(proyectos.size()).isGreaterThan(0);
        assertNotNull(proyectos);
    }

    @Test
    @Order(2)
    void findById() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    @Order(3)
    void findByNombreContaining() {
        //Dado (Given)
        Proyecto unProyecto1 = new Proyecto(null, "Proyecto 1 Nuevo", LocalDate.now(), null);
        entityManager.persist(unProyecto1);
        Proyecto unProyecto2 = new Proyecto(null, "Proyecto 2 Nuevo", LocalDate.now(), null);
        entityManager.persist(unProyecto2);
        entityManager.flush();
        //Cuando (When)
        List<Proyecto> proyectos = jpaRepoData.findByNombreContaining("Nuevo");
        logger.info("Proyectos: " + proyectos + "(size = " + proyectos.size() + ")");
        //Entonces (Then)
        assertThat(proyectos.size()).isGreaterThan(0);
        assertNotNull(proyectos);
    }

    @Test
    @Order(4)
    void save() {
        //Dado (Given)
        Proyecto unProyecto = new Proyecto(null, "Proyecto 1", LocalDate.now(), null);
        //Cuando (When)
        jpaRepo.save(unProyecto);
        System.out.println("unProyecto: " + unProyecto);
        //Entonces (Then)
        assertThat(unProyecto.getId()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    void update() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    @Test
    @Order(6)
    void delete() {
        //Dado (Given)
        //Cuando (When)
        //Entonces (Then)
    }

    // @Disabled - Se puede aplicar a la clase o a un método concreto. Permite deshabilitar/ignorar test.
    // @BeforeEach o @AfterEach no serán invocados para este test.
    @Disabled("Se puede especificar el motivo por el cual se deshabilita el test")
    @Test
    void ejemploTestSecuencial() {
        // insert
        // Verificar que id mayor que 0

        // get
        // Verificar que existe para id

        // delete
        // Verificar que excepción cuando id
    }

    @AfterEach
    void teardown() {
        // teardown each test
    }
    @AfterAll
    static void teardownAll() {
        // teardown (desmontar) all tests
    }
}