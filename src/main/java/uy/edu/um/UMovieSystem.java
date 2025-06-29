package uy.edu.um;

import lombok.Data;
import uy.edu.um.consultas.MenuConsultas;
import uy.edu.um.entities.*;
import uy.edu.um.tad.hash.MyHash;
import uy.edu.um.tad.hash.MyHashImpl;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.*;


@Data
public class UMovieSystem {
    private static final String PATH_MOVIES = "DATASETS/movies_metadata.csv";
    private static final String PATH_RATINGS = "DATASETS/ratings_1mm.csv";
    private static final String PATH_CREDITS = "DATASETS/credits.csv";

    MyHash<Integer, Movie> movies = new MyHashImpl<>();
    private MyHash<Integer, User> users = new MyHashImpl<>();
    private MyHash<Integer, Genre> genres = new MyHashImpl<>();
    private MyHash<Integer, Collection> collections = new MyHashImpl<>();
    private MyHash<Integer, Person> people = new MyHashImpl<>();
    private MyList<Rating> ratings = new MyLinkedListImpl<>();
    private MyHash<Integer, MyList<Movie>> moviesCollection = new MyHashImpl<>();

    public UMovieSystem(){}

    public void menuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Seleccione la opción que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    long inicio = System.currentTimeMillis();
                    cargarMovies();
                    cargarCredits();
                    cargarRatings();
                    long fin = System.currentTimeMillis();
                    System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + (fin - inicio) + " ms");
                    //mostrarInfoPelicula(862);
                    //mostrarInfoPelicula(998);  no existe idMovie
                    //mostrarInfoPelicula(318177); //pelicula con creditos [],[],318177
                    break;
                case 2:
                    MenuConsultas menuConsultas = new MenuConsultas(this);
                    menuConsultas.mostrarMenu();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 3);
    }

    public void cargarMovies() {
        int peliculasCargadas = 0;
        int peliculasIgnoradas = 0;
        int peliculasRepetidas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_MOVIES))) {
            br.readLine(); // saltar cabecera
            String linea;

            while ((linea = br.readLine()) != null) {
                while (linea.chars().filter(ch -> ch == '"').count() % 2 != 0) {  //lee lineas hasta que la cantidad de comillas sea par
                    String siguiente = br.readLine();
                    if (siguiente == null) break;
                    linea += "\n" + siguiente;
                }

                String[] splitLine = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (splitLine.length != 19) {
                    peliculasIgnoradas++;
                    continue;
                }

                try {
                    String idStr = splitLine[5].trim();
                    String title = splitLine[8].trim();
                    String language = splitLine[7].trim();
                    String revenueStr = splitLine[13].trim();
                    String collectionJson = splitLine[1].trim();
                    String genresJson = splitLine[3].trim();

                    if (idStr.isEmpty() || title.isEmpty()) {
                        peliculasIgnoradas++;
                        System.out.println("Línea:\n" + linea);
                        continue;
                    }

                    int id = Integer.parseInt(idStr);
                    double revenue = revenueStr.isEmpty() ? 0 : Double.parseDouble(revenueStr);

                    language = language.isEmpty() ? null : language;

                    Integer collectionId = null;
                    String collectionName = null;
                    if (!collectionJson.isEmpty()) {
                        Matcher mId = Pattern.compile("'id': (\\d+)").matcher(collectionJson);
                        Matcher mName = Pattern.compile("'name': '([^']+)'").matcher(collectionJson);
                        if (mId.find() && mName.find()) {
                            collectionId = Integer.parseInt(mId.group(1));
                            collectionName = mName.group(1);
                            if(!collections.contains(collectionId)){
                                collections.put(collectionId, new Collection(collectionId, collectionName));
                            }
                        }
                    }

                    Movie movie = new Movie(id, title, language, revenue, collectionId);
                    if(!movies.contains(id)){
                        movies.put(id, movie);
                    } else {
                        //System.out.println("Línea:\n" + linea);
                        peliculasRepetidas++;
                    }


                    if (collectionId != null) {
                        if(!moviesCollection.contains(collectionId)){
                            moviesCollection.put(collectionId, new MyLinkedListImpl<>());
                        }
                        moviesCollection.get(collectionId).add(movie);
                    }

                    genresJson = genresJson.replaceAll("^\"|\"$", "");
                    if (!genresJson.isEmpty() && genresJson.startsWith("[")) {
                        Matcher matcher = Pattern.compile("'id': (\\d+), 'name': '([^']+)'").matcher(genresJson);
                        while (matcher.find()) {
                            int genreId = Integer.parseInt(matcher.group(1));
                            String genreName = matcher.group(2);

                            if(!genres.contains(genreId)){
                                genres.put(genreId, new Genre(genreId, genreName));
                            }

                            genres.get(genreId).getMoviesIds().add(id);
                            movie.getGenres().add(genres.get(genreId));
                        }
                    }

                    peliculasCargadas++;
                } catch (Exception e) {
                    peliculasIgnoradas++;
                    //System.out.println(linea); 3 lineas con error
                }
            }
        } catch (IOException e) {
            System.out.println("Archivo no encontrado: " + PATH_MOVIES);
        }
        System.out.println("Películas cargadas: " + peliculasCargadas);
        System.out.println("Películas ignoradas: " + peliculasIgnoradas);
        System.out.println("Peliculas repetidas dentro del csv: " + peliculasRepetidas);
        System.out.println("movies.size(): " + movies.size());
    }

    public void cargarRatings() {
        int ratingsCargados = 0;
        int ratingsIgnorados = 0;
        int idPelisNoExiste = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_RATINGS))) {
            br.readLine(); // salto de encabezado
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length != 4) {
                    //System.out.println("Linea mal separada: " + linea);
                    ratingsIgnorados++;
                    continue;
                }

                try {
                    int userId = Integer.parseInt(campos[0].trim());
                    int movieId = Integer.parseInt(campos[1].trim());
                    double ratingValue = Double.parseDouble(campos[2].trim());
                    long timestamp = Long.parseLong(campos[3].trim());

                    // Si no existe el usuario, lo creo
                    if (!users.contains(userId)) {
                        User nuevo = new User(userId);
                        users.put(userId, nuevo);
                    }

                    // Si no existe la película, se ignora
                    if (!movies.contains(movieId)) {
                        //System.out.println("Ignorado por movieId inexistente: " + movieId + " en línea -> " + linea);
                        idPelisNoExiste++;
                        ratingsIgnorados++;
                        continue;
                    }

                    if( 0 > ratingValue || 5 < ratingValue){
                        ratingsIgnorados++;
                        continue;
                    }

                    Rating r = new Rating(userId, movieId, ratingValue, timestamp);

                    // Agrego el rating a las estructuras
                    movies.get(movieId).getMovieRatings().add(r);
                    movies.get(movieId).sumRate();
                    users.get(userId).getUserRatings().add(r);
                    ratings.add(r);

                    ratingsCargados++;
                } catch (NumberFormatException e) {
                    ratingsIgnorados++;
                }
            }

            System.out.println("Ratings cargados: " + ratingsCargados);
            System.out.println("Ratings ignorados: " + ratingsIgnorados);
            System.out.println("Rating a idMovies que no existen: " + idPelisNoExiste);

        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + PATH_RATINGS);
        }
    }


    public void cargarCredits() {
        int lineaNum = 1;
        int creditosCargados = 0;
        int creditosIgnorados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(PATH_CREDITS))) {
            String linea;
            br.readLine(); //salteo cabecera

            while ((linea = br.readLine()) != null) {
                lineaNum++;

                try {
                    int castStart = linea.indexOf("[");
                    int crewStart = linea.indexOf("[", castStart + 1);
                    int crewEnd = linea.lastIndexOf("]");

                    if (castStart == -1 || crewStart == -1 || crewEnd == -1) continue;

                    String castRaw = linea.substring(castStart, crewStart).trim();
                    String crewRaw = linea.substring(crewStart, crewEnd + 1).trim();
                    String movieIdStr = linea.substring(crewEnd + 1).replaceAll("[^0-9]", "");

                    if (movieIdStr.isEmpty()) continue;
                    int movieId = Integer.parseInt(movieIdStr);

                    if (!movies.contains(movieId)) continue;

                    boolean cargado = false;
                    if (!castRaw.equals("[]")) {
                        parseCast(castRaw, movieId);
                        cargado = true;
                    }
                    if (!crewRaw.equals("[]")) {
                        parseCrew(crewRaw, movieId);
                        cargado = true;
                    }

                    if (cargado) {
                        creditosCargados++;
                    } else {
                        creditosIgnorados++;
                    }

                } catch (Exception e) {
                    System.out.println("Error en línea " + lineaNum + ": " + e.getMessage());
                    creditosIgnorados++;
                }
            }

            System.out.println("Créditos cargados: " + creditosCargados);
            System.out.println("Créditos ignorados: " + creditosIgnorados);

        } catch (IOException e) {
            System.out.println("Error al leer el archivo de créditos: " + e.getMessage());
        }
    }

    public String findByKeyInString(String s, String key) {
        if (s == null || key == null) return null;

        s = s.trim();
        if (s.startsWith("{")) s = s.substring(1);
        if (s.endsWith("}")) s = s.substring(0, s.length() - 1);

        MyList<String> partes = new MyLinkedListImpl<>();
        StringBuilder current = new StringBuilder();
        boolean dentroDeComillas = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '\'') {
                dentroDeComillas = !dentroDeComillas;
            }

            if (c == ',' && !dentroDeComillas) {
                partes.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (!current.isEmpty()) {
            partes.add(current.toString());
        }

        for (int i = 0; i < partes.size(); i++) {
            String parte = partes.get(i);
            String[] kv = parte.split(":", 2);
            if (kv.length == 2) {
                String k = kv[0].trim().replaceAll("^'+|'+$", "");
                String v = kv[1].trim().replaceAll("^'+|'+$", "");
                if (v.equalsIgnoreCase("None")) v = null;

                if (k.equals(key)) {
                    return v;
                }
            }
        }

        return null;
    }

    public void parseCast(String castRaw, int movieId) {
        if (castRaw == null || castRaw.trim().equals("[]")) return;

        castRaw = castRaw.trim();
        if (castRaw.startsWith("[")) castRaw = castRaw.substring(1);
        if (castRaw.endsWith("]")) castRaw = castRaw.substring(0, castRaw.length() - 1);

        Pattern p = Pattern.compile("\\{[^\\{\\}]*\\}");
        Matcher m = p.matcher(castRaw);

        while (m.find()) {
            String obj = m.group();
            String idStr = findByKeyInString(obj, "id");
            String character = findByKeyInString(obj, "character");
            String name = findByKeyInString(obj, "name");

            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    CastMember cm = new CastMember(id, character);
                    Movie movie = movies.get(movieId);
                    if (movie != null) {
                        movie.addCast(cm);
                    }

                    // Registrar persona si no existe
                    if (name != null && !people.contains(id)) {
                        Person pNueva = new Person(id, name);
                        people.put(id, pNueva);
                    }

                } catch (NumberFormatException ignored) {}
            }
        }
    }

    public void parseCrew(String crewRaw, int movieId) {
        if (crewRaw == null || crewRaw.trim().equals("[]")) return;

        crewRaw = crewRaw.trim();
        if (crewRaw.startsWith("[")) crewRaw = crewRaw.substring(1);
        if (crewRaw.endsWith("]")) crewRaw = crewRaw.substring(0, crewRaw.length() - 1);

        Pattern p = Pattern.compile("\\{[^\\{\\}]*\\}");
        Matcher m = p.matcher(crewRaw);

        while (m.find()) {
            String obj = m.group();
            String idStr = findByKeyInString(obj, "id");
            String department = findByKeyInString(obj, "department");
            String job = findByKeyInString(obj, "job");
            String name = findByKeyInString(obj, "name");

            if (idStr != null && !idStr.isEmpty() && Objects.equals(job, "Director") && Objects.equals(department, "Directing")) {  //solo me interesan los directores para las consultas
                try {
                    int id = Integer.parseInt(idStr);
                    CrewMember cm = new CrewMember(id, department, job);
                    Movie movie = movies.get(movieId);
                    if (movie != null) {
                        movie.addCrew(cm);
                    }

                    // Registrar persona si no existe
                    if (name != null && !people.contains(id)) {
                        Person pNueva = new Person(id, name);
                        people.put(id, pNueva);
                    }
                    people.get(id).getMoviesDirigidas().add(movieId);
                } catch (NumberFormatException ignored) {}
            }
        }
    }


    //VERIFICAR INFO DE UNA PELI POR ID
    public void mostrarInfoPelicula(int id) {
        if (!movies.contains(id)) {
            System.out.println("No se encontró una película con ID: " + id);
            return;
        }

        Movie movie = movies.get(id);
        System.out.println("Título: " + movie.getTitle());
        System.out.println("Idioma original: " + movie.getOriginalLanguage());
        System.out.println("Revenue: " + movie.getRevenue());

        // Géneros
        System.out.print("Géneros: ");
        MyList<Genre> genresList = movie.getGenres();
        if (genresList.size() == 0) {
            System.out.println("Sin géneros.");
        } else {
            for (int i = 0; i < genresList.size(); i++) {
                System.out.print(genresList.get(i).getNameGenre());
                if (i < genresList.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        // Colección
        Integer collectionId = movie.getBelongsCollection();
        if (collectionId != null && collectionId != 0 && collections.contains(collectionId)) {
            Collection col = collections.get(collectionId);
            System.out.println("Colección: " + col.getNameCollection() + " (ID: " + col.getIdCollection() + ")");
        } else {
            System.out.println("Colección: Ninguna");
        }


        System.out.println("Actores:");
        MyList<CastMember> cast = movie.getCast();
        for (int i = 0; i < cast.size(); i++) {
            CastMember actor = cast.get(i);
            Person person = people.get(actor.getPersonId());
            if (person != null) {
                System.out.println("- " + person.getName() + " como " + actor.getCharacter());
            }
        }

        System.out.println("Equipo técnico:");
        MyList<CrewMember> crew = movie.getCrew();
        for (int i = 0; i < crew.size(); i++) {
            CrewMember member = crew.get(i);
            Person person = people.get(member.getPersonId());
            if (person != null) {
                System.out.println("- " + person.getName() + " - " + member.getJob() + " (" + member.getDepartment() + ")");
            }
        }

        // Ratings
        MyList<Rating> ratingList = movie.getMovieRatings();
        if (ratingList.size() == 0) {
            System.out.println("No tiene valoraciones.");
        } else {
            double total = 0;
            for (int i = 0; i < ratingList.size(); i++) {
                total += ratingList.get(i).getRatingValue();
            }
            double promedio = total / ratingList.size();
            System.out.println("Promedio de valoraciones: " + String.format("%.2f", promedio) +
                    " (" + ratingList.size() + " valoraciones)");
        }
    }
}

