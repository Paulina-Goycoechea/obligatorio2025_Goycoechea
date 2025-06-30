package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;

import java.util.Scanner;

public class MenuConsultas {
    private UMovieSystem system;
    private Scanner scanner = new Scanner(System.in);

    public MenuConsultas(UMovieSystem system) {
        this.system = system;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("Seleccione la consulta que desee:");
            System.out.println("1. Top 5 de las películas que más calificaciones por idioma.");
            System.out.println("2. Top 10 de las películas que mejor calificación media tienen por parte de los usuarios.");
            System.out.println("3. Top 5 de las colecciones que más ingresos generaron.");
            System.out.println("4. Top 10 de los directores que mejor calificación tienen.");
            System.out.println("5. Actor con más calificaciones recibidas en cada mes del año.");
            System.out.println("6. Usuarios con más calificaciones por género");
            System.out.println("7. Salir");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Top 5 de las películas que más calificaciones por idioma:");
                    System.out.println("<id_pelicula>, <titulo_pelicula>,<total_calificaciones>,<idoma>");
                    ConsultaTop5MoviesLanguages.ejecutar(system);
                    break;
                case 2:
                    System.out.println("Top 10 de las películas que mejor calificación media tienen por parte de los usuarios:");
                    System.out.println("<id_pelicula>, <titulo_pelicula>,<calificacion_media>");
                    ConsultaTop10MoviesMejorRatedUsers.ejecutar(system);
                    break;
                case 3:
                    System.out.println("Top 5 de las colecciones que más ingresos generaron:");
                    System.out.println("<id_coleccion>,<titulo_coleccion>,<cantidad_peliculas>,[id_pelicula_1,id_pelicula_2],<ingreso_generado>");
                    ConsultaTop5CollectionRevenue.ejecutar(system);
                    break;
                case 4:
                    System.out.println("Top 10 de los directores que mejor calificación tienen:");
                    System.out.println("<nombre_director>,<cantidad_peliculas>,<mediana_calificacion>");
                    ConsultaTop10Directores.ejecutar(system);
                    break;
                case 5:
                    System.out.println("Actor con más calificaciones recibidas en cada mes del año:");
                    System.out.println("<mes>,<nombre_actor>,<cantidad_peliculas>,<cantidad de calificaciones>");
                    ConsultaActorMasRatedMes.ejecutar(system);
                    break;
                case 6:
                    System.out.println("Usuarios con más calificaciones por género:");
                    ConsultaUserMasActivoPorGenero.ejecutar(system);
                    break;
                case 7:
                    System.out.println("Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }
}
