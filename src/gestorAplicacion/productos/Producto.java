package gestorAplicacion.productos;

import gestorAplicacion.manejoLocal.Fecha;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

public abstract class Producto implements Serializable, Cloneable,Comparable<Producto> ,Comparator<Producto> {
	
	/* ~~~ Atributos ~~~ */
	@Serial
	private static final long serialVersionUID = 1L;
	protected static int ultimoCodigo = 1;
	protected int codigo;
	protected String nombre;
	protected int valor;
	protected int cantidad;
	protected int cantidadInicial;
	protected boolean prestable ;
	protected byte condicion;
	protected Fecha fechaLanzamiento;
	protected int descuento;
	protected int puntosRequeridos;

    /* ~~~ Constructores ~~~ */
	// Constructor vacio
	public Producto() {
		this.codigo = ultimoCodigo;
		ultimoCodigo++;
	}

	// Constructor con todos los atributos. Recibe dia, mes y año por separado
    public Producto (String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diaLanz, int mesLanz, int yearLanz, int descuento, int puntosRequeridos) {
		this.codigo = ultimoCodigo;
		ultimoCodigo++;
		this.nombre = nombre;
		this.valor = valor;
		this.cantidad = cantidad;
		this.cantidadInicial = cantidadInicial;
		this.prestable = prestable;
		this.condicion = condicion;
		this.fechaLanzamiento = new Fecha(diaLanz, mesLanz, yearLanz);
		this.descuento = descuento;
		this.puntosRequeridos = puntosRequeridos;
	}

	// Constructor con todos los atributos. Recibe fecha en dias
	public Producto (String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condicion, int diasLanz, int descuento, int puntosRequeridos) {
		this.codigo = ultimoCodigo;
		ultimoCodigo++;
		this.nombre = nombre;
		this.valor = valor;
		this.cantidad = cantidad;
		this.cantidadInicial = cantidadInicial;
		this.prestable = prestable;
		this.condicion = condicion;
		this.fechaLanzamiento = new Fecha(diasLanz);
		this.descuento = descuento;
		this.puntosRequeridos = puntosRequeridos;
	}

	/* ~~~ Métodos ~~~ */

	/* ~~ Método para vender un producto ~~ */
	public void vender(int cantidad) {
		this.cantidad -= cantidad;
	}

	/* ~~ Método para retornar un producto ~~ */
	public void retornar(int cantidad) {
		this.cantidad += cantidad;
	}

	/* ~~ Método para ordenar ~~ */
	@Override
	public int compareTo(Producto p){
		if(this.calcularVenta() > p.calcularVenta()){
			return -1;
		}
		if (this.calcularVenta() < p.calcularVenta()){
			return 1;
		}
		return 0;
	}

	/* ~~ Metodo para calcular las ventas ~~ */
	public int calcularVenta(){
		int venta = this.cantidadInicial - this.cantidad;
		return venta;
	}
	public static ArrayList<Producto> Ordenar(ArrayList<Producto> p, String orden){
		if (orden.equals("ventas")) {
			Collections.sort(p);
			return p;

		}
		else if (orden.equals("precio")) {
			for (Producto i : p ){
				while(true){
					int l= i.getValor() - p.get(p.indexOf(i)-1).getValor();
					int r =i.getValor() - p.get(p.indexOf(i)+1).getValor();
					if(p.indexOf(i) != 0 || p.indexOf(i) < p.size()-1){
						if (l > 0){
							p.remove(i);
							p.add(p.indexOf(i)-1,i);
							continue;

						} else if (r < 0) {
							p.add(p.indexOf(i)+1,i);
							p.remove(i);
							continue;
						}
						else{
							break;
						}
					}else if (p.indexOf(i) == 0){
						if (r < 0) {
							p.add(p.indexOf(i)+1,i);
							p.remove(i);
							continue;
						}
						else{
							break;
						}
					} else if (p.indexOf(i) == p.size()-1) {
						if (l > 0) {
							p.remove(i);
							p.add(p.indexOf(i) - 1, i);
							continue;
						}
						else{
							break;
						}
					}
					else {
						break;
					}

				}
			}
			return p;
		}else {
			Collections.sort(p, new Comparator<Producto>() {
				@Override
				public int compare(Producto o1, Producto o2) {
					return o1.getNombre().compareToIgnoreCase(o2.getNombre());
				}
			});
			return p;
		}
	}


	/* ~~ Método para clonar un producto ~~ */
	@Override
	public Producto clone() throws CloneNotSupportedException {
		return (Producto) super.clone();
	}

	/* ~~ Método toString ~~ */
	@Override
	public String toString() {
		return "COD: " + codigo + " | " +
				"NOMBRE: " + nombre + " | " +
				"$ " + valor + " | " +
				"CANT: " + cantidad;
	}


	/* ### Getters y setters ### */
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadInicial() {
		return cantidadInicial;
	}
	public void setCantidadInicial(int cantidadInicial) {
		this.cantidadInicial = cantidadInicial;
	}

	public boolean isPrestable() {
		return prestable;
	}
	public void setPrestable(boolean prestable) {
		this.prestable = prestable;
	}

	public byte getCondicion() {
		return condicion;
	}
	public void setCondicion(byte condicion) {
		this.condicion = condicion;
	}
	public Fecha getFechaLanzamiento() {
		return fechaLanzamiento;
	}
	public void setFechaLanzamiento(Fecha fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getPuntosRequeridos() {
		return puntosRequeridos;
	}
	public void setPuntosRequeridos(int puntosRequeridos) {
		this.puntosRequeridos = puntosRequeridos;
	}


}
