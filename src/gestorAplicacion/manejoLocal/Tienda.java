package gestorAplicacion.manejoLocal;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.productos.Producto;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;

public class Tienda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /*~~ Atributos ~~*/
    private String nombre;
    private long fondos;
    private ArrayList<Fecha> caja = new ArrayList<Fecha>();
    private ArrayList<Producto> inventario = new ArrayList<Producto>();
    private ArrayList<Reabastecimiento> reabastecimientos = new ArrayList<Reabastecimiento>();
    private static ArrayList<Tienda> locales = new ArrayList<Tienda>();
    public ArrayList<Empleado> empleados = new ArrayList<Empleado>();

    /*~~~ Constructores ~~~*/
    public Tienda(){
        super();
    }
    public Tienda(String nombre, long fondos){
        this.nombre = nombre;
        this.fondos = fondos;
        Tienda.locales.add(this);
        this.empleados = new ArrayList<Empleado>();

    }
    /*~~~ Métodos ~~~*/
    public void agregarProducto(Producto producto){
        this.inventario.add(producto);
    }

    public void agregarEmpleado(Empleado empleado){
        this.empleados.add(empleado);
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
}
