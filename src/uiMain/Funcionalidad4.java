package uiMain;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Meta;

public class Funcionalidad4 {
    static Scanner sc = new Scanner(System.in);
    public static void inspeccionEmpleado(Tienda local){
        /* ~~~ Identificación del empleado ~~~ */
        Empleado empleado = identificarEmpleado(local);

        /* ~~~ Calcular meta ~~~ */
        Meta meta = identificarMeta(empleado);

    }

    private static Empleado identificarEmpleado(Tienda local){
        imprimirSeparador();

        Empleado empleado = null;
        int cedula = 0;

        //Mostrar empleados de la tienda
        System.out.println("Empleados de la tienda:");

        for (Empleado e : local.getEmpleados()) {
            System.out.println("* Nombre: " + e.getNombre() + " - Cedula: " + e.getCedula());
        }

        while (empleado == null) {
            System.out.println("Ingrese la cédula del empleado");

            // Buscar al empleado en la lista de empleados por su cédula
            try {
                cedula = sc.nextInt();

                for (Empleado e : local.empleados){
                    if (e.getCedula() == cedula){
                        empleado = e;
                        return empleado;
                    }
                    else {
                        cedula = 0;
                    }
                }
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            // En caso de que el empleado no sea encontrado dar la opción de intentar de nuevo
            if (cedula == 0) {
                System.out.println("\n### ERROR ###");
                System.out.println("Empleado no encontrado. ¿Desea intentar de nuevo? (Y/n).\n");
                char decision = 'y';
                decision = sc.next().charAt(0);
                if (decision == 'n' || decision == 'N') {
                    return null;
                }
            }
        }

        return empleado;
    }

    private static Meta identificarMeta(Empleado empleado){
        imprimirSeparador();

        int diaHoy = 0;
        int mesHoy = 0;
        int yearHoy = 0;
        try{
            System.out.println("Ingrese el día en el que estamos: ");
            diaHoy = sc.nextInt();
        } catch (Exception e){
            System.out.println("\n### ERROR ###");
            System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
            sc.nextLine();  // nextLine para limpiar el buffer
            sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
        }

        try{
            System.out.println("Ingrese el mes en el que estamos: ");
            mesHoy = sc.nextInt();
        } catch (Exception e){
            System.out.println("\n### ERROR ###");
            System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
            sc.nextLine();  // nextLine para limpiar el buffer
            sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
        }

        try{
            System.out.println("Ingrese el año en el que estamos: ");
            yearHoy = sc.nextInt();
        } catch (Exception e){
            System.out.println("\n### ERROR ###");
            System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
            sc.nextLine();  // nextLine para limpiar el buffer
            sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
        }


        int codigo = 0;
        Meta meta = null;

        //Mostrar metas del empleado
        System.out.println("Metas del empleado:");
        for (Meta m : empleado.getMetas()) {
            System.out.println("* Codigo: " + m.getCodigo() + " - Fecha Límite: " + m.getDiaLimite()+ "/" + m.getMesLimite() + "/" + m.getYearLimite() + " - Valor a alcanzar: " + m.getValorAlcanzar() + " - Acumulado: " + m.getAcumulado() + " - Estado: " + m.getEstado()) ;
        }

        System.out.println("Presione enter para calcular el porcentaje de progreso de las metas");
        sc.nextLine();
        sc.nextLine();

        //Calcular porcetanje de las metas
        for (Meta m : empleado.getMetas()) {
            System.out.println("Porcentaje de progreso de las metas del empleado " + empleado.getNombre());

            int porcentajeProgreso = 0;
            porcentajeProgreso = (m.getAcumulado()*100)/m.getValorAlcanzar();
            System.out.println("Código: " + m.getCodigo() + " - Porcentaje de progreso: " + porcentajeProgreso + "%");

            if (porcentajeProgreso == 100) {
                empleado.ingresarMetasAlcanzdas(m);
                m.setEstado("Meta cumplida");
            }

            if (yearHoy > m.getYearLimite() || yearHoy == m.getYearLimite() && mesHoy > m.getMesLimite() || yearHoy == m.getYearLimite() && mesHoy == m.getMesLimite() && diaHoy > m.getDiaLimite())  {
                empleado.ingresarMetasCaducadas(m);
                m.setEstado("Meta caducada");
            }

        }

        System.out.println("Presione enter para revisar si hay metas alcanzadas");
        sc.nextLine();
        sc.nextLine();

        //Revisar si hay metas alcanzadas
        RevisarMetasAlcanzadas(empleado);

        //Revisar si hay metas caducadas
        RevisarMetasCaducadas(empleado);


        return meta;
    }

    private static void RevisarMetasAlcanzadas(Empleado empleado){
        if (empleado.getMetasAlcanzadas().isEmpty()){
            System.out.println("El empleado " + empleado.getNombre() + " todavía no ha cumplido con ninguna meta. Ánimo.");
            System.out.println("Presione enter para continuar");
            sc.nextLine();
            sc.nextLine();
        } else {
            System.out.println("Las metas alcanzadas por el empleado: " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetasAlcanzadas()){
                System.out.println("Código de meta: " + m.getCodigo() + " | Valor a alcanzar: " + m.getValorAlcanzar() + " |  Valor de bonificación: " + m.getValorBonificacion() + " | Fecha límite: " + m.getDiaLimite() + "/" + m.getMesLimite() + "/" +m.getYearLimite() );
            }
        }
    }

    private static Meta RevisarMetasCaducadas(Empleado empleado){
        if (empleado.getMetasCaducadas().isEmpty()){
            System.out.println("El empleado " + empleado.getNombre() + " no tiene metas caducadas");
            System.out.println("Presione enter para continuar");
            sc.nextLine();
            sc.nextLine();
            return null;
        } else {
            System.out.println("Las metas caducadas por el empleado: " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetasCaducadas()) {
                System.out.println("Código de meta: " + m.getCodigo() + " | Valor a alcanzar: " + m.getValorAlcanzar() + " |  Valor de bonificación: " + m.getValorBonificacion() + " | Fecha límite: " + m.getDiaLimite() + "/" + m.getMesLimite() + "/" + m.getYearLimite());
            }
        }
        System.out.println("¿Desea ampliar el plazo de alguna meta? (Y/n. \n");
        char decision = 'y';
        decision = sc.next().charAt(0);
        if (decision == 'n' || decision == 'N'){
            return null;
        }
        else {
            int codigo = 0;
            Meta meta = null;

            System.out.println("Ingrese el código de la meta que desea ampliar el plazo: ");

            try {
                codigo = sc.nextInt();

                for (Meta m : empleado.getMetasCaducadas()){
                    if (m.getCodigo() == codigo){
                        meta = m;
                        return meta;
                    }
                    else {
                        codigo = 0;
                    }
                }
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
            }

            if (codigo == 0) {
                System.out.println("\n### ERROR ###");
                System.out.println("Meta no encontrada. ¿Desea intentar de nuevo? (Y/n).\n");
                char decision1 = 'y';
                decision1 = sc.next().charAt(0);
                if (decision1 == 'n' || decision1 == 'N') {
                    return null;
                }
            }

        }
        return null;
    }

    private static void eliminarMetaCaducada(Empleado empleado){

    }

}
