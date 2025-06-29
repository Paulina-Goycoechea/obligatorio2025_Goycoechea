package uy.edu.um.consultas;

import lombok.Data;

@Data
public class MovieMediaRate implements Comparable<MovieMediaRate> {
    private int id;
    private String title;
    private Double mediaRate;

    public MovieMediaRate(int idMovieR, String title, Double mediaRate) {
        this.id = idMovieR;
        this.title = title;
        this.mediaRate = mediaRate;
    }

    @Override
    public int compareTo(MovieMediaRate otraMovie) {
        return this.mediaRate.compareTo(otraMovie.mediaRate);
    }
}
