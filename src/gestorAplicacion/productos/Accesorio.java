package gestorAplicacion.productos;


public class Accesorio extends Producto {
	
	/*### Atributos ###*/
	
	private String marca;
	private String consola;
	
	/* ### Constructores ### */
	
	public Accesorio (int codigo, String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, int diaLanz, int mesLanz, int yearLanz, String marca, String consola) {
		super(codigo, nombre, valor, cantidad, cantidadInicial, prestable, condiciones, diaLanz, mesLanz, yearLanz);
		this.marca = marca;
		this.consola = consola;
	}
}
