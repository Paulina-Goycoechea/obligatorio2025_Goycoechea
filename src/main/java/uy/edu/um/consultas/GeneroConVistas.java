package uy.edu.um.consultas;

import uy.edu.um.entities.Genre;

class GeneroConVistas implements Comparable<GeneroConVistas> {
    Genre genero;
    int vistas;

    public GeneroConVistas(Genre genero, int vistas) {
        this.genero = genero;
        this.vistas = vistas;
    }

    @Override
    public int compareTo(GeneroConVistas otro) {
        // Mayor cantidad de vistas tiene mayor prioridad
        return Integer.compare(otro.vistas, this.vistas);  // orden descendente
    }
}
