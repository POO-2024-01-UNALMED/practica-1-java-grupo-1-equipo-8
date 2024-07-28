package gestorAplicacion.Productos;

public class Juego extends Producto {
	
	/*atributos*/
	
	private String genero;
	private String plataforma;
	
	/* ### Constructores ### */
	
	public Juego (int Codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, Fecha fechaLanzamiento, String genero, String plataforma) {
		super(Codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, fechaLanzamiento);
		this.genero = genero;
		this.plataforma = plataforma;
	}
	
	
}
