package uy.edu.um.entities;

import lombok.Data;
import lombok.Getter;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

@Data
public class Movie {
    private int idMovie;
    private String title;
    private String originalLanguage;
    private double revenue;
    private Integer belongsCollection;  //null si no pertenece a ninguna coleccion
    private MyList<Genre> genres = new MyLinkedListImpl<>();
    private MyList<Rating> movieRatings = new MyLinkedListImpl<>();
    private MyList<CastMember> cast = new MyLinkedListImpl<>();
    private MyList<CrewMember> crew = new MyLinkedListImpl<>();
    @Getter
    private Integer counterRatings = 0;

    public Movie(int idMovie, String title, String originalLanguage, double revenue, Integer belongsCollection) {
        this.idMovie = idMovie;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.revenue = revenue;
        this.belongsCollection = belongsCollection;
    }

    public void addCast(CastMember castMember) {
        cast.add(castMember);
    }

    public void addCrew(CrewMember crewMember) {
        crew.add(crewMember);
    }

    public void sumRate(){
        counterRatings++;
    }

    public Integer getSumRate(){
        return counterRatings;
    }

    public MyList<CastMember> getCast() {
        return cast;
    }

    public MyList<CrewMember> getCrew() {
        return crew;
    }
}
