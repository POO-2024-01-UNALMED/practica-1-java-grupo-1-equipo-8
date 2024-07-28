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
    // Constructor general
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



}
