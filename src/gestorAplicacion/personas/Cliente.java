package gestorAplicacion.personas;

import gestorAplicacion.informacionVenta.Prestamo;
import gestorAplicacion.informacionVenta.Transaccion;

import java.util.ArrayList;

public class Cliente extends Persona{
    /*~~ Atributos ~~*/
    public static ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private int puntosFidelidad = 0;
    private ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
    private ArrayList<Transaccion> compras = new ArrayList<Transaccion>();

    /* ~~~ Constructores ~~~ */
    public Cliente(int cedula, String nombre, String email, long telefono) {
        super(cedula, nombre, email, telefono);
        clientes.add(this);
    }

    /* ~~~ Métodos ~~~ */
    public void agregarPrestamo(Prestamo prestamo){
        this.prestamos.add(prestamo);
    }
    public void agregarCompra(Transaccion compra){
        this.compras.add(compra);
    }

    /* ~~~ Metodos get y set ~~~ */

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }
    public void setPuntosFidelidad(int puntosFidelidad) {
        this.puntosFidelidad = puntosFidelidad;
    }
    public ArrayList<Prestamo> getPrestamos() {
        return prestamos;
    }
    public void setPrestamos(ArrayList<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
    public ArrayList<Transaccion> getCompras() {
        return compras;
    }
    public void setCompras(ArrayList<Transaccion> compras) {
        this.compras = compras;
    }

    public static ArrayList<Cliente> getClientes(){
        return clientes;
    }
    public static void setClientes(ArrayList<Cliente> clientes){
        Cliente.clientes = clientes;
    }

}