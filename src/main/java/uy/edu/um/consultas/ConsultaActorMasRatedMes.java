package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.CastMember;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Rating;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;
import uy.edu.um.tad.linkedlist.MyList;

public class ConsultaActorMasRatedMes {

    public static void ejecutar(UMovieSystem sistema) {
        long inicio = System.currentTimeMillis();

        // Estructura: mes → actorId → stats
        MyHash<Integer, MyHash<Integer, ActorMes>> actoresPorMes = new MyHashImpl<>();

        MyList<Rating> ratings = sistema.getRatings();
        for (int i = 0; i < ratings.size(); i++) {
            Rating r = ratings.get(i);
            int mes = r.getMes();
            int idMovie = r.getMovieId();

            Movie m = sistema.getMovies().get(idMovie);
            if (m == null) continue;

            MyList<CastMember> cast = m.getCast();
            if (cast == null) continue;

            // Asegurar estructura
            if (!actoresPorMes.contains(mes)) {
                actoresPorMes.put(mes, new MyHashImpl<>());
            }

            MyHash<Integer, ActorMes> statsPorActor = actoresPorMes.get(mes);

            for (int j = 0; j < cast.size(); j++) {
                CastMember actor = cast.get(j);
                int actorId = actor.getPersonId();

                if (!statsPorActor.contains(actorId)) {
                    statsPorActor.put(actorId, new ActorMes(actorId));
                }

                statsPorActor.get(actorId).sumarRating(idMovie);
            }
        }

        // Mostrar resultado para cada mes
        for (int mes = 1; mes <= 12; mes++) {
            if (!actoresPorMes.contains(mes)) continue;

            MyHash<Integer, ActorMes> statsPorActor = actoresPorMes.get(mes);

            int mejorActorId = -1;
            int maxCalificaciones = -1;


            MyList<Integer> actorIds = statsPorActor.keys();
            for (int i = 0; i < actorIds.size(); i++) {
                int actorId = actorIds.get(i);
                ActorMes stats = statsPorActor.get(actorId);

                if (stats.getRatings() > maxCalificaciones) {
                    maxCalificaciones = stats.getRatings();
                    mejorActorId = actorId;
                }
            }

            if (mejorActorId != -1) {
                String nombre = sistema.getPeople().get(mejorActorId).getName();
                int cantidadPeliculas = statsPorActor.get(mejorActorId).getCantidadMovies();
                System.out.println(mes + "," + nombre + "," + cantidadPeliculas + "," + maxCalificaciones);
            }
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }
}

