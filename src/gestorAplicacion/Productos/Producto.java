package gestorAplicacion.Productos;

import java.io.Serializable;
abstract class Producto implements Serializable{
   protected String nombre;
   protected String marca;
   protected String modelo;
   protected int cantidad;
   protected int cantidadInicial;
   protected boolean prestable ;
   protected byte condicion;
   

}
