package uiMain;

import java.sql.ClientInfoStatus;
import java.util.*;

import static uiMain.Main.imprimirSeparador;
import static uiMain.Main.recibirFecha;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.*;
import gestorAplicacion.productos.*;


public class Funcionalidad3 {
    static Scanner sc = new Scanner(System.in);

    public static void revisarInventario(Tienda local, Fecha fechaActual){

        /* ~~~ Selección de las opciones ~~~ */
        byte opcion=0;
        do{
            imprimirSeparador();

            System.out.println("1. Desea revisar los productos del local");
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
                    do{
                        imprimirSeparador();

                        System.out.println("1. Revisar por tipo de producto.");
                        System.out.println("2. Revisar todos los productos en la tienda.");
                        System.out.println("3. Regresar.");

                        try{//Impedir que haya un error si se ingresa un numero no numerico
                            opcion2 = sc.nextByte();

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

                                try{//impedir un error al elegir el tipo
                                    System.out.println("1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                    tipo = sc.nextByte();
                                }catch (Exception e){
                                    imprimirSeparador();

                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");
                                    continue;
                                    }

                                sc.nextLine();//Limpiar buffer
                                String orden = elegirOrden();

                                switch (tipo){
                                    case 1:

                                        //Se eligio revisar por tipo Accesorio
                                        for (Producto i : local.getInventario()){
                                            if (i instanceof Accesorio){
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista,orden);
                                        for(Producto i : lista) {
                                            System.out.println(i.getNombre()+"| precio:"+i.getValor()+" | ventas: "+i.calcularVenta());
                                        }
                                        break;
                                    case 2:
                                        //Se eligio revisar por tipo Consola
                                        for (Producto i : local.getInventario()){
                                            if (i instanceof Consola){
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista,orden);
                                        for(Producto i : lista) {
                                            System.out.println(i.getNombre()+"| precio:"+i.getValor()+" | ventas: "+i.calcularVenta());
                                        }
                                        break;

                                    case 3:
                                        //Se eligio revisar por tipo Juego
                                        for (Producto i : local.getInventario()){
                                            if (i instanceof Juego){
                                                lista.add(i);
                                            }
                                        }
                                        Producto.ordenar(lista,orden);
                                        for(Producto i : lista) {
                                            System.out.println(i.getNombre()+"| precio:"+i.getValor()+" | ventas: "+i.calcularVenta());
                                        }
                                        break;
                                    default:
                                        break;
                                    }
                            case 2:
                                //~~~~~~~~~~  Revisar todos los productos  ~~~~~~~~~~//
                                //Darle todos los objetos del local a la lista
                                Collections.addAll(lista,local.getInventario().toArray(new Producto[0]));

                                orden = elegirOrden();
                                sc.nextLine();//Limpiar buffer

                                for(Producto i : lista) {
                                    System.out.println(i.getNombre()+"| precio:"+i.getValor()+" | ventas: "+i.calcularVenta());
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
        imprimirSeparador();
        String palabra;
        boolean esValido = false;

        do {
            System.out.println("Elija el tipo de orden (sin tildes): \n• Alfabetico \n• Ventas\n• Precio");
            palabra = sc.nextLine();

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

                    hacerReabastecimiento(local,fechaActual,rangoVentas);
                    continue;
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
    private static void hacerReabastecimiento(Tienda local,Fecha fechaActual,ArrayList<Transaccion> rangoVentas){
        byte opcion = 0;
        do{
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
            }
        }while (opcion < 5);
    }
}
