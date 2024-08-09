package gestorAplicacion.productos;

import java.util.Comparator;

public class Consola extends Producto{
	
	/*### Atributos ###*/
	
	private String marca;
	public Consola(){}	// constructor vacío //
	public Consola (String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String marca) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
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
}
