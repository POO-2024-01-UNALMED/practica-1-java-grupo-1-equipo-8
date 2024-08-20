package uiMain;

import baseDatos.Deserializador;
import baseDatos.Serializador;
import gestorAplicacion.informacionVenta.Prestamo;
import gestorAplicacion.informacionVenta.Subasta;
import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Reabastecimiento;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.productos.*;
import gestorAplicacion.personas.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);
    public static Fecha ultimaFecha = new Fecha(1, 1, 2021);

    public static void main(String[] args) {
        /* ~~~ Carga de objetos serializados ~~~ */

        Deserializador.deserializarTiendas();
        Deserializador.deserializarClientes();
        ultimaFecha = Deserializador.deserializarFecha();

        /* ~~~~~~~~~~~~~~~~~~~~~~~ Inicio del programa ~~~~~~~~~~~~~~~~~~~~~~~ */
        /* ~~~ Recibir fecha ~~~ */
        // Se guarda el mes de la ultima fecha registrada con el fin de actualizar ciertos atributos como
        // la cantidad inicial de cada producto.
        int ultimoMes = ultimaFecha.getMes();

        // Recibir fecha actual
        Fecha fechaActual = recibirFechaActual();

        // Comprobar si se cambio de mes
        cambioDeMes(ultimoMes, fechaActual.getMes());

        /* ~~~ Seleccion de local ~~~ */
        Tienda local = null; // Se adquiere el local con el que se quiere trabajar
        local = getLocal();

        /* ~~ Aplicar reabastecimientos ~~ */
        comprobarReabastecimientos(local, fechaActual);

        /* ~~~~~~ Menu principal ~~~~~~ */
        imprimirSeparador();
        imprimirLogo();
        /* ~~ Seleccion de funcionalidad ~~ */
        byte opcion = 1;
        do {
            imprimirSeparador();
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Registrar compra");
            System.out.println("2. Registrar prestamo");
            System.out.println("3. Administrar inventario");
            System.out.println("4. Gestionar empleados");
            System.out.println("5. Subastar");

            System.out.println("--------------------------");
            System.out.println("6. Cambiar de fecha y local");

            System.out.println("0. Guardar y salir");

            System.out.println("Ingrese el numero de la opcion que desea ejecutar:");

            // Recibir entrada del usuario
            try {
                opcion = sc.nextByte();
                sc.nextLine();  // Limpiar el buffer
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            switch (opcion) {
                case 1:
                    // Registrar compra
                    fRegistrarCompra.registrarCompra(local, fechaActual);
                    break;

                case 2:
                    // Registrar prestamo

                    fRegistrarPrestamo.registrarPrestamo(local, fechaActual);
                    break;

                case 3:
                    // Administrar inventario
                    fAdministrarInventario.revisarInventario(local, fechaActual);
                    break;

                case 4:
                    // Gestionar empleados
                    fAdministrarEmpleado.inspeccionEmpleado(local, fechaActual);
                    break;

                case 5:
                    // ~~Placeholder para quinta funcionalidad~~
                    fSubastar.subastar(local, fechaActual);
                    break;

                case 6:
                    // Cambiar de fecha y local
                    fechaActual = recibirFechaActual();
                    local = getLocal();
                    break;

                case 0:
                    // Salir

                    Serializador.serializarTiendas(Tienda.getLocales());
                    Serializador.serializarClientes(Cliente.getClientes());
                    Serializador.serializarUltimaFecha(ultimaFecha);

                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
        } while (opcion != 0);
    }

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ METODOS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    // Separadores de la TUI
    //▅ ▒
    static void imprimirSeparador() {
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        //System.out.println("░░░░░░░░░░░░░░░░░░░░░████████████████████████████████████████████░░░░░░░░░░░░░░░░░░░░░");
    }

    static void imprimirSeparadorPequeno() {
        System.out.println("▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅");
    }

    // Imprimir logo de la tienda en ANSI

    static void imprimirLogo() {
        System.out.println(
                """
                        ██╗   ██╗██╗██╗     ██╗      █████╗      ██╗██╗   ██╗███████╗ ██████╗  ██████╗ ███████╗
                        ██║   ██║██║██║     ██║     ██╔══██╗     ██║██║   ██║██╔════╝██╔════╝ ██╔═══██╗██╔════╝
                        ██║   ██║██║██║     ██║     ███████║     ██║██║   ██║█████╗  ██║  ███╗██║   ██║███████╗
                        ╚██╗ ██╔╝██║██║     ██║     ██╔══██║██   ██║██║   ██║██╔══╝  ██║   ██║██║   ██║╚════██║
                         ╚████╔╝ ██║███████╗███████╗██║  ██║╚█████╔╝╚██████╔╝███████╗╚██████╔╝╚██████╔╝███████║
                          ╚═══╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚══════""");
    }
    // Metodo para identificar a un cliente

    // Recibe una fecha y se asegura de que sea igual o superior a la ultima fecha registrada
    private static Fecha recibirFechaActual() {
        Fecha fechaActual;

        while (true) { // Recibir fecha actual
            imprimirSeparador();

            System.out.println("Ultimo acceso: " + ultimaFecha);
            System.out.println("Ingrese la fecha actual");

            fechaActual = recibirFecha();
            if (fechaActual.getTotalDias() >= ultimaFecha.getTotalDias()) { // Si la fecha ingresada es igual o superior a la ultima fecha registrada
                ultimaFecha = fechaActual;
                break;
            } else {
                System.out.println("\n### ERROR ###");
                System.out.println("La fecha ingresada es anterior a la ultima fecha registrada (" + ultimaFecha + ")" +
                        "\nPresione Enter para volver a intentar.");

                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }
        return fechaActual;
    }

    static Cliente identificarCliente() {
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
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            switch (opcion) {
                case 1:
                    // Nuevo cliente
                    System.out.println("Ingrese cedula del cliente:");
                    cedula = sc.nextInt();
                    sc.nextLine();  // Limpiar el buffer

                    System.out.println("Ingrese nombre del cliente:");
                    String nombre = sc.nextLine();

                    System.out.println("Ingrese correo del cliente:");
                    String correo = sc.nextLine();

                    System.out.println("Ingrese telefono del cliente:");
                    long telefono = sc.nextLong();
                    sc.nextLine();  // Limpiar el buffer

                    System.out.println("Cliente '" + nombre + "' registrado.\n");
                    return new Cliente(cedula, nombre, correo, telefono);

                case 2:
                    // Cliente existente

                    while (cliente == null) {
                        System.out.println("Ingrese cedula del cliente:");

                        // Buscar al cliente en la lista de clientes por su cedula
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
                            System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                            sc.nextLine();  // Limpiar el buffer
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            continue;
                        }

                        // En caso de que el cliente no sea encontrado dar la opcion de intentar de nuevo
                        if (cliente == null) {
                            System.out.println("\n### ERROR ###");
                            if (!siNo("Cliente no encontrado. ¿Desea intentar de nuevo?")) {
                                return null;
                            }
                        }
                    }

                    sc.nextLine();  // Limpiar el buffer
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
        } while (opcion < 1 || opcion > 2);

        return cliente;
    }

    // Recibe una String que es la pregunta que se hara.
    // Devuelve true si la respuesta no es No (ni "n" ni "N")
    public static boolean siNo(String pregunta) {
        Scanner scSiNo = new Scanner(System.in);
        System.out.println(pregunta + " (S/n)");
        char respuesta = scSiNo.next().charAt(0);
        scSiNo.nextLine();  // Limpiar el buffer

        return !(respuesta == 'n' || respuesta == 'N');
    }

    // Identifica una fecha ingresada por el usuario y la devuelve
    public static Fecha recibirFecha() {
        int year;
        int mes;
        int dia;


        // Recibir año
        while (true) {
            try {
                System.out.print("Ingrese año: ");

                year = sc.nextInt();

                if (year < 0) {
                    throw new Exception("");
                }

                break;
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }

        //Recibir mes
        while (true) {
            try {
                System.out.print("Ingrese mes: ");

                mes = sc.nextInt();

                if (mes <= 0 || mes > 12) {
                    throw new Exception("");
                }

                break;
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }

        // Recibir dia
        while (true) {
            try {
                System.out.print("Ingrese dia: ");
                dia = sc.nextInt();

                if (dia <= 0 || dia > 31) {
                    throw new Exception("Dia invalido");
                }

                break;
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }

        sc.nextLine(); // Limpiar el buffer
        return new Fecha(dia, mes, year);
    }

    // Evalua si el mes ha cambiado y actualiza los atributos necesarios
    public static void cambioDeMes(int ultimoMes, int mesActual) {
        if (ultimoMes != mesActual) {
            // Reiniciar cantidad inicial
            for (Tienda tienda : Tienda.getLocales()) {
                for (Producto producto : tienda.getInventario()) {
                    producto.setCantidadInicial(producto.getCantidad());
                }
            }

            // Actualizar prioridades de los productos
            for (Tienda tienda : Tienda.getLocales()) {
                tienda.actualizarPrioridad();
            }
        }
    }

    // Identifica el local con el que se quiere trabajar
    private static Tienda getLocal() {
        Scanner scGetLocal = new Scanner(System.in);
        Tienda local = null;

        do {
            imprimirSeparador();

            int j = 1;
            for (Tienda i : Tienda.getLocales()) { // Bucle para imprimir los locales
                System.out.println("* " + i.getNombre());
                j = j + 1;
            }

            System.out.println("Ingrese el nombre del local");

            String nombreLocal = scGetLocal.nextLine(); // Recibir entrada del usuario

            for (Tienda i : Tienda.getLocales()) { // Bucle para encontrar el local
                if (i.getNombre().equalsIgnoreCase(nombreLocal)) {
                    local = i;
                    break;
                }
            }

            if (local == null) {
                System.out.println("\n### ERROR ###");
                System.out.println("Local no encontrado. Presiona enter para volver a intentar.");
                scGetLocal.nextLine();  // nextLine para esperar a que el usuario presione Enter
            }

        } while (local == null);
        return local;
    }

    // Menu de opcion multiple. Recibe un titulo y un array de opciones a mostrar.
    // Devuelve el byte de la opcion seleccionada.
    public static byte menuOpcionMultiple(String titulo, String[] opciones) {
        Scanner scMenuOpcines = new Scanner(System.in);
        byte opcion;

        do {
            imprimirSeparador();
            if (titulo != null) { // Imprimir el titulo
                System.out.println(titulo);
            }
            for (int i = 0; i < opciones.length; i++) { // Mostrar las opciones
                System.out.println((i + 1) + ". " + opciones[i]);
            }

            System.out.println("0. Volver");

            System.out.print("Ingrese el numero de la opcion que desea ejecutar: ");

            // Recibir entrada
            opcion = 0;

            try {
                opcion = scMenuOpcines.nextByte();
                if (opcion < 0 || opcion > opciones.length) {
                    throw new InputMismatchException();
                }
                return opcion;
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                scMenuOpcines.nextLine();  // nextLine para limpiar el buffer
                scMenuOpcines.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

        } while (opcion < 0 || opcion > opciones.length);

        return opcion;
    }

    // Comprueba todos los reabastecimiento de un local cuya fecha
    // de entrega prevista sea anterior a la actual y los aplica
    public static void comprobarReabastecimientos(Tienda local, Fecha fechaActual) {
        for (Reabastecimiento reabastecimiento : local.getReabastecimientos()) {
            if (reabastecimiento.getFechaEntrega().getTotalDias() <= fechaActual.getTotalDias()) {
                System.out.println("Aplicando reabastecimiento proveniente de " + reabastecimiento.getLocalOrigen() + " con fecha de entrega prevista para " + reabastecimiento.getFechaEntrega());
                reabastecimiento.aplicarReabastecimiento();
            }
        }
    }

    public static class fRegistrarCompra {
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

                        System.out.println("Valor total de la compra: $" + valorFinal);

                        // Ingreso de dinero
                        int valorIngresado = 0;
                        int cambio = 0;

                        // Recibir efectivo
                        while (true) {
                            valorIngresado = 0;
                            System.out.print("Ingrese el valor con el que pagara: ");

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

                        System.out.println("Cambio: $" + cambio);

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
            Scanner scEmp = new Scanner(System.in);

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

    public static class fRegistrarPrestamo {
        static Scanner scPrestam = new Scanner(System.in); // variable scanner

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
                        opcion = scPrestam.nextByte();
                    } catch (Exception e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.\n");
                        scPrestam.nextLine();  // Limpiar el buffer
                        scPrestam.nextLine();  // Esperar a que el usuario presione Enter
                        continue;
                    }

                    scPrestam.nextLine();  // Limpiar el buffer

                    Producto producto;

                    switch (opcion) {
                        case 1: // Agregar producto

                            // Clonar el producto seleccionado para evitar modificar el original
                            try {
                                Producto seleccion = (seleccionarProducto(local.getInventarioPrestamo()));
                                if (seleccion == null) {
                                    throw new CloneNotSupportedException();
                                }
                                producto = seleccion.clone();
                            } catch (CloneNotSupportedException e) {
                                System.out.println("\n### ERROR ###");
                                System.out.println("Error al clonar el producto. Presione Enter para cancelar la operacion.");
                                scPrestam.nextLine();  // Esperar a que el usuario presione Enter

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
                                scPrestam.nextLine();  // Esperar a que el usuario presione Enter
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
                                scPrestam.nextLine();  // Esperar a que el usuario presione Enter
                                break;
                            }

                            System.out.println("CARRITO:");

                            for (Producto p : carrito) {
                                System.out.println("* " + p.getNombre());
                            }

                            System.out.println("\nPresione Enter para continuar.");
                            scPrestam.nextLine();  // Esperar a que el usuario presione Enter

                            break;

                        case 4: // Confirmar carrito para prestamo
                            // Comprobar que el carrito no este vacio
                            if (carrito.isEmpty()) {
                                System.out.println("El carrito esta vacio.");
                                System.out.println("\nPresione Enter para continuar.");
                                scPrestam.nextLine();  // Esperar a que el usuario presione Enter
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
                                    opcionPlazo = scPrestam.nextByte();
                                    scPrestam.nextLine();  // Limpiar el buffer
                                } catch (InputMismatchException e) {
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("Ingrese un numero valido. Presione Enter para volver a intentar.\n");
                                    scPrestam.nextLine();  // Limpiar el buffer
                                    scPrestam.nextLine();  // Esperar a que el usuario presione Enter
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
                                        scPrestam.nextLine();  // Limpiar el buffer
                                        scPrestam.nextLine();  // Esperar a que el usuario presione Enter
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
                                    valorIngresado = scPrestam.nextInt();
                                } catch (InputMismatchException error) {
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                                    scPrestam.nextLine();  // Limpiar el buffer
                                    scPrestam.nextLine();  // Esperar a que el usuario presione Enter
                                    continue;
                                }

                                if (valorIngresado < valorInt) {
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor ingresado es menor al requerido.\n" +
                                            "Presiona enter para volver a intentar.\n");
                                    scPrestam.nextLine();  // Limpiar el buffer
                                    scPrestam.nextLine();  // Esperar a que el usuario presione Enter
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
                            scPrestam.nextLine();  // Limpiar el buffer
                            scPrestam.nextLine(); // Esperar a que el usuario presione Enter


                            break;

                        case 0: // Salir
                            return;

                        default:
                            System.out.println("\n### ERROR ###");
                            System.out.println("Opcion fuera del rango. Presione Enter para intentar de nuevo.");
                            scPrestam.nextLine();  // Esperar a que el usuario presione Enter
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
                            return null;
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
                            return null;
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
                            return null;
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
                    valorIngresado = scPrestam.nextInt();
                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scPrestam.nextLine();  // Limpiar el buffer
                    scPrestam.nextLine();  // Esperar a que el usuario presione Enter
                    continue;
                }

                if (valorIngresado < valorMulta) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor ingresado es menor al requerido.\n" +
                            "Presiona enter para volver a intentar.\n");
                    scPrestam.nextLine();  // Limpiar el buffer
                    scPrestam.nextLine();  // Esperar a que el usuario presione Enter
                } else {
                    break;
                }
            }

            local.agregarFondos(valorMulta);
        }
    }

    public static class fAdministrarInventario {
        static Scanner scInvent = new Scanner(System.in);

        public static void revisarInventario(Tienda local, Fecha fechaActual) {

            /* ~~~ Seleccion de las opciones ~~~ */
            byte opcion;
            ArrayList<Producto> lista;

            while (true) {
                imprimirSeparador();
                System.out.println("Desea\n1. Revisar los productos del local \n2. Modificar la informacion de algun producto\n3. Calcular la prioridad de estos\n4. Crear objeto\n5. Salir ");
                //Evitar un error al ingresar un dato no numerico
                opcion = 0;

                try {
                    opcion = scInvent.nextByte();
                    scInvent.nextLine(); // Limpiar buffer

                } catch (Exception e) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Se debe ingresar un valor numerico valido.");
                    System.out.println("Presione Enter para continuar");
                    scInvent.nextLine(); //Limpiar el buffer
                    scInvent.nextLine();//Esperar que el usuario presione Enter

                    continue;
                }

                switch (opcion) {
                    case 1:
                        //Revisar productos
                        Scanner scOpcion2 = new Scanner(System.in);
                        Scanner scTipo = new Scanner(System.in);
                        byte opcion2; //opcion para decidir en cada caso y para no confundirse con opcion

                        while (true) {
                            imprimirSeparador();
                            System.out.println("1. Revisar por tipo de producto.\n2. Revisar todos los productos en la tienda.\n3. Regresar.");

                            opcion2 = 0;

                            try {//Impedir que haya un error si se ingresa un valor no numerico
                                opcion2 = scOpcion2.nextByte();
                                break;

                            } catch (Exception e) {
                                System.out.println("\n### ERROR ###");
                                System.out.println("Se debe ingresar un valor numerico valido.");
                                System.out.println("Presione Enter para continuar");
                                scOpcion2.nextLine();//Limpiar buffer
                                scOpcion2.nextLine();//Esperar que el usuario presione Enter
                            }
                        }

                        lista = new ArrayList<>();
                        switch (opcion2) {
                            case 1:
                                //Revisar por tipo de producto
                                byte tipo;// tipo a elegir

                                while (true) {
                                    imprimirSeparador();
                                    System.out.println("1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                    tipo = 0;

                                    try {//impedir un error al elegir el tipo
                                        tipo = scTipo.nextByte();
                                        break;
                                    } catch (Exception e) {
                                        System.out.println("\n### ERROR ###");
                                        System.out.println("El valor debe ser numerico");
                                        System.out.println("Presione Enter para continuar");
                                        scTipo.nextLine(); //Limpiar buffer
                                        scTipo.nextLine(); //Esperar que el usuario presione Enter
                                    }
                                }
                                if (tipo == 4) {
                                    continue;
                                }
                                String orden = elegirOrden();

                                switch (tipo) {
                                    case 1:

                                        //Se eligio revisar por tipo Accesorio
                                        for (Producto i : local.getInventario()) {
                                            if (i instanceof Accesorio) {
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista, orden);
                                        for (Producto i : lista) {
                                            System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta());
                                        }
                                        break;
                                    case 2:
                                        //Se eligio revisar por tipo Consola
                                        for (Producto i : local.getInventario()) {
                                            if (i instanceof Consola) {
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista, orden);
                                        for (Producto i : lista) {
                                            System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta());
                                        }
                                        break;

                                    case 3:
                                        //Se eligio revisar por tipo Juego
                                        for (Producto i : local.getInventario()) {
                                            if (i instanceof Juego) {
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista, orden);
                                        for (Producto i : lista) {
                                            System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta() + " | PLATAFORMA: " + ((Juego) i).getPlataforma());
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 2:
                                //~~~~~~~~~~  Revisar todos los productos  ~~~~~~~~~~//
                                //Darle todos los objetos del local a la lista
                                Collections.addAll(lista, local.getInventario().toArray(new Producto[0]));

                                String orden2 = elegirOrden();
                                Producto.ordenar(lista, orden2);

                                for (Producto i : lista) {
                                    if (i instanceof Juego) {
                                        System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta() + " | PLATAFORMA: " + ((Juego) i).getPlataforma());
                                    } else {
                                        System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta());
                                    }
                                }
                                break;
                        }
                        continue;
                    case 2:
                        while (true) {
                            //Modificar la informacion de algun producto
                            byte tipo;
                            lista = new ArrayList<Producto>();
                            try {//impedir un error al elegir el tipo
                                imprimirSeparador();
                                System.out.println("Ingresa el tipo de producto que modificaras\n1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                tipo = scInvent.nextByte();
                            } catch (Exception e) {
                                imprimirSeparador();

                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");
                                scInvent.nextLine();
                                continue;
                            }
                            if (tipo == 1) {
                                for (Producto i : local.getInventario()) {
                                    if (i instanceof Accesorio) {
                                        lista.add(i);
                                    }
                                }
                            } else if (tipo == 2) {
                                for (Producto i : local.getInventario()) {
                                    if (i instanceof Consola) {
                                        lista.add(i);
                                    }
                                }
                            } else if (tipo == 3) {
                                for (Producto i : local.getInventario()) {
                                    if (i instanceof Juego) {
                                        lista.add(i);
                                    }
                                }
                            } else if (tipo == 4) {
                                break;
                            }
                            for (Producto i : lista) {
                                System.out.println(i.getNombre() + "| Codigo: " + i.getCodigo() + " | Precio: $" + i.getValor());
                            }

                            int cod;//Codigo a elegir
                            int index = 0;//Posicion del objeto buscado

                            try {
                                imprimirSeparador();
                                System.out.println("Ingrese el codigo del objeto a modificar o 0 para regresar");

                                cod = scInvent.nextInt();
                            } catch (Exception e) {
                                imprimirSeparador();
                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");

                                continue;
                            }
                            if (cod == 0) {
                                continue;
                            }
                            boolean existe = false;
                            Producto producto = null;
                            for (Producto i : lista) {
                                if (i.getCodigo() == cod) {
                                    index = lista.indexOf(i);
                                    producto = i;
                                    existe = true;
                                }
                            }

                            if (!existe) {
                                imprimirSeparador();
                                System.out.println("El codigo ingresado no existe");

                                continue;
                            }

                            while (true) {
                                imprimirSeparador();
                                if (producto instanceof Juego){
                                    System.out.println("Que desea cambiar \n1.Nombre \n2.Precio \n3.Prestable \n4.Condicion \n5.Descuento \n6.Puntos Requeridos \n7.Genero \n8.Plataforma \n9.Salir");
                                }
                                else if (producto instanceof Consola){
                                    System.out.println("Que desea cambiar \n1.Nombre \n2.Precio \n3.Prestable \n4.Condicion \n5.Descuento \n6.Puntos Requeridos \n7.Marca \n8.Salir");
                                }
                                else if (producto instanceof Accesorio){
                                    System.out.println("Que desea cambiar \n1.Nombre \n2.Precio \n3.Prestable \n4.Condicion \n5.Descuento \n6.Puntos Requeridos \n7.Marca \n8.Consola \n9.Salir");
                                }
                                try {
                                    tipo = scInvent.nextByte();
                                    if (tipo < 1 || tipo > 9) {
                                        imprimirSeparador();
                                        System.out.println("\n### ERROR ###");
                                        System.out.println("El valor debe ser entre 1 y 9");
                                        continue;
                                    }
                                } catch (Exception e) {
                                    imprimirSeparador();
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");
                                    tipo = 0;
                                    scInvent.nextLine();
                                    continue;
                                }
                                switch (tipo) {
                                    case 1:
                                        String nuevoNombre;
                                        imprimirSeparador();
                                        System.out.println("Ingrese el nuevo nombre: ");
                                        scInvent.nextLine();
                                        nuevoNombre = scInvent.nextLine();
                                        producto.setNombre(nuevoNombre);

                                        break;

                                    case 2:
                                        int nuevoPrecio;
                                        while (true) {
                                            imprimirSeparador();
                                            System.out.println("Ingrese el nuevo precio");

                                            try {
                                                nuevoPrecio = scInvent.nextInt();
                                                scInvent.nextLine();
                                                break;
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                scInvent.nextLine();
                                            }
                                        }
                                        producto.setValor(nuevoPrecio);
                                        scInvent.nextLine();
                                        break;
                                    case 3:
                                        imprimirSeparador();
                                        boolean prestable = siNo("Es prestable?");
                                        producto.setPrestable(prestable);
                                        break;
                                    case 4:
                                        byte condicion;
                                        while (true) {
                                            imprimirSeparador();
                                            System.out.println("Ingrese la condicion(1-4:usado 5:nuevo)");
                                            try {
                                                condicion = scInvent.nextByte();
                                                producto.setCondicion(condicion);
                                                break;
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                scInvent.nextLine();
                                            }
                                        }
                                        producto.setCondicion(condicion);
                                        break;
                                    case 5:
                                        int descuento;
                                        while (true) {
                                            imprimirSeparador();
                                            System.out.println("Ingrese el descuento");
                                            try {
                                                descuento = scInvent.nextInt();
                                                producto.setDescuento(descuento);
                                                break;
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                scInvent.nextLine();
                                            }
                                        }
                                        break;
                                    case 6:
                                        int puntosRequeridos;
                                        while (true) {
                                            imprimirSeparador();
                                            System.out.println("Ingrese los puntos requeridos");
                                            try {
                                                puntosRequeridos = scInvent.nextInt();
                                                producto.setPuntosRequeridos(puntosRequeridos);
                                                break;
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                scInvent.nextLine();
                                            }
                                        }
                                        break;
                                    case 7:
                                        if (producto instanceof Juego){
                                            String genero;
                                            imprimirSeparador();
                                            System.out.println("Ingrese el nuevo genero: ");
                                            scInvent.nextLine();
                                            genero = scInvent.nextLine();
                                            ((Juego) producto).setGenero(genero);
                                        }
                                        else if (producto instanceof Consola){
                                            String marca;
                                            imprimirSeparador();
                                            System.out.println("Ingrese la nueva marca: ");
                                            scInvent.nextLine();
                                            marca = scInvent.nextLine();
                                            ((Consola) producto).setMarca(marca);
                                        }
                                        else if (producto instanceof Accesorio){
                                            String marca;
                                            imprimirSeparador();
                                            System.out.println("Ingrese la nueva marca: ");
                                            scInvent.nextLine();
                                            marca = scInvent.nextLine();
                                            ((Accesorio) producto).setMarca(marca);
                                        }
                                        break;
                                    case 8:
                                        if (producto instanceof Juego){
                                            String plataforma;
                                            imprimirSeparador();
                                            System.out.println("Ingrese la nueva plataforma: ");
                                            scInvent.nextLine();
                                            plataforma = scInvent.nextLine();
                                            ((Juego) producto).setPlataforma(plataforma);
                                        }
                                        else if (producto instanceof Accesorio){
                                            String consola;
                                            imprimirSeparador();
                                            System.out.println("Ingrese la nueva consola: ");
                                            scInvent.nextLine();
                                            consola = scInvent.nextLine();
                                            ((Accesorio) producto).setConsola(consola);
                                        }
                                        break;
                                    case 9:
                                        break;
                                }
                                break;

                            }
                            break;
                        }
                        continue;
                    case 3:
                        //Calcular prioridad de productos
                        for (Producto i : local.getInventario()) {
                            if (i.getPrioridad() == null) {
                                if (i.getCantidadInicial() - i.getCantidad() > i.getCantidadInicial() * 0.8) {
                                    i.setPrioridad("Prioridad muy alta");
                                } else if (i.getCantidadInicial() - i.getCantidad() >= i.getCantidadInicial() * 0.51) {
                                    i.setPrioridad("Prioridad alta");
                                } else if (i.getCantidadInicial() - i.getCantidad() >= i.getCantidadInicial() * 0.21) {
                                    i.setPrioridad("Prioridad media");
                                } else {
                                    i.setPrioridad("Prioridad baja");
                                }
                            }
                        }
                        analizarMercado(local, fechaActual);
                        continue;
                    case 4:
                        //Crear producto
                        crearProducto(local);
                        continue;

                    case 5:
                        break;

                }
                break;
            }
        }

        private static String elegirOrden() {
            imprimirSeparadorPequeno();

            Scanner scOrden = new Scanner(System.in);

            String palabra;
            boolean esValido = false;

            do {
                System.out.println("Elija el tipo de orden (sin tildes): \n• Alfabetico \n• Ventas\n• Precio");
                palabra = scOrden.nextLine();

                if (palabra.equalsIgnoreCase("Alfabetico") ||
                        palabra.equalsIgnoreCase("Ventas") ||
                        palabra.equalsIgnoreCase("Precio")) {
                    esValido = true;
                } else {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Valor no valido. Debe ingresar una de las opciones: Alfabetico, Ventas o Precio.");
                }
            } while (!esValido);

            return palabra;
        }

        private static void analizarMercado(Tienda local, Fecha fechaActual) {
            byte opcion = 0;
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Desea \n1.Hacer analisis de mercado \n2.Revisar prioridad de cada producto \n3.Regresar");
                    opcion = scInvent.nextByte();
                    scInvent.nextLine();
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    continue;

                }
                switch (opcion) {
                    case 1:
                        while (true) {
                            //Hacer analisis de mercado
                            imprimirSeparador();
                            System.out.println("Ingresa la fecha inicial");
                            Fecha fechaInicial = recibirFecha();//Pedir fecha inicial para el rango de ventas a tener en cuenta
                            if (fechaInicial.getTotalDias() > fechaActual.getTotalDias()) {
                                imprimirSeparador();
                                System.out.println("La fecha inicial no es menor a la actual");
                                scInvent.nextLine(); //Limpiar buffer
                                continue;
                            }
                            System.out.println("Ingresa la fecha final");

                            Fecha fechaFinal = recibirFecha();//Pedir fecha final para el rango de ventas a tener en cuenta

                            ArrayList<Transaccion> rangoVentas = new ArrayList<>();
                            for (Transaccion i : local.getCaja()) {
                                if (i.getFecha().getTotalDias() >= fechaInicial.getTotalDias() && i.getFecha().getTotalDias() <= fechaFinal.getTotalDias()) {//Verificar que la fecha de transaccion este dentro del rango de tiempo
                                    rangoVentas.add(i);
                                }
                            }

                            if (fechaFinal.getTotalDias() < fechaInicial.getTotalDias()) {
                                imprimirSeparador();
                                System.out.println("La fecha final esta antes de la final");
                                scInvent.nextLine();
                                break;
                            } else if (rangoVentas.isEmpty()) {
                                imprimirSeparador();
                                System.out.println("No hay ventas dentro del rango");
                                scInvent.nextLine();
                                break;
                            }

                            while (true) {
                                try {
                                    imprimirSeparador();
                                    System.out.println("Desea \n1.Ver ventas individuales \n2.ordenes en este rango \n3.Tendencias en este rango \n4.Proceder al reabastecimiento \n5.Regresar");
                                    opcion = scInvent.nextByte();
                                    scInvent.nextLine();
                                } catch (Exception e) {
                                    imprimirSeparador();
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");
                                    scInvent.nextLine();
                                    continue;
                                }
                                switch (opcion) {
                                    case 1:
                                        //Ver ventas individuales
                                        ArrayList<Producto> listaProductos = new ArrayList<Producto>();

                                        // Identificar productos perteneceintes a las transacciones en el rango de fechas
                                        for (Transaccion t : rangoVentas) {
                                            for (Producto p : t.getProductos()) {
                                                listaProductos.add(p);
                                            }
                                        }

                                        // Ordenar los productos e imprimirlos
                                        Producto.ordenar(listaProductos, "");

                                        for (Producto p : listaProductos) {
                                            System.out.println("    * ID: " + p.getCodigo() + " | Nombre: " + p.getNombre() + " | Cantidad: " + p.getCantidad() + " | Precio: " + p.getValor());
                                        }
                                        continue;
                                    case 2:
                                        //Ordenes en el rango
                                        for (Transaccion t : rangoVentas) {
                                            System.out.println("    * ID: " + t.getId() + " | Fecha: " + t.getFecha() + " | Valor: " + t.getValorFinal() + " | Cliente: " + t.getCliente().getNombre() + " | Empleado: " + t.getEmpleado().getNombre() + " | Productos: ");
                                            for (Producto p : t.getProductos()) {
                                                System.out.println("        * ID: " + p.getCodigo() + " | Nombre: " + p.getNombre() + " | Cantidad: " + p.getCantidad() + " | Precio: " + p.getValor());
                                            }
                                        }

                                        continue;
                                    case 3:
                                        //Tendencias en el rango
                                        while (true) {
                                            opcion = 0;
                                            try {
                                                imprimirSeparador();
                                                System.out.println("Desea ver \n1.Generos \n2.Plataformas \n3.Rangos de precio mas vendidos \n4.Cambiar fecha \n5.Regresar");
                                                opcion = scInvent.nextByte();
                                                scInvent.nextLine();
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                scInvent.nextLine();
                                                continue;
                                            }
                                            switch (opcion) {
                                                case 1:
                                                    //Tendencias de generos
                                                    generosMasComprados(local, fechaInicial, fechaFinal);
                                                    imprimirSeparador();
                                                    if (siNo("Desea ver las tendencias en otros locales")) {
                                                        for (Tienda t : Tienda.getLocales()) {
                                                            if (t != local) { // Si el local no es el actual
                                                                System.out.println(" Local " + t.getNombre() + "| Genero mas vendido: " + generoMasComprado(t));
                                                            }
                                                        }
                                                    }
                                                    break;

                                                case 2:
                                                    //Tendencias de plataformas
                                                    plataformasMasComprados(local, fechaInicial, fechaFinal);
                                                    imprimirSeparador();
                                                    if (siNo("Desea ver las tendencias en otros locales")) {
                                                        for (Tienda t : Tienda.getLocales()) {
                                                            if (t != local) { // Si el local no es el actual
                                                                System.out.println(" Local " + t.getNombre() + "| Plataforma mas vendida: " + plataformaMasComprada(t));
                                                            }
                                                        }
                                                    }
                                                    break;

                                                case 3:
                                                    //Tendencias de rangos mas vendidos
                                                    rangosMasComprados(local, fechaInicial, fechaFinal);

                                                    if (siNo("Desea ver las tendencias en otros locales")) {
                                                        for (Tienda t : Tienda.getLocales()) {
                                                            if (t != local) { // Si el local no es el actual
                                                                imprimirSeparadorPequeno();
                                                                System.out.println(" Local " + t.getNombre() + "| Rangos de precio mas vendidos: ");
                                                                rangosMasComprados(t, fechaInicial, fechaFinal);
                                                            }
                                                        }
                                                    }

                                                    break;

                                            }
                                            break;
                                        }
                                        continue;

                                    case 4:
                                        //Proceder con el reabastecimiento
                                        hacerReabastecimiento(local, fechaActual);
                                        continue;
                                    case 5:
                                        break;
                                }
                                break;
                            }
                            break;
                        }
                        continue;

                    case 2:
                        //Revisar prioridad
                        String orden = "prioridad";
                        String decision = "";
                        while (true) {
                            imprimirSeparador();
                            System.out.println("Desea ver los productos agrupados por tipo(No ponga tildes) \n•Si \n•No \n•Salir");

                            decision = scInvent.nextLine();
                            System.out.println("Presione Enter para continuar");
                            scInvent.nextLine();
                            if (decision.equalsIgnoreCase("si")) {
                                byte tipo;
                                ArrayList<Producto> lista = new ArrayList<Producto>();

                                try {//impedir un error al elegir el tipo
                                    imprimirSeparador();
                                    System.out.println("Ingresa el tipo del producto\n1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                    tipo = scInvent.nextByte();
                                    scInvent.nextLine();
                                } catch (Exception e) {//Informar de un error
                                    imprimirSeparador();

                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");
                                    scInvent.nextLine();
                                    continue;
                                }

                                if (tipo == 1) {
                                    for (Producto i : local.getInventario()) {
                                        if (i instanceof Accesorio) {
                                            lista.add(i);
                                        }
                                    }
                                } else if (tipo == 2) {
                                    for (Producto i : local.getInventario()) {
                                        if (i instanceof Consola) {
                                            lista.add(i);
                                        }
                                    }
                                } else if (tipo == 3) {
                                    for (Producto i : local.getInventario()) {
                                        if (i instanceof Juego) {
                                            lista.add(i);
                                        }
                                    }
                                } else {
                                    break;
                                }
                                for (Producto i : lista) {
                                    System.out.println("Nombre: " + i.getNombre() + " | Prioridad: " + i.getPrioridad());
                                }
                                break;

                            } else if (decision.equalsIgnoreCase("no")) {//No ver por tipo de producto
                                while (true) {
                                    imprimirSeparador();
                                    System.out.println("Desea ver los productos por prioridad:\n•Muy alta \n•Alta \n•Baja \n•Todos");
                                    decision = scInvent.nextLine();

                                    if (decision.equalsIgnoreCase("muy alta")) {
                                        for (Producto i : local.getInventario()) {
                                            if (i.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                                System.out.println("Nombre: " + i.getNombre() + " | Prioridad: " + i.getPrioridad());
                                            }
                                        }
                                        break;

                                    } else if (decision.equalsIgnoreCase("alta")) {
                                        for (Producto i : local.getInventario()) {
                                            if (i.getPrioridad().equalsIgnoreCase("prioridad alta")) {
                                                System.out.println("Nombre: " + i.getNombre() + " | Prioridad: " + i.getPrioridad());
                                            }
                                        }
                                        break;

                                    } else if (decision.equalsIgnoreCase("baja")) {
                                        for (Producto i : local.getInventario()) {
                                            if (i.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                                System.out.println("Nombre: " + i.getNombre() + " | Prioridad: " + i.getPrioridad());
                                            }
                                        }
                                        break;

                                    } else if (decision.equalsIgnoreCase("todos")) {
                                        ArrayList<Producto> lista = new ArrayList<Producto>(local.getInventario());
                                        Producto.ordenar(lista, orden);

                                        for (Producto i : lista) {
                                            System.out.println("Nombre: " + i.getNombre() + " | Prioridad: " + i.getPrioridad());
                                        }
                                        break;
                                    } else {
                                        System.out.println("La desicion ingresada no existe");
                                        scInvent.nextLine();
                                    }

                                }
                            } else if (decision.equalsIgnoreCase("salir")) {
                                break;

                            } else {
                                System.out.println("La opcion ingresada no es valida");
                            }
                        }
                        continue;
                    case 3:
                        break;
                }
                break;
            }
        }

        private static void hacerReabastecimiento(Tienda local, Fecha fechaActual) {
            byte opcion = 0;
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Desea \n1.Reabastecer manualmente \n2.En base a la prioridad\n3.Salir");
                    opcion = scInvent.nextByte();
                    scInvent.nextLine();
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    scInvent.nextLine();
                    continue;
                }
                Tienda localDestino = null;
                Tienda localOrigen = null;
                ArrayList<Producto> listaDeObjetos = new ArrayList<>();
                switch (opcion) {
                    case 1://Reabastecer manualmente
                        while (true) {
                            ArrayList<Juego> lista = new ArrayList<>();
                            try {
                                imprimirSeparador();
                                System.out.println("Desea filtrar \n1.Genero \n2.Plataforma\n3.Rango de precio \n4.Regresar");
                                opcion = scInvent.nextByte();
                                scInvent.nextLine();
                            } catch (Exception e) {
                                imprimirSeparador();
                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");
                                scInvent.nextLine();
                                continue;
                            }
                            String valorActual = null;//Variable para recorrer un atributo string
                            boolean existe = false;//valor booleano para comprobar si existe
                            int ID = 0; //ID del objeto a buscar
                            switch (opcion) {
                                case 1://Genero
                                    for (Producto i : local.getInventario()) {
                                        if (i instanceof Juego) {
                                            lista.add((Juego) i);
                                        }
                                    }
                                    local.agregarOrden(reabastecerManual(local, lista, fechaActual, 2));
                                    continue;

                                case 2://Plataforma
                                    for (Producto i : local.getInventario()) {
                                        if (i instanceof Juego) {
                                            lista.add((Juego) i);
                                        }
                                    }
                                    local.agregarOrden(reabastecerManual(local, lista, fechaActual));
                                    continue;

                                case 3://Rango
                                    local.agregarOrden(reabastecerManual(local, local.getInventario(), fechaActual, "rango"));
                                    continue;
                            }
                            if (opcion == 4) {
                                break;
                            }
                            System.out.println("La opcion no es valida");
                        }
                    case 2://Prioridad
                        while (true) {
                            try {
                                imprimirSeparador();
                                System.out.println("Desea ver \n1.Alta\n2.Baja\n3.Regresar");
                                opcion = scInvent.nextByte();
                                scInvent.nextLine();
                            } catch (Exception e) {
                                imprimirSeparador();
                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");
                                scInvent.nextLine();
                                continue;
                            }

                            int decision = 0;
                            switch (opcion) {
                                case 1://Alta prioridad
                                    while (true) {
                                        ArrayList<Producto> lista = new ArrayList<>();
                                        for (Producto i : local.getInventario()) {
                                            if (i.getPrioridad().equalsIgnoreCase("prioridad alta") || i.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista, "prioridad");
                                        if (lista.isEmpty()) {
                                            imprimirSeparador();
                                            System.out.println("No hay productos con prioridad alta o muy alta en este local.");
                                            scInvent.nextLine();
                                            continue;
                                        } else {
                                            for (Producto j : lista) {
                                                System.out.println("Nombre: " + j.getNombre() + " | Prioridad: " + j.getPrioridad() + " | ID: " + j.getCodigo());
                                            }
                                        }
                                        try {
                                            imprimirSeparador();
                                            System.out.println("Ingresa el ID del producto");
                                            decision = scInvent.nextInt();
                                            scInvent.nextLine();
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            scInvent.nextLine();
                                            continue;
                                        }
                                        boolean esTrue = false;
                                        int indice = 0;
                                        Producto producto = null;
                                        ArrayList<Tienda> localesValidos = new ArrayList<>();
                                        for (Producto i : lista) {
                                            if (i.getCodigo() == decision) {
                                                esTrue = true;
                                                producto = i;
                                                break;
                                            }
                                        }
                                        if (esTrue) {
                                            for (Tienda t : Tienda.getLocales()) {//recorrer los locales en busca del producto
                                                for (Producto p : t.getInventario()) {
                                                    if(p.getPrioridad()==null){
                                                        continue;
                                                    }
                                                    if (producto.getNombre().equalsIgnoreCase(p.getNombre()) && t != local) {
                                                        if ("prioridad media".equalsIgnoreCase(p.getPrioridad()) || "prioridad baja".equalsIgnoreCase(p.getPrioridad())) {
                                                            localesValidos.add(t);
                                                        }
                                                    }
                                                }
                                            }
                                            if (localesValidos.isEmpty()) {
                                                imprimirSeparador();
                                                System.out.println("No se encontro el producto disponible en ningun local.");
                                                scInvent.nextLine();
                                                break;
                                            }
                                        } else {
                                            imprimirSeparador();
                                            System.out.println("El ID ingresado no existe");
                                            continue;
                                        }

                                        while (true) {
                                            String eleccion = "";
                                            imprimirSeparador();
                                            System.out.println("Locales con el producto en cuestion");
                                            for (Tienda t : localesValidos) {//recorrer los locales en busca del producto
                                                for (Producto p : t.getInventario()) {
                                                    if (producto.getNombre().equalsIgnoreCase(p.getNombre())) {
                                                        if (p.getPrioridad().equalsIgnoreCase("prioridad media") || p.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                                            System.out.println("•" + t.getNombre() + "| cantidad: " + p.getCantidad());
                                                        }
                                                    }
                                                }
                                            }
                                            System.out.println("Ingrese el nombre del local");
                                            eleccion = scInvent.nextLine();
                                            scInvent.nextLine();
                                            int cantidad = 0;
                                            for (Tienda i : localesValidos) {
                                                if (i.getNombre().equalsIgnoreCase(eleccion)) {
                                                    while (true) {
                                                        try {
                                                            imprimirSeparador();
                                                            System.out.println("Ingrese la cantidad");
                                                            cantidad = scInvent.nextInt();
                                                            localOrigen = i;
                                                        } catch (Exception e) {
                                                            imprimirSeparador();
                                                            System.out.println("\n### ERROR ###");
                                                            System.out.println("El valor debe ser numerico");
                                                            scInvent.nextLine();
                                                            continue;
                                                        }
                                                        Producto pclonado;
                                                        for (Producto p : i.getInventario()) {
                                                            if (p.getNombre().equalsIgnoreCase(lista.get(indice).getNombre())) {
                                                                if (p.getCantidad() - cantidad >= p.getCantidadInicial() * 0.4) {
                                                                    try {
                                                                        pclonado = p.clone();
                                                                    } catch (CloneNotSupportedException e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                    pclonado.setCantidadInicial(cantidad);
                                                                    pclonado.setCantidad(cantidad);
                                                                    localOrigen.retirarProducto(p, cantidad);//quitar las unidades del producto en el local
                                                                    listaDeObjetos.add(pclonado);
                                                                } else {
                                                                    if (p.getCantidad() - p.getCantidadInicial() * 0.4 >= 0) {
                                                                        cantidad = p.getCantidad() - (int) (p.getCantidadInicial() * 0.4);
                                                                    } else if (p.getCantidad() - p.getCantidadInicial() * 0.4 < 0) {
                                                                        cantidad = 0;
                                                                    }
                                                                    imprimirSeparador();
                                                                    System.out.println("La cantidad es superior a la permitida\nCantidad maxima permitida: " + cantidad);
                                                                    scInvent.nextLine();
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        break;
                                                    }
                                                } else if (localesValidos.indexOf(i) == (localesValidos.size() - 1)) {
                                                    eleccion = null;
                                                }
                                            }
                                            if (eleccion == null) {
                                                imprimirSeparador();
                                                System.out.println("El local ingresado no existe");
                                                System.out.println("Desea: \n•Salir\n•Continuar");
                                                eleccion = scInvent.nextLine();
                                                if (eleccion.equalsIgnoreCase("salir")) {
                                                    break;
                                                }
                                            }

                                        }
                                        if (listaDeObjetos.isEmpty()) {
                                            break;
                                        } else {
                                            while (true) {
                                                try {
                                                    imprimirSeparador();
                                                    System.out.println("Desea \n1.Añadir otro producto \n2.Crear rebastecimiento \n3.Cancelar");
                                                    decision = scInvent.nextInt();
                                                    scInvent.nextLine();
                                                    break;
                                                } catch (Exception e) {
                                                    imprimirSeparador();
                                                    System.out.println("\n### ERROR ###");
                                                    System.out.println("El valor debe ser numerico");
                                                    scInvent.nextLine();
                                                }
                                            }
                                            if (decision == 1) {
                                                continue;
                                            } else if (decision == 2) {
                                                local.agregarOrden(new Reabastecimiento(localOrigen, local, new Fecha(fechaActual.getTotalDias() + 30), listaDeObjetos));
                                                break;
                                            } else if (decision == 3) {
                                                break;
                                            }

                                        }
                                    }
                                    continue;
                                case 2://Baja prioridad
                                    while (true) {
                                        ArrayList<Producto> lista = new ArrayList<>();
                                        for (Producto i : local.getInventario()) {
                                            if (i.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista, "alfabetico");
                                        if (lista.isEmpty()) {
                                            imprimirSeparador();
                                            System.out.println("No hay productos con prioridad baja en este local.");
                                            scInvent.nextLine();
                                            break;
                                        } else {
                                            for (Producto j : lista) {
                                                System.out.println("Nombre: " + j.getNombre() + " | Prioridad: " + j.getPrioridad() + " | ID: " + j.getCodigo());
                                            }
                                        }
                                        try {
                                            imprimirSeparador();
                                            System.out.println("Ingresa el ID del producto");
                                            decision = scInvent.nextInt();
                                            scInvent.nextLine();
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            scInvent.nextLine();
                                            continue;
                                        }
                                        boolean esTrue = false;
                                        int indice = 0;
                                        Producto producto = null;
                                        ArrayList<Tienda> localesValidos = new ArrayList<>();
                                        for (Producto i : lista) {
                                            if (i.getCodigo() == decision) {
                                                esTrue = true;
                                                indice = lista.indexOf(i);
                                                producto = i;
                                                break;
                                            }
                                        }
                                        if (esTrue) {
                                            for (Tienda t : Tienda.getLocales()) {//recorrer los locales en busca del producto
                                                for (Producto p : t.getInventario()) {
                                                    if(p.getPrioridad()==null){
                                                        continue;
                                                    }
                                                    if (producto.getNombre().equalsIgnoreCase(p.getNombre()) && t != local) {
                                                        if (p.getPrioridad().equalsIgnoreCase("prioridad alta") || p.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                                            localesValidos.add(t);
                                                        }
                                                    }
                                                }
                                            }
                                            if (localesValidos.isEmpty()) {
                                                imprimirSeparador();
                                                System.out.println("No se encontro necesidad del producto en ningun local.");
                                                scInvent.nextLine();
                                                break;
                                            }
                                        } else {
                                            imprimirSeparador();
                                            System.out.println("El ID ingresado no existe");
                                            continue;
                                        }

                                        while (true) {
                                            String eleccion = "";
                                            imprimirSeparador();
                                            System.out.println("Locales con el producto en cuestion");
                                            for (Tienda t : localesValidos) {//recorrer los locales en busca del producto
                                                for (Producto p : t.getInventario()) {
                                                    if (producto.getNombre().equalsIgnoreCase(p.getNombre())) {
                                                        if (p.getPrioridad().equalsIgnoreCase("prioridad alta") || p.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                                            imprimirSeparador();
                                                            System.out.println("•" + t.getNombre() + "| cantidad: " + p.getCantidad() + " | Prioridad: " + p.getPrioridad());
                                                            scInvent.nextLine();
                                                        }
                                                    }
                                                }
                                            }
                                            System.out.println("Ingrese el nombre del local");
                                            eleccion = scInvent.nextLine();
                                            scInvent.nextLine();
                                            int cantidad = 0;
                                            for (Tienda i : localesValidos) {
                                                if (i.getNombre().equalsIgnoreCase(eleccion)) {
                                                    while (true) {
                                                        try {
                                                            imprimirSeparador();
                                                            System.out.println("Ingrese la cantidad del producto");
                                                            cantidad = scInvent.nextInt();

                                                            localDestino = i;
                                                        } catch (Exception e) {
                                                            imprimirSeparador();
                                                            System.out.println("\n### ERROR ###");
                                                            System.out.println("El valor debe ser numerico");
                                                            scInvent.nextLine();
                                                            continue;
                                                        }
                                                        for (Producto p : i.getInventario()) {
                                                            if (p.getNombre().equalsIgnoreCase(producto.getNombre())) {
                                                                if (local.getInventario().get(indice).getCantidad() - cantidad >= local.getInventario().get(indice).getCantidadInicial() * 0.4) {
                                                                    Producto pclonado;
                                                                    try {
                                                                        pclonado = p.clone();
                                                                    } catch (CloneNotSupportedException e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                    pclonado.setCantidadInicial(cantidad);
                                                                    pclonado.setCantidad(cantidad);
                                                                    local.retirarProducto(p, cantidad);//quitar las unidades del producto en el local
                                                                    listaDeObjetos.add(pclonado);
                                                                } else {
                                                                    if (local.getInventario().get(indice).getCantidad() - local.getInventario().get(indice).getCantidadInicial() * 0.4 >= 0) {
                                                                        cantidad = local.getInventario().get(indice).getCantidad() - (int) (local.getInventario().get(indice).getCantidadInicial() * 0.4);
                                                                    } else if (local.getInventario().get(indice).getCantidad() - local.getInventario().get(indice).getCantidadInicial() * 0.4 < 0) {
                                                                        cantidad = 0;
                                                                    }
                                                                    imprimirSeparador();
                                                                    System.out.println("La cantidad es superior a la permitida\nCantidad maxima permitida: " + cantidad);
                                                                    scInvent.nextLine();
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (cantidad == 0) {
                                                            continue;
                                                        } else {
                                                            break;
                                                        }
                                                    }
                                                } else if (localesValidos.indexOf(i) == (localesValidos.size() - 1)) {
                                                    eleccion = null;
                                                }
                                            }
                                            if (eleccion == null) {
                                                imprimirSeparador();
                                                System.out.println("El local ingresado no existe");
                                                System.out.println("Desea: \n•Salir\n•Continuar");
                                                eleccion = scInvent.nextLine();
                                                if (eleccion.equalsIgnoreCase("salir")) {
                                                    break;
                                                }
                                            }

                                        }
                                        if (listaDeObjetos.isEmpty()) {
                                            break;
                                        } else {
                                            while (true) {
                                                try {
                                                    imprimirSeparador();
                                                    System.out.println("Desea \n1.Añadir otro producto \n2.Crear rebastecimiento \n3.Cancelar");
                                                    decision = scInvent.nextInt();
                                                    scInvent.nextLine();
                                                    break;
                                                } catch (Exception e) {
                                                    imprimirSeparador();
                                                    System.out.println("\n### ERROR ###");
                                                    System.out.println("El valor debe ser numerico");
                                                    scInvent.nextLine();
                                                }
                                            }
                                            if (decision == 1) {
                                                continue;
                                            } else if (decision == 2) {
                                                localDestino.agregarOrden(new Reabastecimiento(local, localDestino, new Fecha(fechaActual.getTotalDias() + 30), listaDeObjetos));
                                                break;
                                            } else if (decision == 3) {
                                                break;
                                            }

                                        }
                                    }
                                    continue;
                                case 3://salir
                                    opcion = 0;
                                    break;
                            }
                            break;
                        }
                    case 3:
                        break;
                }
                break;
            }
        }

        // Metodo que retorna el genero mas comprado en un local ingresado
        // El resultado sera null si la tienda no tiene transacciones
        private static String generoMasComprado(Tienda local) {
            ArrayList<Integer> generosCant = new ArrayList<Integer>();
            ArrayList<String> generos = new ArrayList<String>();

            for (Transaccion t : local.getCaja()) { // Buscar en cada transaccion de la tienda
                for (Producto p : t.getProductos()) { // Buscar cada producto de la transaccion
                    if (p instanceof Juego) {
                        Juego j = (Juego) p;
                        if (generos.contains(j.getGenero())) { // Si el genero ya esta en la lista, aumentar la cantidad
                            int indice = generos.indexOf(j.getGenero());
                            generosCant.set(indice, generosCant.get(indice) + 1);
                        } else { // Si no esta, agregarlo a la lista
                            generos.add(j.getGenero());
                            generosCant.add(1);
                        }
                    }
                }
            }
            // Encontrar el genero mas vendido
            int max = 0;
            String generoFav = "Ninguno. La tienda no ha vendido juegos";
            // Este es el mensaje por defecto del genero favorito que se preservara solo en caso
            // que la tienda no tenga transacciones


            for (int i = 0; i < generosCant.size(); i++) {
                if (generosCant.get(i) > max) {
                    max = generosCant.get(i);
                    generoFav = generos.get(i);
                }
            }

            return generoFav;
        }

        // Metodo que muestra los generos mas comprados y la cantidad de ventas
        private static void generosMasComprados(Tienda local, Fecha fechaInicio, Fecha fechaFin) {
            // Comprobar que la tienda tiene transacciones
            if (local.getCaja().isEmpty()) {
                System.out.println("No hay transacciones en la tienda");
                return;
            }

            ArrayList<Integer> generosCant = new ArrayList<Integer>();
            ArrayList<String> generos = new ArrayList<String>();

            for (Transaccion t : local.getCaja()) { // Buscar en cada transaccion de la tienda
                // Filtrar transacciones que esten dentro del rango de fechas
                if (t.getFecha().getTotalDias() >= fechaInicio.getTotalDias() && t.getFecha().getTotalDias() <= fechaFin.getTotalDias()) {
                    for (Producto p : t.getProductos()) { // Buscar cada producto de la transaccion
                        if (p instanceof Juego) {
                            Juego j = (Juego) p;
                            if (generos.contains(j.getGenero())) { // Si el genero ya esta en la lista, aumentar la cantidad
                                int indice = generos.indexOf(j.getGenero());
                                generosCant.set(indice, generosCant.get(indice) + 1);
                            } else { // Si no esta, agregarlo a la lista
                                generos.add(j.getGenero());
                                generosCant.add(1);
                            }
                        }
                    }
                }
            }

            // Encontrar e imprimir los generos mas vendidos y sus cantidades
            for (int j = 0; j < generosCant.size(); j++) {
                int max = 0;
                String generoFav = "";

                for (int i = 0; i < generosCant.size(); i++) {
                    if (generosCant.get(i) >= max) {
                        max = generosCant.get(i);
                        generoFav = generos.get(i);
                    }
                }

                System.out.println((j + 1) + ". " + generoFav + " -- " + max + " ventas");

                generosCant.remove(generos.indexOf(generoFav));
                generos.remove(generoFav);
            }
        }

        // Metodo que muestra los n generos mas comprados y sus ventas
        private static void generosMasComprados(Tienda local, int n, Fecha fechaInicio, Fecha fechaFin) {
            // Comprobar que la tienda tiene transacciones
            if (local.getCaja().isEmpty()) {
                System.out.println("No hay transacciones en la tienda");
                return;
            }

            ArrayList<Integer> generosCant = new ArrayList<Integer>();
            ArrayList<String> generos = new ArrayList<String>();

            for (Transaccion t : local.getCaja()) { // Buscar en cada transaccion de la tienda
                if (t.getFecha().getTotalDias() >= fechaInicio.getTotalDias() && t.getFecha().getTotalDias() <= fechaFin.getTotalDias()) {
                    for (Producto p : t.getProductos()) { // Buscar cada producto de la transaccion
                        if (p instanceof Juego) {
                            Juego j = (Juego) p;
                            if (generos.contains(j.getGenero())) { // Si el genero ya esta en la lista, aumentar la cantidad
                                int indice = generos.indexOf(j.getGenero());
                                generosCant.set(indice, generosCant.get(indice) + 1);
                            } else { // Si no esta, agregarlo a la lista
                                generos.add(j.getGenero());
                                generosCant.add(1);
                            }
                        }
                    }
                }
            }

            // Encontrar e imprimir los n generos mas vendidos y sus cantidades
            for (int j = 0; j < generosCant.size() && j < n; j++) {
                int max = 0;
                String generoFav = "";

                for (int i = 0; i < generosCant.size(); i++) {
                    if (generosCant.get(i) >= max) {
                        max = generosCant.get(i);
                        generoFav = generos.get(i);
                    }
                }

                System.out.println((j + 1) + ". " + generoFav + " -- " + max + " ventas");

                generosCant.remove(generos.indexOf(generoFav));
                generos.remove(generoFav);
            }
        }

        // Metodo que retorna la plataforma mas comprada en un local ingresado
        private static String plataformaMasComprada(Tienda local) {
            ArrayList<Integer> plataformasCant = new ArrayList<Integer>();
            ArrayList<String> plataformas = new ArrayList<String>();

            for (Transaccion t : local.getCaja()) { // Buscar en cada transaccion de la tienda
                for (Producto p : t.getProductos()) { // Buscar cada producto de la transaccion
                    if (p instanceof Juego) {
                        Juego j = (Juego) p;
                        if (plataformas.contains(j.getPlataforma())) { // Si la plataforma ya esta en la lista, aumentar la cantidad
                            int indice = plataformas.indexOf(j.getPlataforma());
                            plataformasCant.set(indice, plataformasCant.get(indice) + 1);
                        } else { // Si no esta, agregarlo a la lista
                            plataformas.add(j.getPlataforma());
                            plataformasCant.add(1);
                        }
                    }
                }
            }
            // Encontrar la plataforma mas vendida
            int max = 0;
            String plataformaFav = "Ninguna. La tienda no ha vendido juegos";
            // Este es el mensaje por defecto de la plataforma favorita que se preservara solo en caso
            // que la tienda no tenga transacciones

            for (int i = 0; i < plataformasCant.size(); i++) {
                if (plataformasCant.get(i) > max) {
                    max = plataformasCant.get(i);
                    plataformaFav = plataformas.get(i);
                }
            }

            return plataformaFav;
        }

        //Metodo que muestra las plataformas mas compradas
        private static void plataformasMasComprados(Tienda local, Fecha fechaInicio, Fecha fechaFin) {
            // Comprobar que la tienda tiene transacciones
            if (local.getCaja().isEmpty()) {
                System.out.println("No hay transacciones en la tienda");
                return;
            }

            ArrayList<Integer> plataformasCant = new ArrayList<Integer>();
            ArrayList<String> plataformas = new ArrayList<String>();

            for (Transaccion t : local.getCaja()) { // Buscar en cada transaccion de la tienda
                // Filtrar transacciones que esten dentro del rango de fechas
                if (t.getFecha().getTotalDias() >= fechaInicio.getTotalDias() && t.getFecha().getTotalDias() <= fechaFin.getTotalDias()) {
                    for (Producto p : t.getProductos()) { // Buscar cada producto de la transaccion
                        if (p instanceof Juego) {
                            Juego j = (Juego) p;
                            if (plataformas.contains(j.getPlataforma())) { // Si el genero ya esta en la lista, aumentar la cantidad
                                int indice = plataformas.indexOf(j.getPlataforma());
                                plataformasCant.set(indice, plataformasCant.get(indice) + 1);
                            } else { // Si no esta, agregarlo a la lista
                                plataformas.add(j.getGenero());
                                plataformasCant.add(1);
                            }
                        }
                    }
                }
            }

            // Encontrar e imprimir los generos mas vendidos y sus cantidades
            for (int j = 0; j < plataformasCant.size(); j++) {
                int max = 0;
                String generoFav = "";

                for (int i = 0; i < plataformasCant.size(); i++) {
                    if (plataformasCant.get(i) >= max) {
                        max = plataformasCant.get(i);
                        generoFav = plataformas.get(i);
                    }
                }

                System.out.println((j + 1) + ". " + generoFav + " -- " + max + " ventas");

                plataformasCant.remove(plataformas.indexOf(generoFav));
                plataformas.remove(generoFav);
            }
        }

        // Metodo que imprime en orden los rangos de precios de juegos mas vendidos en un local
        private static void rangosMasComprados(Tienda local, Fecha fechaInicio, Fecha fechaFin) {
            // Comprobar que la tienda tiene transacciones
            if (local.getCaja().isEmpty()) {
                System.out.println("No hay transacciones en la tienda");
                return;
            }

            // Variables que representan cada rango y la cantidad de juegos que ha vendido
            int rango1_20 = 0;
            int rango21_40 = 0;
            int rango41_60 = 0;

            for (Transaccion t : local.getCaja()) { // Examinar cada transaccion del local
                // Filtrar transacciones que esten dentro del rango de fechas
                if (t.getFecha().getTotalDias() >= fechaInicio.getTotalDias() && t.getFecha().getTotalDias() <= fechaFin.getTotalDias()) {
                    for (Producto p : t.getProductos()) { // Examinar cada producto de la transaccion
                        if (p instanceof Juego) {
                            Juego j = (Juego) p;
                            if (j.getValor() >= 1 && j.getValor() <= 20) {
                                rango1_20 += j.getCantidad();
                            } else if (j.getValor() >= 21 && j.getValor() <= 40) {
                                rango21_40 += j.getCantidad();
                            } else if (j.getValor() >= 41) {
                                rango41_60 += j.getCantidad();
                            }
                        }
                    }
                }
            }

            // Imprimir los rangos de precios y la cantidad de juegos vendidos en cada uno ordenados
            System.out.println("Rango de precios 1-20: " + rango1_20 + " juegos");
            System.out.println("Rango de precios 21-40: " + rango21_40 + " juegos");
            System.out.println("Rango de precios 41-60: " + rango41_60 + " juegos");
        }

        //Crea una orden de reabastecimiento a partir de la plataformas
        private static Reabastecimiento reabastecerManual(Tienda local, ArrayList<Juego> p, Fecha fechaActual) {
            int opcion;
            int cantidad = 0;
            boolean existe = false;
            String nombre = "";
            Juego producto = null;
            Tienda localOrigen = null;
            ArrayList<String> plataformas = new ArrayList<>();
            ArrayList<Producto> listadeObjetos = new ArrayList<>();
            while (true) {
                Juego.organizar(p, "plataforma");

                for (Juego i : p) {//Conseguir todas las plataformas existentes
                    if (plataformas.isEmpty()) {
                        plataformas.add(i.getPlataforma());
                    } else if (!plataformas.contains(i.getPlataforma())) {
                        plataformas.add(i.getPlataforma());
                    }
                }
                imprimirSeparador();
                for (String palabra : plataformas) {
                    System.out.println("•" + palabra);
                }
                System.out.println("Ingrese la plataforma a elegir: ");
                nombre = scInvent.nextLine();
                scInvent.nextLine();
                if (!plataformas.contains(nombre)) {
                    System.out.println("La plataforma ingresada no es valida");
                    scInvent.nextLine();
                    continue;
                }
                imprimirSeparador();
                for (Juego i : p) {
                    if (i.getPlataforma().equalsIgnoreCase(nombre)) {
                        System.out.println("•" + i.getNombre() + " | COD: " + i.getCodigo() + " | Plataforma: " + i.getPlataforma());
                    }
                }

                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Ingrese el codigo del producto: ");
                        opcion = scInvent.nextInt();
                        scInvent.nextLine();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                for (Juego i : p) {
                    if (i.getCodigo() == opcion) {
                        producto = i;
                        break;
                    }
                }
                if (producto == null) {
                    System.out.println("El producto ingresado no existe");
                    scInvent.nextLine();
                    continue;
                }
                imprimirSeparador();
                for (Tienda t : Tienda.getLocales()) {
                    for (Producto i : t.getInventario()) {
                        if (i.getNombre().equalsIgnoreCase(producto.getNombre())&& t != local) {
                            existe = true;
                            cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                            if (cantidad < 0) {
                                cantidad = 0;
                            }
                            System.out.println("•Local: " + t.getNombre() + " | Cantidad disponible: " + cantidad);
                        } else if (Tienda.getLocales().indexOf(t) == Tienda.getLocales().size() - 1 && t.getInventario().indexOf(i) == t.getInventario().size() - 1 && !existe) {
                            System.out.println("No se han encontrado productos en otros locales");
                            scInvent.nextLine();
                        }
                    }
                }
                if (!existe) {
                    continue;
                }
                while (true) {
                    nombre = "";
                    imprimirSeparador();
                    System.out.println("Ingrese el local deseado: ");
                    nombre = scInvent.nextLine();
                    scInvent.nextLine();
                    for (Tienda t : Tienda.getLocales()) {
                        if (t.getNombre().equalsIgnoreCase(nombre)) {
                            localOrigen = t;
                        }
                    }
                    if (localOrigen == null) {
                        imprimirSeparador();
                        System.out.println("El local ingresado no existe");
                        continue;
                    }
                    break;
                }
                cantidad = 0;
                for (Producto i : localOrigen.getInventario()) {
                    if (i.getNombre().equalsIgnoreCase(producto.getNombre())) {
                        cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                    }
                }
                if (cantidad < 0) {
                    System.out.println("No es posible reabastecer el producto desde este local.");
                    continue;
                }
                while (true) {//ingresar la cantidad a reabastecer
                    try {
                        imprimirSeparador();
                        System.out.println("•" + producto.getNombre() + " | Cantidad disponible para el reabastecimiento: " + cantidad);
                        System.out.println("Ingrese la cantidad a reabastecer: ");
                        cantidad = scInvent.nextInt();
                        scInvent.nextLine();
                        if (cantidad > producto.getCantidadInicial() * 0.4) {
                            imprimirSeparador();
                            System.out.println("La cantidad ingresada es superior a la permitida");
                            System.out.println("Cantidad maxima permitida: " + (int) (producto.getCantidadInicial() * 0.4));
                            scInvent.nextLine();
                            continue;
                        }
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Desea \n1.Reabastecer otro producto \n2.Crear orden de reabastecimiento \n3.Cancelar todo");
                        opcion = scInvent.nextInt();
                        scInvent.nextLine();
                        if (opcion < 0 || opcion > 3) {
                            imprimirSeparador();
                            System.out.println("Opcion no valida");
                            continue;
                        }
                        break;
                    } catch (Exception E) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();

                    }
                }
                if (opcion == 1) {
                    Producto pclonado;
                    try {
                        pclonado = producto.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    pclonado.setCantidadInicial(cantidad);
                    pclonado.setCantidad(cantidad);
                    localOrigen.retirarProducto(producto, cantidad);//quitar las unidades del producto en el local
                    listadeObjetos.add(pclonado);
                    continue;
                } else if (opcion == 2) {
                    break;
                } else if (opcion == 3) {
                    return null;
                }
            }
            return new Reabastecimiento(localOrigen, local, new Fecha(fechaActual.getTotalDias() + 30), listadeObjetos);
        }

        //sobrecarga del metodo para utilizarlo en dos situaciones, crea una orden de reabastecimiento a partir de la plataforma
        private static Reabastecimiento reabastecerManual(Tienda local, ArrayList<Juego> p, Fecha fechaActual, int l) {
            int opcion;
            l = l + 2;
            int cantidad = 0;
            boolean existe = false;
            String nombre = "";
            Juego producto = null;
            Tienda localOrigen = null;
            ArrayList<String> generos = new ArrayList<>();
            ArrayList<Producto> listadeObjetos = new ArrayList<>();
            while (true) {
                Juego.organizar(p, "genero");

                for (Juego i : p) {//Conseguir todos las generos existentes
                    if (generos.isEmpty()) {
                        generos.add(i.getGenero());
                    } else if (!generos.contains(i.getGenero())) {
                        generos.add(i.getGenero());
                    }
                }
                imprimirSeparador();
                for (String palabra : generos) {
                    System.out.println("•" + palabra);
                }
                System.out.println("Ingrese el genero a elegir: ");
                nombre = scInvent.nextLine();
                scInvent.nextLine();
                if (!generos.contains(nombre)) {
                    System.out.println("El genero ingresado no es valido");
                    scInvent.nextLine();
                    continue;
                }
                imprimirSeparador();
                for (Juego i : p) {
                    if (i.getGenero().equalsIgnoreCase(nombre)) {
                        System.out.println("•" + i.getNombre() + " | COD: " + i.getCodigo() + " | Genero: " + i.getGenero()+" | Plataforma: "+i.getPlataforma());
                    }
                }

                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Ingrese el codigo del producto: ");
                        opcion = scInvent.nextInt();
                        scInvent.nextLine();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                for (Juego i : p) {
                    if (i.getCodigo() == opcion) {
                        producto = i;
                        break;
                    }
                }
                if (producto == null){
                    continue;
                }
                imprimirSeparador();
                for (Tienda t : Tienda.getLocales()) {
                    for (Producto i : t.getInventario()) {
                        if (i.getNombre().equalsIgnoreCase(producto.getNombre()) && t != local) {
                            existe = true;
                            cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                            if (cantidad < 0) {
                                cantidad = 0;
                            }
                            System.out.println("•Local: " + t.getNombre() + " | Cantidad disponible: " + cantidad);
                        } else if (Tienda.getLocales().indexOf(t) == Tienda.getLocales().size() - 1 && t.getInventario().indexOf(i) == t.getInventario().size() - 1 && !existe) {
                            System.out.println("No se han encontrado productos en otros locales");
                            scInvent.nextLine();
                        }
                    }
                }
                if (!existe) {
                    continue;
                }
                while (true) {
                    nombre = "";
                    imprimirSeparador();
                    System.out.println("Ingrese el local deseado: ");
                    nombre = scInvent.nextLine();
                    scInvent.nextLine();
                    for (Tienda t : Tienda.getLocales()) {
                        if (t.getNombre().equalsIgnoreCase(nombre)) {
                            localOrigen = t;
                        }
                    }
                    if (localOrigen == null) {
                        imprimirSeparador();
                        System.out.println("El local ingresado no existe");
                        continue;
                    }
                    break;
                }
                for (Producto i : localOrigen.getInventario()) {
                    if (i.getNombre().equalsIgnoreCase(producto.getNombre())) {
                        cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                    }
                }
                if (cantidad < 0) {
                    System.out.println("No es posible reabastecer el producto desde este local.");
                    continue;
                }
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("•" + producto.getNombre() + " | Cantidad disponible para el reabastecimiento: " + cantidad);
                        System.out.println("Ingrese la cantidad a reabastecer: ");
                        cantidad = scInvent.nextInt();
                        scInvent.nextLine();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Desea \n1.Reabastecer otro producto \n2.Crear orden de reabastecimiento \n3.Cancelar todo");
                        opcion = scInvent.nextInt();
                        scInvent.nextLine();
                        if (opcion < 0 || opcion > 3) {
                            imprimirSeparador();
                            System.out.println("Opcion no valida");
                            continue;
                        }
                        break;
                    } catch (Exception E) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                if (opcion == 1) {
                    Producto pclonado;
                    try {
                        pclonado = producto.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    pclonado.setCantidadInicial(cantidad);
                    pclonado.setCantidad(cantidad);
                    localOrigen.retirarProducto(producto, cantidad);//quitar las unidades del producto en el local
                    listadeObjetos.add(pclonado);
                    continue;
                } else if (opcion == 2) {
                    break;
                } else if (opcion == 3) {
                    return null;
                }
            }
            return new Reabastecimiento(localOrigen, local, new Fecha(fechaActual.getTotalDias() + 30), listadeObjetos);
        }

        //sobrecarga para rango
        private static Reabastecimiento reabastecerManual(Tienda local, ArrayList<Producto> p, Fecha fechaActual, String l) {
            int rango1;
            int rango2;
            l = "Actually" + l;
            int opcion;
            int cantidad = 0;
            boolean existe = false;
            String nombre = "";
            Producto producto = null;
            Tienda localOrigen = null;
            ArrayList<Producto> listadeObjetos = new ArrayList<>();
            ArrayList<Producto> lista = new ArrayList<>();
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Ingrese el rango del precio de busqueda");
                    System.out.println("Valor inicial: ");
                    rango1 = scInvent.nextInt();
                    scInvent.nextLine();
                    System.out.println("Valor final: ");
                    rango2 = scInvent.nextInt();
                    scInvent.nextLine();
                    if (rango1 > rango2) {
                        System.out.println("El rango de precio es ilogico");
                        scInvent.nextLine();
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    scInvent.nextLine();
                }
            }
            for (Producto i : p) {
                if (i.getValor() >= rango1 || i.getValor() <= rango2) {
                    lista.add(i);
                }
            }
            producto = fRegistrarCompra.seleccionarProducto(lista);
            while (true) {
                imprimirSeparador();
                for (Tienda t : Tienda.getLocales()) {
                    for (Producto i : t.getInventario()) {
                        if (i.getNombre().equalsIgnoreCase(producto.getNombre()) && t != local) {
                            existe = true;
                            cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                            if (cantidad < 0) {
                                cantidad = 0;
                            }
                            System.out.println("•Local: " + t.getNombre() + " | Cantidad disponible: " + cantidad);
                        } else if (Tienda.getLocales().indexOf(t) == Tienda.getLocales().size() - 1 && t.getInventario().indexOf(i) == t.getInventario().size() - 1 && !existe) {
                            System.out.println("No se han encontrado productos en otros locales");
                            scInvent.nextLine();
                        }
                    }
                }
                if (!existe) {
                    continue;
                }
                while (true) {
                    if (localOrigen != null && !listadeObjetos.isEmpty()) {
                        break;
                    }
                    nombre = "";
                    imprimirSeparador();
                    System.out.println("Ingrese el local deseado: ");
                    nombre = scInvent.nextLine();
                    scInvent.nextLine();
                    for (Tienda t : Tienda.getLocales()) {
                        if (t.getNombre().equalsIgnoreCase(nombre)) {
                            localOrigen = t;
                        }
                    }
                    if (localOrigen == null) {
                        imprimirSeparador();
                        System.out.println("El local ingresado no existe");
                        continue;
                    }
                    break;
                }
                for (Producto i : localOrigen.getInventario()) {
                    if (i.getNombre().equalsIgnoreCase(producto.getNombre())) {
                        cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                    }
                }
                if (cantidad < 0) {
                    System.out.println("No es posible reabastecer el producto desde este local.");
                    continue;
                }
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("•" + producto.getNombre() + " | Cantidad disponible para el reabastecimiento: " + cantidad);
                        System.out.println("Ingrese la cantidad a reabastecer: ");
                        cantidad = scInvent.nextInt();
                        scInvent.nextLine();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Desea \n1.Reabastecer otro producto \n2.Crear orden de reabastecimiento \n3.Cancelar todo");
                        opcion = scInvent.nextInt();
                        scInvent.nextLine();
                        if (opcion < 0 || opcion > 3) {
                            imprimirSeparador();
                            System.out.println("Opcion no valida");
                            continue;
                        }
                        break;
                    } catch (Exception E) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scInvent.nextLine();
                    }
                }
                if (opcion == 1) {
                    Producto pclonado;
                    try {
                        pclonado = producto.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    pclonado.setCantidadInicial(cantidad);
                    pclonado.setCantidad(cantidad);
                    localOrigen.retirarProducto(producto, cantidad);//quitar las unidades del producto en el local
                    listadeObjetos.add(pclonado);
                    continue;
                } else if (opcion == 2) {
                    break;
                } else if (opcion == 3) {
                    return null;
                }
            }
            return new Reabastecimiento(localOrigen, local, new Fecha(fechaActual.getTotalDias() + 30), listadeObjetos);
        }

        private static void crearProducto(Tienda local) {
            Scanner scDio = new Scanner(System.in);
            Scanner scPoo = new Scanner(System.in);
            while (true) {
                boolean existe = false;
                byte tipo;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese el tipo de producto a crear");
                    System.out.println("1.Juego");
                    System.out.println("2.Consola");
                    System.out.println("3.Accesorio");
                    System.out.println("4.salir");
                    tipo = scDio.nextByte();
                    scPoo.nextLine();
                    if (tipo == 1 || tipo == 2 || tipo == 3) {
                        break;
                    } else if (tipo == 4) {
                        return;
                    } else {
                        imprimirSeparador();
                        System.out.println("Opcion no valida");
                        scPoo.nextLine();
                    }
                }
                int valor;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese el valor del producto: ");
                    try {
                        valor = scDio.nextInt();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scPoo.nextLine();

                    }
                }
                String nombre;

                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese el nombre del producto: ");
                    nombre = scPoo.nextLine();
                    scDio.nextLine();
                    if (nombre.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("El nombre no puede estar vacio");
                        sc.nextLine();
                        continue;
                    }
                    break;
                }
                int cantidadInicial;//sera la cantidadInicial y cantidad
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese la cantidad del producto: ");
                    try {
                        cantidadInicial = scDio.nextInt();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scPoo.nextLine();
                        continue;
                    }
                }
                boolean prestable;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Es prestable?");
                    System.out.println("1.Si");
                    System.out.println("2.No");
                    int opcion = 0;
                    try {
                        opcion = scDio.nextInt();
                        scPoo.nextLine();
                    }catch (Exception e){
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scPoo.nextLine();
                    }

                    if (opcion == 1) {
                        prestable = true;
                        break;
                    } else if (opcion == 2) {
                        prestable = false;
                        break;
                    } else {
                        imprimirSeparador();
                        System.out.println("Opcion no valida");
                        scPoo.nextLine();
                    }
                }
                byte condicion;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese la condicion del producto que es un numero(1-4: usados  5:nuevo)");
                    condicion = scDio.nextByte();
                    scPoo.nextLine();
                    if (condicion == 1 || condicion == 2 || condicion == 3 || condicion == 4 || condicion == 5) {
                        break;
                    } else {
                        imprimirSeparador();
                        System.out.println("Opcion no valida");
                        scPoo.nextLine();
                    }
                }
                Fecha fechaLanzamiento;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese la fecha de lanzamiento del producto");
                    fechaLanzamiento = recibirFecha();
                    break;
                }
                int descuento;
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Ingrese el descuento del producto: ");
                        descuento = scDio.nextInt();
                        scPoo.nextLine();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scPoo.nextLine();
                    }
                }
                int puntosRequeridos;
                while (true) {
                    try {
                        imprimirSeparador();
                        System.out.println("Ingrese los puntos requeridos para adquirir el producto: ");
                        puntosRequeridos = scDio.nextInt();
                        scPoo.nextLine();
                        break;
                    } catch (Exception e) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        scPoo.nextLine();
                    }
                }
                if (tipo == 1) {
                    String genero;
                    while (true) {
                        imprimirSeparador();
                        System.out.println("Ingrese el genero del juego: ");
                        genero = scPoo.nextLine();
                        scDio.nextLine();
                        if (genero.isEmpty()) {
                            imprimirSeparador();
                            System.out.println("El genero no puede estar vacio");
                            scPoo.nextLine();
                            continue;
                        }
                        break;
                    }
                    String plataforma;
                    while (true) {
                        imprimirSeparador();
                        System.out.println("Ingrese la plataforma del juego: ");
                        plataforma = scPoo.nextLine();
                        scDio.nextLine();
                        if (plataforma.isEmpty()) {
                            imprimirSeparador();
                            System.out.println("La plataforma no puede estar vacia");
                            scPoo.nextLine();
                            continue;
                        }
                        break;
                    }
                    for (Producto i : local.getInventario()) {
                        if (i instanceof Juego) {
                            if (i.getNombre().equalsIgnoreCase(nombre) && ((Juego) i).getPlataforma().equalsIgnoreCase(plataforma)) {
                                existe = true;
                                break;
                            }
                        }

                    }
                    if (existe) {
                        imprimirSeparador();
                        System.out.println("El producto ya existe en el local");
                        scPoo.nextLine();
                    } else {
                        Juego nuevo = new Juego(nombre, valor, cantidadInicial, cantidadInicial,prestable, condicion, fechaLanzamiento, descuento, puntosRequeridos, genero, plataforma);
                        local.agregarProducto(nuevo);
                        return;
                    }
                }
                else if (tipo == 2) {
                    String marca;
                    while (true) {
                        imprimirSeparador();
                        System.out.println("Ingrese la marca de la consola: ");
                        marca = scPoo.nextLine();
                        scDio.nextLine();
                        if (marca.isEmpty()) {
                            imprimirSeparador();
                            System.out.println("La marca no puede estar vacio");
                            scPoo.nextLine();
                            continue;
                        }
                        break;
                    }
                    for (Producto i : local.getInventario()) {
                        if (i instanceof Consola) {
                            if (i.getNombre().equalsIgnoreCase(nombre)) {
                                existe = true;
                                break;
                            }
                        }

                    }
                    if (existe) {
                        imprimirSeparador();
                        System.out.println("El producto ya existe en el local");
                        scPoo.nextLine();
                    } else {
                        Consola nuevo = new Consola(nombre, valor, cantidadInicial, cantidadInicial,prestable, condicion, fechaLanzamiento, descuento, puntosRequeridos, marca);
                        local.agregarProducto(nuevo);
                        return;
                    }
                }
                else {
                    String marca;
                    while (true) {
                        imprimirSeparador();
                        System.out.println("Ingrese la marca del accesorio: ");
                        marca = scPoo.nextLine();
                        scDio.nextLine();
                        if (marca.isEmpty()) {
                            imprimirSeparador();
                            System.out.println("La marca no puede estar vacio");
                            scPoo.nextLine();
                            continue;
                        }
                        break;
                    }
                    String consola;
                    while (true) {
                        imprimirSeparador();
                        System.out.println("Ingrese la consola del accesorio: ");
                        consola = scPoo.nextLine();
                        scDio.nextLine();
                        if (consola.isEmpty()) {
                            imprimirSeparador();
                            System.out.println("La consola no puede estar vacia");
                            scPoo.nextLine();
                            continue;
                        }
                        break;
                    }
                    for (Producto i : local.getInventario()) {
                        if (i instanceof Accesorio) {
                            if (i.getNombre().equalsIgnoreCase(nombre)) {
                                existe = true;
                                break;
                            }
                        }

                    }
                    if (existe) {
                        imprimirSeparador();
                        System.out.println("El producto ya existe en el local");
                        scPoo.nextLine();
                    } else {
                        Accesorio nuevo = new Accesorio(nombre, valor, cantidadInicial, cantidadInicial,prestable, condicion, fechaLanzamiento, descuento, puntosRequeridos, marca, consola);
                        local.agregarProducto(nuevo);
                        return;
                    }

                }
            }
        }
    }

    public static class fAdministrarEmpleado {
        static Scanner scAdmEmp = new Scanner(System.in);
        private static double totalVentasSemanaActual = 0;
        private static double totaVentasMesActual = 0;
        private static double totalVentasYearActual = 0;
        public static void inspeccionEmpleado(Tienda local, Fecha fechaActual) {
            /* ~~~ Identificacion del empleado ~~~ */
            Empleado empleado = identificarEmpleado(local);

            if (empleado == null){
                return;
            }

            /* ~~~ Gestionar metas ~~~ */
            gestionarMeta(empleado, fechaActual);
            System.out.println("Presione enter para revisar si hay metas alcanzadas");
            scAdmEmp.nextLine();
            scAdmEmp.nextLine();

            /* ~~~ Mostrar metas alcanzadas ~~~ */
            mostrarMetasAlcanzadas(empleado);

            /* ~~~ Revisar metas caducadas ~~~ */
            while (!empleado.getMetasCaducadas().isEmpty()){
                mostrarMetasCaducadas(empleado);
                boolean decision = siNo("¿Desea ampliar el plazo de alguna de las metas?");
                if (decision){
                    Meta meta = revisarMetasCaducadas(empleado);
                    if (meta == null){
                        break;
                    }
                    ampliarMeta(empleado, meta, fechaActual);
                } else {
                    break;
                }
            }

            /* ~~~ Ver rendimiento ~~~ */
            while (true){
                byte decision;

                imprimirSeparador();
                boolean pregunta = siNo("¿Desea ver el rendimiento del empleado?");
                if (!pregunta){
                    break;
                }

                int rendimiento = verRendimiento(empleado, fechaActual);
                compararRendimiento(empleado, fechaActual, rendimiento);
                imprimirSeparador();
                System.out.println("¿Que desea hacer?");
                System.out.println("1. Ver rendimiento en otro periodo");
                System.out.println("2. Continuar");

                try {
                    decision = scAdmEmp.nextByte();
                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }
                if (decision ==  2){
                    break;
                }
            }

            /* ~~~ Modificar Salarios o dias laborales ~~~ */
            while (true) {
                byte decision;

                imprimirSeparador();
                System.out.println("¿Que desea hacer?");
                System.out.println("1. Modificar salarios");
                System.out.println("2. Modificar dias laborales");
                System.out.println("3. Continuar a asignar meta");

                try {
                    decision = scAdmEmp.nextByte();
                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                if (decision == 3){
                    break;
                }

                switch (decision) {
                    case 1:
                        modificarSalario(empleado);
                        break;

                    case 2:
                        modificarDiasLaborales(empleado);
                        break;

                    default:
                        System.out.println("\n### ERROR ###");
                        System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        break;
                }
                imprimirSeparador();
                boolean pregunta = siNo("¿Desea modificar algo mas?");
                if (!pregunta) {
                    break;
                }

            }
            /* ~~~ Asignar una meta ~~~ */

            while (true){
                byte pregunta;

                imprimirSeparador();
                System.out.println("¿Que desea hacer?");
                System.out.println("1. Asignar una meta");
                System.out.println("2. Terminar");

                try {
                    pregunta = scAdmEmp.nextByte();
                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                if (pregunta == 1){
                    asignarMeta(empleado);
                } else if (pregunta == 2) {
                    break;
                } else {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                }
            }
        }


        private static boolean siNo(String pregunta) {
            System.out.println( pregunta + " (S/n)");
            char respuesta = scAdmEmp.next().charAt(0);
            scAdmEmp.nextLine();  // Limpiar el buffer

            return !(respuesta == 'n' || respuesta == 'N');
        }

        private static Empleado identificarEmpleado(Tienda local) {
            /* ~~ Elegir con que empleado se desea usar la funcionalidad ~~ */

            imprimirSeparador();

            Empleado empleado;
            int cedula;

            //Mostrar empleados de la tienda
            System.out.println("Empleados de la tienda:");

            for (Empleado e : local.getEmpleados()) {
                System.out.println("* Nombre: " + e.getNombre() + " - Cedula: " + e.getCedula());
            }

            while (true) {
                System.out.println("Ingrese la cedula del empleado");

                // Buscar al empleado en la lista de empleados por su cedula
                try {
                    cedula = scAdmEmp.nextInt();

                    for (Empleado e : local.getEmpleados()) {
                        if (e.getCedula() == cedula) {
                            empleado = e;
                            return empleado;
                        } else {
                            cedula = 0;
                        }
                    }
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                // En caso de que el empleado no sea encontrado dar la opcion de intentar de nuevo
                if (cedula == 0) {
                    imprimirSeparador();
                    if (!siNo("Empleado no encontrado. ¿Desea intentar de nuevo? (Y/n).\n")){
                        return null;
                    }
                }
            }
        }

        /* ~~ Calcular porcentaje de las metas. Recibe empleado y fecha ~~ */
        private static void gestionarMeta(Empleado empleado, Fecha fechaActual) {

            imprimirSeparador();

            // Se crea una arraylist para guardar las metas que se van a remover
            // para evitar problemas con el for
            ArrayList<Meta> metasARemover = new ArrayList<>();

            System.out.println("Porcentaje de progreso de las metas del empleado " + empleado.getNombre());
            for (Meta m : empleado.getMetas()) {
                int porcentajeProgreso = (m.getAcumulado() * 100) / m.getValorAlcanzar();
                System.out.println("* Codigo: " + m.getCodigo() + " - Porcentaje de progreso: " + porcentajeProgreso + "%");

                if (porcentajeProgreso >= 100 && fechaActual.getTotalDias() <= m.getFecha().getTotalDias()) { //Si el porcentaje de la meta se completo:
                    metasARemover.add(m);
                    empleado.ingresarMetasAlcanzdas(m);
                    m.setEstado("Meta cumplida");
                    empleado.setAcumuladoMensual(m.getValorBonificacion());
                }

                if (fechaActual.getTotalDias() > m.getFecha().getTotalDias()) { //Si la fecha de la meta caduco:
                    metasARemover.add(m);
                    empleado.ingresarMetasCaducadas(m);
                    m.setEstado("Meta caducada");
                }
            }

            //Remover las metas que se completaron o caducaron
            for (Meta m : metasARemover) {
                empleado.getMetas().remove(m);
            }
        }

        /* ~~ Imprimir si hay metas alcanzadas ~~ */
        private static void mostrarMetasAlcanzadas(Empleado empleado) {

            if (empleado.getMetasAlcanzadas().isEmpty()) { //No se ha cumplido con ninguna meta aun
                imprimirSeparador();
                System.out.println("El empleado " + empleado.getNombre() + " todavia no ha cumplido con ninguna meta. animo.");
                System.out.println("Presione enter para continuar");
                scAdmEmp.nextLine();
            } else { //Si se han cumplido metas
                imprimirSeparador();
                System.out.println("Las metas alcanzadas por el empleado: " + empleado.getNombre() + " son: ");
                for (Meta m : empleado.getMetasAlcanzadas()) {
                    System.out.println(m.toString());
                }
            }
        }

        /* ~~ Imprimir metas caducadas. Recibe empleado ~~ */
        private static void mostrarMetasCaducadas(Empleado empleado){

            if (empleado.getMetasCaducadas().isEmpty()) { //Si no hay metas caducadas:
                imprimirSeparador();
                System.out.println("El empleado " + empleado.getNombre() + " no tiene metas caducadas");
                System.out.println("Presione enter para continuar");
                scAdmEmp.nextLine();
                scAdmEmp.nextLine();
            } else { //Si si hay metas caducadas:
                imprimirSeparador();
                System.out.println("Las metas caducadas por el empleado " + empleado.getNombre() + " son: ");
                for (Meta m : empleado.getMetasCaducadas()) {
                    System.out.println(m.toString());
                }
            }
        }

        /* ~~ Elegir meta que se desea modificar. Recibe empleado ~~ */
        private static Meta revisarMetasCaducadas(Empleado empleado) {
            while (true){
                int codigo;

                imprimirSeparador();
                System.out.println("Escriba el codigo de la meta que desea modificar");

                try {
                    codigo = scAdmEmp.nextByte();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                for (Meta m : empleado.getMetasCaducadas()) {
                    if (m.getCodigo() == codigo) {
                        return m;
                    }
                }

                //En caso tal de que se ingrese un codigo de una meta que no existe
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                if (!siNo("Meta no encontrada. ¿Desea intentar de nuevo? (Y/n).\n")){
                    return null;
                }
            }
        }

        /* ~~ Metodo para ampliar plazo de meta caducada ~~ */
        private static void ampliarMeta(Empleado empleado, Meta meta, Fecha fechaActual) {
            while (true) {
                int yearAjuste;
                int mesAjuste;
                int diaAjuste;

                try { //Ingresar año que se desea modificar
                    imprimirSeparador();
                    System.out.println("Ingrese el año en que desea ampliar la meta: ");
                    yearAjuste = scAdmEmp.nextInt();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }
                //Hacer ajuste de la fecha de la meta
                meta.getFecha().setYear(yearAjuste);

                try { //Ingresar el mes que se desea modificar
                    imprimirSeparador();
                    System.out.println("Ingrese el mes en que desea ampliar la meta: ");
                    mesAjuste = scAdmEmp.nextInt();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }
                //Hacer ajuste del mes que se desea modificar
                meta.getFecha().setMes(mesAjuste);

                try { //Ingresar el dia que se desea modificar
                    imprimirSeparador();
                    System.out.println("Ingrese el dia que desea ampliar la meta");
                    diaAjuste = scAdmEmp.nextInt();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }
                //Hacer ajuste del dia que se desea modificar
                meta.getFecha().setDia(diaAjuste);

                //Cambiar total de dias de la meta a 0 para que luego no genere problemas
                meta.getFecha().setTotalDias(0);
                //Hacer ajuste de total de dias de la meta
                meta.getFecha().setTotalDias(meta.getFecha().fechaADias(diaAjuste, mesAjuste, yearAjuste));

                if (meta.getFecha().getTotalDias() < fechaActual.getTotalDias()) { //Si la fecha de la meta es antes de la actual
                    imprimirSeparador();
                    System.out.println("Fecha no valida, presione enter para volver a intentar");
                    scAdmEmp.nextLine();
                    scAdmEmp.nextLine();
                } else { //Si la fecha de la meta es despues de la actual
                    imprimirSeparador();
                    System.out.println("Fecha actualizada. La meta " + meta.getCodigo() + " quedo para " + meta.getFecha().toString());
                    System.out.println("Presione enter para continuar");
                    empleado.getMetasCaducadas().remove(meta);
                    empleado.ingresarMeta(meta);
                    meta.setEstado("En proceso");
                    scAdmEmp.nextLine();
                    scAdmEmp.nextLine();
                    break;
                }
            }
        }

        private static int verRendimiento(Empleado empleado, Fecha fechaActual){
            /* ~~ Ver la cantidad de ventas del empleado ~~ */
            while (true){
                byte opcion;
                byte decision;

                try { //Elegir si se desea ver las ventas del empleado semanal, mensual o anualmente
                    imprimirSeparador();
                    System.out.println("Desea ver el rendimiento del empleado " + empleado.getNombre() + ":");
                    System.out.println("1. Semanal");
                    System.out.println("2. Mensual");
                    System.out.println("3. Anual");
                    opcion = scAdmEmp.nextByte();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                switch (opcion) {
                    case 1: //Ver la cantidad de ventas del empleado en la semana
                        for (Transaccion t : empleado.getTransacciones()) {
                            //Sumar uno al total de ventas de la semana actual si la fecha de la venta es mayor al de la fecha actual -7
                            if (fechaActual.getTotalDias() - 7 <= t.getFecha().getTotalDias()) {
                                totalVentasSemanaActual++;
                            }
                        }
                        imprimirSeparador();
                        System.out.println("El total de ventas semanales del empleado " + empleado.getNombre() + " son: " + (int)totalVentasSemanaActual);
                        break;

                    case 2: //Ver la cantidad de ventas del empleado en el mes
                        for (Transaccion t : empleado.getTransacciones()) {
                            //Sumar uno al total de ventas de la semana actual si la fecha de la venta es mayor al de la fecha actual -31
                            if (fechaActual.getTotalDias() - 31 <= t.getFecha().getTotalDias()) {
                                totaVentasMesActual++;
                            }
                        }
                        imprimirSeparador();
                        System.out.println("El total de ventas mensuales del empleado " + empleado.getNombre() + " son: " + (int)totaVentasMesActual);
                        break;

                    case 3: //Ver la cantidad de ventas del empleado en el año
                        for (Transaccion t : empleado.getTransacciones()) {
                            //Sumar uno al total de ventas de la semana actual si la fecha de la venta es mayor al de la fecha actual -365
                            if (fechaActual.getTotalDias() - 365 <= t.getFecha().getTotalDias()) {
                                totalVentasYearActual++;
                            }
                        }
                        imprimirSeparador();
                        System.out.println("El total de ventas anuales del empleado " + empleado.getNombre() + " son: " + (int)totalVentasYearActual);
                        break;

                    default: //Si se ingresa un numero no valido
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        break;
                }

                while (true){
                    try { //Elegir si se desea ver el rendimiento en otro periodo o pasar a compararlo
                        imprimirSeparador();
                        System.out.println("¿Que desea hacer?");
                        System.out.println("1. Ver el rendimiento en otro periodo de tiempo");
                        System.out.println("2. Comparar rendimiento con periodo anterior");

                        decision = scAdmEmp.nextByte();
                    } catch (InputMismatchException error) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                        scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                        scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                        continue;
                    }
                    if (decision == 1){ //Si elige 1, romper el while y que entre al otro while de nuevo
                        break;
                    } else if (decision == 2) { //Si elige 2, retornar la opcion de rango en que se vio el rendimiento para compararlo con el del periodo anterior
                        return opcion;
                    } else { //Presentar error si elige un numero distinto a 1 o 2
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                        scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                        scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    }
                }
            }
       }

        private static void compararRendimiento(Empleado empleado, Fecha fechaActual, int decision){
            /* ~~ Comparar cantidad de ventas del empleado ~~ */

            switch (decision) {
                case 1: //Comparar la cantidad de ventas con la semana pasada
                    double totalSemana = 0;
                    for (Transaccion t : empleado.getTransacciones()) {
                        //Sumar 1 al total de ventas de la semana pasada si la fecha de la venta es mayor a la fecha actual -14 y ademas es menor al de la fecha actual -7
                        if (fechaActual.getTotalDias() - 14 <= t.getFecha().getTotalDias() && fechaActual.getTotalDias() - 7 >= t.getFecha().getTotalDias()){
                            totalSemana++;
                        }
                    }
                    imprimirSeparador();
                    System.out.println("El total de ventas en la semana anterior del empleado " + empleado.getNombre() + " fueron: " + (int)totalSemana);
                    System.out.println("Presione Enter para continuar.\n");
                    scAdmEmp.nextLine(); // Limpiar el buffer
                    scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter

                    //Si el total de ventas de la semana actual es mayor al total de ventas de la semana pasada, calcular porcentaje de incremento
                    if (totalSemana < totalVentasSemanaActual){
                        double calculo = ((totalVentasSemanaActual - totalSemana)/ totalSemana)* 100;
                        imprimirSeparador();
                        System.out.println("El total de ventas en esta semana incremento en un " + calculo + "%");
                        if (calculo > 30 ){
                            System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado deberia tener una bonificacion remunerada.");
                            System.out.println("Presione Enter para continuar.\n");
                            scAdmEmp.nextLine(); // Limpiar el buffer
                            scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        }
                    //Si el total de ventas de la semana actual es igual al total de ventas de la semana pasada
                    } else if (totalSemana == totalVentasSemanaActual) {
                        imprimirSeparador();
                        System.out.println("El total de ventas fue igual al de la semana pasada: " + (int)totalSemana + " ventas.");
                        System.out.println("Presione Enter para continuar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter

                    //Si el total de ventas de la semana actual es menor al total de ventas de la semana pasada, calcular porcentaje de decremento
                    } else {
                        double calculo = ((totalSemana - totalVentasSemanaActual) / totalSemana)*100;
                        imprimirSeparador();
                        System.out.println("El total de ventas en esta semana disminuyo en un " + calculo +"%");
                        System.out.println("Presione Enter para continuar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                    }
                    break;

                case 2:
                    double totalMes = 0;
                    for (Transaccion t : empleado.getTransacciones()) {
                        //Sumar 1 al total de ventas de la semana pasada si la fecha de la venta es mayor a la fecha actual -62 y ademas es menor al de la fecha actual -31
                        if (fechaActual.getTotalDias() - 62 <= t.getFecha().getTotalDias() && fechaActual.getTotalDias() - 31 >= t.getFecha().getTotalDias()) {
                            totalMes++;
                            imprimirSeparador();
                            System.out.println("El total de ventas en el mes anterior del empleado " + empleado.getNombre() + " fueron: " + (int)totalMes);
                            System.out.println("Presione Enter para continuar.\n");
                            scAdmEmp.nextLine(); // Limpiar el buffer
                            scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        }
                    }

                    //Si el total de ventas del mes actual es mayor al total de ventas del mes pasado, calcular porcentaje de incremento
                    if (totalMes < totaVentasMesActual){
                        double calculo = ((totaVentasMesActual - totalMes)/totalMes)*100;
                        imprimirSeparador();
                        System.out.println("El total de ventas en este mes incremento en un " + calculo + "%");
                        if (calculo > 30 ){
                            System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado deberia tener una bonificacion remunerada.");
                            System.out.println("Presione Enter para continuar.\n");
                            scAdmEmp.nextLine(); // Limpiar el buffer
                            scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter

                        }

                    //Si el total de ventas del mes actual es igual al total de ventas del mes pasado
                    } else if (totalMes == totaVentasMesActual) {
                        imprimirSeparador();
                        System.out.println("El total de ventas fue igual al del mes pasado: " + (int)totalMes + " ventas.");
                        System.out.println("Presione Enter para continuar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter

                    //Si el total de ventas del mes actual es menor al total de ventas del mes pasado, calcular porcentaje de decremento
                    } else {
                        double calculo = ((totalMes - totaVentasMesActual)/totalMes)*100;
                        imprimirSeparador();
                        System.out.println("El total de ventas en este mes disminuyo en un " + calculo +"%");
                        System.out.println("Presione Enter para continuar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                    }
                    break;

                case 3:
                    double totalYear = 0;
                    for (Transaccion t : empleado.getTransacciones()) {
                        //Sumar 1 al total de ventas de la semana pasada si la fecha de la venta es mayor a la fecha actual -730 y ademas es menor al de la fecha actual -365
                        if (fechaActual.getTotalDias() - 730 <= t.getFecha().getTotalDias() && fechaActual.getTotalDias() - 365 >= t.getFecha().getTotalDias()) {
                            totalYear++;
                            imprimirSeparador();
                            System.out.println("El total de ventas en la semana anterior del empleado " + empleado.getNombre() + " fueron:" + (int)totalYear);
                            System.out.println("Presione Enter para continuar.\n");
                            scAdmEmp.nextLine(); // Limpiar el buffer
                            scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        }
                    }

                    //Si el total de ventas del año actual es mayor al total de ventas del año pasado, calcular porcentaje de decremento
                    if (totalYear < totalVentasYearActual){
                        double calculo = ((totalVentasYearActual - totalYear)/totalYear)*100;
                        imprimirSeparador();
                        System.out.println("El total de ventas en esta semana incremento en un " + calculo + "%");
                        if (calculo > 30 ){
                            System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado deberia tener una bonificacion remunerada.");
                            System.out.println("Presione Enter para continuar.\n");
                            scAdmEmp.nextLine(); // Limpiar el buffer
                            scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        }

                    //Si el total de ventas del año actual es igual al total de ventas del año pasado
                    } else if (totalYear == totalVentasYearActual) {
                        imprimirSeparador();
                        System.out.println("El total de ventas fue igual al de la semana pesada " + (int)totalYear + " ventas.");
                        System.out.println("Presione Enter para continuar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter

                    //Si el total de ventas del año actual es menor al total de ventas del año pasado, calcular porcentaje de decremento
                    } else {
                        double calculo = ((totalYear - totalVentasYearActual)/totalYear)*100;
                        imprimirSeparador();
                        System.out.println("El total de ventas en esta semana disminuyo en un " + calculo +"%");
                        System.out.println("Presione Enter para continuar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                    }
                    break;

                default:
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                    scAdmEmp.nextLine(); // Limpiar el buffer
                    scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
        }

        private static void modificarSalario(Empleado empleado) {
            /* ~~ Modificar salario del empleado ~~ */

            int salario;
            while (true){
                try { //Elegir si se desea modificar el salario fijo del empleado o el porcentaje por ventas
                    imprimirSeparador();
                    System.out.println("Desea modificar:");
                    System.out.println("1. Salario fijo");
                    System.out.println("2. Porcentaje por ventas");

                    salario = scAdmEmp.nextInt();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                switch (salario) {
                    case 1:
                        int salarioNuevo;

                        try { //Mostrar cuanto es el salario actual del empleado e ingresar el nuevo salario del empleado
                            imprimirSeparador();
                            System.out.println("El salario fijo del empleado es: " + empleado.getSalario());
                            System.out.println("Ingrese el nuevo salario del empleado");

                            salarioNuevo = scAdmEmp.nextInt();
                        } catch (InputMismatchException error) {
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                            scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                            scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                            continue;
                        }

                        empleado.setSalario(salarioNuevo);
                        imprimirSeparador();
                        System.out.println("El nuevo salario fijo del empleado es: " + empleado.getSalario());
                        return;

                    case 2:
                        int salarioPorcentualNuevo;
                        try { //Mostrar cuanto es el salario actual del empleado e ingresar el nuevo salario del empleado
                            imprimirSeparador();
                            System.out.println("El salario porcentual de empleado es: " + empleado.getSalarioPorcentual());
                            System.out.println("Ingrese el nuevo salario porcentual del empleado: ");

                            salarioPorcentualNuevo = scAdmEmp.nextInt();
                        } catch (InputMismatchException error) {
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                            scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                            scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                            continue;
                        }

                        empleado.setSalarioPorcentual(salarioPorcentualNuevo);
                        imprimirSeparador();
                        System.out.println("El nuevo salario porcentual del empleado es: " + empleado.getSalarioPorcentual());
                        return;

                    default: //En caso tal de que se ingrese una opcion incorrecta
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("Opcion fuera del rango. Presione Enter para volver a intentar.\n");
                        scAdmEmp.nextLine(); // Limpiar el buffer
                        scAdmEmp.nextLine(); // Esperar a que el usuario presione Enter
                        return;
                }
            }
        }

        private static void modificarDiasLaborales(Empleado empleado) {
            /* ~~ Modificar dias laborales del empleado ~~ */

            while (true){
                byte nuevoDias;

                try { //Mostrar numero de dias ue trabaja actualmente en la tienda e ingresar el nuevo numero de dias que trabajara
                    imprimirSeparador();
                    System.out.println("El empleado " + empleado.getNombre() + " trabaja " + empleado.getDiasLaborales() + " a la semana");
                    System.out.println("Ingrese el numero de dias que " + empleado.getNombre() + " trabajara a la semana");

                    nuevoDias = scAdmEmp.nextByte();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                if (nuevoDias < 6 && nuevoDias > 0){ //Si el numero ingresado es menor a 6 dias
                    empleado.setDiasLaborales(nuevoDias);
                    imprimirSeparador();
                    System.out.println("Ahora el empleado trabajara " + empleado.getDiasLaborales() + " dias a la semana");
                    return;
                }
                else { //Si se ingresa un numero mayo a 6 dias
                    imprimirSeparador();
                    System.out.println("Cantidad inhumana de dias. Presione enter para volver a intentarlo");
                }
            }

        }

        private static void asignarMeta(Empleado empleado) {
            /* ~~ Asignar una meta nueva al empleado ~~ */

            while (true){
                int yearLimite;
                int mesLimite;
                int diaLimite;
                int valorAlcanzar;
                int valorBonificacion;

                //Mostrar cuales son las metas del empleado
                System.out.println("Las metas del empleado + " + empleado.getNombre() + " son: ");
                for (Meta m : empleado.getMetas()) {
                    System.out.println(m.toString());
                }
                System.out.println("Presione enter para continuar");
                scAdmEmp.nextLine();
                scAdmEmp.nextLine();


                try { //Ingresar el año limite de la meta
                    System.out.println("Ingrese el año limite de la meta: ");
                    yearLimite = scAdmEmp.nextInt();

                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                try { //Ingresar el mes limite de la meta
                    System.out.println("Ingrese el mes limite de la meta: ");
                    mesLimite = scAdmEmp.nextInt();

                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                try { //Ingresar el dia limite de la meta
                    System.out.println("Ingrese el dia limite de la meta: ");
                    diaLimite = scAdmEmp.nextInt();

                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                try { //Ingresar el valor de ventas a alcanzar
                    System.out.println("Ingrese el valor a alcanzar: ");
                    valorAlcanzar = scAdmEmp.nextInt();

                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                try { //Ingresar el valor de la bonificacion a ganar
                    System.out.println("Ingrese el valor de la bonificacion : ");
                    valorBonificacion = scAdmEmp.nextInt();

                } catch (InputMismatchException error) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un numero valido. Presiona enter para volver a intentar.\n");
                    scAdmEmp.nextLine();  // nextLine para limpiar el buffer
                    scAdmEmp.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }

                //LLamar a constructor para crear meta
                new Meta(empleado, diaLimite, mesLimite, yearLimite, valorAlcanzar, valorBonificacion);
                return;
            }
        }
    }

    public static class fSubastar {
        static Scanner scSub = new Scanner(System.in); // variable scanner

        public static void subastar(Tienda local, Fecha fecha) {

            // Comprobar subastas vencida
            comprobarSubastas(local, fecha);

            //Menu
            byte opcionMenu;
            do {
                // Recibir entrada del usuario
                String[] opcionesMenu = {
                        "Crear Subasta",
                        "Registrar Oferta",
                        "Ver subastas activas",
                        "Actualizar subasta descendente"
                };
                opcionMenu = menuOpcionMultiple("Seleccione una opcion:", opcionesMenu);

                switch (opcionMenu) {
                    case 1: // Crear Subasta
                        ArrayList<Producto> listaProductoSubasta = new ArrayList<>();

                        // Seleccionar productos a subastar
                        do {
                            //Seleccionar tipo de producto
                            Producto producto = fRegistrarCompra.seleccionarProducto(local.getInventarioUsado());
                            //Agregar producto a la lista de subasta
                            listaProductoSubasta.add(producto);
                            local.retirarUnoDeInventario(producto, local.getInventarioUsado());
                            System.out.println("Producto agregado a la subasta.");
                            imprimirSeparador();
                        } while (siNo("¿Desea agregar otro producto a la subasta?"));

                        String[] opcionesSubMenu1 = {
                                "Subasta Ascendente",
                                "Subasta Descendente",
                                "Subasta Anonima",
                        };
                        byte opcionSubMenu1 = menuOpcionMultiple("Indique el tipo de subasta que desea crear:", opcionesSubMenu1);

                        switch (opcionSubMenu1) {
                            case 1: // Subasta Ascendente
                                registrarSubastaAscendente(local, listaProductoSubasta, fecha);
                                break;
                            case 2: // Subasta Descendente
                                registrarSubastaDescendente(local, listaProductoSubasta, fecha);
                                break;
                            case 3: // Subasta Anonima
                                registrarSubastaAnonima(local, listaProductoSubasta, fecha);
                                break;

                            case 0: // Cancelar y salir
                                System.out.println("Creacion de subasta cancelada.");
                                break;

                            default:
                                System.out.println("\n### ERROR ###");
                                System.out.println("Opcion fuera del rango. Presione Enter para intentar de nuevo.");
                                scSub.nextLine();  // Esperar a que el usuario presione Enter
                                break;
                        }

                        break;

                    case 2: // Registrar oferta
                        // Seleccionar subasta
                        Subasta subastaSelec = seleccionarSubasta(local);

                        // En caso de que no haya subastas activas
                        if (subastaSelec == null) {
                            break;
                        }

                        // Identificar cliente
                        Cliente cliente = identificarCliente();

                        if (cliente.getPuntosFidelidad() == 0) { // Verificar que el cliente tenga puntos de fidelidad
                            System.out.println("El cliente " + cliente.getNombre() + " no tiene puntos de fidelidad para ofertar.");
                        }
                        else {
                            // Aplicar oferta segun tipo de subasta
                            switch (subastaSelec.getTipo()) {
                                case "Ascendente":
                                    // Recibir oferta
                                    while (true) {
                                        try {
                                            System.out.println("Ingrese el valor de su oferta: (ultima oferta: " + subastaSelec.getOfertaMayor() + ")");
                                            int oferta = scSub.nextInt();
                                            scSub.nextLine();  // Limpiar el buffer

                                            // Comprobar que el cliente tiene suficientes puntos de fidelidad
                                            if (oferta > cliente.getPuntosFidelidad()) {
                                                System.out.println("El cliente " + cliente.getNombre() + " no tiene suficientes puntos de fidelidad para ofertar.");
                                                System.out.println("Puntos de " + cliente.getNombre() + ": " + cliente.getPuntosFidelidad());
                                            } else {
                                                subastaSelec.agregarOferta(oferta, cliente);
                                                System.out.println("Oferta registrada con exito.");
                                            }
                                            break;
                                        } catch (Exception e) {
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("Ingrese un valor valido e inferior a la oferta anterior. Presione Enter para volver a intentar.\n");
                                            scSub.nextLine();  // Limpiar el buffer
                                            scSub.nextLine();  // Esperar a que el usuario presione Enter
                                        }
                                    }
                                    break;

                                case "Descendente":
                                    System.out.println("El valor actualmente asignado a esta subasta es: " + subastaSelec.getOfertaMayor());
                                    if (cliente.getPuntosFidelidad() >= subastaSelec.getOfertaMayor()) {

                                        if (siNo("¿Desea ofertar con este valor a nombre de " + cliente.getNombre() + "?")) {
                                            subastaSelec.ofertarYFinalizarDescendente(cliente);
                                            System.out.println("Oferta ganadora registrada con exito.");
                                            System.out.println("Productos subastados:");
                                            for (Producto producto : subastaSelec.getProductos()) {
                                                System.out.println("    * " + producto.getNombre());
                                            }
                                        }
                                    }
                                    else {
                                        System.out.println("El cliente " + cliente.getNombre() + " no tiene suficientes puntos de fidelidad para tomar esta subasta.");
                                        System.out.println("Puntos de " + cliente.getNombre() + ": " + cliente.getPuntosFidelidad());
                                    }
                                    break;

                                case "Anonima":
                                    // Recibir oferta
                                    while (true) {
                                        try {
                                            System.out.print("Ingrese el valor de su oferta: ");
                                            int oferta = scSub.nextInt();
                                            scSub.nextLine();  // Limpiar el buffer

                                            // Comprobar que el cliente tiene suficientes puntos de fidelidad
                                            if (oferta > cliente.getPuntosFidelidad()) {
                                                System.out.println("El cliente " + cliente.getNombre() + " no tiene suficientes puntos de fidelidad para ofertar.");
                                                System.out.println("Puntos de " + cliente.getNombre() + ": " + cliente.getPuntosFidelidad());
                                            } else {
                                                subastaSelec.agregarOfertaAnonima(oferta, cliente);
                                                System.out.println("Oferta registrada con exito.");
                                            }
                                            break;
                                        } catch (Exception e) {
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("Ingrese un valor valido. Presione Enter para volver a intentar.\n");
                                            scSub.nextLine();  // Limpiar el buffer
                                            scSub.nextLine();  // Esperar a que el usuario presione Enter
                                        }
                                    }
                                break;
                            }
                        }
                        break;

                    case 3: // Ver subastas activas
                        mostrarSubastas(local);
                        break;

                    case 4: // Actualizar subasta descendente
                        // Seleccionar subasta
                        Subasta subasta = seleccionarSubastaDescendente(local);

                        // En caso de que no haya subastas descendentes activas
                        if (subasta == null) {
                            break;
                        }

                        // Actualizar subasta

                        int disminucion;
                        while (true) {
                            System.out.println("La subasta actualmente tiene un valor de: " + subasta.getOfertaMayor());
                            System.out.println("En cuanto desea disminuir el valor?");
                            disminucion = 0;

                            try {
                                disminucion = scSub.nextInt();
                                scSub.nextLine();  // Limpiar el buffer

                                if (disminucion < 0) {
                                    throw new Exception("El valor de disminucion debe ser mayor o igual a 0.");
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("\n### ERROR ###");
                                System.out.println("Ingrese un valor valido. Presione Enter para volver a intentar.\n");
                                scSub.nextLine();  // Limpiar el buffer
                                scSub.nextLine();  // Esperar a que el usuario presione Enter
                            }
                        }

                        // Disminuir el valor de la subasta
                        subasta.setOfertaMayor(subasta.getOfertaMayor() - disminucion);
                        break;

                    case 0: // Cancelar y salir
                        return;

                    default:
                        System.out.println("\n### ERROR ###");
                        System.out.println("Opcion fuera del rango. Presione Enter para intentar de nuevo.");
                        scSub.nextLine();  // Esperar a que el usuario presione Enter
                        break;
                }
            } while (opcionMenu != 0);
        }

        /* ~~~~ Metodos para crear subastas ~~~~ */
        /* Reciben la tienda, la lista de productos a subastar y la fecha actual como parametros */
        /* Reciben por entrada del usuario el valor inicial de la subasta y la fecha de fin de la subasta */
        private static void registrarSubastaAscendente(Tienda local, ArrayList<Producto> listaProductoSubasta, Fecha fechaActual) {
            Fecha fechaFin;

            // Seleccionar fecha fin
            while (true) {
                System.out.println("Ingrese la fecha de fin de la subasta: ");
                fechaFin = recibirFecha();

                if (fechaFin.getTotalDias() > fechaActual.getTotalDias()) {
                    break;
                } else {
                    System.out.println("La fecha actual debe ser menor a la fecha de fin.");
                }
            }

            /* ~~~ Oferta inicial ~~~ */

            int ofertaInicial = 0;
            if (siNo("¿Desea establecer manualmente la oferta inicial?")) {
                // Seleccionar oferta inicial manualmente
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese el valor inicial de la subasta: (0 para no establecer un valor inicial)");

                    // Recibir entrada
                    ofertaInicial = 0;
                    try {
                        ofertaInicial = scSub.nextInt();
                        if (ofertaInicial < 0) {
                            throw new Exception("El valor inicial debe ser mayor o igual a 0.");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un valor valido. Presione Enter para volver a intentar.\n");
                        scSub.nextLine();  // Limpiar el buffer
                        scSub.nextLine();  // Esperar a que el usuario presione Enter
                    }
                }
            } else {
                ofertaInicial = Subasta.calcularValoracionAscendente(listaProductoSubasta, fechaActual);
                System.out.println("La oferta inicial se ha establecido automaticamente en: " + ofertaInicial);
            }

            // Crear subasta
            new Subasta(fechaActual, fechaFin, listaProductoSubasta, ofertaInicial, local, "Ascendente");
            System.out.println("Subasta creada con exito.");
        }

        private static void registrarSubastaDescendente(Tienda local, ArrayList<Producto> listaProductoSubasta, Fecha fechaActual) {
            Fecha fechaFin;

            // Seleccionar fecha fin
            while (true) {
                System.out.println("Ingrese la fecha de fin de la subasta: ");
                fechaFin = recibirFecha();

                if (fechaFin.getTotalDias() > fechaActual.getTotalDias()) {
                    break;
                } else {
                    System.out.println("La fecha actual debe ser menor a la fecha de fin.");
                }
            }

            // Seleccionar oferta inicial
            int valorInicial;
            if (siNo("¿Desea establecer manualmente el valor inicial?")) {
                // Seleccionar valor inicial manualmente
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese el valor inicial de la subasta");

                    // Recibir entrada
                    valorInicial = 0;
                    try {
                        valorInicial = scSub.nextInt();
                        if (valorInicial <= 0) {
                            throw new Exception("El valor inicial debe ser mayor a 0.");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un valor valido. Presione Enter para volver a intentar.\n");
                        scSub.nextLine();  // Limpiar el buffer
                        scSub.nextLine();  // Esperar a que el usuario presione Enter
                    }
                }
            } else {
                valorInicial = Subasta.calcularValoracionDescendente(listaProductoSubasta, fechaActual);
                System.out.println("La oferta inicial se ha establecido automaticamente en: " + valorInicial);
            }

            // Crear subasta
            new Subasta(fechaActual, fechaFin, listaProductoSubasta, valorInicial, local, "Descendente");
        }

        private static void registrarSubastaAnonima(Tienda local, ArrayList<Producto> listaProductoSubasta, Fecha fechaActual) {
            Fecha fechaFin;

            // Seleccionar fecha fin
            while (true) {
                System.out.println("Ingrese la fecha de fin de la subasta: ");
                fechaFin = recibirFecha();

                if (fechaFin.getTotalDias() > fechaActual.getTotalDias()) {
                    break;
                } else {
                    System.out.println("La fecha actual debe ser menor a la fecha de fin.");
                }
            }

            // Crear subasta
            new Subasta(fechaActual, fechaFin, listaProductoSubasta, 0, local, "Anonima");
        }

        /* ~~~ Metodos para registrar ofertas ~~~ */


        // Metodo para comprobar el estado de las subastas de un local dado
        public static void comprobarSubastas(Tienda local, Fecha fecha) {
            for (Subasta subasta : local.getSubastas()) {
                // Hay subastas con fecha anterior a la actual
                if (subasta.getFechaFin().getTotalDias() < fecha.getTotalDias() && subasta.getEstado().equalsIgnoreCase("Activa")) {
                    imprimirSeparadorPequeno();

                    // Seleccionar subasta vencida
                    if (subasta.getOfertas().isEmpty()) {
                        //Extender la subasta 7 dias mas a partir de la fecha actual
                        System.out.println("La subasta N° " + subasta.getId() + " ha finalizado sin ofertas. Se extendera 7 dias mas.");
                        String res = subasta.extenderSubasta(fecha);
                        if (res != null) {
                            System.out.println(res);
                        }
                    } else {
                        Cliente ganador = subasta.finalizarSubasta();
                        System.out.println("La subasta con ID " + subasta.getId() + " ha finalizado. Los articulos subastados son:");
                        for (Producto articulo : subasta.getProductos()) {
                            if (articulo instanceof Consola) {
                                System.out.println("    * Consola: " + articulo.getNombre() + " | Marca: " + ((Consola) articulo).getMarca());
                            } else if (articulo instanceof Juego) {
                                System.out.println("    * Juego: " + articulo.getNombre() + " | Genero: " + ((Juego) articulo).getGenero() + " | Plataforma: " + ((Juego) articulo).getPlataforma());
                            } else if (articulo instanceof Accesorio) {
                                System.out.println("    * Accesorio: " + articulo.getNombre() + " | Marca: " + ((Accesorio) articulo).getMarca() + " | Consola: " + ((Accesorio) articulo).getConsola());
                            }
                        }

                        System.out.println("El ganador es: " + ganador.getNombre());
                        System.out.println("Presione Enter para continuar.");
                        Scanner scCompa = new Scanner(System.in);
                        scCompa.nextLine(); // Esperar a que el usuario presione Enter
                    }
                }
            }
        }

        // Metodo para mostrar subastas activas
        public static void mostrarSubastas(Tienda local) {
            if (local.getSubastas().isEmpty()) {
                imprimirSeparadorPequeno();
                System.out.println("No hay subastas activas.");
            }
            else {
                System.out.println("Subastas activas:");
                for (Subasta subasta : local.getSubastas()) {
                    imprimirSeparadorPequeno();

                    if (subasta.getEstado().equalsIgnoreCase("Activa")) {
                        System.out.println(subasta);
                                    /* reemplazado por el toString de Subasta
                                    System.out.println("Subasta N° " + subasta.getId());
                                    System.out.println("Fecha de fin: " + subasta.getFechaFin());
                                    System.out.println("Tipo: " + subasta.getTipo());
                                    System.out.println("Productos:");
                                    for (Producto producto : subasta.getProductos()) {
                                        System.out.println("    * " + producto.getNombre());
                                    }
                                    System.out.println("Oferta mayor: " + subasta.getOfertaMayor());
                                    System.out.println("Ofertantes:");
                                    for (Cliente cliente : subasta.getOfertantes()) {
                                        System.out.println("    * " + cliente.getNombre());F
                                    }
                                    System.out.println();
                                    */
                    }
                }
            }
        }

        public static Subasta seleccionarSubasta(Tienda local){
            if (local.getSubastas().isEmpty()) {
                imprimirSeparadorPequeno();
                System.out.println("No hay subastas activas.");
                return null;
            }
            else {
                Subasta subastaSelec = null;
                Scanner scSelecSubasta = new Scanner(System.in);

                while (subastaSelec == null) {
                    // Imprimir unicamente el id, el tipo y la fecha de fin de las subastas activas
                    for (Subasta subasta : local.getSubastas()) {
                        if (subasta.getEstado().equalsIgnoreCase("Activa")) {
                            System.out.println("*  Subasta ID: " + subasta.getId() + "| Tipo: " + subasta.getTipo() + " | Fecha de fin: " + subasta.getFechaFin());
                        }
                    }

                    System.out.println("Ingrese el ID de la subasta a la que desea ofertar:");

                    // Recibir entrada
                    int idSubasta = 0;
                    try {
                        idSubasta = scSelecSubasta.nextInt();
                        for (Subasta subasta : local.getSubastas()) {
                            if (subasta.getId() == idSubasta) {
                                subastaSelec = subasta;
                                return subastaSelec;
                            }
                        }

                        System.out.println("No se encontro ninguna subasta con ese ID.");
                        continue;
                    } catch (Exception e) {
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un valor valido. Presione Enter para volver a intentar.\n");
                        scSelecSubasta.nextLine();  // Limpiar el buffer
                        scSelecSubasta.nextLine();  // Esperar a que el usuario presione Enter
                    }

                }

                return subastaSelec;
            }
        }

        public static Subasta seleccionarSubastaDescendente(Tienda local){
            Subasta subastaSelec = null;
            Scanner scSelecSubasta = new Scanner(System.in);

            // lista para comprobar que haya subastas descendentes activas
            ArrayList<Subasta> listaSubastas = new ArrayList<Subasta>();

            while (subastaSelec == null) {
                // Imprimir unicamente el id y la fecha de fin de las subastas activas
                for (Subasta subasta : local.getSubastas()) {
                    if (subasta.getEstado().equalsIgnoreCase("Activa") && subasta.getTipo().equalsIgnoreCase("Descendente")) {
                        System.out.println("*  Subasta ID: " + subasta.getId() + " | Fecha de fin: " + subasta.getFechaFin());
                        listaSubastas.add(subasta);
                    }
                }

                if (listaSubastas.isEmpty()) {
                    imprimirSeparadorPequeno();
                    System.out.println("No hay subastas descendentes activas.");
                    return null;
                }

                System.out.println("Ingrese el ID de la subasta a la que desea ofertar:");

                // Recibir entrada
                int idSubasta = 0;
                try {
                    idSubasta = scSelecSubasta.nextInt();
                    for (Subasta subasta : local.getSubastas()) {
                        if (subasta.getId() == idSubasta) {
                            subastaSelec = subasta;
                            return subastaSelec;
                        }
                    }

                    System.out.println("No se encontro ninguna subasta con ese ID.");
                    continue;
                } catch (Exception e) {
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un valor valido. Presione Enter para volver a intentar.\n");
                    scSelecSubasta.nextLine();  // Limpiar el buffer
                    scSelecSubasta.nextLine();  // Esperar a que el usuario presione Enter
                }

            }
            return subastaSelec;
        }
    }
}