package uiMain;

import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Empleado;

public class Funcionalidad4 {
    static Scanner sc = new Scanner(System.in);
    public static void inspeccionEmpleado(Tienda local){
        /* ~~~ Identificación del empleado ~~~ */
        Empleado empleado = identificarEmpleado(local);
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
}
