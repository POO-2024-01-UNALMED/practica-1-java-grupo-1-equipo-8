package gestorAplicacion.informacionVenta;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Producto;
import gestorAplicacion.manejoLocal.Tienda;

import java.io.Serializable;
import java.util.ArrayList;

public class Subasta implements Serializable {
    private static int ultimoID = 1;
    private int id;
    private final Fecha fechaInicio;
    private Fecha fechaFin;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> ofertas;
    private ArrayList<Cliente> ofertantes;
    private int ofertaMayor;
    private String estado;
    private final Tienda local;

    public Subasta(Fecha fechaInicio, Fecha fechaFin, ArrayList<Producto> productos, Tienda local) {
        this.id = ultimoID;
        ultimoID++;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.productos = productos;
        this.ofertas = new ArrayList<Integer>();
        this.ofertantes = new ArrayList<Cliente>();
        this.ofertaMayor = 0;
        this.estado = "Activa";
        this.local = local;

        local.agregarSubasta(this);
    }

    /* ~~~ Métodos ~~~ */
    // Método para sumarle 7 dias a la fecha final de una subasta
    public void extenderSubasta() {
        this.fechaFin = new Fecha(this.fechaFin.getTotalDias() + 7);
    }

    /* ~~ Agregar ofertas ~~ */
    // Método para agregar una oferta a una subasta ascendente o descendente
    public void agregarOferta(int oferta, Cliente cliente) throws Exception {
        if (oferta > this.ofertaMayor) {
            this.ofertas.add(oferta);
            this.ofertantes.add(cliente);
            this.ofertaMayor = oferta;
        } else {
            throw new Exception("La oferta debe ser mayor a la última oferta: " + this.ofertaMayor);
        }
    }

    public void agregarOfertaAnonima(int oferta, Cliente cliente) {
        this.ofertas.add(oferta);
        this.ofertantes.add(cliente);

        // Actualizar la oferta mayor
        if (oferta > this.ofertaMayor) {
            this.ofertaMayor = oferta;
        }
    }

    /* ~~ Finalizar subastas ~~ */
    // Método para finalizar una subasta ascendente o descendente. Retorna al ganador
    public Cliente finalizarSubasta() {
        this.estado = "Finalizada";
        // Buscar la ultima oferta y asignarle el producto al ofertante
        Cliente ganador = ofertantes.get(this.ofertantes.size() - 1);

        // Retirar los productos del inventario de la tienda
        for (Producto producto : this.productos) {
            local.retirarDeInventario(producto, local.getInventarioUsado());
        }

        return ganador;
    }

    // Método para finalizar una subasta anónima. Retorna al ganador
    // En caso de empate, el ganador será el primero que ofertó
    public Cliente finalizarSubastaAnonima() {
        this.estado = "Finalizada";
        Cliente ganador = null;

        // Buscar la mayor oferta en la lista de ofertas
        for (int i = 0; i < this.ofertas.size(); i++) {
            if (this.ofertas.get(i) == this.ofertaMayor) {
                ganador = this.ofertantes.get(i);
                break;
            }
        }

        // Retirar los productos del inventario de la tienda
        for (Producto producto : this.productos) {
            local.retirarDeInventario(producto, local.getInventarioUsado());
        }

        return ganador;
    }

    /* ~~~ Getters y setters ~~~ */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fecha getFechaInicio() {
        return fechaInicio;
    }

    public Fecha getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Fecha fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Integer> getOfertas() {
        return ofertas;
    }

    public void setOfertas(ArrayList<Integer> ofertas) {
        this.ofertas = ofertas;
    }

    public ArrayList<Cliente> getOfertantes() {
        return ofertantes;
    }

    public void setOfertantes(ArrayList<Cliente> ofertantes) {
        this.ofertantes = ofertantes;
    }

    public int getOfertaMayor() {
        return ofertaMayor;
    }

    public void setOfertaMayor(int ofertaMayor) {
        this.ofertaMayor = ofertaMayor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Tienda getLocal() {
        return local;
    }
}
