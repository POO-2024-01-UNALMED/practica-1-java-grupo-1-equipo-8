package gestorAplicacion.manejoLocal;
import gestorAplicacion.productos.Producto;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;

public class Tienda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /*~~ Atributos ~~*/
    private long fondos;
    private ArrayList<Fecha> caja;
    private ArrayList<Producto> inventario;
    private ArrayList<Reabastecimiento> reabastecimientos;

                /*~~ Metodos get y set ~~*/

    public long getFondos() {
        return fondos;
    }
    public void setFondos(long fondos) {
        this.fondos = fondos;
    }
    public ArrayList<Fecha> getCaja() {
        return caja;
    }
    public void setCaja(ArrayList<Fecha> caja) {
        this.caja = caja;
    }
    public ArrayList<Producto> getInventario() {
        return inventario;
    }
    public void setInventario(ArrayList<Producto> inventario) {
        this.inventario = inventario;
    }
    public ArrayList<Reabastecimiento> getReabastecimientos() {
        return reabastecimientos;
    }
    public void setReabastecimientos(ArrayList<Reabastecimiento> reabastecimientos) {
        this.reabastecimientos = reabastecimientos;
    }

}
