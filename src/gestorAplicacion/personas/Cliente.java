package gestorAplicacion.personas;

import gestorAplicacion.informacionVenta.Prestamo;
import gestorAplicacion.informacionVenta.Transaccion;

import java.util.ArrayList;

public class Cliente extends Persona{

    /*~~ Atributos ~~*/
    private int puntosFidelidad;
    private ArrayList<Prestamo> prestamos;
    private ArrayList<Transaccion> compras;

                /*~~ Metodos get y set ~~*/

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
}