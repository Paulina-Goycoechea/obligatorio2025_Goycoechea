package uy.edu.um.consultas;

import java.util.Arrays;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Person;
import uy.edu.um.entities.Rating;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

public class ConsultaTop10Directores {
    public static void ejecutar(UMovieSystem system){
        long inicio = System.currentTimeMillis();

        MyHeap<DirectorMediana> heap = new MyHeapImpl<>(false);

        MyList<Person> people = system.getPeople().values();
        for(int i = 0; i < people.size(); i++){
            Person p = people.get(i);
            MyList<Integer> moviesDirected = p.getMoviesDirigidas();

            if(moviesDirected.size() < 1 || moviesDirected.isEmpty()){
                continue;
            }

            int totalRating = 0;
            MyList<Double> directorRate = new MyLinkedListImpl<>();

            for(int j = 0; j < moviesDirected.size(); j++){
                Movie m = system.getMovies().get(moviesDirected.get(j));
                if(m != null){
                    totalRating += m.getSumRate();
                    MyList<Rating> ratingDirector = m.getMovieRatings();
                    for(int k = 0; k < ratingDirector.size(); k++){
                        Rating rating = ratingDirector.get(k);
                        directorRate.add(rating.getRatingValue());
                    }
                }
            }

            if(totalRating <= 100){
                continue;
            }

            double[] r = new double[directorRate.size()];
            for(int n = 0; n < directorRate.size(); n++){
                r[n] = directorRate.get(n);
            }
            Arrays.sort(r);  //optimizacion de quicksort, es in-place

            double mediana;
            if (directorRate.size() % 2 == 0) {
                mediana = (r[directorRate.size() / 2 - 1] + r[directorRate.size() / 2]) / 2.0;
            } else {
                mediana = r[directorRate.size() / 2];
            }

            DirectorMediana director = new DirectorMediana(p.getName(), p.getMoviesDirigidas().size(), mediana);
            heap.insert(director);

            for(int w = 0; w < 10 && heap.size() > 0; w++){
                DirectorMediana d = heap.delete();
                System.out.println(d.getNombre() + "," + d.getCantidadPeliculas() + "," + d.getMediana());
            }

            long fin = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
        }

    }
}
