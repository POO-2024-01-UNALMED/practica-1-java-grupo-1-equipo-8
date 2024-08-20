package gestorAplicacion.productos;
import gestorAplicacion.manejoLocal.Fecha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Juego extends Producto {
	
	/* ~~~~~~~~~~~~~~~~~ Atributos ~~~~~~~~~~~~~~~~~ */
	
	private String genero;
	private String plataforma;
	
	/* ~~~~~~~~~~~~~~~~~ Constructores ~~~~~~~~~~~~~~~~~ */
	public Juego(){}/*### constructor vacio ###*/
	// Constructor con todos los atributos
	public Juego(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos, String genero, String plataforma) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, descuento, puntosRequeridos);
		this.genero = genero;
		this.plataforma = plataforma;
	}
	// Constructor con fecha
	public Juego(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, Fecha fecha, int descuento, int puntosRequeridos, String genero, String plataforma) {
		super(nombre, valor, cantidad, cantidadInicial, prestable, condicion, fecha, descuento, puntosRequeridos);
		this.genero = genero;
		this.plataforma = plataforma;
	}
	// Constructor sin descuento
	public Juego(String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String genero, String plataforma) {
		this(nombre, valor, cantidad, cantidadInicial, prestable, condicion, diaLanz, mesLanz, yearLanz, 0, 0, genero, plataforma);
	}

	// Constructor con cantidadInicial igual a la cantidad actual y sin atributos de descuento
	public Juego(String nombre, int valor, int cantidad, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, String genero, String plataforma) {
		this(nombre, valor, cantidad, cantidad, prestable, condicion, diaLanz, mesLanz, yearLanz, 0, 0, genero, plataforma);
	}

	//~~~~~~~~~~~~~~~~~~ Metodos ~~~~~~~~~~~~~~~~~~//
	// Metodo toString
	@Override
	public String toString() {
		return "COD: " + codigo + " | " +
				"NOMBRE: " + nombre + " | " +
				"$ " + valor + " | " +
				"GENERO: " + genero + " | " +
				"PLATAFORMA: " + plataforma + " | " +
				"CANT: " + cantidad;
	}

	public static ArrayList<Juego> organizar(ArrayList<Juego> p, String orden){
		if (orden.equalsIgnoreCase("genero")) {
			Collections.sort(p, new Comparator<Juego>() {
				@Override
				public int compare(Juego o1, Juego o2) {
					return o1.getGenero().compareToIgnoreCase(o2.getGenero());
				}
			});
			return p;
		} else if (orden.equalsIgnoreCase("plataforma")) {
			Collections.sort(p, new Comparator<Juego>() {
				@Override
				public int compare(Juego o1, Juego o2) {
					return o1.getPlataforma().compareToIgnoreCase(o2.getPlataforma());
				}
			});
			return p;
		} else {
			Collections.sort(p, new Comparator<Producto>() {
				@Override
				public int compare(Producto o1, Producto o2) {
					return o1.getNombre().compareToIgnoreCase(o2.getNombre());
				}
			});
			return p;
		}
	}


	// ~~~~~~~~~~~~~~~~~ Metodos get y set ~~~~~~~~~~~~~~~~~ //
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

	// ~~~~~~~~~~~~~~~~~ Metodo compare ~~~~~~~~~~~~~~~~~ //
	@Override
	public int compare(Producto o1, Producto o2){
		return 0;
	}

	@Override
	public int compareTo(Producto o) {
		return 0;
	}
}
