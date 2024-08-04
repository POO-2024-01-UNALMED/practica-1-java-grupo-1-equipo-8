package gestorAplicacion.personas;

import java.util.ArrayList;

public class Empleado extends Persona{
    /* ~~~ Atributos ~~~ */
    private int salario;
    private int salarioPorcentual;
    private int acumuladoMensual;
    private ArrayList<Meta> metas;

    /* ~~~ Constructores ~~~ */
    public Empleado(int cedula, String nombre, String correo, long telefono, int salario, int salarioPorcentual) {
        super(cedula, nombre, correo, telefono);
        this.salario = salario;
        this.salarioPorcentual = salarioPorcentual;
        this.acumuladoMensual = 0;
        this.metas = new ArrayList<Meta>();
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
        this.acumuladoMensual = acumuladoMensual;
    }
    public ArrayList<Meta> getMetas() {
        return metas;
    }
    public void setMetas(ArrayList<Meta> metas) {
        this.metas = metas;
    }

}
