package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;


public class ConsultaTop5CollectionRevenue {

    public static void ejecutar(UMovieSystem system){
        long inicio = System.currentTimeMillis();

        MyHash<Integer, Double> revenueCollection = new MyHashImpl<>();  //guardo el idCollection con el totalRevenue
        MyHash<Integer, MyList<Integer>> moviesIdCollection = new MyHashImpl<>(); //guardo una lista de moviesId por idCollection

        //Movies con collection:
        MyList<Integer> idCollections = system.getMoviesCollection().keys();

        for(int i = 0; i < idCollections.size(); i++){
            int id = idCollections.get(i);
            MyList<Movie> moviesCollection = system.getMoviesCollection().get(id);
            double totalRevenue = 0;
            MyList<Integer> idsM = new MyLinkedListImpl<>();

            for(int j = 0; j < moviesCollection.size(); j++){
                Movie movie = moviesCollection.get(j);
                totalRevenue += movie.getRevenue();
                idsM.add(movie.getIdMovie());
            }

            revenueCollection.put(id, totalRevenue);
            moviesIdCollection.put(id, idsM);
        }

        //Movies sin collection:
        MyList<Movie> allMovies = system.getMovies().values();
        for(int i = 0; i < allMovies.size(); i++){
            Movie movie = allMovies.get(i);
            if(movie.getBelongsCollection() == null){
                int idColInventado = -movie.getIdMovie(); //le pongo el idMovie negativo para evitar colisiones por las dudas
                MyList<Integer> idM = new MyLinkedListImpl<>();
                idM.add(movie.getIdMovie());
                revenueCollection.put(idColInventado, movie.getRevenue());
                moviesIdCollection.put(idColInventado, idM);
            }
        }

        //Heap para hacer el top
        MyHeap<CollectionRevenue> heap = new MyHeapImpl<>(false);

        MyList<Integer> allKeys = revenueCollection.keys();
        for(int i = 0; i < allKeys.size(); i++){
            int idCollection = allKeys.get(i);
            CollectionRevenue cr = new CollectionRevenue(idCollection, revenueCollection.get(idCollection));
            heap.insert(cr);
        }

        for(int i = 0; i < 5 && heap.size() > 0; i++){
            CollectionRevenue collRev = heap.delete();
            int idC = collRev.getId();
            int id;
            String name;

            if(idC >= 0){
                id = idC;
                name = system.getCollections().get(idC).getNameCollection();
            } else {
                int idM = -idC;
                id = idM;
                name = system.getMovies().get(idM).getTitle();
            }

            MyList<Integer> moviesIds = moviesIdCollection.get(idC);
            double totalRevenue = collRev.getRevenue();

            StringBuilder sb = new StringBuilder();
            sb.append(id).append(",").append(name).append(",").append(moviesIds.size()).append(",[");
            for (int j = 0; j < moviesIds.size(); j++) {
                sb.append(moviesIds.get(j));
                if (j < moviesIds.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("],").append(totalRevenue);
            System.out.println(sb.toString());
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio));

    }
}
