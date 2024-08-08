package gestorAplicacion.personas;
import java.io.Serializable;
import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Fecha;
public class Meta implements Serializable{
    /*~~ Atributos ~~*/
    private Empleado empleado;
    private int diaLimite;
    private int mesLimite;
    private int yearLimite;
    private int valorAlcanzar;
    private int valorBonificacion;
    private String estado = "En proceso";
    private int acumulado;
    private int codigo;

                    /*~~ Constructor ~~*/
    public Meta (int codigo, Empleado empleado, int diaLimite, int mesLimite, int yearLimite, int valorAlcanzar, int valorBonificacion, int acumulado){
        this.codigo = codigo;
        this.empleado = empleado;
        this.diaLimite = diaLimite;
        this.mesLimite = mesLimite;
        this.yearLimite = yearLimite;
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
    public int getDiaLimite() {
        return diaLimite;
    }
    public void setDiaLimite(int diaLimite) {
        this.diaLimite = diaLimite;
    }
    public int getMesLimite() {
        return mesLimite;
    }
    public void setMesLimite(int mesLimite) {
        this.mesLimite = mesLimite;
    }
    public int getYearLimite() {
        return yearLimite;
    }
    public void setYearLimite(int yearLimite) {
        this.yearLimite = yearLimite;
    }
}