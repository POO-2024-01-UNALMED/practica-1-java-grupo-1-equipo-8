package gestorAplicacion.Productos;

import java.io.Serializable;

public class Producto implements Serializable {
    // Atributos de clase
    private static int id = 0;

    // Atributos de instancia
    private String codigo;
    private String nombre;
    private String marca;
    private String modelo;
    private String estado;
    private byte condicion;
    private float precio;
    protected byte descuento;
    protected int puntosRequeridos;

    /* ###Constructores### */
    public Producto ()

    // Método para generar el código
    private String generarCodigo() {
        id++;
        return "P" + id;
    }



    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecio() {
        return precio;
    }

    public String toString() {
	    //pendiente
    }
}
