package uy.edu.um.consultas;

import lombok.Data;

@Data
public class DirectorMediana implements Comparable<DirectorMediana> {
    private String nombre;
    private int cantidadPeliculas;
    private double mediana;

    public DirectorMediana(String nombre, int cantidadPeliculas, double mediana) {
        this.nombre = nombre;
        this.cantidadPeliculas = cantidadPeliculas;
        this.mediana = mediana;
    }

    @Override
    public int compareTo(DirectorMediana otro) {
        return Double.compare(this.mediana, otro.mediana);
    }
}

