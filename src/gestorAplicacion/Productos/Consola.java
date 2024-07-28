package gestorAplicacion.Productos;

public class Consola extends Producto {
	
	/*### Atributos ###*/
	
	private String marca;
	
	public Consola (int codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, Fecha fechaLanzamiento, String marca) {
		super(codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, fechaLanzamiento);
		this.marca = marca;
	}
}
