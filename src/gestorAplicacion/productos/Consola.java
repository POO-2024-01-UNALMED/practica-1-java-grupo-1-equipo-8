package gestorAplicacion.productos;

public class Consola extends Producto {
	
	/*### Atributos ###*/
	
	private String marca;
	public Consola(){}	// constructor vacío //
	public Consola (int codigo, String nombre, int valor, int cantidad, int cantidadInicial, boolean prestable, byte condiciones, int diaLanz, int mesLanz, int yearLanz, String marca) {
		super(codigo, nombre, valor, cantidad, cantidadInicial, prestable, condiciones, diaLanz, mesLanz, yearLanz);
		this.marca = marca;
	}
										//~~~~~~~~~~~~~~~~~~ Métodos get y set ~~~~~~~~~~~~~~~~~~//
	public String getMarca(){
		return marca;
	}
	public void setMarca(String mark){
		this.marca = mark;
	}
}
