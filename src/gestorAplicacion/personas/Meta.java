package gestorAplicacion.personas;
import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Fecha;
public class Meta implements Serializable{
    /*~~ Atributos ~~*/
    private Empleado empleado;
    private Fecha fecha;
    private int valorAlcanzar;
    private int valorBonificacion;
    private String estado = "En proceso";
    private int acumulado;
    private int codigo;

                    /*~~ Constructor ~~*/
    public Meta (int codigo, Empleado empleado, int diaLimite, int mesLimite, int yearLimite, int valorAlcanzar, int valorBonificacion, int acumulado){
        this.codigo = codigo;
        this.empleado = empleado;
        this.fecha = new Fecha(diaLimite, mesLimite, yearLimite);
        this.valorAlcanzar = valorAlcanzar;
        this.valorBonificacion = valorBonificacion;
        this.acumulado = acumulado;
        empleado.ingresarMeta(this);
    }

                    /*~~ Metodos get y set ~~*/

    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public int getValorAlcanzar() {
        return valorAlcanzar;
    }
    public void setValorAlcanzar(int valorAlcanzar) {
        this.valorAlcanzar = valorAlcanzar;
    }
    public int getValorBonificacion() {
        return valorBonificacion;
    }
    public void setValorBonificacion(int valorBonificacion) {
        this.valorBonificacion = valorBonificacion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getAcumulado() {
        return acumulado;
    }
    public void setAcumulado(int acumulado) {
        this.acumulado = acumulado;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public Fecha getFecha() {
        return fecha;
    }
    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
    public String toString(){
        return ("* Código de meta: " + this.getCodigo() + " | Valor a alcanzar: " + this.getValorAlcanzar() + " |  Valor de bonificación: " + this.getValorBonificacion() + " | Fecha límite: " + this.getFecha().toString());
    }
}