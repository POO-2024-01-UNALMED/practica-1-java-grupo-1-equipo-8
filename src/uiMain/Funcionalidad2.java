package uiMain;

import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Accesorio;
import gestorAplicacion.productos.Consola;
import gestorAplicacion.productos.Juego;
import gestorAplicacion.productos.Producto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.identificarCliente;
import static uiMain.Main.imprimirSeparador;

public class Funcionalidad2 {
    static Scanner sc = new Scanner(System.in); // variable scanner


    /* ~~~ Identificación del cliente ~~~ */
    public static void registrarPrestamo(Tienda local, Fecha fecha) {
        Cliente cliente = identificarCliente();

        if (cliente == null) {
            return;
        }

        byte opcion = 0;
        ArrayList<Producto> carrito = new ArrayList<Producto>();

        do {
            imprimirSeparador();
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Ver productos en el carrito");

            System.out.println("4. Confirmar compra");

            // Recibir entrada del usuario
            try {
                opcion = sc.nextByte();
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.\n");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }


            sc.nextLine();  // Limpiar el buffer

            Producto producto;

            switch (opcion) {
                case 1: // Agregar producto

                    // Clonar el producto seleccionado para evitar modificar el original
                    try {
                        producto = (seleccionarProducto(local.getInventarioPrestamo()).clone());
                    } catch (CloneNotSupportedException e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Error al clonar el producto. Presione Enter para cancelar la operación.");
                        sc.nextLine();  // Limpiar el buffer
                        sc.nextLine();  // Esperar a que el usuario presione Enter

                        return;
                    }


                    // Verificar si el producto ya está en el carrito
                    for (Producto p : carrito) {
                        if (p.getNombre().equals(producto.getNombre())) {
                            System.out.println("\n¡Sólo está permitido un ejemplar por préstamo! (ノ ゜Д゜)ノ ︵ ┻━┻");
                            System.out.println("\nPresione Enter para continuar.");
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            break;
                        }
                    }

                    carrito.add(producto);

                    System.out.println("Producto agregado al carrito.");

                    break;

                case 2: // Eliminar producto

                    Producto productoEnCarrito = seleccionarProducto(carrito);

                    if (productoEnCarrito.getCantidad() > 1) {
                        productoEnCarrito.setCantidad(productoEnCarrito.getCantidad() - 1);
                    } else {
                        carrito.remove(productoEnCarrito);
                    }

                    System.out.println("Producto eliminado del carrito.");

                    break;

                case 3: // Ver productos en el carrito

                    // Comprobar que el carrito no esté vacío
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito está vacío.");
                        System.out.println("\nPresione Enter para continuar.");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                    }

                    System.out.println("CARRITO:");

                    for (Producto p : carrito) {
                        System.out.println("* " + p.getNombre());
                    }

                    System.out.println("\nPresione Enter para continuar.");
                    sc.nextLine();  // Esperar a que el usuario presione Enter

                    break;

                case 4: // Confirmar carrito para préstamo
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
                    sc.nextLine();  // Esperar a que el usuario presione Enter
                    break;
            }

            // Método para seleccionar un producto del inventario de préstamos
        }
        while(opcion != 4);
    }

    private static Producto seleccionarProducto (ArrayList < Producto > inventarioPrestamo) {
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
            } catch (InputMismatchException error) {
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

                    // En caso de que no haya consolas disponibles
                    boolean hayConsolas = false;
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Consola && p.getCantidad() > 0) {
                            hayConsolas = true;
                            break;
                        }
                    }

                    if (!hayConsolas) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("No hay consolas disponibles para préstamo.");
                        System.out.println("Presione Enter para volver al menú principal.\n");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    // Mostrar consolas disponibles
                    System.out.println("Consolas disponibles:");
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Consola && p.getCantidad() > 0) {
                            System.out.println("* " + p.toStringPrestable());
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código de la consola que desea seleccionar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Consola && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado.");
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

                    // En caso de que no haya juegos disponibles
                    boolean hayJuegos = false;
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Juego && p.getCantidad() > 0) {
                            hayJuegos = true;
                            break;
                        }
                    }

                    if (!hayJuegos) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("No hay juegos disponibles para préstamo.");
                        System.out.println("Presione Enter para volver al menú principal.\n");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    // Mostrar juegos disponibles
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Juego) {
                            System.out.println("* " + p.toStringPrestable());
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código del juego que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Juego && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado.");
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

                    // En caso de que no haya accesorios disponibles

                    boolean hayAccesorios = false;
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Accesorio && p.getCantidad() > 0) {
                            hayAccesorios = true;
                            break;
                        }
                    }

                    if (!hayAccesorios) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("No hay accesorios disponibles para préstamo.");
                        System.out.println("Presione Enter para volver al menú principal.\n");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }


                    // Mostrar todos los accesorios disponibles
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Accesorio) {
                            System.out.println("* " + p.toStringPrestable());
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código del accesorio que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Accesorio && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado");
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
}