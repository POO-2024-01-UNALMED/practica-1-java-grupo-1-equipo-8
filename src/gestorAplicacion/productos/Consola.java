package gestorAplicacion.productos;
import gestorAplicacion.manejoLocal.Fecha;

import java.util.Comparator;

public class Consola extends Producto{
	
	/* ~~~ Atributos ~~~ */
	private String marca;

	/* ~~~ Constructores ~~~ */
	public Consola(){}	// constructor vacio //
	//Constructor con fecha
	public Consola(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, Fecha fecha, int descuento, int puntosRequeridos, String marca) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, fecha, descuento, puntosRequeridos);
		this.marca = marca;
	}

	// Constructor con todos los atributos
	public Consola (String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String marca) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.marca = marca;
	}

	// Constructor sin cantidadInicial ni atributos de descuento
	public Consola (String nombre, int valor, int cantidad, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String marca) {
		this(nombre, valor, cantidad, cantidad, prestable, condicion, diaLanz, mesLanz, yearLanz, 0, 0, marca);
	}

										//~~~~~~~~~~~~~~~~~~ Metodos get y set ~~~~~~~~~~~~~~~~~~//
	public String getMarca(){
		return marca;
	}
	public void setMarca(String mark){
		this.marca = mark;
	}

	//~~~~~~~~~~~~~~~~~~ Metodo compare ~~~~~~~~~~~~~~~~~~//
	@Override
		public int compare(Producto o1, Producto o2){
		return 1;
	}

	@Override
	public int compareTo(Producto o) {
		return 0;
	}
}
