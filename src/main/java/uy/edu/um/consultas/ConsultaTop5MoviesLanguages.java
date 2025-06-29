package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyList;


public class ConsultaTop5MoviesLanguages {

    public static void ejecutar(UMovieSystem system) {
        long inicio = System.currentTimeMillis();

        String[] language = {"en", "fr", "it", "es", "pt"};
        MyHash<String, MyHeap<MovieRated>> topPorIdioma = new MyHashImpl<>();

        for (String l : language) {
            topPorIdioma.put(l, new MyHeapImpl<>(false)); // heap max
        }

        MyList<Movie> allMovies = system.getMovies().values();

        for(int i=0; i < allMovies.size(); i++) {
            Movie movie = allMovies.get(i);
            if(topPorIdioma.contains(movie.getOriginalLanguage())){
                MovieRated mr = new MovieRated(movie.getIdMovie(), movie.getTitle(), movie.getSumRate(), movie.getOriginalLanguage());
                topPorIdioma.get(movie.getOriginalLanguage()).insert(mr);
            }
        }

        for (String l : language) {
            MyHeap<MovieRated> heap = topPorIdioma.get(l);

            for (int i = 0; i < 5 && heap.size()>0; i++) {
                MovieRated mr = heap.delete();
                System.out.println(mr.getIdMovieR() + ", " + mr.getTitleMR() + ", " + mr.getCounterRateMR() + ", " + mr.getLanguageMR());
            }
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }
}
