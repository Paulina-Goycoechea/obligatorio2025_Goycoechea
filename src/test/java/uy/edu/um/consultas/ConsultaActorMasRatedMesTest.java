package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

        // Crear rating en enero (mes 1)
        ZonedDateTime eneroUTC = ZonedDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
        long timestampEnero = eneroUTC.toEpochSecond();

        Rating rating = new Rating(999, 100, 5.0, timestampEnero);
        sistema.getRatings().add(rating);
        movie.getMovieRatings().add(rating);
        movie.sumRate();
    }

    @Test
    public void testEjecutar() {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        ConsultaActorMasRatedMes.ejecutar(sistema);

        System.setOut(originalOut); // restaurar salida estándar

        String salida = output.toString();
        System.out.println("======= SALIDA REAL =======");
        System.out.println(salida);
        System.out.println("===========================");

        //verificar que contenga la línea esperada (enero = mes 1)
        assertTrue(salida.contains("1,Leonardo DiCaprio,1,1"));
    }
}


