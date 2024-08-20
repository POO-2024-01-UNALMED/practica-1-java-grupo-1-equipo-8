package uiMain;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Meta;
import gestorAplicacion.productos.*;

import static uiMain.Main.*;

public class Funcionalidad1 {
    static Scanner scCompra = new Scanner(System.in); // variable scanner
    static int puntosUsados = 0; // Cantidad de puntos que se usan en la transaccion. Siempre sera menor a la cantidad de puntos del cliente


    public static void registrarCompra(Tienda local, Fecha fecha) {
        /* ~~~ Identificacion del cliente ~~~ */
        Cliente cliente = identificarCliente();

        // Comprobacion de que el cliente se selecciono correctamente
        if (cliente == null) {
            return;
        }

        /* ~~~ Calcular recomendaciones ~~~ */

        if (!(cliente.getCompras().isEmpty())) { // Solo en caso de que la lista no este vacia
            // ArrayLists para almacenar los generos y la cantidad de veces que se han comprado. Estan en el mismo indice
            ArrayList<Integer> generosCant = new ArrayList<Integer>();
            ArrayList<String> generos = new ArrayList<String>();

            for (Transaccion t : cliente.getCompras()) { // Buscar en cada compra del cliente
                for (Producto p : t.getProductos()) { // Buscar cada producto en la lista de productos
                    if (p instanceof Juego) {
                        if (generos.contains(((Juego) p).getGenero())) {    // Si el genero ya esta en la lista, aumentar la cantidad
                            int indice = generos.indexOf(((Juego) p).getGenero());
                            generosCant.set(indice, generosCant.get(indice) + 1);
                        } else { // si no esta, agregarlo a la lista
                            generos.add(((Juego) p).getGenero());
                            generosCant.add(1);
                        }
                    }
                }
            }
            // Encontrar el genero mas comprado
            int max = 0;
            String generoFav = "";


            for (int i = 0; i < generosCant.size(); i++) {
                if (generosCant.get(i) > max) {
                    max = generosCant.get(i);
                    generoFav = generos.get(i);
                }
            }

            // Mostrar genero favorito
            if (!generosCant.isEmpty()) {
                System.out.println("Genero favorito: " + generoFav);
            } else {
                System.out.println("No hay suficientes compras para hacer una recomendacion.");
            }

            // Recomendaciones segun plataforma
            ArrayList<String> plataformas = new ArrayList<String>();

            // Encontrar plataformas para la que el cliente ha comprado productos
            for (Transaccion t : cliente.getCompras()) { // Buscar en cada compra del cliente
                for (Producto p : t.getProductos()) { // Buscar cada producto en la lista de productos
                    if (p instanceof Juego) {
                        if (!(plataformas.contains(((Juego) p).getPlataforma()))) { // Si la plataforma no esta en la lista, agregarla
                            plataformas.add(((Juego) p).getPlataforma());
                        }
                    }
                }
            }

            // Mostrar las plataformas
            if (!plataformas.isEmpty()) {
                System.out.println("Plataformas en las que ha comprado:");
                for (String p : plataformas) {
                    System.out.println("* " + p);
                }
            }
        }

        /* ~~~ Seleccion de productos ~~~ */
        byte opcion = 0;

        ArrayList<Producto> carrito = new ArrayList<Producto>(); // Creacion de ArrayList carrito

        do {
            imprimirSeparador();
            System.out.println("1. Agregar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Ver productos en el carrito");
            System.out.println("4. Confirmar compra");
            System.out.println("0. Cancelar y salir");

            // Recibir entrada del usuario
            try {
                opcion = scCompra.nextByte();
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.\n");
                scCompra.nextLine();  // Limpiar el buffer
                scCompra.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }


            scCompra.nextLine();  // Limpiar el buffer

            Producto producto;

            switch (opcion) {
                case 1: // Agregar producto
                    // Clonar el producto seleccionado para evitar modificar el original
                    try {
                        producto = (seleccionarProducto(local.getInventario()).clone());
                    } catch (CloneNotSupportedException e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Error al clonar el producto. Presione Enter para cancelar la operacion.");
                        scCompra.nextLine();  // Limpiar el buffer
                        scCompra.nextLine();  // Esperar a que el usuario presione Enter

                        return;
                    }


                    // Verificar si el producto ya esta en el carrito
                    Producto estaEnCarrito = hallarEnCarrito(producto, carrito);

                    if (estaEnCarrito != null) { // Si esta, aumentar la cantidad en 1
                        if (estaEnCarrito.getCantidad() >= producto.getCantidad())
                        { // Pero si no hay la cantidad suficiente, mostrar mensaje de error
                            System.out.println("No hay mas unidades de '" + producto.getNombre() + "' disponibles.");
                            System.out.println("\nPresione Enter para continuar.");
                            scCompra.nextLine();  // Esperar a que el usuario presione Enter
                            break;
                        } else {
                            estaEnCarrito.setCantidad(estaEnCarrito.getCantidad() + 1);
                        }
                    } else { // De no estar, agregarlo al carrito con cantidad 1
                        producto.setCantidad(1);
                        carrito.add(producto);
                    }

                    System.out.println("Producto agregado al carrito.");

                    break;

                case 2: // Eliminar producto
                    Producto productoEnCarrito = seleccionarProducto(carrito);

                    if (productoEnCarrito.getCantidad() > 1) { // Si el producto tiene mas de una unidad, disminuir la cantidad en 1
                        productoEnCarrito.setCantidad(productoEnCarrito.getCantidad() - 1);
                    } else { // Si solo hay una unidad, eliminar el producto del carrito
                        carrito.remove(productoEnCarrito);
                    }

                    System.out.println("Producto eliminado del carrito.");

                    break;

                case 3: // Ver productos en el carrito

                    // Comprobar que el carrito no este vacio
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito esta vacio.");
                        System.out.println("\nPresione Enter para continuar.");
                        scCompra.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                    }

                    System.out.println("CARRITO:");

                    for (Producto p : carrito) {
                        System.out.println("* " + p);
                    }

                    System.out.println("\nPresione Enter para continuar.");
                    scCompra.nextLine();  // Esperar a que el usuario presione Enter

                    break;

                case 4: // Completar compra

                    // Comprobar que el carrito no este vacio
                    if (carrito.isEmpty()) {
                        System.out.println("El carrito esta vacio.");
                        System.out.println("\nPresione Enter para continuar.");
                        scCompra.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    int valorFinal = calcularDescuentos(carrito, cliente);  // Calcular valor total de la compra con descuentos

                    System.out.println("Valor total de la compra: $" + valorFinal + "\n");

                    // Ingreso de dinero
                    int valorIngresado = 0;
                    int cambio = 0;

                    // Recibir efectivo
                    while (true) {
                        valorIngresado = 0;
                        System.out.print("Ingrese el valor con el que pagara:");

                        try {
                            valorIngresado = scCompra.nextInt();
                        } catch (InputMismatchException error) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                            scCompra.nextLine();  // Limpiar el buffer
                            scCompra.nextLine();  // Esperar a que el usuario presione Enter
                            continue;
                        }

                        if (valorIngresado < valorFinal) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor ingresado es menor al total de la compra.\n" +
                                    "Presiona enter para volver a intentar.\n");
                            scCompra.nextLine();  // Limpiar el buffer
                            scCompra.nextLine();  // Esperar a que el usuario presione Enter
                        } else {
                            break;
                        }
                    }

                    cambio = valorIngresado - valorFinal;

                    System.out.println("Cambio: $" + cambio + "\n");

                    // Identificar al empleado que atendio la venta

                    Empleado empleado = identificarEmpleado(local);

                    // Actualizar metas del empleado
                    for (Meta m : empleado.getMetas()) {
                        m.incrementarAcumulado(valorFinal);
                    }

                    // Crear transaccion
                    Transaccion transaccion = new Transaccion(fecha, cliente, empleado, local, carrito, valorFinal);

                    System.out.println("\nPresione Enter para confirmar la compra.");
                    scCompra.nextLine();  // Limpiar el buffer
                    scCompra.nextLine(); // Esperar a que el usuario presione Enter

                    // Actualizar inventario
                    for (Producto p : carrito) {
                        if (p instanceof Consola) {
                            for (Producto p2 : local.getInventario()) {
                                if (p2 instanceof Consola && p2.getCodigo() == (p.getCodigo())) {
                                    p2.setCantidad(p2.getCantidad() - p.getCantidad());
                                }
                            }
                        } else if (p instanceof Juego) {
                            for (Producto p2 : local.getInventario()) {
                                if (p2 instanceof Juego && p2.getCodigo() == (p.getCodigo())) {
                                    p2.setCantidad(p2.getCantidad() - p.getCantidad());
                                }
                            }
                        } else if (p instanceof Accesorio) {
                            for (Producto p2 : local.getInventario()) {
                                if (p2 instanceof Accesorio && p2.getCodigo() == (p.getCodigo())) {
                                    p2.setCantidad(p2.getCantidad() - p.getCantidad());
                                }
                            }
                        } else {
                            System.out.println("Error al actualizar el inventario.");
                            return;
                        }
                    }

                    // Añadir la transaccion a la lista de transacciones de la tienda
                    local.agregarTransaccion(transaccion);

                    // Actualizar cliente
                    // Descontar puntos de fidelidad
                    cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() - puntosUsados);
                    // Agregar compra a la lista de compras del cliente. Este metodo tambien otorga los puntos de fidelidad correspondientes
                    cliente.agregarCompra(transaccion);

                    // Finalizacion
                    System.out.println("""
                                       ...
                                                                   
                                       ᕕ( ᐛ )ᕗ
                                       ¡Compra realizada con exito!
                                       """);

                    //(ﾉ◕ヮ◕)ﾉ*:・ﾟ✧
                    return;

                case 0: // Cancelar y salir
                    System.out.println("Compra cancelada.");

                    return;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opcion fuera del rango. Presione Enter para intentar de nuevo.");
                    scCompra.nextLine();  // Esperar a que el usuario presione Enter
                    break;
            }
        } while (opcion != 4);
    }

    // Recibe un ArrayList de productos y devuelve el producto que seleccione
    public static Producto seleccionarProducto(ArrayList<Producto> inventario) {
        byte opcion;

        do {
            imprimirSeparador();
            System.out.println("Ingrese el tipo del producto:");

            System.out.println("1. Consola");
            System.out.println("2. Juego");
            System.out.println("3. Accesorio");

            // Recibir seleccion del usuario

            opcion = 0;

            try {
                opcion = scCompra.nextByte();
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                scCompra.nextLine();  // Limpiar el buffer
                scCompra.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }

            // Seleccion de producto
            int codigo = 0;

            switch (opcion) {
                case 1:
                    // Consola
                    // Mostrar consolas disponibles
                    System.out.println("Consolas disponibles:");
                    for (Producto p : inventario) {
                        if (p instanceof Consola && p.getCantidad() > 0) {
                            System.out.println("* " + p);
                        }
                    }

                    // Recibir seleccion del usuario
                    System.out.print("Ingrese el codigo de la consola que desea seleccionar: ");
                    codigo = scCompra.nextInt();
                    scCompra.nextLine();  // Limpiar el buffer

                    for (Producto p : inventario) {
                        if (p instanceof Consola && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado.");
                            return p;
                        }
                    }

                    // Esta parte solo se ejecutara si no se encontro el codigo dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Consola no encontrada. Presione Enter para volver a intentar.\n");
                    scCompra.nextLine();  // Esperar a que el usuario presione Enter
                    break;

                case 2:
                    // Juego
                    // Mostrar juegos disponibles
                    for (Producto p : inventario) {
                        if (p instanceof Juego && p.getCantidad() > 0) {
                            System.out.println("* " + p);
                        }
                    }

                    // Recibir seleccion del usuario
                    System.out.print("Ingrese el codigo del juego que desea agregar: ");
                    codigo = scCompra.nextInt();
                    scCompra.nextLine();  // Limpiar el buffer

                    for (Producto p : inventario) {
                        if (p instanceof Juego && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado.");
                            return p;
                        }
                    }

                    // Esta parte solo se ejecutara si no se encontro el codigo dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Juego no encontrado. Presione Enter para volver a intentar.\n");
                    scCompra.nextLine();  // Esperar a que el usuario presione Enter
                    break;


                case 3:
                    // Accesorio
                    for (Producto p : inventario) {
                        if (p instanceof Accesorio && p.getCantidad() > 0) {
                            System.out.println("* " + p);
                        }
                    }

                    // Recibir seleccion del usuario
                    System.out.print("Ingrese el codigo del accesorio que desea agregar: ");
                    codigo = scCompra.nextInt();
                    scCompra.nextLine();  // Limpiar el buffer

                    for (Producto p : inventario) {
                        if (p instanceof Accesorio && p.getCodigo() == codigo) {
                            System.out.println("'" + p.getNombre() + "' seleccionado");
                            return p;
                        }
                    }

                    // Esta parte solo se ejecutara si no se encontro el codigo dado
                    System.out.println("\n### ERROR ###");
                    System.out.println("Accesorio no encontrado. Presione Enter para volver a intentar.\n");
                    scCompra.nextLine();  // Esperar a que el usuario presione Enter
                    break;


                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                    scCompra.nextLine(); // Limpiar el buffer
                    scCompra.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }

        } while (true);
    }

    // Encuentra un producto en el carrito por su nombre
    private static Producto hallarEnCarrito(Producto producto, ArrayList<Producto> carrito) {
        for (Producto p : carrito) {
            if (p.getNombre().equals(producto.getNombre())) {
                return p;
            }
        }

        return null;
    }

    private static int calcularDescuentos(ArrayList<Producto> carrito, Cliente cliente) {
        int precioFinal = 0; // Precio final de la transaccion con descuentos aplicados
        int precioFinalIndividual; // Precio final de un producto con descuento aplicado
        int puntosUsados = 0;
        int valorTemp; // Variable usada para el calculo de descuentos

        for (Producto p : carrito) {
            valorTemp = 0;
            precioFinalIndividual = 0;

            if (p.getDescuento() > 0) { // En caso de que el producto tenga descuento
                if (p.getPuntosRequeridos() == 0) { // En caso de que el producto no requiera minimo de puntos
                    valorTemp = p.getValor() * p.getCantidad();
                    precioFinalIndividual = valorTemp - (valorTemp * p.getDescuento() / 100); // Calcular descuento
                    precioFinal += precioFinalIndividual; // sumar descuento

                    System.out.println("    * Descuento aplicado a '" + p.getNombre() + "' : $" + (valorTemp) + " ---> $" + precioFinalIndividual);
                } else if (p.getPuntosRequeridos() > 0 &&
                        (cliente.getPuntosFidelidad() - puntosUsados) >= p.getPuntosRequeridos())
                { // En caso de que el producto tenga un minimo de puntos requeridos y el cliente tenga saldo suficiente
                    valorTemp = p.getValor() * p.getCantidad();
                    precioFinalIndividual = valorTemp - (valorTemp * p.getDescuento() / 100); // Calcular descuento
                    precioFinal += precioFinalIndividual;  // Sumar descuento

                    puntosUsados += p.getPuntosRequeridos(); // Actualizar puntos usados

                    System.out.println("    * Descuento aplicado a '" + p.getNombre() + "' : $" + (valorTemp) + " ---> $" + precioFinalIndividual + " | Puntos usados: " + p.getPuntosRequeridos());

                } else { // En caso de que el producto tenga minimo de puntos pero el cliente no tenga saldo suficiente
                    precioFinal += p.getValor() * p.getCantidad();
                }

            } else { // En caso de que el producto no tenga descuento
                precioFinal += p.getValor() * p.getCantidad();
            }
        }

        if (puntosUsados > 0) {
            System.out.println("Puntos usados: " + puntosUsados);
        }

        return precioFinal;
    }

    // Methodo para encontrar un empleado en la tienda
    public static Empleado identificarEmpleado(Tienda local) {
        imprimirSeparadorPequeno();

        // En caso de que la tienda no tenga empleados
        if (local.getEmpleados().size() == 0) {
            System.out.println("\n### ERROR ###");
            System.out.println("No hay empleados en esta tienda");
            return null;
        }

        Empleado empleado = null;
        int cedulaEmpleado;
        java.util.Scanner scEmp = new java.util.Scanner(System.in);

        while (empleado == null) {
            // Mostrar a todos los empleados del local
            for (Empleado e : local.getEmpleados()) {
                System.out.println(" * " + e.getNombre() + " - " + e.getCedula());
            }
            System.out.println("Ingrese la cedula del empleado encargado de esta transaccion");

            // Recibir cedula del empleado
            cedulaEmpleado = 0;

            try {
                cedulaEmpleado = scEmp.nextInt();
                scEmp.nextLine();  // Limpiar el buffer

                for (Empleado e : local.getEmpleados()) {
                    if (e.getCedula() == cedulaEmpleado) {
                        empleado = e;
                        return empleado;
                    }
                }
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                scEmp.nextLine();  // Limpiar el buffer
                scEmp.nextLine();  // Esperar a que el usuario presione Enter
            }
        }
        return empleado;
    }
}