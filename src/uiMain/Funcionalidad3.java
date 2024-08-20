package uiMain;


import java.util.*;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.*;
import gestorAplicacion.productos.*;

import static uiMain.Main.*;


public class Funcionalidad3 {
    static Scanner sc = new Scanner(System.in);

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
                opcion = sc.nextByte();
                sc.nextLine(); // Limpiar buffer

            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Se debe ingresar un valor numerico valido.");
                System.out.println("Presione Enter para continuar");
                sc.nextLine(); //Limpiar el buffer
                sc.nextLine();//Esperar que el usuario presione Enter

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

                            tipo = sc.nextByte();
                        } catch (Exception e) {
                            imprimirSeparador();

                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor debe ser numerico");
                            sc.nextLine();
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

                            cod = sc.nextInt();
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
                                tipo = sc.nextByte();
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
                                sc.nextLine();
                                continue;
                            }
                            switch (tipo) {
                                case 1:
                                    String nuevoNombre;
                                    imprimirSeparador();
                                    System.out.println("Ingrese el nuevo nombre: ");
                                    sc.nextLine();
                                    nuevoNombre = sc.nextLine();
                                    producto.setNombre(nuevoNombre);

                                    break;

                                case 2:
                                    int nuevoPrecio;
                                    while (true) {
                                        imprimirSeparador();
                                        System.out.println("Ingrese el nuevo precio");

                                        try {
                                            nuevoPrecio = sc.nextInt();
                                            sc.nextLine();
                                            break;
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            sc.nextLine();
                                        }
                                    }
                                    producto.setValor(nuevoPrecio);
                                    sc.nextLine();
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
                                            condicion = sc.nextByte();
                                            producto.setCondicion(condicion);
                                            break;
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            sc.nextLine();
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
                                            descuento = sc.nextInt();
                                            producto.setDescuento(descuento);
                                            break;
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            sc.nextLine();
                                        }
                                    }
                                    break;
                                case 6:
                                    int puntosRequeridos;
                                    while (true) {
                                        imprimirSeparador();
                                        System.out.println("Ingrese los puntos requeridos");
                                        try {
                                            puntosRequeridos = sc.nextInt();
                                            producto.setPuntosRequeridos(puntosRequeridos);
                                            break;
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            sc.nextLine();
                                        }
                                    }
                                    break;
                                case 7:
                                    if (producto instanceof Juego){
                                        String genero;
                                        imprimirSeparador();
                                        System.out.println("Ingrese el nuevo genero: ");
                                        sc.nextLine();
                                        genero = sc.nextLine();
                                        ((Juego) producto).setGenero(genero);
                                    }
                                    else if (producto instanceof Consola){
                                        String marca;
                                        imprimirSeparador();
                                        System.out.println("Ingrese la nueva marca: ");
                                        sc.nextLine();
                                        marca = sc.nextLine();
                                        ((Consola) producto).setMarca(marca);
                                    }
                                    else if (producto instanceof Accesorio){
                                        String marca;
                                        imprimirSeparador();
                                        System.out.println("Ingrese la nueva marca: ");
                                        sc.nextLine();
                                        marca = sc.nextLine();
                                        ((Accesorio) producto).setMarca(marca);
                                    }
                                    break;
                                case 8:
                                    if (producto instanceof Juego){
                                        String plataforma;
                                        imprimirSeparador();
                                        System.out.println("Ingrese la nueva plataforma: ");
                                        sc.nextLine();
                                        plataforma = sc.nextLine();
                                        ((Juego) producto).setPlataforma(plataforma);
                                    }
                                    else if (producto instanceof Accesorio){
                                        String consola;
                                        imprimirSeparador();
                                        System.out.println("Ingrese la nueva consola: ");
                                        sc.nextLine();
                                        consola = sc.nextLine();
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
                opcion = sc.nextByte();
                sc.nextLine();
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
                            sc.nextLine(); //Limpiar buffer
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
                            sc.nextLine();
                            break;
                        } else if (rangoVentas.isEmpty()) {
                            imprimirSeparador();
                            System.out.println("No hay ventas dentro del rango");
                            sc.nextLine();
                            break;
                        }
                        while (true) {
                            try {
                                imprimirSeparador();
                                System.out.println("Desea \n1.Ver ventas individuales \n2.ordenes en este rango \n3.Tendencias en este rango \n4.Proceder al reabastecimiento \n5.Regresar");
                                opcion = sc.nextByte();
                                sc.nextLine();
                            } catch (Exception e) {
                                imprimirSeparador();
                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");
                                sc.nextLine();
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
                                        System.out.println("    * ID: " + p.getCodigo() + " | Nombre: " + p.getNombre() + " | Cantidad: " + p.getCantidad() + " | Precio: " + p.getValor() + " | Prioridad: " + p.getPrioridad());
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
                                            opcion = sc.nextByte();
                                            sc.nextLine();
                                        } catch (Exception e) {
                                            imprimirSeparador();
                                            System.out.println("\n### ERROR ###");
                                            System.out.println("El valor debe ser numerico");
                                            sc.nextLine();
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

                        decision = sc.nextLine();
                        System.out.println("Presione Enter para continuar");
                        sc.nextLine();
                        if (decision.equalsIgnoreCase("si")) {
                            byte tipo;
                            ArrayList<Producto> lista = new ArrayList<Producto>();

                            try {//impedir un error al elegir el tipo
                                imprimirSeparador();
                                System.out.println("Ingresa el tipo del producto\n1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                tipo = sc.nextByte();
                                sc.nextLine();
                            } catch (Exception e) {//Informar de un error
                                imprimirSeparador();

                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");
                                sc.nextLine();
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
                                decision = sc.nextLine();

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
                                    ArrayList<Producto> lista = new ArrayList<Producto>();
                                    Collections.copy(lista, local.getInventario());
                                    Producto.ordenar(lista, orden);

                                    for (Producto i : lista) {
                                        System.out.println("Nombre: " + i.getNombre() + " | Prioridad: " + i.getPrioridad());
                                    }
                                    break;
                                } else {
                                    System.out.println("La desicion ingresada no existe");
                                    sc.nextLine();
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
                opcion = sc.nextByte();
                sc.nextLine();
            } catch (Exception e) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("El valor debe ser numerico");
                sc.nextLine();
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
                            opcion = sc.nextByte();
                            sc.nextLine();
                        } catch (Exception e) {
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor debe ser numerico");
                            sc.nextLine();
                            continue;
                        }
                        for (Producto i : local.getInventario()) {
                            if (i instanceof Juego) {
                                lista.add((Juego) i);
                            }
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
                            System.out.println("Desea ver \n1.Alta\n2.Baja\n3.Regresar");
                            opcion = sc.nextByte();
                            sc.nextLine();
                        } catch (Exception e) {
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor debe ser numerico");
                            sc.nextLine();
                            continue;
                        }
                        ArrayList<Producto> lista = new ArrayList<>();
                        int decision = 0;
                        switch (opcion) {
                            case 1://Alta prioridad
                                while (true) {
                                    for (Producto i : local.getInventario()) {
                                        if (i.getPrioridad().equalsIgnoreCase("prioridad alta") || i.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                            lista.add(i);
                                        }
                                    }
                                    Producto.ordenar(lista, "prioridad");
                                    if (lista.isEmpty()) {
                                        imprimirSeparador();
                                        System.out.println("No hay productos con prioridad alta o muy alta en este local.");
                                        sc.nextLine();
                                        continue;
                                    } else {
                                        for (Producto j : lista) {
                                            System.out.println("Nombre: " + j.getNombre() + " | Prioridad: " + j.getPrioridad() + " | ID: " + j.getCodigo());
                                        }
                                    }
                                    try {
                                        imprimirSeparador();
                                        System.out.println("Ingresa el ID del producto");
                                        decision = sc.nextInt();
                                        sc.nextLine();
                                    } catch (Exception e) {
                                        imprimirSeparador();
                                        System.out.println("\n### ERROR ###");
                                        System.out.println("El valor debe ser numerico");
                                        sc.nextLine();
                                        continue;
                                    }
                                    boolean esTrue = false;
                                    int indice = 0;
                                    ArrayList<Tienda> localesValidos = new ArrayList<>();
                                    for (Producto i : lista) {
                                        if (i.getCodigo() == decision) {
                                            esTrue = true;
                                            indice = lista.indexOf(i);
                                            break;
                                        }
                                    }
                                    if (esTrue) {
                                        for (Tienda t : Tienda.getLocales()) {//recorrer los locales en busca del producto
                                            for (Producto p : t.getInventario()) {
                                                if (lista.get(indice).getNombre().equalsIgnoreCase(p.getNombre()) && t != local) {
                                                    if (p.getPrioridad().equalsIgnoreCase("prioridad media") || p.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                                        localesValidos.add(t);
                                                    }
                                                }
                                            }
                                        }
                                        if (localesValidos.isEmpty()) {
                                            imprimirSeparador();
                                            System.out.println("No se encontro el producto disponible en ningun local.");
                                            sc.nextLine();
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
                                                if (lista.get(indice).getNombre().equalsIgnoreCase(p.getNombre())) {
                                                    if (p.getPrioridad().equalsIgnoreCase("prioridad media") || p.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                                        System.out.println("•" + t.getNombre() + "| cantidad: " + p.getCantidad());
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println("Ingrese el nombre del local");
                                        eleccion = sc.nextLine();
                                        sc.nextLine();
                                        int cantidad = 0;
                                        for (Tienda i : localesValidos) {
                                            if (i.getNombre().equalsIgnoreCase(eleccion)) {
                                                while (true) {
                                                    try {
                                                        imprimirSeparador();
                                                        System.out.println("Ingrese la cantidad");
                                                        cantidad = sc.nextInt();
                                                        localOrigen = i;
                                                    } catch (Exception e) {
                                                        imprimirSeparador();
                                                        System.out.println("\n### ERROR ###");
                                                        System.out.println("El valor debe ser numerico");
                                                        sc.nextLine();
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
                                                                sc.nextLine();
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
                                            eleccion = sc.nextLine();
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
                                                decision = sc.nextInt();
                                                sc.nextLine();
                                                break;
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                sc.nextLine();
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
                                    for (Producto i : local.getInventario()) {
                                        if (i.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                            lista.add(i);
                                        }
                                    }
                                    Producto.ordenar(lista, "alfabetico");
                                    if (lista.isEmpty()) {
                                        imprimirSeparador();
                                        System.out.println("No hay productos con prioridad baja en este local.");
                                        sc.nextLine();
                                        break;
                                    } else {
                                        for (Producto j : lista) {
                                            System.out.println("Nombre: " + j.getNombre() + " | Prioridad: " + j.getPrioridad() + " | ID: " + j.getCodigo());
                                        }
                                    }
                                    try {
                                        imprimirSeparador();
                                        System.out.println("Ingresa el ID del producto");
                                        decision = sc.nextInt();
                                        sc.nextLine();
                                    } catch (Exception e) {
                                        imprimirSeparador();
                                        System.out.println("\n### ERROR ###");
                                        System.out.println("El valor debe ser numerico");
                                        sc.nextLine();
                                        continue;
                                    }
                                    boolean esTrue = false;
                                    int indice = 0;
                                    ArrayList<Tienda> localesValidos = new ArrayList<>();
                                    for (Producto i : lista) {
                                        if (i.getCodigo() == decision) {
                                            esTrue = true;
                                            indice = lista.indexOf(i);
                                            break;
                                        }
                                    }
                                    if (esTrue) {
                                        for (Tienda t : Tienda.getLocales()) {//recorrer los locales en busca del producto
                                            for (Producto p : t.getInventario()) {
                                                if (lista.get(indice).getNombre().equalsIgnoreCase(p.getNombre()) && t != local) {
                                                    if (p.getPrioridad().equalsIgnoreCase("prioridad alta") || p.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                                        localesValidos.add(t);
                                                    }
                                                }
                                            }
                                        }
                                        if (localesValidos.isEmpty()) {
                                            imprimirSeparador();
                                            System.out.println("No se encontro necesidad del producto en ningun local.");
                                            sc.nextLine();
                                            continue;
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
                                                if (lista.get(indice).getNombre().equalsIgnoreCase(p.getNombre())) {
                                                    if (p.getPrioridad().equalsIgnoreCase("prioridad alta") || p.getPrioridad().equalsIgnoreCase("prioridad muy alta")) {
                                                        imprimirSeparador();
                                                        System.out.println("•" + t.getNombre() + "| cantidad: " + p.getCantidad() + " | Prioridad: " + p.getPrioridad());
                                                        sc.nextLine();
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println("Ingrese el nombre del local");
                                        eleccion = sc.nextLine();
                                        sc.nextLine();
                                        int cantidad = 0;
                                        for (Tienda i : localesValidos) {
                                            if (i.getNombre().equalsIgnoreCase(eleccion)) {
                                                while (true) {
                                                    try {
                                                        imprimirSeparador();
                                                        System.out.println("Ingrese la cantidad del producto");
                                                        cantidad = sc.nextInt();

                                                        localDestino = i;
                                                    } catch (Exception e) {
                                                        imprimirSeparador();
                                                        System.out.println("\n### ERROR ###");
                                                        System.out.println("El valor debe ser numerico");
                                                        sc.nextLine();
                                                        continue;
                                                    }
                                                    for (Producto p : i.getInventario()) {
                                                        if (p.getNombre().equalsIgnoreCase(lista.get(indice).getNombre())) {
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
                                                                sc.nextLine();
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
                                            eleccion = sc.nextLine();
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
                                                decision = sc.nextInt();
                                                sc.nextLine();
                                                break;
                                            } catch (Exception e) {
                                                imprimirSeparador();
                                                System.out.println("\n### ERROR ###");
                                                System.out.println("El valor debe ser numerico");
                                                sc.nextLine();
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
            nombre = sc.nextLine();
            sc.nextLine();
            if (!plataformas.contains(nombre)) {
                System.out.println("La plataforma ingresada no es valida");
                sc.nextLine();
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
                    opcion = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
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
                sc.nextLine();
                continue;
            }
            imprimirSeparador();
            for (Tienda t : Tienda.getLocales()) {
                for (Producto i : t.getInventario()) {
                    if (i.getNombre().equalsIgnoreCase(producto.getNombre())) {
                        existe = true;
                        cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                        if (cantidad < 0) {
                            cantidad = 0;
                        }
                        System.out.println("•Local: " + t.getNombre() + " | Cantidad disponible: " + cantidad);
                    } else if (Tienda.getLocales().indexOf(t) == Tienda.getLocales().size() - 1 && t.getInventario().indexOf(i) == t.getInventario().size() - 1 && !existe) {
                        System.out.println("No se han encontrado productos en otros locales");
                        sc.nextLine();
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
                nombre = sc.nextLine();
                sc.nextLine();
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
                    cantidad = sc.nextInt();
                    sc.nextLine();
                    if (cantidad > producto.getCantidadInicial() * 0.4) {
                        imprimirSeparador();
                        System.out.println("La cantidad ingresada es superior a la permitida");
                        System.out.println("Cantidad maxima permitida: " + (int) (producto.getCantidadInicial() * 0.4));
                        sc.nextLine();
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Desea \n1.Reabastecer otro producto \n2.Crear orden de reabastecimiento \n3.Cancelar todo");
                    opcion = sc.nextInt();
                    sc.nextLine();
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
                    sc.nextLine();

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
                    generos.add(i.getPlataforma());
                } else if (!generos.contains(i.getPlataforma())) {
                    generos.add(i.getPlataforma());
                }
            }
            imprimirSeparador();
            for (String palabra : generos) {
                System.out.println("•" + palabra);
            }
            System.out.println("Ingrese el genero a elegir: ");
            nombre = sc.nextLine();
            sc.nextLine();
            if (!generos.contains(nombre)) {
                System.out.println("La plataforma ingresada no es valida");
                sc.nextLine();
                continue;
            }
            imprimirSeparador();
            for (Juego i : p) {
                if (i.getPlataforma().equalsIgnoreCase(nombre)) {
                    System.out.println("•" + i.getNombre() + " | COD: " + i.getCodigo() + " | Genero: " + i.getGenero());
                }
            }

            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Ingrese el codigo del producto: ");
                    opcion = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            for (Juego i : p) {
                if (i.getCodigo() == opcion) {
                    producto = i;
                    break;
                }
            }
            imprimirSeparador();
            for (Tienda t : Tienda.getLocales()) {
                for (Producto i : t.getInventario()) {
                    if (i.getNombre().equalsIgnoreCase(producto.getNombre())) {
                        existe = true;
                        cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                        if (cantidad < 0) {
                            cantidad = 0;
                        }
                        System.out.println("•Local: " + t.getNombre() + " | Cantidad disponible: " + cantidad);
                    } else if (Tienda.getLocales().indexOf(t) == Tienda.getLocales().size() - 1 && t.getInventario().indexOf(i) == t.getInventario().size() - 1 && !existe) {
                        System.out.println("No se han encontrado productos en otros locales");
                        sc.nextLine();
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
                nombre = sc.nextLine();
                sc.nextLine();
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
                    cantidad = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Desea \n1.Reabastecer otro producto \n2.Crear orden de reabastecimiento \n3.Cancelar todo");
                    opcion = sc.nextInt();
                    sc.nextLine();
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
                    sc.nextLine();
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
                rango1 = sc.nextInt();
                sc.nextLine();
                System.out.println("Valor final: ");
                rango2 = sc.nextInt();
                sc.nextLine();
                if (rango1 > rango2) {
                    System.out.println("El rango de precio es ilogico");
                    sc.nextLine();
                    continue;
                }
                break;
            } catch (Exception e) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("El valor debe ser numerico");
                sc.nextLine();
            }
        }
        for (Producto i : p) {
            if (i.getValor() >= rango1 || i.getValor() <= rango2) {
                lista.add(i);
            }
        }
        producto = Funcionalidad1.seleccionarProducto(lista);
        while (true) {
            imprimirSeparador();
            for (Tienda t : Tienda.getLocales()) {
                for (Producto i : t.getInventario()) {
                    if (i.getNombre().equalsIgnoreCase(producto.getNombre())) {
                        existe = true;
                        cantidad = i.getCantidad() - (int) (i.getCantidadInicial() * 0.4);
                        if (cantidad < 0) {
                            cantidad = 0;
                        }
                        System.out.println("•Local: " + t.getNombre() + " | Cantidad disponible: " + cantidad);
                    } else if (Tienda.getLocales().indexOf(t) == Tienda.getLocales().size() - 1 && t.getInventario().indexOf(i) == t.getInventario().size() - 1 && !existe) {
                        System.out.println("No se han encontrado productos en otros locales");
                        sc.nextLine();
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
                nombre = sc.nextLine();
                sc.nextLine();
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
                    cantidad = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Desea \n1.Reabastecer otro producto \n2.Crear orden de reabastecimiento \n3.Cancelar todo");
                    opcion = sc.nextInt();
                    sc.nextLine();
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
                    sc.nextLine();
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
                sc.nextLine();
                if (tipo == 1 || tipo == 2 || tipo == 3) {
                    break;
                } else if (tipo == 4) {
                    return;
                } else {
                    imprimirSeparador();
                    System.out.println("Opcion no valida");
                    sc.nextLine();
                }
            }
            String nombre;
            while (true) {
                imprimirSeparador();
                System.out.println("Ingrese el nombre del producto: ");
                nombre = scDio.nextLine();
                sc.nextLine();
                if (nombre.isEmpty()) {
                    imprimirSeparador();
                    System.out.println("El nombre no puede estar vacio");
                    sc.nextLine();
                    continue;
                }
                break;
            }
            int valor;
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Ingrese el valor del producto: ");
                    valor = scDio.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            int cantidadInicial;//sera la cantidadInicial y cantidad
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Ingrese la cantidad del producto: ");
                    cantidadInicial = scDio.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            boolean prestable;
            while (true) {
                imprimirSeparador();
                System.out.println("Es prestable?");
                System.out.println("1.Si");
                System.out.println("2.No");
                int opcion = scDio.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    prestable = true;
                    break;
                } else if (opcion == 2) {
                    prestable = false;
                    break;
                } else {
                    imprimirSeparador();
                    System.out.println("Opcion no valida");
                    sc.nextLine();
                }
            }
            byte condicion;
            while (true) {
                imprimirSeparador();
                System.out.println("Ingrese la condicion del producto(1-4: usados  5:nuevo)");
                condicion = scDio.nextByte();
                sc.nextLine();
                if (condicion == 1 || condicion == 2 || condicion == 3 || condicion == 4 || condicion == 5) {
                    break;
                } else {
                    imprimirSeparador();
                    System.out.println("Opcion no valida");
                    sc.nextLine();
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
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            int puntosRequeridos;
            while (true) {
                try {
                    imprimirSeparador();
                    System.out.println("Ingrese los puntos requeridos para adquirir el producto: ");
                    puntosRequeridos = scDio.nextInt();
                    sc.nextLine();
                    break;
                } catch (Exception e) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("El valor debe ser numerico");
                    sc.nextLine();
                }
            }
            if (tipo == 1) {
                String genero;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese el genero del juego: ");
                    genero = scDio.nextLine();
                    sc.nextLine();
                    if (genero.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("El genero no puede estar vacio");
                        sc.nextLine();
                        continue;
                    }
                    break;
                }
                String plataforma;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese la plataforma del juego: ");
                    plataforma = scDio.nextLine();
                    sc.nextLine();
                    if (plataforma.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("La plataforma no puede estar vacia");
                        sc.nextLine();
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
                    sc.nextLine();
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
                    marca = scDio.nextLine();
                    sc.nextLine();
                    if (marca.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("La marca no puede estar vacio");
                        sc.nextLine();
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
                    sc.nextLine();
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
                    marca = scDio.nextLine();
                    sc.nextLine();
                    if (marca.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("La marca no puede estar vacio");
                        sc.nextLine();
                        continue;
                    }
                    break;
                }
                String consola;
                while (true) {
                    imprimirSeparador();
                    System.out.println("Ingrese la consola del accesorio: ");
                    consola = scDio.nextLine();
                    sc.nextLine();
                    if (consola.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("La consola no puede estar vacia");
                        sc.nextLine();
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
                    sc.nextLine();
                } else {
                    Accesorio nuevo = new Accesorio(nombre, valor, cantidadInicial, cantidadInicial,prestable, condicion, fechaLanzamiento, descuento, puntosRequeridos, marca, consola);
                    local.agregarProducto(nuevo);
                    return;
                }

            }
        }
    }
}