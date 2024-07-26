package gestorAplicacion.Productos;

import java.io.Serializable;

public class Producto implements Serializable {
    // Atributos de clase
    protected static int id = 0;

    // Atributos de instancia
    protected byte codigo;
    protected String nombre;
    protected String marca;
    protected String modelo;
    protected String estado;
    protected byte condicion;
    protected float precio;
    protected byte descuento;
    protected int puntosRequeridos;

    /* ###Constructores### */
    public Producto (byte codigo, String nombre, String marca, String modelo, String estado, byte condicion, float precio, byte descuento, int puntosRequeridos) {
    //this.codigo = codigo;
      this.nombre = nombre;
      this.marca = marca;
      this.modelo = modelo;
      this.estado = estado;
      this.condicion = condicion;
      this.precio = precio;
      this.descuento = descuento;
      this.puntosRequeridos = puntosRequeridos;
    }

    // Método para generar el código
    protected String generarCodigo() {
        id++;
        return "P" + id;
    }


    /* ###Setters y Getters### */
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
