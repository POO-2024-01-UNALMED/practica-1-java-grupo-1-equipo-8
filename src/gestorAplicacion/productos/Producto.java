package gestorAplicacion.productos;

import gestorAplicacion.manejoLocal.Fecha;

import java.io.Serial;
import java.io.Serializable;
public abstract class Producto implements Serializable, Cloneable{
	
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
