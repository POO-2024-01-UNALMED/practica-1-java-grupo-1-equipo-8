package uiMain;

import baseDatos.Deserializador;
import baseDatos.Serializador;
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
        tienda1.agregarProducto(new Juego("Carlos Duty", 30, 40, 40, false, (byte) 5, 10, 7, 2018, 5, 0, "FPS", "Xbox 360"));
        tienda1.agregarProducto(new Juego("Carlos Duty 2, Ahora es personal", 30, 30, 60, false , (byte) 5, 20, 10, 2024, "FPS", "Xbox 720"));
        tienda1.agregarProducto(new Juego("Super Mario 128", 60, 50, 50, false, (byte) 5, 10, 10, 2021, "Plataformas", "Noentiendo Swap"));

        tienda1.agregarProducto(new Accesorio("Control Polystation 5", 50, 60, 60, false, (byte) 5, 11, 11, 2021, 0, 0, "Sony", "Polystation 5"));
        tienda1.agregarProducto(new Accesorio("Control Polystation 4", 40, 50, 50, false, (byte) 5, 12, 12, 2013, 10, 0, "Sony", "Polystation 4"));
        tienda1.agregarProducto(new Accesorio("Control Polystation 3", 30, 40, 40, false, (byte) 5, 13, 11, 2006, 20, 0, "Sony", "Polystation 3"));
        tienda1.agregarProducto(new Accesorio("Control Xbox 720", 55, 30, 50, false, (byte) 5, 14, 11, 2021, 0, 0, "Microsoft", "Xbox 720"));
        tienda1.agregarProducto(new Accesorio("Control Xbox 360", 45, 20, 40, false, (byte) 5, 15, 12, 2005, 20, 0, "Microsoft", "Xbox 360"));
        tienda1.agregarProducto(new Accesorio("Control JoyCon Noentiendo Swap", 70, 40, 40, false, (byte) 5, 13, 7, 2018, 10, 0, "Noentiendo", "Noentiendo Swap"));
        tienda1.agregarProducto(new Accesorio("Control Pro Noentiendo Swap", 80, 40, 40, false, (byte) 5, 13, 7, 2018, 10, 0, "Noentiendo", "Noentiendo Swap"));

        // productos de prestamo
        tienda1.agregarProducto(new Consola("Polystation 3", 180, 8, true, (byte) 4, 13, 11, 2006, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 360", 200, 10, true, (byte) 4, 15, 12, 2005, "Microsoft"));
        tienda1.agregarProducto(new Consola("Polystation 4", 280, 5, true, (byte) 4, 12, 12, 2013, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 720", 350, 3, true, (byte) 4, 12, 12, 2021, "Microsoft"));

        tienda1.agregarProducto(new Juego("Ronaldinho Soccer", 40, 10, true, (byte) 4, 15, 8, 2020, "Deportes", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Carlos Duty", 30, 10, true, (byte) 4, 10, 7, 2018, "FPS", "Xbox 360"));
        tienda1.agregarProducto(new Juego("Carlos Duty 2, Ahora es personal", 30, 10, true, (byte) 4, 20, 10, 2024, "FPS", "Xbox 720"));
        tienda1.agregarProducto(new Juego("Super Mario 128", 60, 10, true, (byte) 4, 10, 10, 2021, "Plataformas", "Noentiendo Swap"));


        // ~~~~~~~~~~~~~~~ personal ~~~~~~~~~~~~~~~
        Empleado empleado1 = new Empleado(1004, "Emanuel", "ehoyosi@hotmail.com", 3444404, 1000, 10, tienda1);
        Empleado empleado2 = new Empleado(2004, "Joma", "jomachado@hotmail.com", 3444405, 1500, 12, tienda1);

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
    static Cliente cliente2 = new Cliente(124, "Pedro", "pedro@mail.com", 311204);
    static Cliente cliente3 = new Cliente(125, "Maria", "maria@mail.com@", 311205);

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        /* ~~~ Carga de objetos serializados ~~~ */
        Deserializador.deserializarTiendas();
        Deserializador.deserializarClientes();

        /* ~~~ Menu principal ~~~ */
        imprimirLogo();

        Fecha fechaActual = recibirFecha();

        // TODO: Imprimir local y fecha actuales en el menu principal

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
                    }
                    else{
                        System.out.print("\n### ERROR ###\n");
                        System.out.println("Local no encontrado. Presione enter para volver a intentar.");
                        sc.nextLine();
                    }
                    break;
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

                        Funcionalidad2.registrarPrestamo(local, fechaActual);

                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 3:
                        // Administrar inventario
                        Funcionalidad3.revisarInventario(local, fechaActual);
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 4:
                        // Gestionar empleados
                        Funcionalidad4.inspeccionEmpleado(local, fechaActual);
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 5:
                        // ~~Placeholder para quinta funcionalidad~~
                        sc.nextLine();  // Limpiar el buffer
                        break;

                    case 0:
                        // Salir

                        Serializador.serializarTiendas(Tienda.getLocales());
                        Serializador.serializarClientes(Cliente.getClientes());

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

    // Método para identificar a un cliente
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

    // Recibe una String que es la pregunta que se hará.
    // Devuelve true si la respuesta no es No (ni "n" ni "N")
    public static boolean siNo(String pregunta) {
        System.out.println(pregunta + " (S/n)");
        char respuesta = sc.next().charAt(0);
        sc.nextLine();  // Limpiar el buffer

        return (respuesta == 'n' || respuesta == 'N');
    }

    public static Fecha recibirFecha() {
        int year;
        int mes;
        int dia;


        // Recibir año
        while (true) {
            try {
                System.out.print("Ingrese año actual: ");

                year = sc.nextInt();

                if (year < 0) { throw new Exception(""); }

                break;
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }

        //Recibir mes
        while (true) {
            try {
                System.out.print("Ingrese mes actual: ");

                mes = sc.nextInt();

                if (mes < 0 || mes > 12) { throw new Exception(""); }

                break;
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }

        // Recibir día
        while (true) {
            try {
                System.out.print("Ingrese día actual: ");
                dia = sc.nextInt();

                //TODO: Validar que el día sea válido para el mes
                if (dia == 0 || dia > 31) { throw new Exception("Día inválido"); }

                break;
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presione Enter para volver a intentar.");
                sc.nextLine();  // Limpiar el buffer
                sc.nextLine();  // Esperar a que el usuario presione Enter
            }
        }

        sc.nextLine(); // Limpiar el buffer
        return new Fecha(dia, mes, year);
    }
}