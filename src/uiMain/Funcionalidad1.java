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
        byte opcion;
        ArrayList<Producto> carrito = new ArrayList<Producto>();

        do {
            imprimirSeparador();
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Ver productos en el carrito");

            System.out.println("4. Confirmar compra");

            // Recibir entrada del usuario
            opcion = sc.nextByte();
            sc.nextLine();  // Limpiar el buffer

            Producto producto;

            switch (opcion) {
                case 1:
                    // Agregar producto

                    // Clonar el producto seleccionado para evitar modificar el original
                    try {
                        producto = (seleccionarProducto(local).clone());
                    }
                    catch (CloneNotSupportedException e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Error al clonar el producto. Presione Enter para cancelar la operación.");
                        sc.nextLine();  // Limpiar el buffer
                        sc.nextLine();  // Esperar a que el usuario presione Enter

                        return;
                    }


                    // Verificar si el producto ya está en el carrito
                    Producto productoEnCarrito = hallarEnCarrito(producto, carrito);

                    if (productoEnCarrito != null) { // Si está, aumentar la cantidad en 1
                        if (productoEnCarrito.getCantidad() >= producto.getCantidad()) { // Pero si no hay la cantidad suficiente, mostrar mensaje de error
                            System.out.println("No hay más unidades de '" + producto.getNombre() + "' disponibles.");
                            System.out.println("\nPresione Enter para continuar.");
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            break;
                        } else {
                            productoEnCarrito.setCantidad(productoEnCarrito.getCantidad() + 1);
                        }
                    }
                    else { // De no estar, agregarlo al carrito con cantidad 1
                        producto.setCantidad(1);
                        carrito.add(producto);
                    }

                    break;

                case 2:
                    // Eliminar producto
                    // TODO: Implementar la eliminación de productos del carrito

                    // Mostrar productos en carrito

                    System.out.println("CARRITO:");
                    int i = 1;
                    for (Producto p : carrito) {
                        System.out.println(i + ". " + p.getNombre() " | " + p.getCantidad() + " unidades");
                        i++;
                    }

                    // Recibir selección del usuario
                    System.out.println("Ingrese el número del producto que desea eliminar:");
                    try {
                        int seleccion = sc.nextInt();
                        sc.nextLine();  // Limpiar el buffer
                    }
                    catch { // En caso de que la selección no sea válida
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.\n");
                        sc.nextLine();  // Limpiar el buffer
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                    }

                    // TODO: Implementar la eliminación del producto seleccionado del carrito
                    // TODO: Cambiar el formato de los códigos a uno con letra y número

                    break;

                case 3:
                    // Ver productos en el carrito

                    // Comprobar que el carrito no esté vacío
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito está vacío.");
                        System.out.println("\nPresione Enter para continuar.");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                    }

                    System.out.println("CARRITO:");

                    for (Producto p : carrito) {
                        System.out.println("* " + p);
                    }

                    System.out.println("\nPresione Enter para continuar.");
                    sc.nextLine();  // Esperar a que el usuario presione Enter

                    break;

                case 4:
                    // Confirmar compra
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
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

    private static Producto seleccionarProducto(Tienda local) {
        byte opcion = 0;

        do {
            imprimirSeparador();
            System.out.println("Ingrese el tipo del producto:");

            System.out.println("1. Consola");
            System.out.println("2. Juego");
            System.out.println("3. Accesorio");

            // Recibir selección del usuario
            try {
                opcion = sc.nextByte();
            }
            catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }

            // Selección de producto
            int codigo = 0;

            switch (opcion) {
                case 1:
                    // Consola
                    // Mostrar consolas disponibles
                    System.out.println("Consolas disponibles:");
                    for (Producto p : local.getInventario()) {
                        if (p instanceof Consola && p.getCantidad() > 0) {
                            System.out.println("* " + p);
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código de la consola que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : local.getInventario()) {
                        if (p instanceof Consola && p.getCodigo() == codigo) {
                            System.out.println( "'" + p.getNombre() + "' agregado al carrito.");
                            return p;
                        }
                    }

                    // Esta parte sólo se ejecutará si no se encontró el código dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Consola no encontrada. Presione Enter para volver a intentar.\n");
                    sc.nextLine();  // Esperar a que el usuario presione Enter
                    break;

                case 2:
                    // Juego
                    // Mostrar juegos disponibles
                    for (Producto p : local.getInventario()) {
                        if (p instanceof Juego) {
                            System.out.println(p);
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código del juego que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : local.getInventario()) {
                        if (p instanceof Juego && p.getCodigo() == codigo) {
                            System.out.println( "'" + p.getNombre() + "' agregado al carrito.");
                            return p;
                        }
                    }

                    // Esta parte sólo se ejecutará si no se encontró el código dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Juego no encontrado. Presione Enter para volver a intentar.\n");
                    sc.nextLine();  // Esperar a que el usuario presione Enter
                    break;


                case 3:
                    // Accesorio
                    for (Producto p : local.getInventario()) {
                        if (p instanceof Accesorio) {
                            System.out.println(p);
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código del accesorio que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : local.getInventario()) {
                        if (p instanceof Accesorio && p.getCodigo() == codigo) {
                            System.out.println( "'" + p.getNombre() + "' agregado al carrito.");
                            return p;
                        }
                    }

                    // Esta parte sólo se ejecutará si no se encontró el código dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Accesorio no encontrado. Presione Enter para volver a intentar.\n");
                    sc.nextLine();  // Esperar a que el usuario presione Enter
                    break;


                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }

        } while (true);
    }

    private static Producto hallarEnCarrito(Producto producto, ArrayList<Producto> carrito){
        for (Producto p : carrito) {
            if (p.getNombre().equals(producto.getNombre())) {
                return p;
            }
        }

        return null;
    }

}