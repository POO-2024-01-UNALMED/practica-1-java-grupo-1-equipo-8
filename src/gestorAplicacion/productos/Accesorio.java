package gestorAplicacion.productos;


import java.util.Comparator;

public class Accesorio extends Producto{
	
	/*### Atributos ###*/
	
	private String marca;
	private String consola;
	
	/* ### Constructores ### */

	// Constructor con todos los atributos
	public Accesorio(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String marca, String consola) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.marca = marca;
		this.consola = consola;
	}

	// Constructor sin cantidadInicial ni atributos de descuento
	public Accesorio(String nombre, int valor, int cantidad, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String marca, String consola) {
		super(nombre, valor, cantidad, prestable, condicion, diaLanz, mesLanz, yearLanz);
		this.marca = marca;
		this.consola = consola;
	}

	/* ~~ Método compare ~~ */
	@Override
	public int compare(Producto o1, Producto o2){
		return 1;
	}

	/* ~~~ Getters y setters ~~~ */

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getConsola() {
		return consola;
	}
	public void setConsola(String consola) {
		this.consola = consola;
	}

	@Override
	public int compareTo(Producto o) {
		return 0;
	}
}
