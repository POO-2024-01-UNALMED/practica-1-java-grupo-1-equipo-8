package gestorAplicacion.Productos;

import gestorAplicacion.Fecha;

public class Consola extends Producto {
	
	/*### Atributos ###*/
	
	private String marca;
	
	public Consola (int codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, int diaLanz, int mesLanz, int yearLanz, String marca) {
		super(codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, diaLanz, mesLanz, yearLanz);
		this.marca = marca;
	}
}
