package gestorAplicacion.Productos;

public class Accesorio extends Producto {
	
	/*atributos*/
	
	private String marca;
	private String consola;
	
	/* ### Constructores ### */
	
	public Accesorio (int codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, Fecha fechaLanzamiento, String marca, String consola) {
		super(codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, fechaLanzamiento);
		this.marca = marca;
	}
}
