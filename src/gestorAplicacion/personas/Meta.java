package gestorAplicacion.personas;
import java.io.Serializable;
import gestorAplicacion.manejoLocal.Fecha;
public class Meta implements Serializable{
    /*~~ Atributos ~~*/
    private Empleado empleado;
    private Fecha fechaLimite;
    private int valorAlcanzar;
    private int valorBonificacion;
    private String estado;

                    /*~~ Metodos get y set ~~*/

    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Fecha getFechaLimite() {
        return fechaLimite;
    }
    public void setFechaLimite(Fecha fechaLimite) {
        this.fechaLimite = fechaLimite;
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



}