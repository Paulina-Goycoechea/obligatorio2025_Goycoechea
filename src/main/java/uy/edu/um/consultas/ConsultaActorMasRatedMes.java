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

            MyHash<Integer, MyHash<Integer, ActorMes>> actoresPorMes = new MyHashImpl<>();

            MyList<Rating> ratings = sistema.getRatings();
            //System.out.println("Total ratings: " + ratings.size());

            for (int i = 0; i < ratings.size(); i++) {
                Rating r = ratings.get(i);
                int mes = r.getMes();
                int idMovie = r.getMovieId();

                Movie m = sistema.getMovies().get(idMovie);
                if (m == null || m.getCast() == null) continue;

                MyList<CastMember> cast = m.getCast();
                if (cast.isEmpty()) continue;

                if (!actoresPorMes.contains(mes)) {
                    actoresPorMes.put(mes, new MyHashImpl<>());
                }

                MyHash<Integer, ActorMes> estPorActor = actoresPorMes.get(mes);

                for (int j = 0; j < cast.size(); j++) {
                    CastMember actor = cast.get(j);
                    int actorId = actor.getPersonId();

                    if (!estPorActor.contains(actorId)) {
                        estPorActor.put(actorId, new ActorMes(actorId));
                    }

                    estPorActor.get(actorId).sumarRating(idMovie);
                }

                /*if (i % 100000 == 0) {
                    System.out.println("Procesando rating #" + i);
                }*/
            }

            for (int mes = 1; mes <= 12; mes++) {
                if (!actoresPorMes.contains(mes)) continue;

                MyHash<Integer, ActorMes> estPorActor = actoresPorMes.get(mes);

                int mejorActorId = -1;
                int maxCalificaciones = -1;

                MyList<Integer> actorIds = estPorActor.keys();
                for (int i = 0; i < actorIds.size(); i++) {
                    int actorId = actorIds.get(i);
                    ActorMes estadist = estPorActor.get(actorId);
                    if (estadist.getRatings() > maxCalificaciones) {
                        maxCalificaciones = estadist.getRatings();
                        mejorActorId = actorId;
                    }
                }

                if (mejorActorId != -1 && sistema.getPeople().contains(mejorActorId)) {
                    String nombre = sistema.getPeople().get(mejorActorId).getName();
                    int cantidadPeliculas = estPorActor.get(mejorActorId).getCantidadMovies();
                    System.out.println(mes + "," + nombre + "," + cantidadPeliculas + "," + maxCalificaciones);
                }
            }

            long fin = System.currentTimeMillis();
            System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
        }
    }


