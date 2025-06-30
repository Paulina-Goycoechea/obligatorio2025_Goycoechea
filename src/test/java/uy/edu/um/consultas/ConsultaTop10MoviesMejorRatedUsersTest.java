package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsultaTop10MoviesMejorRatedUsersTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Película 1: promedio 5.0 (101 ratings)
        Movie m1 = new Movie(1, "Pelicula Perfecta", "en", 0, null);
        sistema.getMovies().put(1, m1);
        for (int i = 0; i < 101; i++) {
            Rating r = new Rating(i, 1, 5.0, 1609459200L);
            sistema.getRatings().add(r);
            m1.getMovieRatings().add(r);
            m1.sumRate();
        }

        // Película 2: promedio 4.0 (101 ratings)
        Movie m2 = new Movie(2, "Pelicula Buena", "en", 0, null);
        sistema.getMovies().put(2, m2);
        for (int i = 0; i < 101; i++) {
            Rating r = new Rating(1000 + i, 2, 4.0, 1609459200L);
            sistema.getRatings().add(r);
            m2.getMovieRatings().add(r);
            m2.sumRate();
        }

        // Película 3: promedio 3.0 (101 ratings)
        Movie m3 = new Movie(3, "Pelicula Regular", "en", 0, null);
        sistema.getMovies().put(3, m3);
        for (int i = 0; i < 101; i++) {
            Rating r = new Rating(2000 + i, 3, 3.0, 1609459200L);
            sistema.getRatings().add(r);
            m3.getMovieRatings().add(r);
            m3.sumRate();
        }
    }

    @Test
    public void testEjecutar() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        ConsultaTop10MoviesMejorRatedUsers.ejecutar(sistema);

        System.setOut(originalOut); // restaurar salida

        String salida = output.toString();
        System.out.println("======= SALIDA REAL =======");
        System.out.println(salida);
        System.out.println("===========================");

        assertTrue(salida.contains("1,Pelicula Perfecta,5.0"));
        assertTrue(salida.contains("2,Pelicula Buena,4.0"));
        assertTrue(salida.contains("3,Pelicula Regular,3.0"));
    }
}


