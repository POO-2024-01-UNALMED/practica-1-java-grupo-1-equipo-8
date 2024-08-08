package uiMain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.*;

public class Funcionalidad1 {
    static Scanner sc = new Scanner(System.in); // variable scanner
    static int puntosUsados = 0; // Cantidad de puntos que se usan en la transacción. Siempre será menor a la cantidad de puntos del cliente


    public static void registrarCompra(Tienda local, Fecha fecha) {
        /* ~~~ Identificación del cliente ~~~ */
        Cliente cliente = identificarCliente();

        if (cliente == null) {
            return;
        }

        /* ~~~ Calcular recomendaciones ~~~ */

        if (!(cliente.getCompras().isEmpty())) { // Sólo en caso de que la lista no esté vacía
            // ArrayLists para almacenar los géneros y la cantidad de veces que se han comprado. Estan en el mismo indice
            ArrayList<Integer> generosCant = new ArrayList<Integer>();
            ArrayList<String> generos = new ArrayList<String>();

            for (Transaccion t : cliente.getCompras()) { // Buscar en cada compra del cliente
                for (Producto p : t.getProductos()) { // Buscar cada producto en la lista de productos
                    if (p instanceof Juego) {
                        if (generos.contains(((Juego) p).getGenero())) {    // Si el género ya está en la lista, aumentar la cantidad
                            int indice = generos.indexOf(((Juego) p).getGenero());
                            generosCant.set(indice, generosCant.get(indice) + 1);
                        } else { // si no está, agregarlo a la lista
                            generos.add(((Juego) p).getGenero());
                            generosCant.add(1);
                        }
                    }
                }
            }
            // Encontrar el género más comprado
            int max = 0;
            String generoFav = "";



            for (int i = 0; i < generosCant.size(); i++) {
                if (generosCant.get(i) > max) {
                    max = generosCant.get(i);
                    generoFav = generos.get(i);
                }
            }

            // TODO: Recomendar juegos del género más comprado

            // Mostrar género favorito
            if (generosCant.size() > 0) {
                System.out.println("Género favorito: " + generoFav);
            }
            else {
                System.out.println("No hay suficientes compras para hacer una recomendación.");
            }

            // Recomendaciones segun plataforma
            ArrayList<String> plataformas = new ArrayList<String>();

            // Encontrar plataformas para la que el cliente ha comprado productos
            for (Transaccion t : cliente.getCompras()) { // Buscar en cada compra del cliente
                for (Producto p : t.getProductos()) { // Buscar cada producto en la lista de productos
                    if (p instanceof Juego) {
                        if (!(plataformas.contains(((Juego) p).getPlataforma()))) { // Si la plataforma no está en la lista, agregarla
                            plataformas.add(((Juego) p).getPlataforma());
                        }
                    }
                }
            }

            // Mostrar las plataformas
            if (plataformas.size() > 0) {
                System.out.println("Plataformas en las que ha comprado:");
                for (String p : plataformas) {
                    System.out.println("* " + p);
                }
            }
        }

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
                case 1: // Agregar producto

                    // Clonar el producto seleccionado para evitar modificar el original
                    try {
                        producto = (seleccionarProducto(local.getInventario()).clone());
                    }
                    catch (CloneNotSupportedException e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Error al clonar el producto. Presione Enter para cancelar la operación.");
                        sc.nextLine();  // Limpiar el buffer
                        sc.nextLine();  // Esperar a que el usuario presione Enter

                        return;
                    }


                    // Verificar si el producto ya está en el carrito
                    Producto estaEnCarrito = hallarEnCarrito(producto, carrito);

                    if (estaEnCarrito != null) { // Si está, aumentar la cantidad en 1
                        if (estaEnCarrito.getCantidad() >= producto.getCantidad()) { // Pero si no hay la cantidad suficiente, mostrar mensaje de error
                            System.out.println("No hay más unidades de '" + producto.getNombre() + "' disponibles.");
                            System.out.println("\nPresione Enter para continuar.");
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            break;
                        } else {
                            estaEnCarrito.setCantidad(estaEnCarrito.getCantidad() + 1);
                        }
                    }
                    else { // De no estar, agregarlo al carrito con cantidad 1
                        producto.setCantidad(1);
                        carrito.add(producto);
                    }

                    System.out.println("Producto agregado al carrito.");

                    break;

                case 2: // Eliminar producto

                    // Mostrar productos en carrito
                    /*
                    System.out.println("CARRITO:");
                    int i = 1;
                    for (Producto p : carrito) {
                        System.out.println(i + ". " + p.getNombre() + " | " + p.getCantidad() + " unidades");
                        i++;
                    }
                    */

                    /*
                    // Recibir selección del usuario
                    System.out.println("Ingrese el número del producto que desea eliminar:");
                    try {
                        int seleccion = sc.nextInt();
                        sc.nextLine();  // Limpiar el buffer
                    }
                    catch(Exception e) { // En caso de que la selección no sea válida
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.\n");
                        sc.nextLine();  // Limpiar el buffer
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                    }
                    */

                    // TODO: Cambiar el formato de los códigos a uno con letra y número

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
                        System.out.println("* " + p);
                    }

                    System.out.println("\nPresione Enter para continuar.");
                    sc.nextLine();  // Esperar a que el usuario presione Enter

                    break;

                case 4: // Completar compra

                    if (carrito.isEmpty()) {
                        System.out.println("El carrito está vacío.");
                        System.out.println("\nPresione Enter para continuar.");
                        sc.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    int valorFinal = calcularDescuentos(carrito, cliente);  // Calcular valor total de la compra con descuentos
                    Transaccion transaccion = new Transaccion(fecha, cliente, local, carrito, valorFinal);

                    int valorIngresado = 0;
                    int cambio = 0;

                    System.out.println("Valor total de la compra: $" + valorFinal + "\n");

                    // Ingreso de dinero
                    // TODO: Pago fraccionado

                        // Recibir efectivo
                    while (true) {
                        valorIngresado = 0;
                        System.out.print("Ingrese el valor con el que pagará:");

                        try {
                            valorIngresado = sc.nextInt();
                        } catch (InputMismatchException error) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                            sc.nextLine();  // Limpiar el buffer
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            continue;
                        }

                        if (valorIngresado < valorFinal) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor ingresado es menor al total de la compra. Presiona enter para volver a intentar.\n");
                            sc.nextLine();  // Limpiar el buffer
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                        } else {
                            break;
                        }
                    }

                    cambio = valorIngresado - valorFinal;

                    System.out.println("Cambio: $" + cambio);
                    System.out.println("\nPresione Enter para confirmar la compra.");
                    sc.nextLine();  // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter

                    // Actualizar inventario
                    for (Producto p : carrito) {
                        if (p instanceof Consola) {
                            for (Producto p2 : local.getInventario()) {
                                if (p2 instanceof Consola && p2.getCodigo() == (p.getCodigo())) {
                                    p2.setCantidad(p2.getCantidad() - p.getCantidad());
                                }
                            }
                        }
                        else if (p instanceof Juego) {
                            for (Producto p2 : local.getInventario()) {
                                if (p2 instanceof Juego && p2.getCodigo() == (p.getCodigo())) {
                                    p2.setCantidad(p2.getCantidad() - p.getCantidad());
                                }
                            }
                        }
                        else if (p instanceof Accesorio) {
                            for (Producto p2 : local.getInventario()) {
                                if (p2 instanceof Accesorio && p2.getCodigo() == (p.getCodigo())) {
                                    p2.setCantidad(p2.getCantidad() - p.getCantidad());
                                }
                            }
                        }

                        else { System.out.println("Error al actualizar el inventario."); return;}
                    }

                    // Añadir la transacción a la lista de transacciones de la tienda
                    local.agregarTransaccion(transaccion);

                    // Actualizar cliente
                        // Actualizar puntos de fidelidad
                    cliente.setPuntosFidelidad(cliente.getPuntosFidelidad() - puntosUsados);
                        // Agregar compra a la lista de compras del cliente
                    cliente.agregarCompra(transaccion);

                    // Finalización
                    System.out.println("""
                            ...
                            
                            ᕕ( ᐛ )ᕗ
                            ¡Compra realizada con éxito!
                            """);

                    //(ﾉ◕ヮ◕)ﾉ*:・ﾟ✧

                    // TODO: Método que cambia el emoji aleatoriamente en cada compra

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

                    System.out.println("Cliente '" + nombre + "' registrado.\n");
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
                                    System.out.println("Cliente '" + cliente.getNombre() + "' encontrado.\n");
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
                            if (siNo("Cliente no encontrado. ¿Desea intentar de nuevo?")) {
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

    private static Producto seleccionarProducto(ArrayList<Producto> inventario) {
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
                    for (Producto p : inventario) {
                        if (p instanceof Consola && p.getCantidad() > 0) {
                            System.out.println("* " + p);
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código de la consola que desea seleccionar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : inventario) {
                        if (p instanceof Consola && p.getCodigo() == codigo) {
                            System.out.println( "'" + p.getNombre() + "' seleccionado.");
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
                    for (Producto p : inventario) {
                        if (p instanceof Juego) {
                            System.out.println(p);
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código del juego que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : inventario) {
                        if (p instanceof Juego && p.getCodigo() == codigo) {
                            System.out.println( "'" + p.getNombre() + "' seleccionado.");
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
                    for (Producto p : inventario) {
                        if (p instanceof Accesorio) {
                            System.out.println(p);
                        }
                    }

                    // Recibir selección del usuario
                    System.out.print("Ingrese el código del accesorio que desea agregar: ");
                    codigo = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    for (Producto p : inventario) {
                        if (p instanceof Accesorio && p.getCodigo() == codigo) {
                            System.out.println( "'" + p.getNombre() + "' seleccionado");
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

    private static int calcularDescuentos(ArrayList<Producto> carrito, Cliente cliente) {
        int precioFinal = 0; // Precio final de la transacción con descuentos aplicados
        int puntosUsados = 0;
        int valorTemp; // Variable usada para el calculo de descuentos

        for (Producto p : carrito) {
            valorTemp = 0;

            if (p.getDescuento() > 0) { // En caso de que el producto tenga descuento
                if (p.getPuntosRequeridos() == 0){ // En caso de que el producto no requiera mínimo de puntos
                    valorTemp = p.getValor() * p.getCantidad();
                    precioFinal += valorTemp - (valorTemp * p.getDescuento() / 100); // Calcular descuento
                }
                else if (p.getPuntosRequeridos() > 0 && (cliente.getPuntosFidelidad() - puntosUsados) >= p.getPuntosRequeridos()) { // En caso de que el producto tenga un mínimo de puntos requeridos y el cliente tenga saldo suficiente
                        valorTemp = p.getValor() * p.getCantidad();
                        precioFinal += valorTemp - (valorTemp * p.getDescuento() / 100); // Calcular descuento

                        puntosUsados += p.getPuntosRequeridos(); // Actualizar puntos usados
                }
                else { // En caso de que el producto tenga mínimo de puntos pero el cliente no tenga saldo suficiente
                    precioFinal += p.getValor() * p.getCantidad();
                }

            }
            else { // En caso de que el producto no tenga descuento
                precioFinal += p.getValor() * p.getCantidad();
            }
        }

        return precioFinal;
    }

    // Devuelve true si la respuesta no es No (ni "n" ni "N")
    private static boolean siNo(String pregunta) {
        System.out.println(pregunta + " (S/n)");
        char respuesta = sc.next().charAt(0);
        sc.nextLine();  // Limpiar el buffer

        return !(respuesta == 'n' || respuesta == 'N');
    }

}

/* NO REMOVER HASTA COMPROBAR QUE LA APROXIMACION ACTUAL FUNCIONA
class Par {
    String primero;
    int segundo;

    public Par(String primero, int segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public String getPrimero() {
        return primero;
    }
    public int getSegundo() {
        return segundo;
    }

    public void setPrimero(String primero) {
        this.primero = primero;
    }
    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }
}
 */