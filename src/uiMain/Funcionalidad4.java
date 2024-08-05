package uiMain;

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
        calcularProgreso(meta);
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

            // En caso de que el cliente no sea encontrado dar la opción de intentar de nuevo
            if (cedula == 0) {
                System.out.println("\n### ERROR ###");
                System.out.println("Cliente no encontrado. ¿Desea intentar de nuevo? (Y/n).\n");
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

        int codigo = 0;
        Meta meta = null;

        //Mostrar metas del empleado
        System.out.println("Metas del empleado:");
        for (Meta m : empleado.getMetas()) {
            System.out.println("* Codigo: " + m.getCodigo() + " - Fecha Límite: " + m.getDiaLimite()+ "/" + m.getMesLimite() + "/" + m.getYearLimite() + " - Valor a alcanzar: " + m.getValorAlcanzar() + " - Acumulado: " + m.getAcumulado() + " - Estado: " + m.getEstado()) ;
        }

        while (meta == null) {
            System.out.println("Ingrese el código de la meta que desea calcular su porcentaje de progreso");

            //Buscar la meta en la lista por su código
            try {
                codigo = sc.nextInt();

                for (Meta m : empleado.getMetas()) {
                    if (m.getCodigo() == codigo) {
                        meta = m;
                    } else {
                        codigo = 0;
                    }
                }
            } catch (Exception e) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            // En caso de que la meta no sea encontrada dar la opción de intentar de nuevo
            if (codigo == 0) {
                System.out.println("\n### ERROR ###");
                System.out.println("Meta no encontrada. ¿Desea intentar de nuevo? (Y/n).\n");
                char decision = 'y';
                decision = sc.next().charAt(0);
                if (decision == 'n' || decision == 'N') {
                    return null;
                }
            }
        }
        return meta;
    }

    private static void calcularProgreso(Meta meta){
        imprimirSeparador();

        int porcentaProgreso = 0;

        System.out.println("Valor a alcanzar de la meta: " + meta.getValorAlcanzar());
        System.out.println("Valor acumulado actual: " + meta.getAcumulado());
        System.out.println("Calculando el progreso de la meta... ");

        porcentaProgreso = (meta.getAcumulado()*100)/meta.getValorAlcanzar();

        System.out.println("El porcentaje de progreso es del: " + porcentaProgreso + "%");
    }
}
