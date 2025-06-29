package uy.edu.um.entities;

import lombok.Data;

@Data
public class CastMember {
    private int personId;
    private String character;

    public CastMember(int personId, String character) {
        this.personId = personId;
        this.character = character;
    }
}
