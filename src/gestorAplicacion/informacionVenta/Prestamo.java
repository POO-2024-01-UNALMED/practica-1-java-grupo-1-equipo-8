package gestorAplicacion.informacionVenta;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Producto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
public class Prestamo implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    /* ~~~ Atributos ~~~ */

    private static int ultimoID = 1;
    private int id;
    private Fecha fechaInicio;
    private Fecha fechaFin;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private float valorTotal;
    private String estado;

    /* ~~~ Constructores ~~~ */
    public Prestamo(Fecha fechaInicio, Fecha fechaFin, Cliente cliente, ArrayList<Producto> productos, float valorTotal, String estado) {
        this.id = ultimoID;
        ultimoID++;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cliente = cliente;
        this.productos = productos;
        this.valorTotal = valorTotal;
        this.estado = estado;

        cliente.agregarPrestamo(this);
    }

    /* ~~~ Métodos ~~~ */
    public String toString() {
        return "ID: " + this.id +
                "Fecha de inicio: " + this.fechaInicio +
                "Fecha de fin: " + this.fechaFin +
                "Cliente: " + this.cliente +
                "Productos: " + this.productos +
                "Valor total: " + this.valorTotal +
                "Estado: " + this.estado;
    }


    /* ~~ Metodos get y set ~~ */

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Fecha getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Fecha fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Fecha getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Fecha fechaFin) {
        this.fechaFin = fechaFin;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    public float getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}