package uiMain;

import gestorAplicacion.manejoLocal.Tienda;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    //  ~~ Crear una clase Tienda para comprobar el codigo ~~ //
    static {
        Tienda tienda1 = new Tienda("Perez",123);
    }
    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        /* ~~~ Menu principal ~~~ */
        imprimirLogo();

        /* ~~ Selección de local ~~ */
        Tienda local = null; // Se adquiere el local con el que se quiere trabajar
            do{
                imprimirSeparador();

                for (Tienda i : Tienda.getLocales()){ // Bucle para imprimir los locales
                    int j = 1;
                    System.out.println(j+". "+i.getNombre());
                    j= j+1;
                }

                System.out.println("Ingrese el nombre del local");

                String nombreLocal = sc.nextLine(); // Recibir entrada del usuario
                // sc.nextLine();

                for (Tienda i : Tienda.getLocales()){ // Bucle para encontrar el local
                    if (i.getNombre().equals(nombreLocal)) {
                        local = i;
                        break;
                    }
                    else{
                        System.out.println("Local no encontrado. Presione enter para volver a intentar.");
                        sc.nextLine();
                    }
                }
            } while (local == null);

        /* ~~ Selección de funcionalidad ~~ */
        byte opcion = 1;
            do {
                imprimirSeparador();
                System.out.println("MENU PRINCIPAL");
                System.out.println("1. Registrar compra");
                System.out.println("2. Registrar préstamo");
                System.out.println("3. Administrar inventario");
                System.out.println("4. Gestionar empleados");
                System.out.println("5. ~~Placeholder para quinta funcionalidad~~");

                System.out.println("0. Salir");

                System.out.println("Ingrese el número de la opción que desea ejecutar:");

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
                        // Registrar compra
                        Funcionalidad1.registrarCompra(local);

                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 2:
                        // Registrar préstamo
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 3:
                        // Administrar inventario
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 4:
                        // Gestionar empleados
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 5:
                        // ~~Placeholder para quinta funcionalidad~~
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 0:
                        System.out.println("Saliendo...");
                        break;

                    default:
                        System.out.println("\n### ERROR ###");
                        System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                        break;
                }
            } while (opcion != 0);
    }

    // Separador de la TUI
    //▅ ▒
    static void imprimirSeparador() {
        System.out.println("▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅");
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
}