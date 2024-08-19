package uiMain;

import gestorAplicacion.informacionVenta.Subasta;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;
import gestorAplicacion.productos.Producto;

import java.util.ArrayList;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;
import static uiMain.Main.recibirFecha;

public class Funcionalidad5 {
    static Scanner sc = new Scanner(System.in); // variable scanner
    static int puntosUsados = 0; // Cantidad de puntos que se usan en la transacción. Siempre será menor a la cantidad de puntos del cliente

    public void subastar(Tienda local, Fecha fecha) {

        // Comprobar subastas vencida
        comprobarSubastas(local, fecha);

        //Menú
        byte opcionMenu = 0;
        do {
            imprimirSeparador();
            System.out.println("1. Crear Subasta");
            System.out.println("0. Cancelar y salir");

            // Recibir entrada del usuario
            try {
                opcionMenu = sc.nextByte();
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.\n");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
                continue;
            }
            sc.nextLine();
            switch (opcionMenu) {
                case 1: // Crear Subasta
                    ArrayList<Producto> listaProductoSubasta = new ArrayList<>();
                    boolean registrarOtro;
                    do {
                        //Seleccionar tipo de producto
                        Producto producto = Funcionalidad1.seleccionarProducto(local.getInventarioUsado());
                        //Agregar producto a la lista de subasta
                        listaProductoSubasta.add(producto);
                        System.out.println("Producto agregado a la subasta.");
                        imprimirSeparador();
                        System.out.println("¿Desea agregar otro producto a la subasta? (s/n)");
                        var res = sc.nextByte();
                        char resChar = (char) res;
                        registrarOtro = Character.toLowerCase(resChar) == 's';
                    } while (registrarOtro);

                    //Se abre submenu para seleccionar tipo de subasta
                    byte opcionSubMenu1 = 0;
                    do {
                        imprimirSeparador();
                        System.out.println("1. Subasta Anónima");
                        System.out.println("0. Cancelar y salir");
                        try {
                            opcionSubMenu1 = sc.nextByte();
                        } catch (Exception e) {
                            System.out.println("\n### ERROR ###");
                            System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.\n");
                            sc.nextLine();  // Limpiar el buffer
                            sc.nextLine();  // Esperar a que el usuario presione Enter
                            continue;
                        }
                        switch (opcionSubMenu1) {
                            case 1: // Subasta Anónima

                                registrarSubasta(local, listaProductoSubasta, 0, "Anonima");
                                break;
                            case 2: // Subasta Ascendente
                                boolean valorPredefinido;
                                System.out.println("¿Tendrá valor inicial predefinido? (s/n)");
                                var res = sc.nextByte();
                                char resChar = (char) res;
                                byte valorInicialAscendente = 0;
                                if (Character.toLowerCase(resChar) == 's') {
                                    System.out.println("¿Desea establecer el valor inicial manualmente? (s/n)");
                                    var res2 = sc.nextByte();
                                    char resChar2 = (char) res2;

                                    if (Character.toLowerCase(resChar2) == 's') {
                                        do {
                                            System.out.println("Ingrese el valor inicial de la subasta: ");
                                            try {
                                                valorInicialAscendente = sc.nextByte();
                                                if (valorInicialAscendente <= 0) {
                                                    System.out.println("El valor inicial debe ser mayor a 0");
                                                }
                                            } catch (Exception e) {
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("Ingrese un valor válido. Presione Enter para " +
                                                        "volver a intentar.\n");
                                                sc.nextLine();  // Limpiar el buffer
                                                sc.nextLine();  // Esperar a que el usuario presione Enter

                                            }

                                        } while (valorInicialAscendente <= 0);

                                    } else {
                                        // Se hacen malabares para calcular el valor inicial
                                    }
                                }
                                registrarSubasta(local, listaProductoSubasta, valorInicialAscendente, "Ascendente");
                                break;
                            case 3: // Subasta Descendente
                                int valorInicialDescente = 0;
                                // Se hacen malabares para calcular el valor inicial
                                registrarSubasta(local, listaProductoSubasta, valorInicialDescente, "Descendente");
                                break;

                            case 0: // Cancelar y salir
                                System.out.println("Creación de subasta cancelada.");
                                break;

                            default:
                                System.out.println("\n### ERROR ###");
                                System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
                                sc.nextLine();  // Esperar a que el usuario presione Enter
                                break;
                        }
                    } while (opcionSubMenu1 != 0);

                    break;

                case 0: // Cancelar y salir
                    System.out.println("Registro de subasta cancelado.");

                    return;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para intentar de nuevo.");
                    sc.nextLine();  // Esperar a que el usuario presione Enter
                    break;
            }
        } while (opcionMenu != 0);
    }

    private static void registrarSubasta(
            Tienda local, ArrayList<Producto> listaProductoSubasta,
            int ofertaMayor, String tipo) {
        // Seleccionar fecha de inicio
        System.out.println("Ingrese la fecha de inicio de la subasta: ");
        Fecha fechaInicio = recibirFecha();
        // Seleccionar fecha de fin
        System.out.println("Ingrese la fecha de fin de la subasta: ");
        Fecha fechaFin = recibirFecha();
        // Crear subasta
        new Subasta(fechaInicio, fechaFin, listaProductoSubasta, 0, local,
                tipo);
        System.out.println("Subasta creada con éxito.");
    }


    // Método para comprobar el estado de las subastas de un local dado
    public void comprobarSubastas(Tienda local, Fecha fecha) {
        for (Subasta subasta : local.getSubastas()) {
            // Hay subastas con fecha anterior a la actual
            if (subasta.getFechaFin().getTotalDias() > fecha.getTotalDias()) {
                // Seleccionar subasta vencida
                if (subasta.getOfertas().isEmpty()) {
                    //Extender la subasta 7 días más
                    System.out.println("La subasta N° " + subasta.getId() +
                            " ha finalizado sin ofertas. Se extenderá 7 días más.");
                    String res = subasta.extenderSubasta();
                    if (res != null) {
                        System.out.println(res);
                    }
                } else {
                    Cliente ganador = subasta.finalizarSubasta();
                    System.out.println("La subasta ha finalizado. El ganador es: " + ganador.getNombre());
                }
            }
        }
    }
}
