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
            /*~~ Constructor reabastecimiento ~~*/
    public Reabastecimiento(Tienda localOrigen,Tienda localDestino,Fecha fechaEntrega,ArrayList<Producto> productosRecibidos){
        this.localOrigen = localOrigen;
        this.localDestino = localDestino;
        this.fechaEntrega = fechaEntrega;
        this.productosRecibidos = productosRecibidos;
    }
            /*~~~~~~~ Metodos ~~~~~~~*/

    // Metodo que aplica reabastecimiento a todos los productos del inventario
    // de este (el reabastecimiento) al inventario de la tienda destino
    public void aplicarReabastecimiento(){
        for (Producto producto : productosRecibidos) {
            localDestino.agregarProducto(producto);
        }
    }

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
