package es.cesargp.gestorficheros;

import java.util.ArrayList;
import java.util.List;

/**
 *  <p>
 *      Programa que permite leer y escribir datos a
 *      ficheros de registros y ficheros CSV.
 *  </p>
 *  <p>
 *      En los ficheros de registros, todos los campos
 *      deben tener un tamaño conocido de antemano y
 *      todos los registros deben seguir la misma
 *      estructura.
 *  </p>
 *  <p>
 *      Los ficheros CSV deben tener un delimitador
 *      conocido de antemano, que se pasará como
 *      parámetro al constructor.
 *  </p>
 * 
 *  @author     César Gutiérrez
 *  @version    0
 */
public class App {

    /**
     *  Prueba la implementación de los métodos de
     *  la clase {@link Gestor} para la subclase
     *  {@link GestorCSV}.
     */
    public static void probarCSV() {
        System.out.println("PRUEBA CSV:");

        // Crear un gestor para un CSV delimitado por ';'.
        final GestorCSV csv = new GestorCSV(
            "datos.csv", ";"
        );

        // Crear un CSV.
        csv.crear();

        // Exportar una serie de datos al fichero.
        csv.exportar(
            List.of(
                new String[] { "Jugador1", "52100" },
                new String[] { "Jugador2", "13400" },
                new String[] { "Jugador3", "2300", "01-05-2023" },
                new String[] { "Jugador4", "1050", "[Fecha desconocida]" }
            )
        );

        // Importar los datos a una lista.
        List<String[]> tabla = csv.importar();

        // Mostrar la lista por pantalla.
        for(String[] fila : tabla) {
            System.out.print(" - ");

            boolean primero = true;
            for(String campo : fila) {
                if(primero) primero = false;
                else System.out.print(", ");

                System.out.print(campo);
            }

            System.out.println();
        }

        System.out.println();
    }

    /**
     *  Prueba la implementación de los métodos de
     *  la clase {@link Gestor} para la subclase
     *  {@link GestorRegistros}.
     */
    public static void probarRegistros() {
        System.out.println("PRUEBA REGISTROS:");

        // Crear un gestor para un fichero de registros
        // con los campos DNI(9), Nombre(32) y Edad(3).
        final GestorRegistros reg = new GestorRegistros(
            "datos.txt", new Campo[] {
                new Campo("DNI", 9),
                new Campo("Nombre", 32),
                new Campo("Edad", 3)
            }
        );

        // Crear el fichero de registros.
        reg.crear();

        // Exportar una serie de datos al fichero.
        reg.exportar(
            List.of(
                new String[] { "00000000T", "Nombre real", "100" },
                new String[] { "[N/A]", "Miriam Fernandez", "37" },
                new String[] { "12345678Z", "Gonzalo Gonzalez", "50" }
            )
        );

        // Importar los datos a una lista.
        List<String[]> registros = reg.importar();

        // Mostrar la lista por pantalla.
        for(String[] fila : registros) {
            System.out.print(" - ");

            boolean primero = true;
            for(String campo : fila) {
                if(primero) primero = false;
                else System.out.print(", ");

                System.out.print(campo);
            }

            System.out.println();
        }

        System.out.println();
    }

    /**
     *  Método principal del programa.
     * 
     *  @param  args    Parámetros recibidos
     */
    public static void main(String[] args) {
        probarCSV();
        probarRegistros();
    }
}
