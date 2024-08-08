package gestorAplicacion.productos;

public class Juego extends Producto {
	
	/*atributos*/
	
	private String genero;
	private String plataforma;
	
	/* ### Constructores ### */
	public Juego(){}/*### constructor vacio ###*/

	public Juego(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String genero, String plataforma) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.genero = genero;
		this.plataforma = plataforma;
	}

	public Juego(String nombre, int cantidad, int valor, int cantidadInicial, boolean prestable, byte condicion, int diasLanz, int descuento, int puntosRequeridos, String genero, String plataforma) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diasLanz, descuento, puntosRequeridos);
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
