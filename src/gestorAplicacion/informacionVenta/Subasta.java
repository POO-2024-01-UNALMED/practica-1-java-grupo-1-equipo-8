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
    private final String tipo;
    private final Tienda local;

    // Constructor para subastas ascendentes
    public Subasta(Fecha fechaInicio, Fecha fechaFin, ArrayList<Producto> productos, int ofertaMayor, Tienda local, String tipo) {
        this.id = ultimoID;
        ultimoID++;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.productos = productos;
        this.ofertas = new ArrayList<Integer>();
        this.ofertantes = new ArrayList<Cliente>();
        this.ofertaMayor = ofertaMayor;
        this.estado = "Activa";
        this.tipo = tipo;
        this.local = local;

        local.agregarSubasta(this);
    }

    /* ~~~ Metodos ~~~ */
    // Metodo para sumarle 7 dias a la fecha final de una subasta
    public String extenderSubasta(Fecha fechaActual) {
        this.fechaFin = new Fecha(fechaActual.getTotalDias() + 7);
        if ((this.tipo.equals("Ascendente") || this.tipo.equals("Descendente") && this.ofertaMayor > 0)) {
            int ofertaMayorAnterior = this.ofertaMayor;
            int ofertaMayorNueva = (int) (this.ofertaMayor * 0.8);
            this.ofertaMayor = ofertaMayorNueva;
            return "Se hizo un descuento del 20% a la oferta anterior: " + ofertaMayorAnterior + " -> " +
                    ofertaMayorNueva;
        }
        return null;
    }

    /* ~~ Agregar ofertas ~~ */
    // Metodo para agregar una oferta a una subasta ascendente
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

    // Registra la oferta que finaliza una subasta descendente. Retorna al ganador
    public Cliente ofertarYFinalizarDescendente(Cliente cliente) {
        this.ofertas.add(this.ofertaMayor);
        this.ofertantes.add(cliente);
        this.estado = "Finalizada";

        // Actualizar puntos del ganador
        cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() - this.ofertaMayor);

        return cliente;
    }

    /* ~~ Finalizar subastas ~~ */
    // Metodo para finalizar una subasta ascendente o descendente. Retorna al ganador
    public Cliente finalizarSubasta() {
        this.estado = "Finalizada";
        // Buscar la ultima oferta y asignarle el producto al ofertante
        Cliente ganador = ofertantes.get(this.ofertantes.size() - 1);

        // Actualizar puntos del ganador
        ganador.setPuntosFidelidad(ganador.getPuntosFidelidad() - this.getOfertaMayor());

        return ganador;
    }

    // Metodo para finalizar una subasta anónima. Retorna al ganador
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

        return ganador;
    }

    /* ~~ Calcular valoración de objetos de subasta ~~ */

    // Calcular valoración de subasta ascendente del conjunto de productos que recibe
    public static int calcularValoracionAscendente(ArrayList<Producto> productos, Fecha fechaActual) {
        int rareza;
        int valorInicial;
        int valorTotal = 0;

        for (Producto producto : productos) { // Evaluar la rareza de los productos
            rareza = 0;
            valorInicial = 0;

            // Valorar por la diferencia de años entre la fecha de lanzamiento y el año actual
            int yearsDiferencia = Math.abs(fechaActual.getYear() - producto.getFechaLanzamiento().getYear());
            rareza += yearsDiferencia / 5;

            // Valorar por condicion
            rareza += producto.getCondicion() - 1;

            // Añadir el valor de la rareza al valor total
            valorInicial = (int) (producto.getValor() * (Math.pow(1.2, rareza) - 1));

            valorTotal += valorInicial;
        }

        return valorTotal;
    }

    // Calcular valoración de subasta descendente del conjunto de productos que recibe
    public static int calcularValoracionDescendente(ArrayList<Producto> productos, Fecha fechaActual) {
        int rareza;
        int valorInicial;
        int valorTotal = 0;

        for (Producto producto : productos) { // Evaluar la rareza de los productos
            rareza = 0;
            valorInicial = 0;

            // Valorar por edad
            int yearsDiferencia = Math.abs(fechaActual.getYear() - producto.getFechaLanzamiento().getYear());
            rareza += yearsDiferencia / 5;

            // Valorar por condicion
            rareza += producto.getCondicion() - 1;

            // Añadir el valor de la rareza al valor total
            valorInicial = (int) (producto.getValor() * (Math.pow(1.4, rareza) + 1));

            valorTotal += valorInicial;
        }

        return valorTotal;
    }


    // toString
    @Override
    public String toString() {
        return "Subasta" +
                "ID: " + id + "\n" +
                "Fecha de fin: " + fechaFin + "\n" +
                "Tipo: " + tipo + "\n" +
                "Productos: " + "\n" +imprimirProductos() +
                "Oferta mayor: " + ofertaMayor + "\n" +
                "Ofertantes: " + imprimirOfertantes();
    }

    public String imprimirProductos() {
        String productos = "";
        for (Producto producto : this.productos) {
            productos += "   * " + producto.getNombre() + "\n";
        }
        return productos;
    }

    public String imprimirOfertantes() {
        String ofertantes = "";

        for (Cliente cliente : this.ofertantes) {
            ofertantes += "    * " + cliente.getNombre() + "\n";
        }
        return ofertantes;
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

    public String getTipo() {
        return tipo;
    }
}
