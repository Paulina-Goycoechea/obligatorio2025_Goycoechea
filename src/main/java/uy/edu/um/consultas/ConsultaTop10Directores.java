package uy.edu.um.consultas;

import uy.edu.um.UMovieSystem;
import uy.edu.um.entities.Movie;
import uy.edu.um.entities.Person;
import uy.edu.um.entities.Rating;
import uy.edu.um.tad.heap.MyHeap;
import uy.edu.um.tad.heap.MyHeapImpl;
import uy.edu.um.tad.linkedlist.MyList;

public class ConsultaTop10Directores {

    public static void ejecutar(UMovieSystem system) {
        long inicio = System.currentTimeMillis();

        MyHeap<DirectorMediana> heap = new MyHeapImpl<>(false); // max-heap

        MyList<Person> people = system.getPeople().values();

        for (int i = 0; i < people.size(); i++) {
            Person p = people.get(i);
            MyList<Integer> moviesDirected = p.getMoviesDirigidas();

            if (moviesDirected == null || moviesDirected.size() <= 1) continue;

            int totalRatings = 0;
            for (int j = 0; j < moviesDirected.size(); j++) {
                Movie m = system.getMovies().get(moviesDirected.get(j));
                if (m != null) {
                    totalRatings += m.getSumRate();
                }
            }

            if (totalRatings <= 100) continue;

            double[] r = new double[totalRatings];
            int index = 0;
            for (int j = 0; j < moviesDirected.size(); j++) {
                Movie m = system.getMovies().get(moviesDirected.get(j));
                if (m != null) {
                    MyList<Rating> ratings = m.getMovieRatings();
                    for (int k = 0; k < ratings.size(); k++) {
                        r[index++] = ratings.get(k).getRatingValue();
                    }
                }
            }

            double mediana;
            if (totalRatings % 2 == 0) {
                double m1 = quickSelect(r.clone(), totalRatings / 2);
                double m2 = quickSelect(r.clone(), totalRatings / 2 + 1);
                mediana = (m1 + m2) / 2.0;
            } else {
                mediana = quickSelect(r.clone(), (totalRatings + 1) / 2);
            }

            heap.insert(new DirectorMediana(p.getName(), moviesDirected.size(), mediana));
        }

        for (int i = 0; i < 10 && heap.size() > 0; i++) {
            DirectorMediana d = heap.delete();
            System.out.println(d.getNombre() + "," + d.getCantidadPeliculas() + "," + d.getMediana());
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución de la consulta: " + (fin - inicio) + " ms");
    }


    private static double quickSelect(double[] array, int k) {
        return quickSelect(array, 0, array.length - 1, k);
    }

    private static double quickSelect(double[] array, int left, int right, int k) {
        if (left == right) return array[left];

        int pivotIndex = partition(array, left, right);
        int length = pivotIndex - left + 1;

        if (k == length) return array[pivotIndex];
        else if (k < length) return quickSelect(array, left, pivotIndex - 1, k);
        else return quickSelect(array, pivotIndex + 1, right, k - length);
    }

    private static int partition(double[] array, int left, int right) {
        double pivot = array[right];
        int i = left;

        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                double temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
            }
        }
        double temp = array[i];
        array[i] = array[right];
        array[right] = temp;

        return i;
    }
}

