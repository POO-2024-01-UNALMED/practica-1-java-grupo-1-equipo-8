package gestorAplicacion.productos;


import java.util.Comparator;

public class Accesorio extends Producto{
	
	/*### Atributos ###*/
	
	private String marca;
	private String consola;
	
	/* ### Constructores ### */

	public Accesorio(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String marca, String consola) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.marca = marca;
		this.consola = consola;
	}
	/* ~~ Método compare ~~ */
	@Override
	public int compare(Producto o1, Producto o2){
		return 1;
	}

}
