package uy.edu.um.consultas;

class UsuarioConResenias implements Comparable<UsuarioConResenias> {
    int idUsuario;
    int cantidad;

    public UsuarioConResenias(int idUsuario, int cantidad) {
        this.idUsuario = idUsuario;
        this.cantidad = cantidad;
    }

    @Override
    public int compareTo(UsuarioConResenias otro) {
        return Integer.compare(otro.cantidad, this.cantidad); // orden descendente
    }
}
