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
    static int puntosUsados = 0; // Cantidad de puntos que se usan en la transacción. Siempre será menor a la cantidad de puntos del cliente

    public static void subastar(Tienda local, Fecha fecha) {

        // Comprobar subastas vencida
        comprobarSubastas(local, fecha);

        //Menú
        byte opcionMenu;
        do {
            // Recibir entrada del usuario
            String[] opcionesMenu = {
                    "Crear Subasta",
                    "Registrar Oferta",
                    "Ver subastas activas",
            };
            opcionMenu = menuOpcionMultiple("Seleccione una opción:", opcionesMenu);

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
                            "Subasta Anónima",
                    };
                    byte opcionSubMenu1 = menuOpcionMultiple("Indique el tipo de subasta que desea crear:", opcionesSubMenu1);

                    switch (opcionSubMenu1) {
                        case 1: // Subasta Ascendente
                            registrarSubastaAscendente(local, listaProductoSubasta, fecha);
                            break;
                        case 2: // Subasta Descendente
                            registrarSubastaDescendente(local, listaProductoSubasta, fecha);
                            break;
                        case 3: // Subasta Anónima
                            registrarSubastaAnonima(local, listaProductoSubasta, fecha);
                            break;

                        case 0: // Cancelar y salir
                            System.out.println("Creación de subasta cancelada.");
                            break;

                        default:
                            System.out.println("\n### ERROR ###");
                            System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
                            scSub.nextLine();  // Esperar a que el usuario presione Enter
                            break;
                    }

                    break;

                case 2: // Registrar oferta
                    // Seleccionar subasta
                    //for ()


                    break;

                case 3: // Ver subastas activas
                    if (local.getSubastas().isEmpty()) {
                        System.out.println("No hay subastas activas.");
                        break;
                    }
                    else {
                        System.out.println("Subastas activas:");
                        for (Subasta subasta : local.getSubastas()) {
                            imprimirSeparadorPequeno();

                            if (subasta.getEstado().equalsIgnoreCase("Activa")) {
                                System.out.println(subasta);

/*                                System.out.println("Subasta N° " + subasta.getId());
                                System.out.println("Fecha de fin: " + subasta.getFechaFin());
                                System.out.println("Tipo: " + subasta.getTipo());
                                System.out.println("Productos:");
                                for (Producto producto : subasta.getProductos()) {
                                    System.out.println("    * " + producto.getNombre());
                                }
                                System.out.println("Oferta mayor: " + subasta.getOfertaMayor());
                                System.out.println("Ofertantes:");
                                for (Cliente cliente : subasta.getOfertantes()) {
                                    System.out.println("    * " + cliente.getNombre());
                                }
                                System.out.println();*/
                            }
                        }
                    }

                break;

                case 0: // Cancelar y salir
                    System.out.println("Registro de subasta cancelado.");

                    return;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
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
                    System.out.println("Ingrese un valor válido. Presione Enter para volver a intentar.\n");
                    scSub.nextLine();  // Limpiar el buffer
                    scSub.nextLine();  // Esperar a que el usuario presione Enter
                }
            }
        } else {
            ofertaInicial = Subasta.calcularValoracionAscendente(listaProductoSubasta, fechaActual);
            System.out.println("La oferta inicial se ha establecido automáticamente en: " + ofertaInicial);
        }

        // Crear subasta
        new Subasta(fechaActual, fechaFin, listaProductoSubasta, ofertaInicial, local, "Ascendente");
        System.out.println("Subasta creada con éxito.");
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
                    System.out.println("Ingrese un valor válido. Presione Enter para volver a intentar.\n");
                    scSub.nextLine();  // Limpiar el buffer
                    scSub.nextLine();  // Esperar a que el usuario presione Enter
                }
            }
        } else {
            valorInicial = Subasta.calcularValoracionDescendente(listaProductoSubasta, fechaActual);
            System.out.println("La oferta inicial se ha establecido automáticamente en: " + valorInicial);
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
            if (subasta.getFechaFin().getTotalDias() < fecha.getTotalDias()) {
                imprimirSeparadorPequeno();

                // Seleccionar subasta vencida
                if (subasta.getOfertas().isEmpty()) {
                    //Extender la subasta 7 días más a partir de la fecha actual
                    System.out.println("La subasta N° " + subasta.getId() + " ha finalizado sin ofertas. Se extenderá 7 días más.");
                    String res = subasta.extenderSubasta(fecha);
                    if (res != null) {
                        System.out.println(res);
                    }
                } else {
                    Cliente ganador = subasta.finalizarSubasta();
                    System.out.println("La subasta ha finalizado. Los artículos subastados son:");
                    for (Producto articulo : subasta.getProductos()) {
                        if (articulo instanceof Consola) {
                            System.out.println("Consola: " + articulo.getNombre() + " | Marca: " + ((Consola) articulo).getMarca());
                        } else if (articulo instanceof Juego) {
                            System.out.println("Juego: " + articulo.getNombre() + " | Género: " + ((Juego) articulo).getGenero() + " | Plataforma: " + ((Juego) articulo).getPlataforma());
                        } else if (articulo instanceof Accesorio) {
                            System.out.println("Accesorio: " + articulo.getNombre() + " | Marca: " + ((Accesorio) articulo).getMarca() + " | Consola: " + ((Accesorio) articulo).getConsola());
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
}
