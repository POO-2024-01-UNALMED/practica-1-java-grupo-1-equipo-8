package gestorAplicacion.Productos;

public class Juego extends Producto {
	
	/*atributos*/
	
	private String genero;
	private String plataforma;
	
	/* ### Constructores ### */
	
	public Juego (int codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, int diaLanz, int mesLanz, int yearLanz, String genero, String plataforma) {
		super(codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, diaLanz, mesLanz, yearLanz);
		this.genero = genero;
		this.plataforma = plataforma;
	}
	
	
}
