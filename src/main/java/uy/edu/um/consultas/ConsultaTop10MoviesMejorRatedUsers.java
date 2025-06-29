package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyList;


public class ConsultaTop10MoviesMejorRatedUsers {

    public static void ejecutar(UMovieSystem system) {
        long inicio = System.currentTimeMillis();

        MyHeap<MovieMediaRate> heap = new MyHeapImpl<>(false);

        MyList<Movie> allMovies = system.getMovies().values();
        for(int i = 0; i < allMovies.size(); i++ ) {
            Movie movie = allMovies.get(i);

            if (movie.getCounterRatings() > 100) {
                MyList<Rating> listRating = movie.getMovieRatings();
                double sum = 0;
                for (int j = 0; j < listRating.size(); j++) {
                    sum = sum + listRating.get(j).getRatingValue();
                }
                double prom = sum / movie.getCounterRatings();
                MovieMediaRate mmr = new MovieMediaRate(movie.getIdMovie(), movie.getTitle(), prom);
                heap.insert(mmr);
            }
        }

        for(int i = 0; i < 10 && heap.size() > 0; i++){
            MovieMediaRate mmr = heap.delete();
            System.out.println(mmr.getId() + "," + mmr.getTitle() + "," + mmr.getMediaRate());
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }
}
