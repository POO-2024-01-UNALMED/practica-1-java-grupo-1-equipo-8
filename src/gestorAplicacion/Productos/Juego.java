package gestorAplicacion.Productos;

public class Juego extends Producto {
	
	/*atributos*/
	
	private String genero;
	private String plataforma;
	
	/* ### Constructores ### */
	public Juego(){}/*### constructor vacio ###*/

	public Juego (int codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, int diaLanz, int mesLanz, int yearLanz, String genero, String plataforma) {
		super(codigo, nombre, cantidad, cantidadInicial, prestable, condiciones, diaLanz, mesLanz, yearLanz);
		this.genero = genero;
		this.plataforma = plataforma;
	}
							//~~~~~~~~~~~~~~~~~~ Métodos get y set ~~~~~~~~~~~~~~~~~~//
	public String getGenero(){
		return genero;
	}
	public void setGenero(String genero1){
		this.genero = genero1;
	}
	public String getPlataforma(){
		return plataforma;
	}
	public void setPlataforma(String plataforma1){
		this.plataforma = plataforma1;
	}
}
