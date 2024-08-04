package uiMain;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.*;

public class Funcionalidad1 {
    static Scanner sc = new Scanner(System.in);

    public static void registrarCompra(Tienda local) {
        /* ~~~ Identificación del cliente ~~~ */
        Cliente cliente = identificarCliente();

        if (cliente == null) {
            return;
        }




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
                    agregarProducto(local);
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
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
                    sc.nextLine();  // Limpiar el buffer
                    sc.nextLine();  // Esperar a que el usuario presione Enter
                    break;
            }
        } while (opcion != 4);
    }

    private static Cliente identificarCliente() {
        imprimirSeparador();

        Cliente cliente = null;
        int cedula;

        // Elegir si el cliente es nuevo o uno ya existente
        System.out.println("¿Cliente nuevo o existente?");
        byte opcion = 0;
        do {
            System.out.println("1. Nuevo");
            System.out.println("2. Existente");

            // Recibir entrada del usuario
            try {
                opcion = sc.nextByte();
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            switch (opcion) {
                case 1:
                    // Nuevo cliente
                    System.out.println("Ingrese cédula del cliente:");
                    cedula = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    System.out.println("Ingrese nombre del cliente:");
                    String nombre = sc.nextLine();

                    System.out.println("Ingrese correo del cliente:");
                    String correo = sc.nextLine();

                    System.out.println("Ingrese teléfono del cliente:");
                    long telefono = sc.nextLong();
                    sc.nextLine();  // Limpiar el buffer

                    return new Cliente(cedula, nombre, correo, telefono);

                case 2:
                    // Cliente existente

                    while (cliente == null) {
                        System.out.println("Ingrese cédula del cliente:");

                        // Buscar al cliente en la lista de clientes por su cédula
                        try {
                            cedula = sc.nextInt();

                            for (Cliente c : Cliente.clientes) {
                                if (c.getCedula() == cedula) {
                                    cliente = c;
                                    return cliente;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                            sc.nextLine();  // Limpiar el buffer
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            continue;
                        }

                        // En caso de que el cliente no sea encontrado dar la opción de intentar de nuevo
                        if (cliente == null) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("Cliente no encontrado. ¿Desea intentar de nuevo? (Y/n).\n");
                            char decision = 'y';
                            decision = sc.next().charAt(0);
                            if (decision == 'n' || decision == 'N') {
                                return null;
                            }
                        }
                    }

                    sc.nextLine();  // Limpiar el buffer
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
        } while (opcion < 1 || opcion > 2);

        return cliente;
    }

    private static void agregarProducto (Tienda local) {
        byte opcion = 0;

        do {
            imprimirSeparador();
            System.out.println("Ingrese el tipo del producto:");

            System.out.println("1. Consola");
            System.out.println("2. Juego");
            System.out.println("3. Accesorio");

            // Recibir entrada del usuario
            try {
                opcion = sc.nextByte();
            }
            catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            switch (opcion) {
                case 1:
                    // Consola
                    System.out.println("Consolas disponibles:");
                    for (Producto p : local.getInventario()) {
                        if (p instanceof Consola) {
                            System.out.println("* " + p);
                        }
                    }
                    break;

                case 2:
                    // Juego
                    for (Producto p : local.getInventario()) {
                        if (p instanceof Juego) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 3:
                    // Accesorio
                    for (Producto p : local.getInventario()) {
                        if (p instanceof Accesorio) {
                            System.out.println(p);
                        }
                    }
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }

        } while (opcion < 1 || opcion > 3);
    }
}