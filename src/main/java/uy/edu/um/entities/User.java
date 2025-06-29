package uy.edu.um.entities;

import lombok.Data;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

@Data
public class User {
    private int idUser;
    private MyList<Rating> userRatings = new MyLinkedListImpl<>();

    public User(int idUser) {
        this.idUser = idUser;
    }

}
