package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Genre;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;
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
                }


                MyHash<Integer, Integer> raitingUsuariosDelMes = topUsuarios.get(gen);

                if (!raitingUsuariosDelMes.contains(idUsuario)){
                    raitingUsuariosDelMes.put(idUsuario, 0);
                }
                raitingUsuariosDelMes.put(idUsuario, raitingUsuariosDelMes.get(idUsuario) + 1);
            }
        }
    }

}
