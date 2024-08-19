package gestorAplicacion.manejoLocal;
import gestorAplicacion.informacionVenta.Subasta;
import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.productos.Producto;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Tienda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /* ~~~ Atributos ~~~ */
    private String nombre;
    private long fondos;
    private ArrayList<Transaccion> caja = new ArrayList<Transaccion>();
    private ArrayList<Subasta> subastas = new ArrayList<Subasta>();

    /* ~~ Inventarios ~~ */
    private ArrayList<Producto> inventario = new ArrayList<Producto>();
    private ArrayList<Producto> inventarioPrestamo = new ArrayList<Producto>();
    private ArrayList<Producto> inventarioUsado = new ArrayList<Producto>();

    private ArrayList<Reabastecimiento> reabastecimientos = new ArrayList<Reabastecimiento>();
    private static ArrayList<Tienda> locales = new ArrayList<Tienda>();
    private ArrayList<Empleado> empleados = new ArrayList<Empleado>();

    /* ~~~ Constructores ~~~ */
    public Tienda(){
        super();
    }
    public Tienda(String nombre, long fondos){
        this.nombre = nombre;
        this.fondos = fondos;
        Tienda.locales.add(this);
    }
    /* ~~~ Métodos ~~~ */
    // Agregar producto al inventario correspondiente
    public void agregarProducto(Producto producto){
        if (producto.isPrestable()) {
            this.inventarioPrestamo.add(producto);
        } else if (producto.getCondicion() < 5) {
            this.inventarioUsado.add(producto);
        } else {
            this.inventario.add(producto);
        }
    }

    // Metodo que reduce la cantidad de un producto en un inventario dado segun codigo
    public void retirarDeInventario(Producto producto, ArrayList<Producto> inventario){
        for (Producto p : inventario) {
            if (p.getCodigo() == producto.getCodigo()) {
                p.setCantidad(p.getCantidad() - producto.getCantidad());
            }
        }
    }

    public void agregarEmpleado(Empleado empleado){
        this.empleados.add(empleado);
    }

    public void agregarTransaccion(Transaccion transaccion){
        this.caja.add(transaccion);
        this.agregarFondos(transaccion.getValorFinal());
    }

    public void agregarFondos(long fondos){
        this.fondos += fondos;
    }

    public void agregarSubasta(Subasta subasta){
        this.subastas.add(subasta);
    }

    // Recibe un producto que busca en el inventario e incrementa su cantidad en la cantidad dada
    public void reabastecerProducto(Producto producto, int cantidad){
        for (Producto p : this.inventario) {
            if (p.equals(producto)) {
                p.setCantidad(p.getCantidad() + cantidad);
                p.setCantidadInicial(p.getCantidadInicial() + cantidad);

                break;
            }
        }

        // En caso de que el producto no se encuentre
        this.inventario.add(producto);
    }

    // Metodo para retirar producto reabastecido en otro local
    public void retirarProducto(Producto producto, int cantidad){
        for (Producto p : this.inventario) {
            if (p.equals(producto)) {
                p.setCantidad(p.getCantidad() - cantidad);
                p.  setCantidadInicial(p.getCantidadInicial() - cantidad);
            }
        }
    }
    // Metodo para agregar orden de reabastecimiento
    public void agregarOrden(Reabastecimiento orden){
        this.reabastecimientos.add(orden);
    }


    /*~~ Getters y setters ~~*/
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public long getFondos() {
        return fondos;
    }
    public void setFondos(long fondos) {
        this.fondos = fondos;
    }
    public ArrayList<Transaccion> getCaja() {
        return caja;
    }
    public void setCaja(ArrayList<Transaccion> caja) {
        this.caja = caja;
    }

    public ArrayList<Producto> getInventario() {
        return inventario;
    }
    public void setInventario(ArrayList<Producto> inventario) {
        this.inventario = inventario;
    }

    public ArrayList<Producto> getInventarioPrestamo() {
        return inventarioPrestamo;
    }
    public void setInventarioPrestamo(ArrayList<Producto> inventarioPrestamo) {
        this.inventarioPrestamo = inventarioPrestamo;
    }

    public ArrayList<Producto> getInventarioUsado() {
        return inventarioUsado;
    }
    public void setInventarioUsado(ArrayList<Producto> inventarioUsado) {
        this.inventarioUsado = inventarioUsado;
    }

    public ArrayList<Reabastecimiento> getReabastecimientos() {
        return reabastecimientos;
    }
    public void setReabastecimientos(ArrayList<Reabastecimiento> reabastecimientos) {
        this.reabastecimientos = reabastecimientos;
    }
    public static ArrayList<Tienda> getLocales() {
        return locales;
    }
    public static void setLocales(ArrayList<Tienda> locales) {
        Tienda.locales = locales;
    }
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public ArrayList<Subasta> getSubastas() {
        return subastas;
    }
    public void setSubastas(ArrayList<Subasta> subastas) {
        this.subastas = subastas;
    }
}
