package gestorAplicacion.productos;

public class Juego extends Producto {
	
	/* ~~~~~~~~~~~~~~~~~ Atributos ~~~~~~~~~~~~~~~~~ */
	
	private String genero;
	private String plataforma;
	
	/* ~~~~~~~~~~~~~~~~~z Constructores ~~~~~~~~~~~~~~~~~ */
	public Juego(){}/*### constructor vacio ###*/

	// Constructor con todos los atributos
	public Juego(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String genero, String plataforma) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.genero = genero;
		this.plataforma = plataforma;
	}

	// Constructor sin descuento
	public Juego(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String genero, String plataforma) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz);
		this.genero = genero;
		this.plataforma = plataforma;
	}

	// Constructor sin cantidadInicial ni atributos de descuento
	public Juego(String nombre, int valor, int cantidad, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String genero, String plataforma) {
		super(nombre, valor, cantidad, prestable, condicion, diaLanz, mesLanz, yearLanz);
		this.genero = genero;
		this.plataforma = plataforma;
	}

	//~~~~~~~~~~~~~~~~~~ Métodos ~~~~~~~~~~~~~~~~~~//



	// ~~~~~~~~~~~~~~~~~ Métodos get y set ~~~~~~~~~~~~~~~~~ //
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

	// ~~~~~~~~~~~~~~~~~ Método compare ~~~~~~~~~~~~~~~~~ //
	@Override
	public int compare(Producto o1, Producto o2){
		return 1;
	}
}
