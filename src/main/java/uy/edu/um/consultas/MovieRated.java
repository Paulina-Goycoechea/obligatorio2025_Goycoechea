package uy.edu.um.consultas;

import lombok.Data;

@Data
public class MovieRated implements Comparable<MovieRated> {
    private int idMovieR;
    private String titleMR;
    private Integer counterRateMR;
    private String languageMR;

    public MovieRated(int idMovieR, String title, int counterRate, String language) {
        this.idMovieR = idMovieR;
        this.titleMR = title;
        this.counterRateMR = counterRate;
        this.languageMR = language;
    }

    @Override
    public int compareTo(MovieRated otraMovie) {
        return this.counterRateMR.compareTo(otraMovie.counterRateMR);
    }
}
