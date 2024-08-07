package gestorAplicacion.personas;

import gestorAplicacion.manejoLocal.Tienda;

import java.util.ArrayList;

public class Empleado extends Persona{
    /* ~~~ Atributos ~~~ */
    private int salario;
    private int salarioPorcentual;
    private int acumuladoMensual;
    private ArrayList<Meta> metas = new ArrayList<Meta>();
    private ArrayList<Meta> metasAlcanzadas = new ArrayList<Meta>();
    private ArrayList<Meta> metasCaducadas = new ArrayList<Meta>();
    private Tienda tienda;


    /* ~~~ Constructores ~~~ */
    public Empleado(int cedula, String nombre, String correo, long telefono, int salario, int salarioPorcentual, Tienda tienda) {
        super(cedula, nombre, correo, telefono);
        this.salario = salario;
        this.salarioPorcentual = salarioPorcentual;
        this.acumuladoMensual = 0;
        this.metas = new ArrayList<Meta>();
        tienda.agregarEmpleado(this);
    }

    public void ingresarMeta(Meta meta){
        this.metas.add(meta);
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
    public ArrayList<Meta> getMetasAlcanzadas() {
        return metasAlcanzadas;
    }
    public void setMetasAlcanzadas(ArrayList<Meta> metasAlcanzadas) {
        this.metasAlcanzadas = metasAlcanzadas;
    }
    public void ingresarMetasAlcanzdas(Meta meta){
        this.metasAlcanzadas.add(meta);
    }
    public ArrayList<Meta> getMetasCaducadas() {
        return metasCaducadas;
    }
    public void setMetasCaducadas(ArrayList<Meta> metasCaducadas) {
        this.metasCaducadas = metasCaducadas;
    }
    public void ingresarMetasCaducadas(Meta meta) {
        this.metasCaducadas.add(meta);
    }
    public Tienda getTienda() {
        return tienda;
    }
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
