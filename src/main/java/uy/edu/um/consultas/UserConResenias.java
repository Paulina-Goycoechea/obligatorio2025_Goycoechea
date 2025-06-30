package uy.edu.um.consultas;

class UserConResenias implements Comparable<UserConResenias> {
    int idUsuario;
    int cantidad;

    public UserConResenias(int idUsuario, int cantidad) {
        this.idUsuario = idUsuario;
        this.cantidad = cantidad;
    }

    @Override
    public int compareTo(UserConResenias otro) {
        return Integer.compare(otro.cantidad, this.cantidad); // orden descendente
    }
}
