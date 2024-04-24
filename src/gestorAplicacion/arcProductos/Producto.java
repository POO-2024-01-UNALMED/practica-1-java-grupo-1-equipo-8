package gestorAplicacion.arcProductos;

import java.io.Serializable;

public class Producto implements Serializable {
    // Atributos de clase
    private static int id = 0;

    // Atributos de instancia
    private String codigo;
    private String nombre;
    private String modelo;
    private short año;
    private String color;
    private String marca;
    private String estado;
    private byte condicion;
    private float precio;

    // Constructor con todos los atributos
    public Producto(String nombre, String modelo, short año, String color, String marca, String estado,
            byte condicion, float precio) {
        this.codigo = generarCodigo();
        this.nombre = nombre;
        this.modelo = modelo;
        this.año = año;
        this.color = color;
        this.marca = marca;
        this.estado = estado;
        this.condicion = condicion;
        this.precio = precio;
    }

    // Constructores con parámetros opcionales
    public Producto(String nombre, String modelo, short año, String color, String marca, float precio) {
        this(nombre, modelo, año, color, marca, "Stock", (byte) 10, precio);
    }

    public Producto(String nombre, String modelo, short año, float precio) {
        this(nombre, modelo, año, "NA", "generica", precio);
    }

    public Producto(String nombre, String modelo, float precio) {
        this(nombre, modelo, (short) 0, precio);
    }

    // Método para generar el código
    private String generarCodigo() {
        id++;
        return "P" + id;
    }

    // seters y getter
    protected void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    protected String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public short getAño() {
        return año;
    }

    public void setAño(short año) {
        this.año = año;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte getCondicion() {
        return condicion;
    }

    public void setCondicion(byte condicion) {
        if (condicion > 0) {
            this.condicion = condicion;
            if (this.condicion < 3) {
                setEstado("Deteriorado");
            }
        }

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
