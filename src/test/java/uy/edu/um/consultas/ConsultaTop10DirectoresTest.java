package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsultaTop10DirectoresTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Crear películas dirigidas por Christopher Nolan
        Movie m1 = new Movie(101, "Inception", "en", 0, null);
        Movie m2 = new Movie(102, "Interstellar", "en", 0, null);
        sistema.getMovies().put(101, m1);
        sistema.getMovies().put(102, m2);

        // Usar parseCrew para registrar director (como lo hace cargarCredits)
        String crewJson = "[{'id': 1, 'department': 'Directing', 'job': 'Director', 'name': 'Christopher Nolan'}]";
        sistema.parseCrew(crewJson, 101);
        sistema.parseCrew(crewJson, 102);

        // Agregar ratings con sumRate()
        for (int i = 0; i < 51; i++) {
            Rating r1 = new Rating(i, 101, 5.0, 1609459200L); // 2021-01-01
            sistema.getRatings().add(r1);
            m1.getMovieRatings().add(r1);
            m1.sumRate();
        }

        for (int i = 0; i < 50; i++) {
            Rating r2 = new Rating(100 + i, 102, 3.0, 1609459200L);
            sistema.getRatings().add(r2);
            m2.getMovieRatings().add(r2);
            m2.sumRate();
        }
    }

    @Test
    public void testEjecutar() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        ConsultaTop10Directores.ejecutar(sistema);

        System.setOut(originalOut); // restaurar salida estándar

        String salida = output.toString();
        System.out.println("======= SALIDA REAL =======");
        System.out.println(salida);
        System.out.println("===========================");

        assertTrue(salida.contains("Christopher Nolan"));
        assertTrue(salida.contains("2"));
        assertTrue(salida.contains("5.0"));
    }
}


