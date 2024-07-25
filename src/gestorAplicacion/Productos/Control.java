package gestorAplicacion.Productos;

public class Control extends Producto {
    private String consola;
    private String juego;

    // Constructor principal de la subclase Control
    public Control(String nombre, String modelo, short año, String color, String marca, String estado, byte condicion,
            float precio) {
        // Llama al constructor de la superclase Producto
        super(nombre, modelo, año, color, marca, estado, condicion, precio);
        // Modifica el atributo codigo
        setCodigo("Control:" + getCodigo());
    }

    public Control(String nombre, String modelo, short año, String color, String marca, String estado, float precio) {
        // Llama al constructor principal de la subclase Control
        this(nombre, modelo, año, color, marca, estado, (byte) 10, precio);
    }

    public Control(String nombre, String modelo, short año, String color, String marca, float precio) {
        // Llama al constructor principal de la subclase Control
        this(nombre, modelo, año, color, marca, "Stock", (byte) 10, precio);
    }

    public Control(String nombre, String modelo, short año, String color, float precio) {
        // Llama al constructor principal de la subclase Control
        this(nombre, modelo, año, color, "Generica", "Stock", (byte) 10, precio);
    }

    public Control(String nombre, String modelo, short año, float precio) {
        // Llama al constructor principal de la subclase Control
        this(nombre, modelo, año, "N/A", "Generica", "Stock", (byte) 10, precio);
    }

    public Control(String nombre, String modelo, float precio) {
        // Llama al constructor principal de la subclase Control
        this(nombre, modelo, (short) 0, "N/A", "Generica", "Stock", (byte) 10, precio);
    }

    public Control(String nombre, float precio) {
        // Llama al constructor principal de la subclase Control
        this(nombre, "N/A", (short) 0, "N/A", "Generica", "Stock", (byte) 10, precio);
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public String getConsola() {
        return this.consola;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }

    public String getJuego() {
        return this.juego;
    }
}
