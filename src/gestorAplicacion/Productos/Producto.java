package gestorAplicacion.Productos;

import java.io.Serializable;
abstract class Producto implements Serializable{
	
	/* ### Atributos ### */
	
	protected int codigo;
	protected String nombre;
	protected int cantidad;
	protected int cantidadInicial;
	protected boolean prestable ;
	protected byte condicion;
	protected Fecha fechaLanzamiento;

    /* ### Constructores ### */
    public Producto (int Codigo, String nombre, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, Fecha fechaLanzamiento){
		this.codigo = Codigo;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.cantidadInicial = cantidadInicial;
		this.prestable = prestable;
		this.condicion = condiciones;
		this.fechaLanzamiento = fechaLanzamiento;
	}

	/* ### Métodos ### */


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
}
