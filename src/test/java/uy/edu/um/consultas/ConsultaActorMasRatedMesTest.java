package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.CastMember;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Person;
import uy.edu.um.entities.Rating;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
/*
public class ConsultaActorMasRatedMesTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Crear actor y persona
        Person actor = new Person(1, "Leonardo DiCaprio");
        sistema.getPeople().put(1, actor);

        // Crear película con cast
        Movie movie = new Movie(100, "Inception", "en", 1000000, null);
        CastMember cm = new CastMember(1, "Cobb");
        movie.addCast(cm);
        sistema.getMovies().put(100, movie);

        // Crear rating en enero (timestamp equivale a enero)
        long timestampEnero = 1577836800L; // 1 de enero de 2020
        Rating rating = new Rating(999, 100, 5.0, timestampEnero);
        sistema.getRatings().add(rating);
    }

    @Test
    public void testEjecutar() {
        // Capturar salida por consola
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsultaActorMasRatedMes.ejecutar(sistema);

        String salida = output.toString();

        // Verificamos que contenga la línea esperada
        assertTrue(salida.contains("1,Leonardo DiCaprio,1,1"));
    }
}*/
