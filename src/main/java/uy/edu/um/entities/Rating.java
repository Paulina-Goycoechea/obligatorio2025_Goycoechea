package uy.edu.um.entities;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class Rating {
    private int userId;
    private int movieId;
    private double ratingValue;
    private long timestamp;
    private int mes;

    public Rating(int userId, int movieId, double ratingValue, long timestamp) {
        this.userId = userId;
        this.movieId = movieId;
        this.ratingValue = ratingValue;
        this.timestamp = timestamp;

        LocalDateTime fecha = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
        this.mes = fecha.getMonthValue();
        /*
        LocalDateTime fecha = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        this.mes = fecha.getMonthValue();*/
    }

    public int getMes() {
        return mes;
    }
}
