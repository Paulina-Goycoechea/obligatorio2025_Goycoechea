package uy.edu.um.consultas;

import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;

public class ActorMes {
    private int actorId;
    private int ratings;
    private MyHash<Integer, Boolean> peliculasVistas = new MyHashImpl<>();

    public ActorMes(int actorId) {
        this.actorId = actorId;
        this.ratings = 0;
    }

    public void sumarRating(int idPelicula) {
        if (!peliculasVistas.contains(idPelicula)) {
            peliculasVistas.put(idPelicula, true);
        }
        ratings++;
    }

    public int getRatings() {
        return ratings;
    }

    public int getCantidadMovies() {
        return peliculasVistas.keys().size();
    }

    public int getActorId() {
        return actorId;
    }
}

