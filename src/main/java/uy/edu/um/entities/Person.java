package uy.edu.um.entities;

import lombok.Data;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

@Data
public class Person {
    private int personId;
    private String name;
    private MyList<Integer> moviesActuadas = new MyLinkedListImpl<>();
    private MyList<Integer> moviesDirigidas = new MyLinkedListImpl<>();

    public Person(int personId, String name) {
        this.personId = personId;
        this.name = name;
    }
}
