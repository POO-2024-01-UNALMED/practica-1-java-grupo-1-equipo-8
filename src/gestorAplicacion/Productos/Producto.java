package gestorAplicacion.Productos;

import java.io.Serializable;
abstract class Producto implements Serializable{
   /*Atributos*/
    protected String nombre;
    protected String marca;
    protected String modelo;
    protected int condición;
}
