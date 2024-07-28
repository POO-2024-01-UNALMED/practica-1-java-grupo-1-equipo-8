package gestorAplicacion.Productos;

public class Accesorio extends Producto {
	
	/*atributos*/
	private String marca;
	
	/* ### Constructores ### */
	public Accesorio(){} /*### constructor vacio ###*/
	public Accesorio (int codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, int diaLanz, int mesLanz, int yearLanz, String marca, String consola) {
		super(codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, diaLanz, mesLanz, yearLanz);
		this.marca = marca;
	}
}
