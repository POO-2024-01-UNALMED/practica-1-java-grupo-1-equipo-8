package uiMain;

import baseDatos.Deserializador;
import baseDatos.Serializador;
import gestorAplicacion.informacionVenta.Subasta;
import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Reabastecimiento;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.productos.*;
import gestorAplicacion.personas.*;

import javax.swing.plaf.multi.MultiMenuBarUI;
import java.io.Serial;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /* ~~~ Objetos para pruebas ~~~ */

    // Comentar para probar la serializacion

    static Tienda tienda1 = new Tienda("Volador", 123);

    static {
        tienda1.agregarProducto(new Consola("Polystation 5", 400, 10, 10, false, (byte) 5, 11, 11, 2021, 5, 100, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 1080", 450, 10, 15, false, (byte) 5, 9, 11, 2021, 5, 0, "Microsoft"));
        tienda1.agregarProducto(new Consola("Xbox 720", 250, 15, 15, false, (byte) 5, 12, 12, 2013, 5, 100, "Microsoft"));
        tienda1.agregarProducto(new Consola("Noentiendo Swap", 300, 20, 20, false, (byte) 5, 13, 7, 2018, 5, 0, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Polystation 4", 250, 25, 25, false, (byte) 5, 14, 11, 2013, 15, 200, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 360", 150, 15, 30, false, (byte) 5, 15, 12, 2005, 10, 0, "Microsoft"));
        tienda1.agregarProducto(new Consola("Polystation 6", 600, 1, 1, false, (byte) 5, 16, 11, 2028, 0, 0, "Sony"));
        tienda1.agregarProducto(new Consola("Arati 7800", 2000, 1, 1, false, (byte) 5, 17, 11, 1986, 0, 0, "Arati"));

        tienda1.agregarProducto(new Juego("Carlos Duty", 30, 40, 40, false, (byte) 5, 10, 7, 2018, 5, 0, "FPS", "Xbox 360"));
        tienda1.agregarProducto(new Juego("Carlos Duty 2, Ahora es personal", 30, 30, 60, false, (byte) 5, 20, 10, 2024, "FPS", "Xbox 720"));
        tienda1.agregarProducto(new Juego("Cyberpunk 2078", 55, 10, 15, false, (byte) 5, 15, 12, 2023, "RPG", "Xbox 1080"));
        tienda1.agregarProducto(new Juego("Arch", 45, 8, 10, false, (byte) 5, 20, 11, 2023, "Aventura", "Xbox 1080"));
        tienda1.agregarProducto(new Juego("Alive Space", 50, 12, 15, false, (byte) 5, 18, 10, 2023, "Horror", "Xbox 1080"));
        tienda1.agregarProducto(new Juego("Full Life", 60, 5, 10, false, (byte) 5, 25, 9, 2023, "FPS", "Xbox 1080"));
        tienda1.agregarProducto(new Juego("Meinkraft", 50, 7, 10, false, (byte) 5, 30, 8, 2023, "Sandbox", "Xbox 1080"));
        tienda1.agregarProducto(new Juego("Ronaldinho Soccer", 40, 40, 40, false, (byte) 5, 15, 8, 2020, 5, 0, "Deportes", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Cyberpunk 2078", 60, 60, 60, false, (byte) 5, 15, 12, 2023, 10, 50, "RPG", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Super Mario 256", 60, 50, 50, false, (byte) 5, 10, 10, 2022, "Plataformas", "Noentiendo Swap"));
        tienda1.agregarProducto(new Juego("Arch", 65, 55, 70, false, (byte) 5, 20, 11, 2023, 10, 50, "Aventura", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Alive Space", 50, 45, 50, false, (byte) 5, 18, 10, 2023, "Horror", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Full Life", 30, 40, 40, false, (byte) 5, 25, 9, 2023, 10, 0, "FPS", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Meinkraft", 20, 50, 100, false, (byte) 5, 30, 8, 2023, 30, 100, "Sandbox", "Polystation 5"));
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
        tienda1.agregarProducto(new Consola("Xbox 720", 350, 3, true, (byte) 4, 12, 12, 2013, "Microsoft"));

        tienda1.agregarProducto(new Juego("Ronaldinho Soccer", 40, 10, true, (byte) 4, 15, 8, 2020, "Deportes", "Polystation 5"));
        tienda1.agregarProducto(new Juego("Carlos Duty", 30, 10, true, (byte) 4, 10, 7, 2018, "FPS", "Xbox 360"));
        tienda1.agregarProducto(new Juego("Carlos Duty 2, Ahora es personal", 30, 10, true, (byte) 4, 20, 10, 2024, "FPS", "Xbox 720"));
        tienda1.agregarProducto(new Juego("Super Mario 128", 60, 10, true, (byte) 4, 10, 10, 2021, "Plataformas", "Noentiendo Swap"));

        // productos usados
        tienda1.agregarProducto(new Consola("Polystation 2", 50, 5, false, (byte) 3, 11, 10, 2000, "Sony"));
        tienda1.agregarProducto(new Consola("Polystation 3", 150, 8, false, (byte) 3, 13, 11, 2006, "Sony"));
        tienda1.agregarProducto(new Consola("Polystation 4", 200, 3, false, (byte) 3, 12, 12, 2013, "Sony"));
        tienda1.agregarProducto(new Consola("Xbox 360", 130, 5, false, (byte) 3, 15, 12, 2005, "Microsoft"));
        tienda1.agregarProducto(new Consola("Noentiendo Wii", 100, 3, false, (byte) 3, 13, 7, 2006, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Noentiendo Wii", 80, 2, false, (byte) 2, 13, 7, 2006, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Noentiendo DS", 100, 3, false, (byte) 3, 21, 11, 2004, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Noentiendo 32", 150, 4, false, (byte) 4, 29, 9, 1996, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Noentiendo SEN", 200, 1, false, (byte) 3, 18, 10, 1985, "Noentiendo"));
        tienda1.agregarProducto(new Consola("Arati 2600", 800, 1, false, (byte) 4, 11, 9, 1977, "Arati"));
        tienda1.agregarProducto(new Consola("Magnavox Odyssey", 900, 1, false, (byte) 4, 27, 8, 1972, "Magnavox"));

        tienda1.agregarProducto(new Juego("Ronaldinho Soccer 2010", 10, 10, false, (byte) 3, 15, 6, 2010, "Deportes", "Polystation 3"));
        tienda1.agregarProducto(new Juego("Carlos Duty 0.5", 10, 10, false, (byte) 3, 20, 10, 2019, "FPS", "Xbox 360"));
        tienda1.agregarProducto(new Juego("Carlos Duty 2, Ahora es personal", 30, 10, false, (byte) 3, 20, 10, 2024, "FPS", "Xbox 720"));
        tienda1.agregarProducto(new Juego("Super Mario 128", 50, 7, false, (byte) 3, 10, 10, 2021, "Plataformas", "Noentiendo Swap"));
        tienda1.agregarProducto(new Juego("Super Mario 32", 30, 5, false, (byte) 2, 10, 10, 1996, "Plataformas", "Noentiendo 32"));
        tienda1.agregarProducto(new Juego("Super Mario 8", 50, 1, false, (byte) 3, 10, 10, 1985, "Plataformas", "Noentiendo SEN"));

        tienda1.agregarProducto(new Accesorio("Control Polystation 3", 20, 10, false, (byte) 3, 13, 11, 2006, "Sony", "Polystation 3"));
        tienda1.agregarProducto(new Accesorio("Control Xbox 360", 35, 10, false, (byte) 3, 15, 12, 2005, "Microsoft", "Xbox 360"));
        tienda1.agregarProducto(new Accesorio("Control Polystation 4", 30, 10, false, (byte) 3, 12, 12, 2013, "Sony", "Polystation 4"));
        tienda1.agregarProducto(new Accesorio("Control Arati 2600", 50, 10, false, (byte) 3, 11, 9, 1977, "Arati", "Arati 2600"));
        tienda1.agregarProducto(new Accesorio("Control Magnavox Odyssey", 50, 10, false, (byte) 3, 27, 8, 1972, "Magnavox", "Magnavox Odyssey"));


        // ~~~~~~~~~~~~~~~ personal ~~~~~~~~~~~~~~~
        Empleado empleado1 = new Empleado(1004, "Emanuel", "ehoyosi@hotmail.com", 3444404, 1000, 10, (byte) 5, tienda1);
        Empleado empleado2 = new Empleado(2004, "Joma", "jomachado@hotmail.com", 3444405, 1500, 12, (byte) 5, tienda1);

        //metas
        Meta meta1 = new Meta(empleado1, 15, 6, 2024, 30, 8000);
        Meta meta2 = new Meta(empleado1, 22, 6, 2024, 26, 8000);
        Meta meta3 = new Meta(empleado1, 21, 6, 2024, 24, 8000);
        Meta meta4 = new Meta(empleado1, 18, 6, 2024, 35, 10000);
        Meta meta5 = new Meta(empleado1, 19, 6, 2024, 28, 10000);
        Meta meta6 = new Meta(empleado1, 31, 6, 2024, 30, 10000);

        meta1.setAcumulado(29);
        meta2.setAcumulado(29);
        meta3.setAcumulado(29);
        meta4.setAcumulado(29);
        meta5.setAcumulado(29);
        meta6.setAcumulado(29);

        Cliente cliente1 = new Cliente(1312, "David", "dad", 323);

        Fecha fecha1 = new Fecha(15,6,2024);
        Fecha fecha2 = new Fecha(11,6,2024);
        Fecha fecha3 = new Fecha(14,6,2024);
        Fecha fecha4 = new Fecha(13,6,2024);
        Fecha fecha5 = new Fecha(10,6,2024);
        Fecha fecha6 = new Fecha(12,6,2024);
        Fecha fecha7 = new Fecha(11,6,2024);

        Fecha fecha8 = new Fecha(8,6,2024);
        Fecha fecha9 = new Fecha(7,6,2024);
        Fecha fecha10 = new Fecha(8,6,2024);
        Fecha fecha11 = new Fecha(5,6,2024);
        Fecha fecha12 = new Fecha(7,6,2024);


        new Transaccion(fecha1, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha2, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha3, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha4, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha5, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha6, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha7, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);

        new Transaccion(fecha8, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha9, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha10, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha11, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
        new Transaccion(fecha12, cliente1, empleado1, tienda1, new ArrayList<Producto>(), 10000);
    }

    static Tienda tienda2 = new Tienda("Robledo", 1420);

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

    static Tienda tienda3 = new Tienda("Laureles", 1421);
    static Tienda tienda4 = new Tienda("Buenos Aires", 1422);

    static {
        // Adding products to "Laureles"
        tienda3.agregarProducto(new Consola("Polystation 5", 400, 10, 10, false, (byte) 5, 11, 11, 2021, 5, 100, "Sony"));
        tienda3.agregarProducto(new Consola("Xbox 1080", 450, 10, 15, false, (byte) 5, 9, 11, 2021, 5, 0, "Microsoft"));
        tienda3.agregarProducto(new Consola("Noentiendo Swap", 300, 20, 20, false, (byte) 5, 13, 7, 2018, 5, 0, "Noentiendo"));
        tienda3.agregarProducto(new Consola("Polystation 4", 250, 25, 25, false, (byte) 5, 14, 11, 2013, 15, 200, "Sony"));
        tienda3.agregarProducto(new Consola("Xbox 360", 150, 15, 30, false, (byte) 5, 15, 12, 2005, 10, 0, "Microsoft"));
        tienda3.agregarProducto(new Juego("Cyberpunk 2078", 55, 10, 15, false, (byte) 5, 15, 12, 2023, "RPG", "Xbox 1080"));
        tienda3.agregarProducto(new Juego("Arch", 45, 8, 14, false, (byte) 5, 20, 11, 2023, "Aventura", "Xbox 1080"));
        tienda3.agregarProducto(new Juego("Alive Space", 50, 12, 25, false, (byte) 5, 18, 10, 2023, "Horror", "Xbox 1080"));
        tienda3.agregarProducto(new Juego("Full Life", 60, 5, 20, false, (byte) 5, 25, 9, 2023, "FPS", "Xbox 1080"));
        tienda3.agregarProducto(new Juego("Meinkraft", 50, 7, 30, false, (byte) 5, 30, 8, 2023, "Sandbox", "Xbox 1080"));
        tienda3.agregarProducto(new Accesorio("Control Polystation 5", 50, 60, 60, false, (byte) 5, 11, 11, 2021, 0, 0, "Sony", "Polystation 5"));
        tienda3.agregarProducto(new Accesorio("Control Xbox 720", 55, 30, 50, false, (byte) 5, 14, 11, 2021, 0, 0, "Microsoft", "Xbox 720"));

        Empleado empleado3_1 = new Empleado(3001, "Jhorman", "jhorman@example.com", 3444406, 1200, 8, (byte) 6, tienda3);
        Empleado empleado3_2 = new Empleado(3002, "Sebastian", "sebastian@example.com", 3444407, 1300, 9, (byte) 6, tienda3);

        Empleado empleado4_1 = new Empleado(4001, "Pablo", "pablo@example.com", 3444408, 1400, 10, (byte) 6, tienda4);
        Empleado empleado4_2 = new Empleado(4002, "Miguel", "miguel@example.com", 3444409, 1500, 11, (byte) 6, tienda4);

        tienda4.agregarProducto(new Consola("Polystation 5", 450, 10, 10, false, (byte) 5, 11, 11, 2021, 0, 0, "Sony"));
        tienda4.agregarProducto(new Consola("Polystation 4", 280, 15, 15, false, (byte) 5, 12, 12, 2013, 10, 0, "Sony"));
        tienda4.agregarProducto(new Consola("Polystation 3", 180, 20, 20, false, (byte) 5, 13, 11, 2006, 20, 0, "Sony"));
        tienda4.agregarProducto(new Juego("Ronaldinho Soccer", 40, 40, 40, false, (byte) 5, 15, 8, 2020, 15, 0, "Deportes", "Polystation 5"));
        tienda4.agregarProducto(new Juego("Carlos Duty", 30, 40, 40, false, (byte) 5, 10, 7, 2018, 30, 0, "FPS", "Polystation 4"));
        tienda4.agregarProducto(new Juego("Cyberpunk 2078", 55, 10, 15, false, (byte) 5, 15, 12, 2023, "RPG", "Xbox 1080"));
        tienda4.agregarProducto(new Juego("Arch", 45, 8, 10, false, (byte) 5, 20, 11, 2023, "Aventura", "Xbox 1080"));
        tienda4.agregarProducto(new Juego("Alive Space", 50, 12, 15, false, (byte) 5, 18, 10, 2023, "Horror", "Xbox 1080"));
        tienda4.agregarProducto(new Juego("Full Life", 60, 5, 10, false, (byte) 5, 25, 9, 2023, "FPS", "Xbox 1080"));
        tienda4.agregarProducto(new Juego("Meinkraft", 50, 7, 10, false, (byte) 5, 30, 8, 2023, "Sandbox", "Xbox 1080"));
        tienda4.agregarProducto(new Accesorio("Control Polystation 5", 50, 60, 60, false, (byte) 5, 11, 11, 2021, 0, 0, "Sony", "Polystation 5"));
        tienda4.agregarProducto(new Accesorio("Control Xbox 720", 55, 30, 50, false, (byte) 5, 14, 11, 2021, 0, 0, "Microsoft", "Xbox 720"));
        tienda4.agregarProducto(new Accesorio("Control Polystation 4", 40, 50, 50, false, (byte) 5, 12, 12, 2013, 10, 0, "Sony", "Polystation 4"));
        tienda4.agregarProducto(new Accesorio("Control Polystation 3", 30, 40, 40, false, (byte) 5, 13, 11, 2006, 20, 0, "Sony", "Polystation 3"));
    }

    // Clientes
    static Cliente cliente1 = new Cliente(120, "Juan", "juan@mail.com", 311203, 1000);
    static Cliente cliente2 = new Cliente(121, "Pedro", "pedro@mail.com", 311204, 300);
    static Cliente cliente3 = new Cliente(122, "Maria", "maria@mail.com", 311205, 400);
    static Cliente cliente4 = new Cliente(123, "Ana", "ana@mail.com", 311206, 1000);
    static Cliente cliente5 = new Cliente(124, "Luis", "luis@mail.com", 311207, 1500);
    static Cliente cliente6 = new Cliente(125, "Carlos", "carlos@mail.com", 311208, 0);
    static Cliente cliente7 = new Cliente(126, "Sofia", "sofia@mail.com", 311209, 2000);
    static Cliente cliente8 = new Cliente(127, "Laura", "laura@mail.com", 311210, 500);
    static Cliente cliente9 = new Cliente(128, "Miguel", "miguel@mail.com", 311211, 700);
    static Cliente cliente10 = new Cliente(129, "Elena", "elena@mail.com", 311212, 900);
    static Cliente cliente11 = new Cliente(130, "Jorge", "jorge@mail.com", 311213, 0);
    static Cliente cliente12 = new Cliente(131, "Patricia", "patricia@mail.com", 311214, 1200);
    static Cliente cliente13 = new Cliente(132, "Fernando", "fernando@mail.com", 311215, 1400);
    static Cliente cliente14 = new Cliente(133, "Lucia", "lucia@mail.com", 311216, 1600);
    static Cliente cliente15 = new Cliente(134, "Andres", "andres@mail.com", 311217, 1800);
    static Cliente cliente16 = new Cliente(135, "Monica", "monica@mail.com", 311218, 2000);
    static Cliente cliente17 = new Cliente(136, "Ricardo", "ricardo@mail.com", 311219, 2200);

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     DESCOMENTAR LINEA 23 PARA USAR SIN SERIALIZACION */


    // Variable scanner para entrada de datos
    static Scanner sc = new Scanner(System.in);
    public static Fecha ultimaFecha = new Fecha(1, 1, 2021);

    public static void main(String[] args) {
        /* ~~~ Carga de objetos serializados ~~~ */
        // DESCOMENTAR PARA ACTIVAR LA DESERIALIZACIoN

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
                    Funcionalidad1.registrarCompra(local, fechaActual);
                    break;

                case 2:
                    // Registrar prestamo

                    Funcionalidad2.registrarPrestamo(local, fechaActual);
                    break;

                case 3:
                    // Administrar inventario
                    Funcionalidad3.revisarInventario(local, fechaActual);
                    break;

                case 4:
                    // Gestionar empleados
                    Funcionalidad4.inspeccionEmpleado(local, fechaActual);
                    break;

                case 5:
                    // ~~Placeholder para quinta funcionalidad~~
                    Funcionalidad5.subastar(local, fechaActual);
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




    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FUNCIONALIDAD 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */



    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FUNCIONALIDAD 2 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FUNCIONALIDAD 3 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FUNCIONALIDAD 4 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FUNCIONALIDAD 5 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    /*public void subastar(Tienda local, Fecha fecha) {

    }*/


    /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
}