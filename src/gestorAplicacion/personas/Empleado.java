package gestorAplicacion.personas;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Tienda;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Empleado extends Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /* ~~~ Atributos ~~~ */
    private int salario;
    private int salarioPorcentual;
    private int acumuladoMensual;
    private byte diasLaborales;
    private ArrayList<Meta> metas = new ArrayList<Meta>();
    private ArrayList<Meta> metasAlcanzadas = new ArrayList<Meta>();
    private ArrayList<Meta> metasCaducadas = new ArrayList<Meta>();
    private ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
    private Tienda tienda;


    /* ~~~ Constructores ~~~ */
    public Empleado(int cedula, String nombre, String correo, long telefono, int salario, int salarioPorcentual, byte diasLaborales, Tienda tienda) {
        super(cedula, nombre, correo, telefono);
        this.salario = salario;
        this.salarioPorcentual = salarioPorcentual;
        this.acumuladoMensual = 0;
        this.diasLaborales = diasLaborales;
        this.metas = new ArrayList<Meta>();
        tienda.agregarEmpleado(this);
    }

    public void ingresarMeta(Meta meta){
        this.metas.add(meta);
    }
    public void ingresarMetasCaducadas(Meta meta) {
        this.metasCaducadas.add(meta);
    }
    public void ingresarMetasAlcanzdas(Meta meta){
        this.metasAlcanzadas.add(meta);
    }
    public void ingresarTransaccion(Transaccion transaccion) {
        this.transacciones.add(transaccion);
        //TODO: Aumentar acumulado cada vez que se llama esta función o (quitar atributo acumulado)
    }

    /* ~~~ Metodos get y set ~~~ */

    public int getSalario() {
        return salario;
    }
    public void setSalario(int salario) {
        this.salario = salario;
    }
    public int getSalarioPorcentual() {
        return salarioPorcentual;
    }
    public void setSalarioPorcentual(int salarioPorcentual) {
        this.salarioPorcentual = salarioPorcentual;
    }
    public int getAcumuladoMensual() {
        return acumuladoMensual;
    }
    public void setAcumuladoMensual(int acumuladoMensual) {
        this.acumuladoMensual = getAcumuladoMensual() + acumuladoMensual;
    }
    public byte getDiasLaborales() {
        return diasLaborales;
    }
    public void setDiasLaborales(byte diasLaborales) {
        this.diasLaborales = diasLaborales;
    }
    public ArrayList<Meta> getMetas() {
        return metas;
    }
    public void setMetas(ArrayList<Meta> metas) {
        this.metas = metas;
    }
    public ArrayList<Meta> getMetasAlcanzadas() {
        return metasAlcanzadas;
    }
    public void setMetasAlcanzadas(ArrayList<Meta> metasAlcanzadas) {
        this.metasAlcanzadas = metasAlcanzadas;
    }
    public ArrayList<Meta> getMetasCaducadas() {
        return metasCaducadas;
    }
    public void setMetasCaducadas(ArrayList<Meta> metasCaducadas) {
        this.metasCaducadas = metasCaducadas;
    }
    public Tienda getTienda() {
        return tienda;
    }
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
    public ArrayList<Transaccion> getTransacciones() {
        return transacciones;
    }
    public void setTransacciones(ArrayList<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}
