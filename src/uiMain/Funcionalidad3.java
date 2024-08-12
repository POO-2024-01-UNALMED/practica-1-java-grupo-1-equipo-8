package uiMain;

import java.util.*;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.manejoLocal.*;
import gestorAplicacion.productos.*;


public class Funcionalidad3 {
    static Scanner sc = new Scanner(System.in);

    public static void revisarInventario(Tienda local){

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
                        ArrayList<Producto> lista = new ArrayList<Producto>();
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
                    }else{
                        for (Producto i:local.getInventario()){
                            if (i instanceof Juego){
                                lista.add(i);
                            }
                        }
                    }
                    for(Producto i:lista){
                        System.out.println(i.getNombre()+"| Código: "+i.getCodigo());
                    }

                    int cod;//Codigo a elegir
                    byte index;//Posición del objeto buscado

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
                            index = (byte)lista.indexOf(i);
                            existe = true;
                        }
                    }

                    if (!existe){
                        imprimirSeparador();
                        System.out.println("El código ingresado no existe");

                        continue;
                    }

                    
                case 3:
                    //Calcular prioridad de productos
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

    private static void analizarMercao(){

    }
    private static void hacerReabastecimiento(){

    }
}
