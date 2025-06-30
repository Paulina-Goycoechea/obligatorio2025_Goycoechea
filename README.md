# UMovie - Obligatorio Programación II

## Instrucciones para ejecutar el proyecto

Antes de ejecutar el programa principal (`Main`), es necesario preparar la carpeta con los datos.

---

### Paso 1: Ubicar la carpeta `DATASETS`

Asegúrese de tener una carpeta llamada **`DATASETS`** en la **raíz del proyecto**. Esta carpeta debe contener los siguientes archivos CSV:

- `movies_metadata.csv`
- `credits.csv`
- `ratings.csv`

---

### Paso 2: Variables internas de ruta

Dentro de la clase `UMovieSystem`, ya están definidas tres variables constantes que apuntan a los archivos CSV:

```java
private static final String PATH_MOVIES = "DATASETS/movies_metadata.csv";
private static final String PATH_RATINGS = "DATASETS/ratings.csv";
private static final String PATH_CREDITS = "DATASETS/credits.csv";

No es necesario modificar estas rutas si los archivos están correctamente ubicados en la carpeta DATASETS.
De lo contrario, se deben de modificar las rutas para el correcto funcionamiento del programa.