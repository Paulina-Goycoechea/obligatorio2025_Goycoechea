package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.CrewMember;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Person;
import uy.edu.um.entities.Rating;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
/*
public class ConsultaTop10DirectoresTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Director: Christopher Nolan (ID: 1)
        Person nolan = new Person(1, "Christopher Nolan");
        sistema.getPeople().put(1, nolan);

        // Película 1 dirigida por Nolan
        Movie m1 = new Movie(101, "Inception", "en", 0, null);
        m1.addCrew(new CrewMember(1, "Directing", "Director"));
        sistema.getMovies().put(101, m1);
        nolan.getMoviesDirigidas().add(101);

        // Película 2 dirigida por Nolan
        Movie m2 = new Movie(102, "Interstellar", "en", 0, null);
        m2.addCrew(new CrewMember(1, "Directing", "Director"));
        sistema.getMovies().put(102, m2);
        nolan.getMoviesDirigidas().add(102);

        // 50 ratings para cada película (total 100)
        for (int i = 0; i < 50; i++) {
            Rating r1 = new Rating(i, 101, 5.0, 1609459200L);
            Rating r2 = new Rating(i + 50, 102, 3.0, 1609459200L);

            sistema.getRatings().add(r1);
            sistema.getRatings().add(r2);

            m1.getMovieRatings().add(r1);
            m2.getMovieRatings().add(r2);
        }
    }

    @Test
    public void testEjecutar() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsultaTop10Directores.ejecutar(sistema);

        String salida = output.toString();
        System.out.println("======= SALIDA REAL =======");
        System.out.println(salida);
        System.out.println("===========================");

        assertTrue(salida.contains("Christopher Nolan"));
        assertTrue(salida.contains("2")); // cantidad de películas
        assertTrue(salida.contains("4.0")); // mediana entre 5.0 y 3.0
    }
}*/
