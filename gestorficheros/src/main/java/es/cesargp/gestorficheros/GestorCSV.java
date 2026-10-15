package es.cesargp.gestorficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *	<p>
 *		{@link Gestor} que permite importar y exportar
 *		datos de un fichero CSV.
 *	</p>
 *	<p>
 *		Para crear un gestor de CSV se debe aportar la
 *		ruta del fichero asociado y el delimitador que
 *		se usa dentro del CSV para separar los campos.
 *	</p>
 * 
 *	@author		César Gutiérrez
 *	@version	0
 */
public class GestorCSV extends Gestor {

	/**
	 *	Delimitador que separa los campos del CSV.
	 */
	private String delimitador;

	@Override
	public List<String[]> importar() {
		// Crea lista vacía.
		List<String[]> ret = new ArrayList<>();

		// Abre fichero para lectura secuencial.
		try(
			BufferedReader lector = new BufferedReader(
				new FileReader(this.getFichero())
			)
		) {
			// Lee primera linea y comenzar bucle.
			String linea = lector.readLine();
			while(linea != null) {
				// Separa su contenido usando el delimitador.
				final String[] campos = linea.split(delimitador);

				// Añade array de campos a la lista final.
				ret.add(campos);

				// Lee siguiente linea.
				linea = lector.readLine();
			}
		} catch(IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
		}

		return ret;
	}

	@Override
	public void exportar(List<String[]> contenido) {
		// Abre fichero para escritura secuencial desde
		// su posición final (es decir, en modo APPEND).
		try(
			BufferedWriter escritor = new BufferedWriter(
				new FileWriter(this.getFichero(), true)
			);
		) {
			// Recorre cada fila de la tabla.
			for(String[] fila : contenido) {

				// Recorre cada campo de la fila.
				for(int i = 0; i < fila.length; i++) {
					// ¿No es el primer campo? Pone delimitador delante.
					if(i != 0) escritor.write(delimitador);

					// Escribe el campo.
					escritor.write(fila[i]);
				}

				// Escribe un salto de linea.
				escritor.newLine();
			}
		} catch(IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
		}
	}

	/**
	 *	Crea un gestor de CSV asociado a un fichero,
	 *	cuyos campos se separan usando el delimitador
	 *	especificado.
	 * 
	 *	@param	fichero		Fichero asociado al gestor
	 *	@param	delimitador	Delimitador que separa los campos
	 */
	public GestorCSV(File fichero, String delimitador) {
		super(fichero);
		this.delimitador = delimitador;
	}

	/**
	 *	Crea un gestor de CSV asociado a un fichero
	 *	ubicado en la ruta especificada, que usa el
	 *	delimitador recibido por parámetro.
	 * 
	 *	@param	ruta		Ruta del fichero asociado al gestor
	 *	@param	delimitador	Delimitador que separa los campos
	 */
	public GestorCSV(String ruta, String delimitador) {
		this(new File(ruta), delimitador);
	}
}