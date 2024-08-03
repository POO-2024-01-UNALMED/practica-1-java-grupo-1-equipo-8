package uiMain;

import java.util.ArrayList;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.personas.Cliente;
import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.productos.Producto;

public class Funcionalidad1 {
    static Scanner sc = new Scanner(System.in);

    public void registrarCompra() {
        /* ~~~ Calcular recomendaciones ~~~ */



        /* ~~~ Selección de productos ~~~ */
        byte opcion = 0;
        ArrayList<Producto> carrito = new ArrayList<Producto>();

        do {
            imprimirSeparador();
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Ver productos en el carrito");

            System.out.println("4. Confirmar compra");

            // Recibir entrada del usuario
            opcion = sc.nextByte();

            switch (opcion) {
                case 1:
                    // Agregar producto
                    agregarProducto();
                    break;

                case 2:
                    // Eliminar producto
                    break;

                case 3:
                    // Ver productos en el carrito
                    break;

                case 4:
                    // Confirmar compra
                    break;

                default:
                    System.out.println("\n##################################");
                    System.out.println("Opción inválida. Intente de nuevo.");
                    System.out.println("##################################\n");
                    break;
            }
        } while (opcion != 4);
    }

    private void agregarProducto () {
        byte option = 0;

        do {
            imprimirSeparador();
            System.out.println("Ingrese el tipo del producto:");

            System.out.println("1. Consola");
            System.out.println("2. Juego");
            System.out.println("3. Accesorio");

            // Recibir entrada del usuario
            option = sc.nextByte();

        } while (option < 1 || option > 3);
    }
}