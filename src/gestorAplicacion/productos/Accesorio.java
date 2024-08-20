package gestorAplicacion.productos;
import gestorAplicacion.manejoLocal.Fecha;

import java.util.Comparator;

public class Accesorio extends Producto{
	
	/*### Atributos ###*/
	
	private String marca;
	private String consola;
	
	/* ### Constructores ### */

	// Constructor con todos los atributos
	public Accesorio(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String marca, String consola) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.marca = marca;
		this.consola = consola;
	}
	// Constructor con fecha
	public Accesorio(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, Fecha fecha, int descuento, int puntosRequeridos, String marca, String consola) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, fecha, descuento, puntosRequeridos);
		this.marca = marca;
		this.consola = consola;
	}
	// Constructor sin cantidadInicial ni atributos de descuento
	public Accesorio(String nombre, int valor, int cantidad, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String marca, String consola) {
		this(nombre, valor, cantidad, cantidad, prestable, condicion, diaLanz, mesLanz, yearLanz, 0, 0, marca, consola);
	}

	/* ~~~ Metodo tostring ~~~ */
	@Override
	public String toString() {
		return "COD: " + codigo + " | " +
				"NOMBRE: " + nombre + " | " +
				"CONSOLA: " + consola + " | " +
				"$ " + valor + " | " +
				"CANT: " + cantidad;
	}

	/* ~~ Metodo compare ~~ */
	@Override
	public int compare(Producto o1, Producto o2){
		return 1;
	}

	@Override
	public int compareTo(Producto o) {
		return 0;
	}

	/* ~~~ Getters y setters ~~~ */

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getConsola() {
		return consola;
	}
	public void setConsola(String consola) {
		this.consola = consola;
	}
}
