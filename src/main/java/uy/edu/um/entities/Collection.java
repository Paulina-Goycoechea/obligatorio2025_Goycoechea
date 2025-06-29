package uy.edu.um.entities;

import lombok.Data;

@Data
public class Collection {
    private int idCollection;
    private String nameCollection;

    public Collection(int idCollection, String nameCollection){
        this.idCollection = idCollection;
        this.nameCollection = nameCollection;
    }
}
