package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsultaUserMasActivoPorGeneroTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Crear géneros
        Genre drama = new Genre(1, "Drama");
        Genre comedy = new Genre(2, "Comedy");
        sistema.getGenres().put(1, drama);
        sistema.getGenres().put(2, comedy);

        // Crear películas
        Movie dramaMovie = new Movie(10, "Drama1", "en", 0, null);
        dramaMovie.getGenres().add(drama);
        drama.getMoviesIds().add(10);
        sistema.getMovies().put(10, dramaMovie);

        Movie comedyMovie = new Movie(20, "Comedy1", "en", 0, null);
        comedyMovie.getGenres().add(comedy);
        comedy.getMoviesIds().add(20);
        sistema.getMovies().put(20, comedyMovie);

        // Usuarios para DRAMA
        // Usuario 100 con 3 ratings a Drama
        User u100 = new User(100);
        sistema.getUsers().put(100, u100);
        for (int i = 0; i < 3; i++) {
            Rating r = new Rating(100, 10, 5.0, 1609459200L);
            sistema.getRatings().add(r);
            u100.getUserRatings().add(r);
            dramaMovie.getMovieRatings().add(r);
            dramaMovie.sumRate();
        }

        // Usuario 101 con 2 ratings a Drama
        User u101 = new User(101);
        sistema.getUsers().put(101, u101);
        for (int i = 0; i < 2; i++) {
            Rating r = new Rating(101, 10, 4.0, 1609459200L);
            sistema.getRatings().add(r);
            u101.getUserRatings().add(r);
            dramaMovie.getMovieRatings().add(r);
            dramaMovie.sumRate();
        }

        // Usuario 102 con 1 rating a Drama
        User u102 = new User(102);
        sistema.getUsers().put(102, u102);
        Rating r = new Rating(102, 10, 3.0, 1609459200L);
        sistema.getRatings().add(r);
        u102.getUserRatings().add(r);
        dramaMovie.getMovieRatings().add(r);
        dramaMovie.sumRate();

        // Usuarios para COMEDY
        // Usuario 200 con 4 ratings
        User u200 = new User(200);
        sistema.getUsers().put(200, u200);
        for (int i = 0; i < 4; i++) {
            Rating rt = new Rating(200, 20, 4.0, 1609459200L);
            sistema.getRatings().add(rt);
            u200.getUserRatings().add(rt);
            comedyMovie.getMovieRatings().add(rt);
            comedyMovie.sumRate();
        }

        // Usuario 201 con 3 ratings
        User u201 = new User(201);
        sistema.getUsers().put(201, u201);
        for (int i = 0; i < 3; i++) {
            Rating rt = new Rating(201, 20, 5.0, 1609459200L);
            sistema.getRatings().add(rt);
            u201.getUserRatings().add(rt);
            comedyMovie.getMovieRatings().add(rt);
            comedyMovie.sumRate();
        }

        // Usuario 202 con 2 ratings
        User u202 = new User(202);
        sistema.getUsers().put(202, u202);
        for (int i = 0; i < 2; i++) {
            Rating rt = new Rating(202, 20, 3.0, 1609459200L);
            sistema.getRatings().add(rt);
            u202.getUserRatings().add(rt);
            comedyMovie.getMovieRatings().add(rt);
            comedyMovie.sumRate();
        }
    }

    @Test
    public void testEjecutar() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        ConsultaUserMasActivoPorGenero.ejecutar(sistema);

        System.setOut(originalOut); // restaurar salida

        String salida = output.toString();
        System.out.println("======= SALIDA REAL =======");
        System.out.println(salida);
        System.out.println("===========================");

        //verificaciones por género
        assertTrue(salida.contains("Top 3 usuarios para el género: Drama"));
        assertTrue(salida.contains("Usuario 100 con 3 reseñas"));
        assertTrue(salida.contains("Usuario 101 con 2 reseñas"));
        assertTrue(salida.contains("Usuario 102 con 1 reseñas"));

        assertTrue(salida.contains("Top 3 usuarios para el género: Comedy"));
        assertTrue(salida.contains("Usuario 200 con 4 reseñas"));
        assertTrue(salida.contains("Usuario 201 con 3 reseñas"));
        assertTrue(salida.contains("Usuario 202 con 2 reseñas"));
    }
}
