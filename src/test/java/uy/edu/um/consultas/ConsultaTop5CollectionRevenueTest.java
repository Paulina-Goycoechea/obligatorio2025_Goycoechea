package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Collection;
import uy.edu.um.entities.Movie;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsultaTop5CollectionRevenueTest extends TestCase {

    private UMovieSystem sistema;

    @Before
    public void setUp() {
        sistema = new UMovieSystem();

        // Colección 1: "Harry Potter" (2 películas)
        Collection col1 = new Collection(1, "Harry Potter");
        sistema.getCollections().put(1, col1);

        Movie peli1 = new Movie(10, "Harry Potter 1", "en", 1000000, 1);
        Movie peli2 = new Movie(11, "Harry Potter 2", "en", 2000000, 1);
        sistema.getMovies().put(10, peli1);
        sistema.getMovies().put(11, peli2);

        MyList<Movie> listaCol1 = new MyLinkedListImpl<>();
        listaCol1.add(peli1);
        listaCol1.add(peli2);
        sistema.getMoviesCollection().put(1, listaCol1);

        // Colección 2: "Star Wars" (1 película)
        Collection col2 = new Collection(2, "Star Wars");
        sistema.getCollections().put(2, col2);

        Movie peli3 = new Movie(20, "Star Wars", "en", 500000, 2);
        sistema.getMovies().put(20, peli3);

        MyList<Movie> listaCol2 = new MyLinkedListImpl<>();
        listaCol2.add(peli3);
        sistema.getMoviesCollection().put(2, listaCol2);
    }

    @Test
    public void testEjecutar() {
        // Capturar salida
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        ConsultaTop5CollectionRevenue.ejecutar(sistema);

        String salida = output.toString();
        System.out.println("SALIDA CAPTURADA:\n" + salida);

        assertTrue(salida.contains("1")); // id de Harry Potter
        assertTrue(salida.contains("Harry Potter"));
        assertTrue(salida.contains("3000000")); // total revenue
    }
}
