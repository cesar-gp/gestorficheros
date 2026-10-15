package es.cesargp.gestorficheros;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *	<p>
 *		Clase que envuelve una {@link File} y permite
 *		interactuar con ella usando funciones simples.
 *	</p>
 *	<p>
 *		Esta clase permite crear y borrar el fichero.
 *		Además, obliga a todas sus subclases a implementar
 *		métodos para importar datos del fichero y exportarlos
 *		al mismo.
 *	</p>
 *	<p>
 *		Los datos en los ficheros deben estar estructurados
 *		en forma de tabla, por eso se importan y exportan
 *		usando una lista de arrays de Strings.
 *	</p>
 *	<p>
 *		Cada elemento de la lista se interpreta como una fila
 *		y, dentro del array, cada elemento se interpreta como
 *		una celda de la tabla.
 *	</p>
 * 
 *	@author		César Gutiérrez
 *	@version	0
 */
public abstract class Gestor {

	/**
	 *	Fichero sobre el que opera la clase.
	 */
	private File fichero;

	/**
	 *	Devuelve el fichero sobre el que opera la clase.
	 * 
	 *	@return	Fichero sobre el que opera la clase.
	 */
	public File getFichero() {
		return this.fichero;
	}

	/**
	 *	Crea el fichero si no existe.
	 */
	public void crear() {
		try {
			// Crea el fichero si no existe.
			if(!this.fichero.exists()) this.fichero.createNewFile();
		} catch(IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
		}
	}

	/**
	 *	Borra el fichero solo si existe y es un archivo.
	 */
	public void borrar() {
		// Borra el archivo si existe y es un archivo.
		if(this.fichero.isFile()) this.fichero.delete();
	}

	/**
	 *	Importa los datos del fichero en forma de tabla.
	 * 
	 *	@return	Lista de filas de la tabla.
	 */
	public abstract List<String[]> importar();

	/**
	 *	Recibe una serie de datos en forma de tabla y
	 *	los escribe directamente en el fichero.
	 * 
	 *	@param	contenido	Lista de filas a escribir.
	 */
	public abstract void exportar(List<String[]> contenido);

	/**
	 *	Crea un gestor para el fichero especificado.
	 * 
	 *	@param	fichero	Fichero asociado al gestor.
	 */
	protected Gestor(File fichero) {
		this.fichero = fichero;
	}

	/**
	 *	Crea un gestor para el fichero ubicado en
	 *	la ruta especificada.
	 * 
	 *	@param	ruta	Ruta del fichero asociado al gestor.
	 */
	protected Gestor(String ruta) {
		this(new File(ruta));
	}
}