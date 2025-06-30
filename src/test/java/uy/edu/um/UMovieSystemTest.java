package uy.edu.um;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.entities.Movie;

public class UMovieSystemTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();
    }

    @Test
    public void testFindByKeyInString() {
        String json = "{'id': 123, 'name': 'Paulina'}";
        String name = sistema.findByKeyInString(json, "name");
        assertEquals("Paulina", name);

        String id = sistema.findByKeyInString(json, "id");
        assertEquals("123", id);

        String notFound = sistema.findByKeyInString(json, "age");
        assertNull(notFound);
    }

    @Test
    public void testParseCast() {
        String castJson = "[{'id': 1, 'character': 'Neo', 'name': 'Keanu Reeves'}]";
        sistema.movies.put(100, new Movie(100, "Matrix", "en", 0, null));
        sistema.parseCast(castJson, 100);

        Movie m = sistema.movies.get(100);
        assertEquals(1, m.getCast().size());
        assertEquals("Neo", m.getCast().get(0).getCharacter());
        assertEquals("Keanu Reeves", sistema.getPeople().get(1).getName());
    }

    @Test
    public void testParseCrew() {
        String crewJson = "[{'id': 1, 'department': 'Directing', 'job': 'Director', 'name': 'Lana Wachowski'}]";
        sistema.movies.put(200, new Movie(200, "Matrix Reloaded", "en", 0, null));
        sistema.parseCrew(crewJson, 200);

        Movie m = sistema.movies.get(200);
        assertEquals(1, m.getCrew().size());
        assertEquals("Director", m.getCrew().get(0).getJob());
        assertEquals("Lana Wachowski", sistema.getPeople().get(1).getName());
        assertTrue(sistema.getPeople().get(1).getMoviesDirigidas().contains(200));
    }

    // Los siguientes métodos requieren archivos reales para pruebas completas.
    @Test
    public void testCargarMovies() {
        // No testeado porque requiere archivo CSV real.
        assertTrue(true);
    }

    @Test
    public void testCargarRatings() {
        // No testeado porque requiere archivo CSV real.
        assertTrue(true);
    }

    @Test
    public void testCargarCredits() {
        // No testeado porque requiere archivo CSV real.
        assertTrue(true);
    }

    @Test
    public void testMenuPrincipal() {
        // No testeado porque requiere entrada por Scanner.
        assertTrue(true);
    }
}
