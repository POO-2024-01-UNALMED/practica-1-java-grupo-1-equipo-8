package gestorAplicacion.Productos;

import java.io.Serializable;
abstract class Producto implements Serializable{
	
	/*atributos*/
	
	protected int codigo;
	protected String nombre;
	protected int cantidad;
	protected int cantidadInicial;
	protected boolean prestable ;
	protected byte condicion;
	protected Fecha fechaLanzamiento;
   

}
