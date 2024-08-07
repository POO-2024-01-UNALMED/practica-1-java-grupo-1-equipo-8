package uiMain;

import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.productos.*;
import gestorAplicacion.personas.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* ~~~ Objetos para pruebas ~~~ */

    static Tienda tienda1 = new Tienda("Volador",123);
    static {
        tienda1.agregarProducto(new Consola("Polystation 5", 400, 10, 10, false, (byte) 5, 11, 11, 2021, 5, 100, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 720", 350, 15, 15, false, (byte) 5, 12, 12, 2021, 5, 100, "Microsoft"));
        tienda1.agregarProducto(new Consola("Noentiendo Swap", 300, 20, 20, false, (byte) 5, 13, 7, 2018, 5, 0, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Polystation 4", 250, 25, 25, false, (byte) 5, 14, 11, 2013, 15, 200, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 360", 200, 30, 30, false, (byte) 5, 15, 12, 2005, 10, 0, "Microsoft"));
        tienda1.agregarProducto(new Consola("Polystation 6", 500, 1, 1, false, (byte) 5, 16, 11, 2028, 0, 0, "Sony"));

        tienda1.agregarProducto(new Juego("Ronaldinho Soccer", 40, 40, 40, false, (byte) 5, 15, 8, 2020, 5, 0, "Deportes", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Carlos Duty", 30, 40, 40, false, (byte) 5, 10, 7, 2018, "FPS", "Xbox 360"));
        tienda1.agregarProducto(new Juego("Carlos Duty 2, Ahora es personal", 60, 30, 30, false , (byte) 5, 20, 10, 2024, "FPS", "Xbox 720"));
        tienda1.agregarProducto(new Juego("Super Mario 128", 40, 50, 50, false, (byte) 5, 10, 10, 2021, "Plataformas", "Noentiendo Swap"));

        tienda1.agregarProducto(new Accesorio("Control Polystation 5", 50, 60, 60, false, (byte) 5, 11, 11, 2021, 0, 0, "Sony", "Polystation 5"));
        tienda1.agregarProducto(new Accesorio("Control Polystation 4", 40, 50, 50, false, (byte) 5, 12, 12, 2013, 10, 0, "Sony", "Polystation 4"));
        tienda1.agregarProducto(new Accesorio("Control Polystation 3", 30, 40, 40, false, (byte) 5, 13, 11, 2006, 20, 0, "Sony", "Polystation 3"));
        tienda1.agregarProducto(new Accesorio("Control Xbox 720", 55, 50, 30, false, (byte) 5, 14, 11, 2021, 0, 0, "Microsoft", "Xbox 720"));
        tienda1.agregarProducto(new Accesorio("Control Xbox 360", 45, 40, 20, false, (byte) 5, 15, 12, 2005, 20, 0, "Microsoft", "Xbox 360"));
        tienda1.agregarProducto(new Accesorio("Control JoyCon Noentiendo Swap", 70, 40, 40, false, (byte) 5, 13, 7, 2018, 10, 0, "Noentiendo", "Noentiendo Swap"));
        tienda1.agregarProducto(new Accesorio("Control Pro Noentiendo Swap", 80, 40, 40, false, (byte) 5, 13, 7, 2018, 10, 0, "Noentiendo", "Noentiendo Swap"));

        // personal
        Empleado empleado1 = new Empleado(1, "Emanuel", "ehoyosi@hotmail.com", 3444404, 1000, 10, tienda1);
        Empleado empleado2 = new Empleado(2, "Joma", "jomachado@hotmail.com", 3444405, 1500, 12, tienda1);

        //metas
        new Meta(1, empleado1, 15, 6, 2024, 30, 8000, 10 );
        new Meta(2, empleado1, 18, 6, 2024, 35, 10000, 10);
    }

    static Tienda tienda2 = new Tienda("Robledo",1420);
    static {
        tienda2.agregarProducto(new Consola("Polystation 5", 450, 10, 10, false, (byte) 5, 11, 11, 2021, 0, 0, "Sony"));
        tienda2.agregarProducto(new Consola("Polystation 4", 280, 15, 15, false, (byte) 5, 12, 12, 2013, 10, 0, "Sony"));
        tienda2.agregarProducto(new Consola("Polystation 3", 180, 20, 20, false, (byte) 5, 13, 11, 2006, 20, 0, "Sony"));

        tienda2.agregarProducto(new Juego("Ronaldinho Soccer", 40, 40, 40, false, (byte) 5, 15, 8, 2020, 15, 0, "Deportes", "Polystation 5"));
        tienda2.agregarProducto(new Juego("Carlos Duty", 30, 40, 40, false, (byte) 5, 10, 7, 2018, 30, 0, "FPS", "Polystation 4"));

        tienda2.agregarProducto(new Accesorio("Control Polystation 5", 50, 70, 70, false, (byte) 5, 11, 11, 2021, 5, 50, "Sony", "Polystation 5"));
        tienda2.agregarProducto(new Accesorio("Control Polystation 4", 40, 60, 60, false, (byte) 5, 12, 12, 2013, 15, 100, "Sony", "Polystation 4"));
        tienda2.agregarProducto(new Accesorio("Control Polystation 3", 30, 50, 50, false, (byte) 5, 13, 11, 2006, 40, 300, "Sony", "Polystation 3"));
    }

    /* ~~~~~~~~~~~~~~~~~~ */
    /* Clientes */
    static Cliente cliente1 = new Cliente(123, "Juan", "juan@mail.com", 311203);
    static Cliente cliente2 = new Cliente(125, "Pedro", "pedro@mail.com", 311204);
    static Cliente cliente3 = new Cliente(126, "Maria", "maria@mail.com@", 311205);

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        /* ~~~ Menu principal ~~~ */
        imprimirLogo();

        // TODO: Seleccion de fecha
        // TODO: Imprimir local y fecha actuales en el menu principal

        Fecha fechaActual = new Fecha(10000);



        /* ~~ Selección de local ~~ */
        Tienda local = null; // Se adquiere el local con el que se quiere trabajar
            do{
                imprimirSeparador();

                int j = 1;
                for (Tienda i : Tienda.getLocales()){ // Bucle para imprimir los locales
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
                        System.out.print("\n### ERROR ###\n");
                        System.out.println("Local no encontrado. Presione enter para volver a intentar.");
                        sc.nextLine();
                        break;
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
                System.out.println("5. Subastar");

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
                        Funcionalidad1.registrarCompra(local, fechaActual);

                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 2:
                        // Registrar préstamo
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 3:
                        // Administrar inventario
                        Funcionalidad3.revisarInventario(local);
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 4:
                        // Gestionar empleados
                        Funcionalidad4.inspeccionEmpleado(local);
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
        System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░");
        //System.out.println("░░░░░░░░░░░░░░░░░░░░░████████████████████████████████████████████░░░░░░░░░░░░░░░░░░░░░");
        //System.out.println("▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅▅");
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