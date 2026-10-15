package es.cesargp.gestorficheros;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 *	<p>
 *		{@link Gestor} que permite importar y exportar
 *		datos de un fichero organizado en registros.
 *	</p>
 *	<p>
 *		El gestor recibe un fichero y un vector que contiene
 *		la lista de campos incluidos en cada registro (por
 *		ejemplo: nombre, edad y sexo), con sus longitudes
 *		en bytes.
 *	</p>
 *	<p>
 *		El gestor usa esa lista de campos para escribir
 *		registros al fichero y leerlos mediante acceso
 *		aleatorio.
 *	</p>
 * 
 *	@author		César Gutiérrez
 *	@version	0
 */
public class GestorRegistros extends Gestor {
	
	/**
	 *	Vector de campos incluidos en cada registro.
	 */
	private Campo[] campos;

	/**
	 *	Devuelve el vector de campos incluidos en
	 *	cada registro.
	 * 
	 *	@return	Vector de campos incluidos en cada registro
	 */
	public Campo[] getCampos() {
		return this.campos;
	}

	/**
	 *	<p>
	 *		Calcula la longitud de los registros en bytes
	 *		y la devuelve.
	 *	</p>
	 *	<p>
	 *		El cálculo se realiza sumando las longitudes
	 *		de todos los campos.
	 *	</p>
	 * 
	 *	@return	Longitud en bytes de todos los registros
	 */
	public int medirRegistro() {
		// Suma la longitud de cada campo.
		int resultado = 0;
		for(Campo c : this.campos) resultado += c.getLongitud();

		// Devuelve la suma.
		return resultado;
	}

	/**
	 *	<p>
	 *		Calcula la cantidad de registros que hay escritos
	 *		dentro del fichero asociado al gestor.
	 *	</p>
	 *	<p>
	 *		El cálculo se realiza tomando la longitud del
	 *		fichero completo y dividiéndola entre los bytes
	 *		que ocupa cada uno de sus registros.
	 *	</p>
	 * 
	 *	@return	Número de registros en el fichero
	 */
	public long contarRegistros() {
		// ¿Registros de longitud 0? Devuelve '-1'.
		final int longitudRegistro = medirRegistro();
		if(longitudRegistro == 0) {
			System.err.println(
				"Error: el fichero contiene registros de longitud 0."
			);
			return -1;
		}

		// Devuelve la longitud del archivo entre la
		// longitud de un registro.
		return this.getFichero().length() / longitudRegistro;
	}

	/**
	 *	<p>
	 *		Recibe un registro (un vector de campos en forma
	 *		de cadenas de texto) y lo escribe en la posición
	 *		final del fichero.
	 *	</p>
	 *	<p>
	 *		Por ejemplo, para escribir un registro en un fichero
	 *		que tiene los campos nombre, edad y sexo, podríamos
	 *		realizar la siguiente llamada:
	 *	</p>
	 *
	 *	<pre><code>
	 *	gestor.escribirRegistro(
	 *		new String[] { "Felipe", "42", "M" }
	 *	);
	 *	</code></pre>
	 * 
	 *	@param	valores	Valores de los campos internos del
	 *					registro a escribir.
	 * 
	 *	@return	Si el registro se ha escrito o no.
	 */
	public boolean escribirRegistro(String[] valores) {
		// ¿El array recibido contiene un número de
		// campos incorrecto? Devuelve 'false'.
		if(valores.length != this.campos.length) {
			System.err.println(
				"Error: se han recibido " + valores.length
				+ " campo(s), pero los registros del fichero '"
				+ this.getFichero() + "' tienen "
				+ this.campos.length + " campo(s)."
			);
			return false;
		}

		// Abrir el fichero con permiso de escritura ("rw").
		try(
			RandomAccessFile raf = new RandomAccessFile(
				this.getFichero(), "rw"
			);
		) {
			// Saltar al final del archivo.
			raf.seek(raf.length());

			// Recorrer los valores recibidos.
			for(int i = 0; i < valores.length; i++) {
				// Obtener longitud del valor actual.
				final int longitudValor = valores[i].length();

				// Obtener longitud del campo en el que queremos
				// escribir el valor actual.
				final int longitudCampo = this.campos[i].getLongitud();

				// Adaptar longitud del valor a su campo.
				String valorProcesado = "";
				for(int pos = 0; pos < longitudCampo; pos++) {
					if(pos < longitudValor) {
						// ¿No hemos leído el valor entero? Copiar letra.
						valorProcesado += valores[i].charAt(pos);
					} else {
						// ¿Quedan caracteres por rellenar? Meter espacios.
						valorProcesado += ' ';
					}
				}

				// Escribir el valor "procesado" en el fichero.
				raf.write(valorProcesado.getBytes());
			}
		} catch(IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 *	<p>
	 *		Lee un registro posicionado en un índice concreto
	 *		dentro del fichero (contando desde 0).
	 *	</p>
	 *	<p>
	 *		Por ejemplo, en un fichero con estos datos:
	 *	</p>
	 * 
	 *	<pre><code>
	 *	Felipe    42 M
	 *	Rafa      30 M
	 *	Estefania 21 F
	 *	</code></pre>
	 * 
	 *	<p>
	 *		La llamada {@code gestor.leerRegistro(2)} devuelve
	 *		los datos del tercer registro:
	 *	</p>
	 * 
	 *	<pre><code>
	 *	new String[] { "Estefania ", "21 ", "F" }
	 *	</code></pre>
	 *	
	 *	@param	i	Índice del registro a leer
	 * 
	 *	@return	Vector que contiene los campos del
	 *			registro leido
	 */
	public String[] leerRegistro(int i) {
		// Rechaza índices negativos.
		if(i < 0) {
			System.err.println(
				"Error: no hay registros con índice negativo."
			);
			return null;
		}

		// Rechaza índices por encima del número de registros.
		final long max = contarRegistros();
		if(i >= max) {
			System.err.println(
				"Error: se ha intentado leer el índice " + i
				+ " de un archivo con " + max + " registros."
			);
			return null;
		}

		// Abre el archivo con permiso de lectura ("r").
		try(
			RandomAccessFile raf = new RandomAccessFile(
				this.getFichero(), "r"
			)
		) {
			// Salta al registro con el índice recibido.
			final int longitudRegistro = medirRegistro();
			raf.seek(i * longitudRegistro);

			// Lo lee y lo copia a un array de bytes.
			final byte[] registro = new byte[longitudRegistro];
			raf.read(registro);

			// Crea un array de Strings para almacenar los campos.
			final String[] resultado = new String[this.campos.length];

			// Recorre cada campo del registro.
			int bytesLeidos = 0;
			for(int campo = 0; campo < this.campos.length; campo++) {
				// Obtiene la longitud del campo actual.
				final int longitud = this.campos[campo].getLongitud();
				
				// Recorre los bytes que le corresponden.
				String contenidoCampo = "";
				for(int pos = 0; pos < longitud; pos++) {
					contenidoCampo += (char) registro[bytesLeidos + pos];
				}

				// Guarda su contenido en el array final.
				resultado[campo] = contenidoCampo;

				// Suma la longitud del registro a los bytes leidos.
				bytesLeidos += longitud;
			}

			return resultado;
		} catch(IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
		}

		// ¿Algún error? Devuelve 'null'.
		return null;
	}

	@Override
	public List<String[]> importar() {
		List<String[]> ret = new ArrayList<>();

		// Obtener registros e irlos añadiendo a la lista.
		for(int i = 0; i < contarRegistros(); i++) {
			String[] registro = leerRegistro(i);
			if(registro == null) {
				System.err.println(
					"Error: no se ha podido leer el registro "
					+ " del indice " + i + "."
				);
			}

			ret.add(registro);
		}

		return ret;
	}

	@Override
	public void exportar(List<String[]> contenido) {
		// Escribir registros uno por uno.
		for(int i = 0; i < contenido.size(); i++) {
			boolean resultado = escribirRegistro(contenido.get(i));
			if(!resultado) {
				System.err.println(
					"Error: no se ha podido escribir el "
					+ " registro en el índice " + i + "."
				);
			}
		}
	}

	/**
	 *	<p>
	 *		Crea un gestor de registros asociado a un fichero
	 *		y a un vector de campos.
	 *	</p>
	 *	<p>
	 *		La lista de campos debe coincidir con la estructura
	 *		real del archivo. Si no coincide, es posible
	 *		encontrarse con errores difíciles de solucionar.
	 *	</p>
	 *
	 *	@param	fichero	Fichero asociado al gestor
	 *	@param	campos	Vector con campos internos de
	 *					cada registro
	 */
	public GestorRegistros(File fichero, Campo[] campos) {
		super(fichero);
		this.campos = campos;
	}

	/**
	 *	<p>
	 *		Crea un gestor de registros asociado al fichero
	 *		ubicado en la ruta especificada y a un vector de
	 *		campos.
	 *	</p>
	 *	<p>
	 *		La lista de campos debe coincidir con la estructura
	 *		real del archivo. Si no coincide, es posible
	 *		encontrarse con errores difíciles de solucionar.
	 *	</p>
	 * 
	 *	@param	ruta	Ruta del fichero asociado al gestor
	 *	@param	campos	Vector con campos internos de
	 *					cada registro
	 */
	public GestorRegistros(String ruta, Campo[] campos) {
		this(new File(ruta), campos);
	}
}