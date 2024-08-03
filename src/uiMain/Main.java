package uiMain;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        imprimirLogo();

        /* ~~~ Menu principal ~~~ */

        byte opcion = 0;
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
            opcion = sc.nextByte();

            switch (opcion) {
                case 1:
                    // Registrar compra
                    break;

                case 2:
                    // Registrar préstamo
                    break;

                case 3:
                    // Administrar inventario
                    break;

                case 4:
                    // Gestionar empleados
                    break;

                case 5:
                    // ~~Placeholder para quinta funcionalidad~~
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("\n##################################");
                    System.out.println("Opción inválida. Intente de nuevo.");
                    System.out.println("##################################\n");
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
                        "██╗   ██╗██╗██╗     ██╗      █████╗      ██╗██╗   ██╗███████╗ ██████╗  ██████╗ ███████╗\n" +
                        "██║   ██║██║██║     ██║     ██╔══██╗     ██║██║   ██║██╔════╝██╔════╝ ██╔═══██╗██╔════╝\n" +
                        "██║   ██║██║██║     ██║     ███████║     ██║██║   ██║█████╗  ██║  ███╗██║   ██║███████╗\n" +
                        "╚██╗ ██╔╝██║██║     ██║     ██╔══██║██   ██║██║   ██║██╔══╝  ██║   ██║██║   ██║╚════██║\n" +
                        " ╚████╔╝ ██║███████╗███████╗██║  ██║╚█████╔╝╚██████╔╝███████╗╚██████╔╝╚██████╔╝███████║\n" +
                        "  ╚═══╝  ╚═╝╚══════╝╚══════╝╚═╝  ╚═╝ ╚════╝  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚══════");

    }
}