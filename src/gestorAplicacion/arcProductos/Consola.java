package gestorAplicacion.arcProductos;

public class Consola extends Producto {
    // Constructor principal de la subclase Consola
    public Consola(String nombre, String modelo, short año, String color, String marca, String estado, byte condicion,
            float precio) {
        // Llama al constructor de la superclase Producto
        super(nombre, modelo, año, color, marca, estado, condicion, precio);
        // Modifica el atributo codigo
        setCodigo("Consola:" + getCodigo());
    }

    public Consola(String nombre, String modelo, short año, String color, String marca, float precio) {
        // Llama al constructor principal de la subclase Consola
        this(nombre, modelo, año, color, marca, "Stock", (byte) 10, precio);
    }

    public Consola(String nombre, String modelo, short año, float precio) {
        // Llama al constructor principal de la subclase Consola
        this(nombre, modelo, año, "N/A", "N/A", "Stock", (byte) 10, precio);
    }

    public Consola(String nombre, String modelo, float precio) {
        // Llama al constructor principal de la subclase Consola
        this(nombre, modelo, (short) 0, "N/A", "N/A", "Stock", (byte) 10, precio);
    }

    public Consola(String nombre, float precio) {
        // Llama al constructor principal de la subclase Consola
        this(nombre, "N/A", (short) 0, "N/A", "N/A", "Stock", (byte) 10, precio);
    }
}
