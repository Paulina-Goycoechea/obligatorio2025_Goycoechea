package uy.edu.um.consultas;

import junit.framework.TestCase;
import org.junit.Test;
import uy.edu.um.UMovieSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MenuConsultasTest extends TestCase {

    @Test
    public void testMostrarMenu() {
        // Simula que el usuario ingresa "7" para salir del menú
        String simulatedInput = "7\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Capturar salida del sistema
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        // Ejecutar el menú
        UMovieSystem sistema = new UMovieSystem();
        MenuConsultas menu = new MenuConsultas(sistema);
        menu.mostrarMenu();

        // Restaurar salida estándar
        System.setOut(originalOut);

        String salida = output.toString();
        System.out.println("======= SALIDA DEL MENÚ =======");
        System.out.println(salida);
        System.out.println("================================");

        // Verificar que se imprimieron las opciones correctamente
        assertTrue(salida.contains("1. Top 5 de las películas que más calificaciones por idioma."));
        assertTrue(salida.contains("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios."));
        assertTrue(salida.contains("6. Usuarios con más calificaciones por género"));
        assertTrue(salida.contains("7. Salir"));

        // Verificar que el mensaje final de salida esté presente
        assertTrue(salida.contains("Volviendo al menú principal."));
    }
}
