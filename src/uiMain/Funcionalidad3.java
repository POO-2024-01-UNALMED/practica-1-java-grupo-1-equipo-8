package uiMain;

import java.security.PrivilegedAction;
import java.sql.Array;
import java.sql.ClientInfoStatus;
import java.util.*;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.*;
import gestorAplicacion.productos.*;

import static uiMain.Main.*;


public class Funcionalidad3 {
    static Scanner sc = new Scanner(System.in);

    public static void revisarInventario(Tienda local, Fecha fechaActual){

        /* ~~~ Selección de las opciones ~~~ */
        byte opcion=0;
        do{
            imprimirSeparador();

            System.out.println("1. Revisar los productos del local");
            System.out.println("2. Modificar la información de algún producto");
            System.out.println("3. Calcular la prioridad de estos");
            System.out.println("4. Salir");
            //Evitar un error al ingresar un dato no numerico
            try{
                opcion = sc.nextByte();
                sc.nextLine(); // Limpiar buffer

            } catch (Exception e){
                System.out.println("\n### ERROR ###");
                System.out.println("Se debe ingresar un valor numerico válido.");
                sc.nextLine(); //Limpiar el buffer
                sc.nextLine();//Esperar que el usuario presione Enter

                continue;
            }
            byte opcion2 = 0; //opcion para decidir en cada caso y para no confundirse con opcion

            switch (opcion){
                case 1:
                    //Revisar productos
                    Scanner scOpcion2 = new Scanner(System.in);
                    Scanner scTipo = new Scanner(System.in);

                    do{
                        imprimirSeparador();

                        System.out.println("1. Revisar por tipo de producto.");
                        System.out.println("2. Revisar todos los productos en la tienda.");
                        System.out.println("3. Regresar.");

                        try{//Impedir que haya un error si se ingresa un valor no numerico
                            opcion2 = scOpcion2.nextByte();
                        }catch (Exception e){
                            System.out.println("\n### ERROR ###");
                            System.out.println("Se debe ingresar un valor numerico válido.");
                            sc.nextLine(); //Limpiar el buffer
                            sc.nextLine();//Esperar que el usuario presione Enter

                            continue;
                        }
                        ArrayList<Producto> lista = new ArrayList<>();
                        switch (opcion2) {
                            case 1:
                                //Revisar por tipo de producto
                                imprimirSeparador();
                                byte tipo;// tipo a elegir

                                try {//impedir un error al elegir el tipo
                                    System.out.println("1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones
                                    tipo = scTipo.nextByte();
                                } catch (Exception e) {
                                    imprimirSeparador();

                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");
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
                                            System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta());
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

                                for(Producto i : lista) {
                                    System.out.println("* ID: " + i.getCodigo() + " | NOMBRE: " + i.getNombre() + " | PRECIO: $" + i.getValor() + " | CANTIDAD: " + i.getCantidad() + " | VENTAS: " + i.calcularVenta());
                                }
                                break;
                        }
                    }while(opcion2 != 3);
                case 2:
                    //Modificar la información de algún producto
                    byte tipo;
                    ArrayList<Producto> lista = new ArrayList<Producto>();
                    try{//impedir un error al elegir el tipo
                        System.out.println("Ingresa el tipo de producto que modificarás\n1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                        tipo = sc.nextByte();
                    }catch (Exception e){
                        imprimirSeparador();

                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");
                        sc.nextLine();
                        continue;
                    }
                    if (tipo == 1){
                        for (Producto i:local.getInventario()){
                            if (i instanceof Accesorio){
                                lista.add(i);
                            }
                        }
                    } else if (tipo == 2) {
                        for (Producto i:local.getInventario()){
                            if (i instanceof Consola){
                                lista.add(i);
                            }
                        }
                    }else if(tipo == 3) {
                        for (Producto i:local.getInventario()){
                            if (i instanceof Juego){
                                lista.add(i);
                            }
                        }
                    }else {
                        break;
                    }
                    for(Producto i:lista){
                        System.out.println(i.getNombre()+"| Código: "+i.getCodigo());
                    }

                    int cod;//Codigo a elegir
                    int index = 0;//Posición del objeto buscado

                    try{
                        imprimirSeparador();
                        System.out.println("Ingrese el codigo del objeto a modificar");

                        sc.nextLine();
                        cod = sc.nextInt();

                    }catch (Exception e){
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("El valor debe ser numerico");

                        continue;
                    }
                    boolean existe = false;

                    for (Producto i : lista){
                        if (i.getCodigo() == cod){
                            index = lista.indexOf(i);
                            existe = true;
                        }
                    }

                    if (!existe){
                        imprimirSeparador();
                        System.out.println("El código ingresado no existe");

                        continue;
                    }
                    imprimirSeparador();
                    System.out.println("Que desea cambiar \n1.Nombre \n2.Precio \n3.Salir");
                    do{
                        try{
                            sc.nextLine();
                            tipo = sc.nextByte();

                        }catch (Exception e){
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor debe ser numerico");
                            tipo = 0;
                            sc.nextLine();
                        }
                        switch (tipo){
                            case 1:
                                imprimirSeparador();
                                System.out.println("Ingrese el nuevo nombre");
                                sc.nextLine();
                                String nuevoNombre = sc.nextLine();
                                local.getInventario().get(local.getInventario().indexOf(lista.get(index))).setNombre(nuevoNombre);

                                break;

                            case 2:
                                imprimirSeparador();
                                System.out.println("Ingrese el nuevo precio");
                                int nuevoPrecio = 0;
                                try{
                                    sc.nextLine();
                                    nuevoPrecio = sc.nextInt();
                                }catch (Exception e){
                                    imprimirSeparador();
                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");

                                    continue;
                                }
                                local.getInventario().get(local.getInventario().indexOf(lista.get(index))).setValor(nuevoPrecio);
                                break;

                            default:
                                break;

                        }
                        break;

                    }while(tipo == 0);

                case 3:
                    //Calcular prioridad de productos
                    for (Producto i: local.getInventario()){
                        if (i.getPrioridad() == null){
                            if(i.calcularVenta() > i.getCantidadInicial()*0.8){
                                i.setPrioridad("Prioridad muy alta");
                            } else if (i.calcularVenta() >= i.getCantidadInicial()*0.51) {
                                i.setPrioridad("Prioridad alta");
                            } else if (i.calcularVenta() >= i.getCantidadInicial()*0.21) {
                                i.setPrioridad("Prioridad media");
                            }else{
                                i.setPrioridad("Prioridad baja");
                            }
                        }
                    }
                    analizarMercado(local, fechaActual);
            }
        }while(opcion != 4);


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
                System.out.println("\n### ERROR ###");
                System.out.println("Valor no válido. Debe ingresar una de las opciones: Alfabetico, Ventas o Precio.");
            }
        } while (!esValido);

        return palabra;
    }

    private static void analizarMercado(Tienda local, Fecha fechaActual){
        byte opcion = 0;
        do{
            try{
                imprimirSeparador();
                System.out.println("Desea \n1.Hacer análisis de mercado \n2.Revisar prioridad de cada producto \n3.Regresar");

                sc.nextLine();
                opcion = sc.nextByte();

            }catch (Exception e){
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("El valor debe ser numerico");

            }
            switch (opcion) {
                case 1:
                    //Hacer análisis de mercado
                    System.out.println("Ingresa la fecha inicial");
                    sc.nextLine();//Limpiar buffer

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
                        if (i.getFecha().getTotalDias() > fechaInicial.getTotalDias() && i.getFecha().getTotalDias() < fechaFinal.getTotalDias()) {//Verificar que la fecha de transaccion esté dentro del rango de tiempo
                            rangoVentas.add(i);
                        }
                    }

                    if (fechaFinal.getTotalDias() < fechaInicial.getTotalDias()) {
                        imprimirSeparador();
                        System.out.println("La fecha final está antes de la final");
                        sc.nextLine();
                        continue;
                    } else if (rangoVentas.isEmpty()) {
                        imprimirSeparador();
                        System.out.println("No hay ventas dentro del rango");
                        sc.nextLine();
                    }
                    while(true){
                        try{
                            imprimirSeparador();
                            System.out.println("Desea \n1.Ver ventas individuales \n2.Órdenes en este rango \n3.Tendencias en este rango \n4.Proceder al reabastecimiento \n5.Regresar");
                            //TODO:encontrar una manera de regresar al momento de ingresar la fecha
                            sc.nextLine();
                            opcion = sc.nextByte();

                        }catch (Exception e){
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor debe ser numerico");
                            sc.nextLine();
                        }
                        switch (opcion){
                            case 1:
                                //Ver ventas individuales

                            case 2:
                                //Ordenes en el rango
                            case 3:
                                //Tendencias en el rango
                                while (true){
                                    try{
                                        imprimirSeparador();
                                        System.out.println("Desea ver \n1.Géneros \n2.Plataformas \n3.Rangos de precio más vendidos \n4.Cambiar fecha \n5.Regresar");
                                        sc.nextLine();
                                        opcion = sc.nextByte();

                                    }catch (Exception e){
                                        imprimirSeparador();
                                        System.out.println("\n### ERROR ###");
                                        System.out.println("El valor debe ser numerico");
                                        sc.nextLine();
                                        continue;
                                    }
                                    switch (opcion){
                                        case 1:
                                            //Tendencias de generos
                                            int media = 0;
                                            for (Transaccion i : rangoVentas){
                                                //TODO:Como dar el resultado de cada genero, por separado
                                            }
                                        case 2:
                                            //Tendencias de plataformas
                                            //TODO:Como dar el resultado de cada plataforma, por separado

                                        case 3:
                                            //Tendencias de rangos mas vendidos
                                            int rango1_20 = 0;
                                            int rango21_40 = 0;
                                            int rango41_60 = 0;
                                            for (Producto i : local.getInventario()){
                                                if (i.getValor() <= 20 && i.getValor()>=1){
                                                    rango1_20 += i.calcularVenta();
                                                } else if (i.getValor() <= 40 && i.getValor()>=21){
                                                    rango21_40 += i.calcularVenta();
                                                }else if (i.getValor() <= 60 && i.getValor()>=41) {
                                                    rango41_60 += i.calcularVenta();
                                                }
                                            }
                                            if (rango1_20 >= rango21_40 && rango1_20 >= rango41_60){
                                                System.out.println("Rango 1-20 \nVentas: "+rango1_20);
                                                if (rango21_40 >= rango41_60){
                                                    System.out.println("Rango 21-40 \nVentas: "+rango21_40);
                                                    System.out.println("Rango 41-60 \nVentas: "+rango41_60);
                                                    sc.nextLine();
                                                }else{
                                                    System.out.println("Rango 41-60 \nVentas: "+rango41_60);
                                                    System.out.println("Rango 21-40 \nVentas: "+rango21_40);
                                                    sc.nextLine();
                                                }
                                            } else if (rango21_40 >= rango1_20 && rango21_40 >=rango41_60) {
                                                System.out.println("Rango 21-40 \nVentas: "+rango21_40);
                                                if (rango1_20 >=rango41_60 ){
                                                    System.out.println("Rango 1-20 \nVentas: "+rango1_20);
                                                    System.out.println("Rango 41-60 \nVentas: "+rango41_60);
                                                    sc.nextLine();
                                                }else{
                                                    System.out.println("Rango 41-60 \nVentas: "+rango41_60);
                                                    System.out.println("Rango 1-20 \nVentas: "+rango1_20);
                                                    sc.nextLine();
                                                }
                                            } else{
                                                System.out.println("Rango 41-60 \nVentas: "+rango41_60);
                                                if (rango1_20 >= rango21_40){
                                                    System.out.println("Rango 1-20 \nVentas: "+rango1_20);
                                                    System.out.println("Rango 21-40 \nVentas: "+rango21_40);
                                                    sc.nextLine();
                                                }else{
                                                    System.out.println("Rango 21-40 \nVentas: "+rango21_40);
                                                    System.out.println("Rango 1-20 \nVentas: "+rango1_20);
                                                    sc.nextLine();
                                                }
                                            }
                                            //TODO:comparar la tendencia en otros locales, revisar ventas de un rango o salir
                                        case 4:
                                            //Cambiar fecha?

                                        case 5:
                                            //Regresar
                                            opcion =0;
                                            break;
                                    }
                                    System.out.println("El valor ingresado no es valido");
                                }
                            case 4:
                                //Proceder con el reabastecimiento
                                    //hacer reabastecimiento
                            case 5://salir
                                break;
                        }
                        System.out.println("La opción no es válida");
                    }

                case 2:

                    //Revisar prioridad
                    String orden = "prioridad";
                    String decision = "";
                    while(true) {
                        imprimirSeparador();
                        System.out.println("Desea ver los productos agrupados por tipo(No ponga tildes) \n•Si \n•No \n• Salir");

                        sc.nextLine();
                        decision = sc.nextLine();
                        if(decision.equalsIgnoreCase("si")){
                            byte tipo;
                            ArrayList<Producto> lista = new ArrayList<Producto>();

                            try{//impedir un error al elegir el tipo
                                System.out.println("Ingresa el tipo de producto que modificarás\n1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                tipo = sc.nextByte();
                            }catch (Exception e){//Informar de un error
                                imprimirSeparador();

                                System.out.println("\n### ERROR ###");
                                System.out.println("El valor debe ser numerico");
                                sc.nextLine();
                                continue;
                            }

                            if (tipo == 1){
                                for (Producto i:local.getInventario()){
                                    if (i instanceof Accesorio){
                                        lista.add(i);
                                    }
                                }
                            } else if (tipo == 2) {
                                for (Producto i:local.getInventario()){
                                    if (i instanceof Consola){
                                        lista.add(i);
                                    }
                                }
                            }else if(tipo == 3) {
                                for (Producto i:local.getInventario()){
                                    if (i instanceof Juego){
                                        lista.add(i);
                                    }
                                }
                            }else {
                                break;
                            }
                            for(Producto i: lista){
                                System.out.println("Nombre: "+i.getNombre()+" | Prioridad: "+i.getPrioridad());
                            }
                            break;

                        } else if (decision.equalsIgnoreCase("no")) {//No ver por tipo de producto
                            while (true){
                                imprimirSeparador();
                                System.out.println("Desea ver los productos por prioridad:\n•Muy alta \n•Alta \n•Baja \n•Todos");
                                decision = sc.nextLine();

                                if (decision.equalsIgnoreCase("muy alta")){
                                    for (Producto i: local.getInventario()){
                                        if (i.getPrioridad().equalsIgnoreCase("prioridad muy alta")){
                                            System.out.println("Nombre: "+i.getNombre()+" | Prioridad: "+i.getPrioridad());
                                        }
                                    }
                                    break;

                                } else if (decision.equalsIgnoreCase("alta")) {
                                    for (Producto i: local.getInventario()){
                                        if (i.getPrioridad().equalsIgnoreCase("prioridad alta")){
                                            System.out.println("Nombre: "+i.getNombre()+" | Prioridad: "+i.getPrioridad());
                                        }
                                    }
                                    break;

                                }else if(decision.equalsIgnoreCase("baja")){
                                    for (Producto i: local.getInventario()){
                                        if (i.getPrioridad().equalsIgnoreCase("prioridad baja")){
                                            System.out.println("Nombre: "+i.getNombre()+" | Prioridad: "+i.getPrioridad());
                                        }
                                    }
                                    break;

                                } else if (decision.equalsIgnoreCase("todos")) {
                                    ArrayList<Producto> lista = new ArrayList<Producto>();
                                    Collections.copy(lista,local.getInventario());
                                    Producto.ordenar(lista,orden);

                                    for (Producto i: lista){
                                        System.out.println("Nombre: "+i.getNombre()+" | Prioridad: "+i.getPrioridad());
                                    }
                                    break;
                                }else{
                                    System.out.println("La desición ingresada no existe");
                                    sc.nextLine();
                                }

                            }
                        } else if (decision.equalsIgnoreCase("salir")) {
                            break;

                        }else{
                            System.out.println("La opcion ingresada no es valida");
                        }
                    }

                case 3:
                    break;
            }
        }while(opcion < 3);
    }
    private static void hacerReabastecimiento(Tienda local,Fecha fechaActual){
        byte opcion = 0;
        while (true){
            try{
                imprimirSeparador();
                System.out.println("Desea \n1.Reabastecer manualmente \n2.En base a la prioridad\n3.Entre locales\n4.Salir");
                sc.nextLine();
                opcion = sc.nextByte();

            }catch (Exception e){
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("El valor debe ser numerico");
                sc.nextLine();
                continue;
            }
            switch (opcion){
                case 1://manualmente
                    while(true){
                        try{
                            imprimirSeparador();
                            System.out.println("Desea filtrar \n1.Genero \n2.Plataforma\n3.Rango de precio ");
                            sc.nextLine();
                            opcion = sc.nextByte();

                        }catch (Exception e) {
                            imprimirSeparador();
                            System.out.println("\n### ERROR ###");
                            System.out.println("El valor debe ser numerico");
                            sc.nextLine();
                            continue;
                        }
                        switch (opcion){
                            case 1://Genero
                                //TODO:no entiendo
                            case 2://Plataforma

                            case 3://Rango

                        }
                        System.out.println("La opción no es válida");
                    }
                case 2://Prioridad
                    while (true){
                        try {
                            System.out.println("Desea ver \n1.Alta\n2.Baja\n3.Regresar");
                            sc.nextLine();
                            opcion = sc.nextByte();

                        }catch (Exception e){
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
                                        sc.nextLine();
                                        decision = sc.nextInt();
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
                                            System.out.println("No se encontró el producto disponible en ningún local.");
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
                                                    if (p.getPrioridad().equalsIgnoreCase("prioridad media") || p.getPrioridad().equalsIgnoreCase("prioridad baja")) {
                                                        System.out.println("•" + t.getNombre() + "| cantidad: " + p.getCantidad());
                                                    }
                                                }
                                            }
                                        }
                                        System.out.println("Ingrese el nombre del local");
                                        eleccion = sc.nextLine();
                                        sc.nextLine();
                                        double cantidad = 0;
                                        for (Tienda i : localesValidos) {
                                            if (i.getNombre().equalsIgnoreCase(eleccion)) {
                                                while (true) {
                                                    try {
                                                        imprimirSeparador();
                                                        System.out.println("Ingrese la cantidad");
                                                        cantidad = sc.nextInt();
                                                        cantidad = (int) cantidad;
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
                                                            if (p.getCantidad() - cantidad >= p.getCantidadInicial() * 0.4) {
                                                                //TODO:Encontrar la manera de clonar el obeto y agregarlo a la lista, ademas restar la cantidad enviada al objeto dentro del local y darle ese valor al objeto dentro de la listaDeObjetos
                                                                listaDeObjetos.add(p);
                                                            } else {
                                                                if (p.getCantidad() - p.getCantidadInicial() * 0.4 >= 0) {
                                                                    cantidad = p.getCantidad() - p.getCantidadInicial() * 0.4;
                                                                    cantidad = (int) cantidad;
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
                                                System.out.println("Desea \n1.Añadir otro producto \n2.Crear rebastecimiento");
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
                                            local.agregarOrden(new Reabastecimiento(localDestino, local, new Fecha(fechaActual.getTotalDias() + 30), listaDeObjetos));
                                            break;
                                        }

                                    }
                                }
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
                                        continue;
                                    } else {
                                        for (Producto j : lista) {
                                            System.out.println("Nombre: " + j.getNombre() + " | Prioridad: " + j.getPrioridad() + " | ID: " + j.getCodigo());
                                        }
                                    }
                                    try {
                                        imprimirSeparador();
                                        System.out.println("Ingresa el ID del producto");
                                        sc.nextLine();
                                        decision = sc.nextInt();
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
                                            System.out.println("No se encontró necesidad del producto en ningún local.");
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
                                        double cantidad = 0;
                                        for (Tienda i : localesValidos) {
                                            if (i.getNombre().equalsIgnoreCase(eleccion)) {
                                                while (true) {
                                                    try {
                                                        imprimirSeparador();
                                                        System.out.println("Ingrese la cantidad del producto");
                                                        cantidad = sc.nextInt();
                                                        cantidad = (int) cantidad;
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
                                                                //TODO:Encontrar la manera de clonar el obeto y agregarlo a la lista, ademas restar la cantidad enviada al objeto dentro del local y darle ese valor al objeto dentro de la listaDeObjetos
                                                                listaDeObjetos.add(lista.get(indice));
                                                            } else {
                                                                if (local.getInventario().get(indice).getCantidad() - local.getInventario().get(indice).getCantidadInicial() * 0.4 >= 0) {
                                                                    cantidad = local.getInventario().get(indice).getCantidad() - local.getInventario().get(indice).getCantidadInicial() * 0.4;
                                                                    cantidad = (int) cantidad;
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
                                                System.out.println("Desea \n1.Añadir otro producto \n2.Crear rebastecimiento");
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
                                        }

                                    }
                                }
                            case 3://salir
                                opcion = 0;
                                break;
                        }
                    }
                case 3:
                    break;
            }
        }
    }

    // Método que retorna el género más comprado en un local ingresado
    // El resultado será null si la tienda no tiene transacciones
    private static String generoMasComprado(Tienda local) {
        ArrayList<Integer> generosCant = new ArrayList<Integer>();
        ArrayList<String> generos = new ArrayList<String>();

        for (Transaccion t : local.getCaja()) { // Buscar en cada transacción de la tienda
            for (Producto p : t.getProductos()) { // Buscar cada producto de la transacción
                if (p instanceof Juego) {
                    Juego j = (Juego) p;
                    if (generos.contains(j.getGenero())) { // Si el género ya está en la lista, aumentar la cantidad
                        int indice = generos.indexOf(j.getGenero());
                        generosCant.set(indice, generosCant.get(indice) + 1);
                    } else { // Si no está, agregarlo a la lista
                        generos.add(j.getGenero());
                        generosCant.add(1);
                    }
                }
            }
        }
        // Encontrar el género más vendido
        int max = 0;
        String generoFav = "Ninguno. La tienda no ha vendido juegos";
        // Este es el mensaje por defecto del género favorito que se preservará sólo en caso
        // que la tienda no tenga transacciones


        for (int i = 0; i < generosCant.size(); i++) {
            if (generosCant.get(i) > max) {
                max = generosCant.get(i);
                generoFav = generos.get(i);
            }
        }

        return generoFav;
    }

    // Método que muestra los 5 géneros más comprados y la cantidad de ventas
    private static void mostrarGeneros(Tienda local) {
        // Comprobar que la tienda tiene transacciones
        if (local.getCaja().isEmpty()) {
            System.out.println("No hay transacciones en la tienda");
            return;
        }

        ArrayList<Integer> generosCant = new ArrayList<Integer>();
        ArrayList<String> generos = new ArrayList<String>();

        for (Transaccion t : local.getCaja()) { // Buscar en cada transacción de la tienda
            for (Producto p : t.getProductos()) { // Buscar cada producto de la transacción
                if (p instanceof Juego) {
                    Juego j = (Juego) p;
                    if (generos.contains(j.getGenero())) { // Si el género ya está en la lista, aumentar la cantidad
                        int indice = generos.indexOf(j.getGenero());
                        generosCant.set(indice, generosCant.get(indice) + 1);
                    } else { // Si no está, agregarlo a la lista
                        generos.add(j.getGenero());
                        generosCant.add(1);
                    }
                }
            }
        }
        // Encontrar e imprimir los 5 géneros más vendidos y sus cantidades
        for (int j = 0 ; j < 5 ; j++) {
            int max = 0;
            String generoFav = "";

            for (int i = 0; i < generosCant.size(); i++) {
                if (generosCant.get(i) > max) {
                    max = generosCant.get(i);
                    generoFav = generos.get(i);
                }
            }

            System.out.println(j + ". " + generoFav + " -- " + max + " ventas");

            generosCant.remove(generos.indexOf(generoFav));
            generos.remove(generoFav);
        }
    }
}

