package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
/*
public class ConsultaTop5MoviesLanguagesTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Película 1: inglés, 3 ratings
        Movie peli1 = new Movie(1, "Inception", "en", 0, null);
        sistema.getMovies().put(1, peli1);
        for (int i = 0; i < 3; i++) {
            Rating r = new Rating(i, 1, 4.0, 1609459200L); // 2021-01-01
            sistema.getRatings().add(r);
            peli1.getMovieRatings().add(r);
            peli1.sumRate();
        }

        // Película 2: inglés, 2 ratings
        Movie peli2 = new Movie(2, "Interstellar", "en", 0, null);
        sistema.getMovies().put(2, peli2);
        for (int i = 0; i < 2; i++) {
            Rating r = new Rating(10 + i, 2, 5.0, 1609459200L);
            sistema.getRatings().add(r);
            peli2.getMovieRatings().add(r);
            peli2.sumRate();
        }

        // Película 3: español, 4 ratings
        Movie peli3 = new Movie(3, "El Secreto de Sus Ojos", "es", 0, null);
        sistema.getMovies().put(3, peli3);
        for (int i = 0; i < 4; i++) {
            Rating r = new Rating(20 + i, 3, 5.0, 1609459200L);
            sistema.getRatings().add(r);
            peli3.getMovieRatings().add(r);
            peli3.sumRate();
        }

        // Película 4: idioma no permitido
        Movie peli4 = new Movie(4, "Das Boot", "de", 0, null);
        sistema.getMovies().put(4, peli4);
        Rating r = new Rating(999, 4, 5.0, 1609459200L);
        sistema.getRatings().add(r);
        peli4.getMovieRatings().add(r);
        peli4.sumRate();
    }

    @Test
    public void testEjecutar() {
        // Capturar salida
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsultaTop5MoviesLanguages.ejecutar(sistema);

        String salida = output.toString();
        System.out.println("SALIDA CAPTURADA:\n" + salida);

        // Verificar películas esperadas por idioma
        assertTrue(salida.contains("1,Inception,3,en"));
        assertTrue(salida.contains("2,Interstellar,2,en"));
        assertTrue(salida.contains("3,El Secreto de Sus Ojos,4,es"));

        // Verificar que película en idioma "de" (alemán) no aparezca
        assertFalse(salida.contains("Das Boot"));
    }
}*/
