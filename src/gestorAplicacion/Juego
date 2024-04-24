package gestorAplicacion.arcProductos;

public class Juego extends Producto {
    private String genero;
    private String consola;

    // Constructor principal de la subclase Juego
    public Juego(String nombre, short año, String genero, String marca, String estado, byte condicion,
            float precio) {
        // Llama al constructor de la superclase Producto
        super(nombre, "N/A", año, "N/A", marca, estado, condicion, precio);
        this.genero = genero;
        setCodigo("Juego:" + getCodigo());
    }

    public Juego(String nombre, short año, String genero, String marca, float precio) {
        // Llama al constructor con estado y condicion por defecto
        this(nombre, año, genero, marca, "Stock", (byte) 10, precio);
    }

    public Juego(String nombre, short año, String genero, float precio) {
        // Llama al constructor con genero, marca y estado por defecto
        this(nombre, año, genero, "generica", precio);
    }

    public Juego(String nombre, String genero, float precio) {
        // Llama al constructor con año por defecto
        super(nombre, "N/A", (short) 0, "N/A", "generica", "Stock", (byte) 10, precio);
        this.genero = genero;
    }

    public Juego(String nombre, float precio) {
        // Llama al constructor con año por defecto
        super(nombre, "N/A", (short) 0, "N/A", "generica", "Stock", (byte) 10, precio);
        this.genero = "N/A";
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

}
