package uiMain;

import gestorAplicacion.informacionVenta.Prestamo;
import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.productos.Accesorio;
import gestorAplicacion.productos.Consola;
import gestorAplicacion.productos.Juego;
import gestorAplicacion.productos.Producto;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.*;

public class Funcionalidad2 {
    static Scanner sc = new Scanner(System.in); // variable scanner

    public static void registrarPrestamo(Tienda local, Fecha fecha) {

        Cliente cliente = identificarCliente();

        if (cliente == null) {
            return;
        }

        // Comprobar si hay prestamos vencidos
        comprobarPrestamos(cliente, fecha);

        if (tieneVencidos(cliente)) {
            System.out.println("El cliente tiene uno o más prestamos vencidos:");
        }

        /* ~~ Devolucion de productos prestados ~~ */
        // Comprobar que el cliente tenga prestamos activos o vencidos
        boolean prestamoActivo = false;
        for (Prestamo p : cliente.getPrestamos()) {
            if (p.getEstado().equals("Activo") || p.getEstado().equals("Vencido")) {
                prestamoActivo = true;
                break;
            }
        }

        // Devolver productos prestados
        if (prestamoActivo) {
            if (siNo("¿Desea devolver productos prestados?")) {
                for (Prestamo p : cliente.getPrestamos()) {
                    if (p.getEstado().equals("Vencido")) { // Para los prestamos vencidos
                        int multa = 0;
                        int diasVencidos = fecha.getTotalDias() - p.getFechaFin().getTotalDias();

                        for (Producto producto : p.getProductos()) {
                            multa += (int) (producto.getValor() * 0.1 * diasVencidos);
                        }

                        System.out.println(p);
                        System.out.println("Este prestamo esta vencido.");

                        if (siNo("¿Desea devolver los productos de este prestamo?")) {
                            multa += comprobarDevolucion(p);
                            cobrarMulta(multa, local);
                            devolverProductosPrestados(p, local);
                            System.out.println("Productos devueltos.");
                        }
                    }

                    if (p.getEstado().equals("Activo")) {
                        int multa = 0;
                        System.out.println("* Prestamo con ID " + p.getId() + " generado el " + p.getFechaInicio() + ", con fecha de fin el " + p.getFechaFin() + " y productos: " + p.getProductos());

                        if (siNo("¿Desea devolver los productos de este prestamo?")) {
                            multa = comprobarDevolucion(p);
                            cobrarMulta(multa, local);
                            devolverProductosPrestados(p, local);
                            System.out.println("Productos devueltos.");
                        }
                    }
                }

                // Volver a comprobar si hay prestamos vencidos
                comprobarPrestamos(cliente, fecha);

                if (tieneVencidos(cliente)) {
                    System.out.println("El cliente sigue teniendo prestamos vencidos. No se puede realizar un nuevo prestamo.");
                    return;
                }
            }
        }

        // Consultar si se desea realizar un prestamo
        if (!siNo("¿Desea realizar un prestamo?")) {    // En caso que no
            return;
        } else {   // En caso que si
            /* ~~ Seleccion de productos para el prestamo ~~ */
            byte opcion = 0;
            ArrayList<Producto> carrito = new ArrayList<Producto>();

            do {
                imprimirSeparador();
                System.out.println("1. Agregar producto");
                System.out.println("2. Eliminar producto");
                System.out.println("3. Ver productos en el carrito");
                System.out.println("4. Confirmar Prestamo");
                System.out.println("0. Cancelar y salir");

                // Recibir entrada del usuario
                try {
                    opcion = sc.nextByte();
                } catch (Exception e) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.\n");
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
                            System.out.println("Error al clonar el producto. Presione Enter para cancelar la operacion.");
                            sc.nextLine();  // Esperar a que el usuario presione Enter

                            return;
                        }

                        // Verificar si el producto ya esta en el carrito
                        boolean productoYaEnCarrito = false;
                        for (Producto p : carrito) {
                            if (p.getNombre().equals(producto.getNombre())) {
                                productoYaEnCarrito = true;
                                break;
                            }
                        }

                        if (productoYaEnCarrito) {
                            System.out.println("¡Solo esta permitido un ejemplar por prestamo! (ノ ゜Д゜)ノ ︵ ┻━┻");
                            System.out.println("\nPresione Enter para continuar.");
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                        } else { // En caso de que no este, agregarlo al carrito
                            carrito.add(producto);
                            System.out.println("Producto agregado al carrito.");
                        }

                        break;

                    case 2: // Eliminar producto

                        Producto productoEnCarrito = seleccionarProducto(carrito);

                        carrito.remove(productoEnCarrito);

                        System.out.println("Producto eliminado del carrito.");

                        break;

                    case 3: // Ver productos en el carrito

                        // Comprobar que el carrito no este vacio
                        if (carrito.isEmpty()) {
                            System.out.println("El carrito esta vacio.");
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

                    case 4: // Confirmar carrito para prestamo
                        // Comprobar que el carrito no este vacio
                        if (carrito.isEmpty()) {
                            System.out.println("El carrito esta vacio.");
                            System.out.println("\nPresione Enter para continuar.");
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            continue;
                        }

                        // Calcular valor temporal
                        double valor = 0;
                        for (Producto p : carrito) {
                            valor += (p.getValor() * 0.01);
                        }

                        // Elegir plazo
                        System.out.println("1. 2 semanas (14 dias)");
                        System.out.println("2. 1 mes (30 dias)");
                        System.out.println("3. Mes y medio (45 dias)");
                        System.out.println("4. 2 meses (60 dias)");

                        System.out.println("Ingrese el tipo de plazo para el prestamo:");

                        int dias = 0; // Variable para calcular la fecha final segun la duracion del prestamo

                        while (dias == 0) {
                            byte opcionPlazo = 0;
                            try {
                                opcionPlazo = sc.nextByte();
                                sc.nextLine();  // Limpiar el buffer
                            } catch (InputMismatchException e) {
                                System.out.println("\n### ERROR ###");
                                System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.\n");
                                sc.nextLine();  // Limpiar el buffer
                                sc.nextLine();  // Esperar a que el usuario presione Enter
                                continue;
                            }

                            switch (opcionPlazo) {
                                case 1:
                                    dias = 14;
                                    valor = (valor * dias);
                                    break;
                                case 2:
                                    dias = 30;
                                    valor = (valor * dias);
                                    break;
                                case 3:
                                    dias = 45;
                                    valor = (int) (valor * dias * 0.90);
                                    break;
                                case 4:
                                    dias = 60;
                                    valor = (int) (valor * dias * 0.85);
                                    break;
                                default:
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                                    sc.nextLine();  // Limpiar el buffer
                                    sc.nextLine();  // Esperar a que el usuario presione Enter
                                    continue;
                            }
                        }

                        int valorInt = (int) valor;

                        System.out.println("El precio total del prestamo es de $" + valorInt + " por " + dias + " dias.");

                        Fecha fechaFin = new Fecha(fecha.getTotalDias() + dias);  // Fecha de fin del prestamo

                        // Recepcion de pago

                        int valorIngresado = 0;
                        int cambio = 0;

                        // Recibir efectivo
                        while (true) {
                            valorIngresado = 0;
                            System.out.print("Ingrese el valor con el que pagara: ");

                            try {
                                valorIngresado = sc.nextInt();
                            } catch (InputMismatchException error) {
                                System.out.println("\n### ERROR ###");
                                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                                sc.nextLine();  // Limpiar el buffer
                                sc.nextLine();  // Esperar a que el usuario presione Enter
                                continue;
                            }

                            if (valorIngresado < valorInt) {
                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor ingresado es menor al requerido.\n" +
                                        "Presiona enter para volver a intentar.\n");
                                sc.nextLine();  // Limpiar el buffer
                                sc.nextLine();  // Esperar a que el usuario presione Enter
                            } else {
                                break;
                            }
                        }

                        cambio = valorIngresado - valorInt;

                        System.out.println("Cambio: $" + cambio);

                        // Remover productos del inventario
                        for (Producto p : carrito) {
                            Tienda.retirarUnoDeInventario(p, local.getInventarioPrestamo());
                        }

                        // Crear prestamo
                        Prestamo prestamo = new Prestamo(fecha, fechaFin, cliente, carrito, valorInt, "Activo");
                        // Añadir fondos al local
                        local.agregarFondos(valorInt);

                        System.out.println("\nPresione Enter para confirmar el prestamo.");
                        sc.nextLine();  // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter


                        break;

                    case 0: // Salir
                        return;

                    default:
                        System.out.println("\n### ERROR ###");
                        System.out.println("Opcion fuera del rango. Presione Enter para intentar de nuevo.");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                }

            } while (opcion != 4 && opcion != 0);
        }
    }

    // Metodo para seleccionar un producto del inventario de prestamos
    private static Producto seleccionarProducto(ArrayList<Producto> inventarioPrestamo) {
        byte opcion = 0;
        Scanner scSelProPres = new Scanner(System.in);

        do {
            imprimirSeparador();
            System.out.println("Ingrese el tipo del producto:");

            System.out.println("1. Consola");
            System.out.println("2. Juego");
            System.out.println("3. Accesorio");

            // Recibir seleccion del usuario
            try {
                opcion = scSelProPres.nextByte();
                scSelProPres.nextLine();  // Limpiar el buffer
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.");
                scSelProPres.nextLine();  // Limpiar el buffer
                scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }

            // Seleccion de producto
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
                        System.out.println("No hay consolas disponibles para prestamo.");
                        System.out.println("Presione Enter para volver al menu principal.\n");
                        scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    // Mostrar consolas disponibles
                    System.out.println("Consolas disponibles:");
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Consola && p.getCantidad() > 0) {
                            System.out.println("* " + p.toStringPrestable());
                        }
                    }

                    // Recibir seleccion del usuario
                    System.out.print("Ingrese el codigo de la consola que desea seleccionar: ");
                    codigo = scSelProPres.nextInt();
                    scSelProPres.nextLine();  // Limpiar el buffer

                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Consola && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado.");
                            return p;
                        }
                    }

                    // Esta parte solo se ejecutara si no se encontro el codigo dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Consola no encontrada. Presione Enter para volver a intentar.\n");
                    scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
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
                        System.out.println("No hay juegos disponibles para prestamo.");
                        System.out.println("Presione Enter para volver al menu principal.\n");
                        scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    // Mostrar juegos disponibles
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Juego) {
                            System.out.println("* " + p.toStringPrestable());
                        }
                    }

                    // Recibir seleccion del usuario
                    System.out.print("Ingrese el codigo del juego que desea agregar: ");
                    codigo = scSelProPres.nextInt();
                    scSelProPres.nextLine();  // Limpiar el buffer

                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Juego && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado.");
                            return p;
                        }
                    }

                    // Esta parte solo se ejecutara si no se encontro el codigo dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Juego no encontrado. Presione Enter para volver a intentar.\n");
                    scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
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
                        System.out.println("No hay accesorios disponibles para prestamo.");
                        System.out.println("Presione Enter para volver al menu principal.\n");
                        scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }


                    // Mostrar todos los accesorios disponibles
                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Accesorio) {
                            System.out.println("* " + p.toStringPrestable());
                        }
                    }

                    // Recibir seleccion del usuario
                    System.out.print("Ingrese el codigo del accesorio que desea agregar: ");
                    codigo = scSelProPres.nextInt();
                    scSelProPres.nextLine();  // Limpiar el buffer

                    for (Producto p : inventarioPrestamo) {
                        if (p instanceof Accesorio && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado");
                            return p;
                        }
                    }

                    // Esta parte solo se ejecutara si no se encontro el codigo dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Accesorio no encontrado. Presione Enter para volver a intentar.\n");
                    scSelProPres.nextLine();  // Esperar a que el usuario presione Enter
                    break;


                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                    scSelProPres.nextLine(); // Limpiar el buffer
                    scSelProPres.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
        } while (true);
    }

    // Buscar prestamos vencidos para un cliente dado
    private static void comprobarPrestamos(Cliente cliente, Fecha fecha) {
        if (!cliente.getPrestamos().isEmpty()) {
            for (Prestamo p : cliente.getPrestamos()) {
                if (p.getFechaFin().getTotalDias() < fecha.getTotalDias()) {
                    System.out.println("El prestamo con ID " + p.getId() + " ha vencido.");
                    p.setEstado("Vencido");
                }
            }
        }
    }

    // Devolver true si el cliente tiene prestamos vencidos
    private static boolean tieneVencidos(Cliente cliente) {
        for (Prestamo p : cliente.getPrestamos()) {
            if (p.getEstado().equals("Vencido")) {
                return true;
            }
        }
        return false;
    }

    // Metodo que se encarga unicamente de devolver los productos al stock del local ingresado
    // y actualizar el estado del prestamo
    private static void devolverProductosPrestados(Prestamo prestamo, Tienda local) {
        ArrayList<Producto> paraAnadir = new ArrayList<>();
        for (Producto producto : prestamo.getProductos()) {
            for (Producto p : local.getInventarioPrestamo()) {
                if (p.getNombre().equals(producto.getNombre())) {
                    p.setCantidad(p.getCantidad() + 1);
                } else {
                    paraAnadir.add(producto);
                }
            }
        }

        for (Producto producto : paraAnadir) {
            local.getInventarioPrestamo().add(producto);
        }

        prestamo.setEstado("Devuelto");
    }

    // Metodo que lleva a cabo el chequeo individual de cada producto
    // de una devolucion y retorna una multa de ser necesario
    private static int comprobarDevolucion(Prestamo prestamo) {
        int multa = 0;
        System.out.println("Por favor, revise el estado de los productos devueltos.");

        for (Producto producto : prestamo.getProductos()) {
            if (!siNo("¿El producto '" + producto.getNombre() + "' se encuentra en buen estado?")) {
                multa += (int) (producto.getValor() * 0.25);
            }
        }

        return multa;
    }

    // Metodo que lleva a cabo el cobro de una multa con valor dado
    public static void cobrarMulta(int valorMulta, Tienda local) {
        if (valorMulta == 0) {
            return;
        }

        while (true) {
            int valorIngresado = 0;
            System.out.println("Valor de multa: " + valorMulta);
            System.out.print("Ingrese el valor con el que pagara: ");

            try {
                valorIngresado = sc.nextInt();
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }

            if (valorIngresado < valorMulta) {
                System.out.println("\n### ERROR ###");
                System.out.println("El valor ingresado es menor al requerido.\n" +
                        "Presiona enter para volver a intentar.\n");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            } else {
                break;
            }
        }

        local.agregarFondos(valorMulta);
    }
}