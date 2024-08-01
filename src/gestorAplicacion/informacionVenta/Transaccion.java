package gestorAplicacion.informacionVenta;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Producto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Transaccion implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

                    /* ~~~ Atributos ~~~ */

    private int id;
    private int cantidad;
    private Fecha fecha;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private int valorSinDescuento;
    private int valorTotal;

                /*~~ Metodos get y set ~~*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public Fecha getFecha() {
        return fecha;
    }
    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
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
    public int getValorSinDescuento() {
        return valorSinDescuento;
    }
    public void setValorSinDescuento(int valorSinDescuento) {
        this.valorSinDescuento = valorSinDescuento;
    }
    public int getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

}