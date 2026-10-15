package es.cesargp.gestorficheros;

/**
 *	<p>
 *		División interna que separa los valores dentro
 *		de un registro. Se identifica usando un nombre
 *		y debe tener siempre una longitud determinada.
 *	</p>
 *	<p>
 *		Por ejemplo, un registro se puede dividir en los
 *		siguientes campos:
 *	</p>
 *	<ul>
 *		<li>Nombre (9 bytes).</li>
 *		<li>Edad (3 bytes).</li>
 *		<li>Sexo (1 byte).</li>
 *	</ul>
 *	<p>
 *		Esto permite almacenar registros en ficheros y
 *		acceder a ellos saltando directamente a su
 *		posición dentro del archivo.
 *	</p>
 *	<p>
 *		<em>Nota:</em> los caracteres especiales (todos los
 *		que no están en la tabla ASCII) ocupan varios bytes.
 *		No es recomendable usarlos en ficheros de registros.
 *	</p>
 * 
 *	@author		César Gutiérrez
 *	@version	0
 */
public class Campo {

	/**
	 *	Nombre del campo.
	 */
	private String nombre;

	/**
	 *	Longitud en bytes del campo.
	 */
	private int longitud;

	/**
	 *	Devuelve el nombre del campo.
	 * 
	 *	@return	Nombre del campo
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 *	Devuelve la longitud en bytes del campo.
	 * 
	 *	@return	Longitud en bytes del campo
	 */
	public int getLongitud() {
		return this.longitud;
	}

	/**
	 *	Crea un campo identificado por un nombre
	 *	y con la longitud especificada.
	 * 
	 *	@param	nombre		Nombre del campo
	 *	@param	longitud	Longitud en bytes del campo
	 */
	public Campo(String nombre, int longitud) {
		this.nombre = nombre;
		this.longitud = longitud;
	}
}