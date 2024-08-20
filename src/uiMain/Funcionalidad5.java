package uiMain;

import gestorAplicacion.informacionVenta.Subasta;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Accesorio;
import gestorAplicacion.productos.Consola;
import gestorAplicacion.productos.Juego;
import gestorAplicacion.productos.Producto;

import java.util.ArrayList;
import java.util.Scanner;

import static uiMain.Main.*;

public class Funcionalidad5 {
    static Scanner scSub = new Scanner(System.in); // variable scanner
    static int puntosUsados = 0; // Cantidad de puntos que se usan en la transaccion. Siempre sera menor a la cantidad de puntos del cliente

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
                        Producto producto = Funcionalidad1.seleccionarProducto(local.getInventarioUsado());
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
