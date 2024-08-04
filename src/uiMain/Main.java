package uiMain;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        imprimirLogo();

        /* ~~~ Menu principal ~~~ */

        byte opcion = 1;
            do {
                imprimirSeparador();
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
                        Funcionalidad1.registrarCompra();

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