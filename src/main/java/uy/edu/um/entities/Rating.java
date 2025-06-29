package uy.edu.um.entities;

import lombok.Data;

@Data
public class Rating {
    private int userId;
    private int movieId;
    private double ratingValue;
    private long timestamp;

    public Rating(int userId, int movieId, double ratingValue, long timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.ratingValue = ratingValue;
        this.timestamp = timestamp;
    }
}
