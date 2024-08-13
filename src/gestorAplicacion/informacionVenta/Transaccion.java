package gestorAplicacion.informacionVenta;
import gestorAplicacion.manejoLocal.*;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.productos.Producto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Transaccion implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    /* ~~~ Atributos ~~~ */

    private static int ultimoID = 1;
    private int id;
    private Fecha fecha;
    private Cliente cliente;
    private Empleado empleado;
    private Tienda local;
    private ArrayList<Producto> productos;
    private int valorSinDescuento;
    private int valorFinal;

    /* ~~~ Constructores ~~~ */

    // Constructor con todos los atributos
    public Transaccion(int id, Fecha fecha, Cliente cliente, Empleado empleado, Tienda local, ArrayList<Producto> productos, int valorSinDescuento, int valorFinal) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.empleado = empleado;
        this.local = local;
        this.productos = productos;
        this.valorSinDescuento = valorSinDescuento;
        this.valorFinal = valorFinal;
        empleado.ingresarTransaccion(this);
    }

    // Constructor sin id ni valorSinDescuento (se calcula automáticamente)
    public Transaccion(Fecha fecha, Cliente cliente, Empleado empleado, Tienda local, ArrayList<Producto> productos, int valorFinal) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.empleado = empleado;
        this.local = local;
        this.productos = productos;
        this.valorSinDescuento = hallarValorSinDescuento(this.productos);
        this.valorFinal = valorFinal;
        empleado.ingresarTransaccion(this);
    }

    /* ~~~ Métodos ~~~ */
    public void agregarALocal(Tienda local){
        this.local.agregarTransaccion(this);
    }

    public int hallarValorSinDescuento(ArrayList<Producto> productos){
        int valor = 0;
        for (Producto p : productos) {
            valor += p.getValor();
        }
        return valor;
    }

    /*~~ Metodos get y set ~~*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public int getValorFinal() {
        return valorFinal;
    }
    public void setValorFinal(int valorFinal) {
        this.valorFinal = valorFinal;
    }

}