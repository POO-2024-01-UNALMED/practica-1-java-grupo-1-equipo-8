package gestorAplicacion.personas;
import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Fecha;
public class Meta implements Serializable{
    /*~~ Atributos ~~*/
    private static int ultimoID = 1;
    private int codigo;
    private Empleado empleado;
    private Fecha fecha;
    private int valorAlcanzar;
    private int valorBonificacion;
    private String estado = "En proceso";
    private int acumulado;

                    /*~~ Constructor ~~*/
    public Meta (Empleado empleado, int diaLimite, int mesLimite, int yearLimite, int valorAlcanzar, int valorBonificacion){
        this.codigo = ultimoID;
        this.empleado = empleado;
        this.fecha = new Fecha(diaLimite, mesLimite, yearLimite);
        this.valorAlcanzar = valorAlcanzar;
        this.valorBonificacion = valorBonificacion;
        empleado.ingresarMeta(this);
        ultimoID++;
    }

                    /*~~ Incrementar acumulado ~~*/
    // Incrementa el acumulado de cada meta activa del empleado
    public void incrementarAcumulado(int valor){
        if (this.estado.equals("En proceso")) {
            this.acumulado += valor;
        }
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
    public void setAcumuladoAcumulado(int acumulado) {
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
        return ("* Codigo de meta: " + this.getCodigo() + " | Valor a alcanzar: " + this.getValorAlcanzar() + " |  Valor de bonificacion: " + this.getValorBonificacion() + " | Fecha limite: " + this.getFecha().toString());
    }
}