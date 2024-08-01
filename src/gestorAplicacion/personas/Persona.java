package gestorAplicacion.personas;
import java.io.Serial;
import java.io.Serializable;
public abstract class Persona implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    /*~~ Atributos ~~*/
    private long cedula;
    private String nombre;
    private String correo;
    private long telefono;

            /*~~ Metodos get y set ~~*/

    public long getCedula() {
        return cedula;
    }
    public void setCedula(long cedula) {
        this.cedula = cedula;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public long getTelefono() {
        return telefono;
    }
    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
}
