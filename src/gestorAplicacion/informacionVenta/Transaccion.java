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


}
