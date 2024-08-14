package gestorAplicacion.manejoLocal;
import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.productos.Producto;

import java.io.Serializable;
import java.io.Serial;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Tienda implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /*~~ Atributos ~~*/
    private String nombre;
    private long fondos;
    private ArrayList<Transaccion> caja = new ArrayList<Transaccion>();
    //TODO: Implementar inventario para artículos usados y de préstamo

    // Inventarios
    private ArrayList<Producto> inventario = new ArrayList<Producto>();
    private ArrayList<Producto> inventarioPrestamo = new ArrayList<Producto>();
    private ArrayList<Producto> inventarioUsado = new ArrayList<Producto>();

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
    }
    /*~~~ Métodos ~~~*/
    // Agregar producto al
    public void agregarProducto(Producto producto){
        if (producto.isPrestable()) {
            this.inventarioPrestamo.add(producto);
        } else if (producto.getCondicion() < 5) {
            this.inventarioUsado.add(producto);
        } else {
            this.inventario.add(producto);
        }
    }

    // Metodo que reduce la cantidad de un producto en el inventario segun el entero que se le ingrese
    public void venderProducto(Producto producto, int cantidad){
        for (Producto p : this.inventario) {
            if (p.equals(producto)) {
                p.setCantidad(p.getCantidad() - cantidad);
            }
        }
    }

    public void agregarEmpleado(Empleado empleado){
        this.empleados.add(empleado);
    }

    public void agregarTransaccion(Transaccion transaccion){
        this.caja.add(transaccion);
    }

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
                p.setCantidadInicial(p.getCantidadInicial() - cantidad);
            }
        }
    }

    // Método para encontrar un empleado en la tienda
    public Empleado identificarEmpleado() {
        // En caso de que la tienda no tenga empleados
        if (this.getEmpleados().size() == 0) {
            System.out.println("\n### ERROR ###");
            System.out.println("No hay empleados en esta tienda");
            return null;
        }


        Empleado empleado = null;
        java.util.Scanner sc = new java.util.Scanner(System.in);

        // Mostrar a todos los empleados del local
        for (Empleado e: this.getEmpleados()){
            System.out.println(" * " + e.getNombre() + " - " + e.getCedula());
        }
        // Recibir cédula del empleado
        System.out.println("Ingrese la cedula del empleado encargado de esta transacción");

        while (empleado == null) {
            try {
                int cedulaEmpleado = sc.nextInt();
                sc.nextLine();  // Limpiar el buffer

                for (Empleado e : this.getEmpleados()) {
                    if (e.getCedula() == cedulaEmpleado) {
                        empleado = e;
                        return empleado;
                    }
                }
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }
        return empleado;
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
}
