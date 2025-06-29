package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Person;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyList;

public class ConsultaTop10Directores {
    public static void ejecutar(UMovieSystem system){
        long inicio = System.currentTimeMillis();

        MyHeap<DirectorMediana> heap = new MyHeapImpl<>(false);

        MyList<Person> people = system.getPeople().values();

    }
}
