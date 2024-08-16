package gestorAplicacion.productos;

import java.util.Comparator;

public class Consola extends Producto{
	
	/* ~~~ Atributos ~~~ */
	private String marca;

	/* ~~~ Constructores ~~~ */
	public Consola(){}	// constructor vacío //

	// Constructor con todos los atributos
	public Consola (String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String marca) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.marca = marca;
	}

	// Constructor sin cantidadInicial ni atributos de descuento
	public Consola (String nombre, int valor, int cantidad, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String marca) {
		super(nombre, valor, cantidad, prestable, condicion, diaLanz, mesLanz, yearLanz);
		this.marca = marca;
	}

										//~~~~~~~~~~~~~~~~~~ Métodos get y set ~~~~~~~~~~~~~~~~~~//
	public String getMarca(){
		return marca;
	}
	public void setMarca(String mark){
		this.marca = mark;
	}

	//~~~~~~~~~~~~~~~~~~ Método compare ~~~~~~~~~~~~~~~~~~//
	@Override
		public int compare(Producto o1, Producto o2){
		return 1;
	}

	@Override
	public int compareTo(Producto o) {
		return 0;
	}
}
