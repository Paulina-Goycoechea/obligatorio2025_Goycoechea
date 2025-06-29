package uy.edu.um.entities;

import lombok.Data;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

@Data
public class Genre {
    private int idGenre;
    private String nameGenre;
    private MyList<Integer> moviesIds = new MyLinkedListImpl<>();

    public Genre(int idGenre, String nameGenre) {
        this.idGenre = idGenre;
        this.nameGenre = nameGenre;
    }
}
