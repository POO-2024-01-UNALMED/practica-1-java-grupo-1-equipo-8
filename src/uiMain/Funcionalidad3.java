package uiMain;

import java.util.*;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.manejoLocal.*;
import gestorAplicacion.productos.Accesorio;
import gestorAplicacion.productos.Producto;

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
                        switch (opcion2) {
                            case 1:
                                //Revisar por tipo de producto
                                imprimirSeparador();
                                byte tipo = 0;// tipo a elegir

                                try{//impedir un error al elegir el tipo
                                    System.out.println("1. Accesorio \n2. Consola \n3. Juego \n4. Regresar");//Se muestran las opciones

                                    tipo = sc.nextByte();
                                }catch (Exception e){
                                    imprimirSeparador();

                                    System.out.println("\n### ERROR ###");
                                    System.out.println("El valor debe ser numerico");
                                    continue;
                                    }
                                String orden = null;
                                ArrayList<Producto> nuevaLista = new ArrayList<Producto>();
                                switch (tipo){
                                    case 1:
                                        orden = elegirOrden();
                                        //Se eligio revisar por tipo Accesorio
                                        for (Producto i : local.getInventario()){
                                            if (i instanceof Accesorio){
                                                nuevaLista.add(i);
                                            }
                                        }
                                        Producto.Ordenar(nuevaLista, orden);

                                    case 2:
                                        orden = elegirOrden();
                                        //Se eligio revisar por tipo Consola

                                    case 3:
                                        orden = elegirOrden();
                                        //Se eligio revisar por tipo Juego

                                    default:
                                        continue;
                                    }
                            case 2:
                                //Revisar todos los productos
                        }
                    }while(opcion2 != 3);
                case 2:
                    //Modificar la información de algún producto

                case 3:
                    //Calcular prioridad de productos
            }
        }while(opcion != 4);


    }
    private static String elegirOrden(){
        String orden = null;
        do{
            imprimirSeparador();
            System.out.println("Elija el tipo de orden(no poner tildes) \n• Alfabetico \n• Ventas\n• Precio");
            orden = sc.nextLine();

        }while(0 != orden.compareToIgnoreCase("ventas") && 0!= orden.compareToIgnoreCase("precio") && 0 != orden.compareToIgnoreCase("alfabetico"));
        return orden;
    }

    private static void analizarMercao(){

    }
    private static void hacerReabastecimiento(){

    }
}
