package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Genre;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyList;

public class UsuarioMasActivoPorGenero {

    public static void ejecutar(UMovieSystem sistema) {
        long inicio = System.currentTimeMillis();

        // Estructura: genero --> usuarioId ---> #ratings
        MyHash<Genre, MyHash<Integer, Integer>> topUsuarios = new MyHashImpl<>();
        MyList<Rating> ratings = sistema.getRatings();
        MyHash<Genre, Integer> vistasGeneros = new MyHashImpl<>();

        int size1 = ratings.size();
        for (int i = 0; i < size1; i++) {
            Rating r = ratings.get(i);
            int idUsuario = r.getUserId();
            int idMovie = r.getMovieId();

            Movie m = sistema.getMovies().get(idMovie);
            if (m == null) continue;

            MyList<Genre> generos = m.getGenres();
            for (int j = 0; j < generos.size(); j++){
                Genre gen = generos.get(j);

                if (!topUsuarios.contains(gen)){
                    topUsuarios.put(gen, new MyHashImpl<>());
                    vistasGeneros.put(gen, 0);
                }

                MyHash<Integer, Integer> raitingUsuariosDelMes = topUsuarios.get(gen);
                vistasGeneros.put(gen, vistasGeneros.get(gen) + 1);

                if (!raitingUsuariosDelMes.contains(idUsuario)){
                    raitingUsuariosDelMes.put(idUsuario, 0);
                }
                raitingUsuariosDelMes.put(idUsuario, raitingUsuariosDelMes.get(idUsuario) + 1);
            }

        }

        MyHeap<GeneroConVistas> heap = new MyHeapImpl<>();

        MyList<Genre> generos2 = vistasGeneros.keys();
        for (int k = 0; k < generos2.size(); k++) {
            Genre g = generos2.get(k);
            int vistas = vistasGeneros.get(g);
            heap.insert(new GeneroConVistas(g, vistas));
        }

        for (int l = 0; l < 10 && heap.size() > 0; l++) {
            GeneroConVistas top = heap.delete();

            MyHash<Integer, Integer> usuarios = topUsuarios.get(top.genero);
            MyHeap<UsuarioConResenias> heap2 = new MyHeapImpl<>();

            MyList<Integer> idsUsuarios = usuarios.keys();
            for (int j = 0; j < idsUsuarios.size(); j++) {
                int id = idsUsuarios.get(j);
                int cantidad = usuarios.get(id);
                heap2.insert(new UsuarioConResenias(id, cantidad));
            }

            // Mostrar el top K usuarios del género
            int k = 3; // Depende de la cantidad de usuarios que se quiera mostrar
            System.out.println("Top " + k + " usuarios para el género: " + top.genero.getNameGenre());

            for (int n = 0; n < k && heap2.size() > 0; n++) {
                UsuarioConResenias u = heap2.delete();
                System.out.println("Usuario " + u.idUsuario + " con " + u.cantidad + " reseñas");
            }

            System.out.println(); // separación entre géneros
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }

}
