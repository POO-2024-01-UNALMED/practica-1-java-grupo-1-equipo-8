package gestorAplicacion.manejoLocal;
import gestorAplicacion.productos.Producto;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;

public class Reabastecimiento implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    /*~~ Atributos ~~*/
    private Tienda localOrigen;
    private Tienda localDestino;
    private Fecha fechaEntrega;
    private ArrayList<Producto> productosRecibidos;

            /*~~ Metodos get y set ~~*/

    public Tienda getLocalOrigen() {
        return localOrigen;
    }
    public void setLocalOrigen(Tienda localOrigen) {
        this.localOrigen = localOrigen;
    }
    public Tienda getLocalDestino() {
        return localDestino;
    }
    public void setLocalDestino(Tienda localDestino) {
        this.localDestino = localDestino;
    }
    public Fecha getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(Fecha fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public ArrayList<Producto> getProductosRecibidos() {
        return productosRecibidos;
    }
    public void setProductosRecibidos(ArrayList<Producto> productosRecibidos) {
        this.productosRecibidos = productosRecibidos;
    }
}
