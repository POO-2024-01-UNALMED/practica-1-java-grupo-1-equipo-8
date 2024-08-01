package gestorAplicacion.informacionVenta;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Producto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
public class Prestamo implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

                    /*~~ Atributos ~~*/
    private int id;
    private int cod;
    private Fecha fechaInicio;
    private Fecha fechaFin;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private float valorTotal;
    private String estado;
    private int dias;
    private int exceso_dias;
    private float multa;

                /*~~ Metodos get y set ~~*/

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public Fecha getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Fecha fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Fecha getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Fecha fechaFin) {
        this.fechaFin = fechaFin;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public ArrayList<Producto> getProductos() {
        return productos;
    }
    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    public float getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getDias() {
        return dias;
    }
    public void setDias(int dias) {
        this.dias = dias;
    }
    public int getExceso_dias() {
        return exceso_dias;
    }
    public void setExceso_dias(int exceso_dias) {
        this.exceso_dias = exceso_dias;
    }
    public float getMulta() {
        return multa;
    }
    public void setMulta(float multa) {
        this.multa = multa;
    }
}