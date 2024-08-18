package uiMain;

import java.util.InputMismatchException;
import java.util.Scanner;

import static uiMain.Main.imprimirSeparador;

import gestorAplicacion.informacionVenta.Transaccion;
import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Empleado;
import gestorAplicacion.personas.Meta;

public class Funcionalidad4 {
    static Scanner sc = new Scanner(System.in);
    private static int totalVentasSemanaActual = 0;
    private static int totaVentasMesActual = 0;
    private static int totalVentasYearActual = 0;
    public static void inspeccionEmpleado(Tienda local, Fecha fechaActual) {
        /* ~~~ Identificación del empleado ~~~ */
        Empleado empleado = identificarEmpleado(local);

        if (empleado == null){
            return;
        }

        /* ~~~ Gestionar metas ~~~ */
        gestionarMeta(empleado, fechaActual);
        System.out.println("Presione enter para revisar si hay metas alcanzadas");
        sc.nextLine();
        sc.nextLine();

        /* ~~~ Revisar metas alcanzadas ~~~ */
        revisarMetasAlcanzadas(empleado);

        /* ~~~ Revisar metas caducadas ~~~ */
        while (!empleado.getMetasCaducadas().isEmpty()){
            mostrarMetasCaducadas(empleado);
            boolean decision = siNo("¿Desea ampliar el plazo de alguna de las metas?");
            if (decision){
                Meta meta = revisarMetasCaducadas(empleado);
                if (meta == null){
                    break;
                }
                ampliarMeta(empleado, meta, fechaActual);
            } else {
                break;
            }

        }

        /* ~~~ Ver rendimiento ~~~ */
        while (true){
            int rendimiento = verRendimiento(empleado, fechaActual);
            compararRendimiento(empleado, fechaActual, rendimiento);
            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Ver rendimiento en otro periodo");
            System.out.println("2. Asignar sueldo");
            int decision = sc.nextInt();
            if (decision ==  2){
                break;
            }
        }

        /* ~~~ Modificar Salarios o días laborales ~~~ */
        while (true){
            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Modificar salarios");
            System.out.println("2. Modificar días laborales");
            int decision = sc.nextInt();

            switch (decision){
                case 1:
                    modificarSalario(empleado);
                    break;

                case 2:
                    modificarDiasLaborales(empleado);
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
            boolean pregunta = siNo("¿Desea modificar algo más?");
            if (!pregunta){
                break;
            }

        }
        //TODO: Asignar una meta
        /* ~~~ Asignar una meta ~~~ */

        System.out.println("¿Qué desea hacer?");
        System.out.println("1. Asignar una meta");
        System.out.println("2. Terminar");
        byte pregunta = sc.nextByte();

        if (pregunta == 1){
            asignarMeta(empleado);
        }

    }


    private static boolean siNo(String pregunta) {
        System.out.println( pregunta + " (S/n)");
        char respuesta = sc.next().charAt(0);
        sc.nextLine();  // Limpiar el buffer

        return !(respuesta == 'n' || respuesta == 'N');
    }

    private static Empleado identificarEmpleado(Tienda local) {
        imprimirSeparador();

        Empleado empleado = null;
        int cedula = 0;

        //Mostrar empleados de la tienda
        System.out.println("Empleados de la tienda:");

        for (Empleado e : local.getEmpleados()) {
            System.out.println("* Nombre: " + e.getNombre() + " - Cedula: " + e.getCedula());
        }

        while (true) {
            System.out.println("Ingrese la cédula del empleado");

            // Buscar al empleado en la lista de empleados por su cédula
            try {
                cedula = sc.nextInt();

                for (Empleado e : local.empleados) {
                    if (e.getCedula() == cedula) {
                        empleado = e;
                        return empleado;
                    } else {
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
    }

    private static void gestionarMeta(Empleado empleado, Fecha fechaActual) {
        imprimirSeparador();

        //Calcular porcentaje de las metas
        System.out.println("Porcentaje de progreso de las metas del empleado " + empleado.getNombre());
        for (Meta m : empleado.getMetas()) {
            int porcentajeProgreso = (m.getAcumulado() * 100) / m.getValorAlcanzar();
            System.out.println("* Código: " + m.getCodigo() + " - Porcentaje de progreso: " + porcentajeProgreso + "%");

            if (porcentajeProgreso == 100) {
                empleado.getMetas().remove(m);
                empleado.ingresarMetasAlcanzdas(m);
                m.setEstado("Meta cumplida");
            }

            if (fechaActual.getTotalDias() > m.getFecha().getTotalDias()) {
                empleado.getMetas().remove(m);
                empleado.ingresarMetasCaducadas(m);
                m.setEstado("Meta caducada");
            }

        }
    }

    private static void revisarMetasAlcanzadas(Empleado empleado) {
        if (empleado.getMetasAlcanzadas().isEmpty()) {
            System.out.println("El empleado " + empleado.getNombre() + " todavía no ha cumplido con ninguna meta. Ánimo.");
            System.out.println("Presione enter para continuar");
            sc.nextLine();
        } else {
            System.out.println("Las metas alcanzadas por el empleado: " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetasAlcanzadas()) {
                System.out.println(m.toString());
            }
        }
    }

    private static void mostrarMetasCaducadas(Empleado empleado){
        if (empleado.getMetasCaducadas().isEmpty()) {
            System.out.println("El empleado " + empleado.getNombre() + " no tiene metas caducadas");
            System.out.println("Presione enter para continuar");
            sc.nextLine();
            sc.nextLine();
        } else {
            System.out.println("Las metas caducadas por el empleado " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetasCaducadas()) {
                System.out.println(m.toString());
            }
        }
    }
    private static Meta revisarMetasCaducadas(Empleado empleado) {
        while (true){
            System.out.println("Escriba el codigo de la meta que desea modificar");
            int codigo = sc.nextInt();

            for (Meta m : empleado.getMetasCaducadas()) {
                if (m.getCodigo() == codigo) {
                    return m;
                }
            }

            System.out.println("\n### ERROR ###");
            System.out.println("Meta no encontrada. ¿Desea intentar de nuevo? (Y/n).\n");
            char decision1 = 'y';
            decision1 = sc.next().charAt(0);
            if (decision1 == 'n' || decision1 == 'N') {
                return null;
            }
        }
    }

    private static void ampliarMeta(Empleado empleado, Meta meta, Fecha fechaActual) {
        while (true) {

            System.out.println("Ingrese el año en que desea ampliar la meta: ");
            int yearAjuste = sc.nextInt();
            meta.getFecha().setYear(yearAjuste);

            System.out.println("Ingrese el mes en que desea ampliar la meta: ");
            int mesAjuste = sc.nextInt();
            meta.getFecha().setMes(mesAjuste);

            System.out.println("Ingrese el día que desea ampliar la meta");
            int diaAjuste = sc.nextInt();
            meta.getFecha().setDia(diaAjuste);

            meta.getFecha().setTotalDias(0);
            meta.getFecha().setTotalDias(meta.getFecha().fechaADias(diaAjuste, mesAjuste, yearAjuste));

            if (meta.getFecha().getTotalDias() < fechaActual.getTotalDias()) {
                System.out.println("Fecha no válida, presione enter para volver a intentar");
                sc.nextLine();
                sc.nextLine();
            } else {
                System.out.println("Fecha actualizada. La meta " + meta.getCodigo() + " quedó para " + meta.getFecha().toString());
                System.out.println("Presione enter para continuar");
                empleado.getMetasCaducadas().remove(meta);
                empleado.ingresarMeta(meta);
                meta.setEstado("En proceso");
                sc.nextLine();
                sc.nextLine();
                break;
            }
        }
    }
    private static int verRendimiento(Empleado empleado, Fecha fechaActual){
        while (true){
            System.out.println("Desea ver el rendimiento del empleado " + empleado.getNombre() + ":");
            System.out.println("1. Semanal");
            System.out.println("2. Mensual");
            System.out.println("3. Anual");
            byte opcion = sc.nextByte();

            switch (opcion) {
                case 1:
                    for (Transaccion t : empleado.getTransacciones()) {
                        if (fechaActual.getTotalDias() - 7 > t.getFecha().getTotalDias()) {
                            totalVentasSemanaActual++;
                        }
                    }
                    System.out.println("El total de ventas semanales del empleado " + empleado.getNombre() + " son: " + totalVentasSemanaActual);
                    break;

                case 2:
                    for (Transaccion t : empleado.getTransacciones()) {
                        if (fechaActual.getTotalDias() - 31 > t.getFecha().getTotalDias()) {
                            totaVentasMesActual++;
                        }
                    }
                    System.out.println("El total de ventas mensuales del empleado " + empleado.getNombre() + " son:" + totaVentasMesActual);
                    break;

                case 3:
                    for (Transaccion t : empleado.getTransacciones()) {
                        if (fechaActual.getTotalDias() - 365 > t.getFecha().getTotalDias()) {
                            totalVentasYearActual++;
                        }
                    }
                    System.out.println("El total de ventas anuales del empleado " + empleado.getNombre() + " son:" + totalVentasYearActual);
                    break;

                default:
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }
            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Ver el rendimiento en otro periodo de tiempo");
            System.out.println("2. Comparar rendimiento con periodo anterior");

            int decision = sc.nextInt();
            if (decision == 2){
                return opcion;
            }
        }
   }

    private static void compararRendimiento(Empleado empleado, Fecha fechaActual, int decision){

        switch (decision) {
            case 1:
                int totalSemana = 0;
                for (Transaccion t : empleado.getTransacciones()) {
                    if (fechaActual.getTotalDias() - 14 > t.getFecha().getTotalDias() - 7) {
                        totalSemana++;
                        System.out.println("El total de ventas en la semana anterior del empleado " + empleado.getNombre() + " fueron:" + totalSemana);
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                }

                if (totalSemana < totalVentasSemanaActual){
                    int calculo = ((totalVentasSemanaActual - totalSemana)/totalSemana)*100;
                    System.out.println("El total de ventas en esta semana incrementó en un " + calculo + "%");
                    if (calculo > 30 ){
                        System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado debería tener una bonificación remunerada.");
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                } else if (totalSemana == totalVentasSemanaActual) {
                    System.out.println("El total de ventas fue igual al de la semana pasada " + totalSemana + " ventas.");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                } else {
                    int calculo = ((totalSemana - totalVentasSemanaActual)/totalSemana)*100;
                    System.out.println("El total de ventas en esta semana disminuyó en un " + calculo +"%");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                }
                break;

            case 2:
                int totalMes = 0;
                for (Transaccion t : empleado.getTransacciones()) {
                    if (fechaActual.getTotalDias() - 62 > t.getFecha().getTotalDias() - 31) {
                        totalMes++;
                        System.out.println("El total de ventas en el mes anterior del empleado " + empleado.getNombre() + " fueron:" + totalMes);
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                }

                if (totalMes < totaVentasMesActual){
                    int calculo = ((totaVentasMesActual - totalMes)/totalMes)*100;
                    System.out.println("El total de ventas en este mes incrementó en un " + calculo + "%");
                    if (calculo > 30 ){
                        System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado debería tener una bonificación remunerada.");
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                } else if (totalMes == totaVentasMesActual) {
                    System.out.println("El total de ventas fue igual al del mes pasado: " + totalMes + " ventas.");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                } else {
                    int calculo = ((totalMes - totaVentasMesActual)/totalMes)*100;
                    System.out.println("El total de ventas en este mes disminuyó en un " + calculo +"%");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                }
                break;

            case 3:
                int totalYear = 0;
                for (Transaccion t : empleado.getTransacciones()) {
                    if (fechaActual.getTotalDias() - 14 > t.getFecha().getTotalDias() - 7) {
                        totalYear++;
                        System.out.println("El total de ventas en la semana anterior del empleado " + empleado.getNombre() + " fueron:" + totalYear);
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                }

                if (totalYear < totalVentasYearActual){
                    int calculo = ((totalVentasYearActual - totalYear)/totalYear)*100;
                    System.out.println("El total de ventas en esta semana incrementó en un " + calculo + "%");
                    if (calculo > 30 ){
                        System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado debería tener una bonificación remunerada.");
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                } else if (totalYear == totalVentasYearActual) {
                    System.out.println("El total de ventas fue igual al de la semana pesada " + totalYear + " ventas.");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                } else {
                    int calculo = ((totalYear - totalVentasYearActual)/totalYear)*100;
                    System.out.println("El total de ventas en esta semana disminuyó en un " + calculo +"%");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                }
                break;

            default:
                System.out.println("\n### ERROR ###");
                System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                sc.nextLine(); // Limpiar el buffer
                sc.nextLine(); // Esperar a que el usuario presione Enter
                break;
        }
    }

    private static void modificarSalario(Empleado empleado) {
        System.out.println("Desea modificar:");
        System.out.println("1. Salario fijo");
        System.out.println("2. Porcentaje por ventas");
        int salario = sc.nextInt();

        switch (salario) {
            case 1:
                System.out.println("El salario fijo del empleado es: " + empleado.getSalario());
                System.out.println("Ingrese el nuevo salario del empleado");

                int salarioNuevo = sc.nextInt();
                empleado.setSalario(salarioNuevo);

                System.out.println("El nuevo salario fijo del empleado es: " + empleado.getSalario());

            case 2:
                System.out.println("El salario porcentual de empleado es: " + empleado.getSalarioPorcentual());
                System.out.println("Ingrese el nuevo salario porcentual del empleado: ");

                int salarioPorcentualNuevo = sc.nextInt();
                empleado.setSalarioPorcentual(salarioPorcentualNuevo);

                System.out.println("El nuevo salario porcentual del empleado es: " + empleado.getSalarioPorcentual());

            default:
                System.out.println("\n### ERROR ###");
                System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                sc.nextLine(); // Limpiar el buffer
                sc.nextLine(); // Esperar a que el usuario presione Enter
                break;
        }

    }

    private static void modificarDiasLaborales(Empleado empleado) {
        while (true){
            System.out.println("El empleado " + empleado.getNombre() + "trabaja " + empleado.getDiasLaborales() + " a la semana");
            System.out.println("Ingrese el número de días que " + empleado + " trabaje a la semana");
            byte nuevoDias = sc.nextByte();

            if (nuevoDias < 6 ){
                empleado.setDiasLaborales(nuevoDias);
                System.out.println("Ahora el empleado trabajará " + empleado.getDiasLaborales() + " días a la semana");
                return;
            }
            else {
                System.out.println("Cantidad inhumana de días. Presione enter para volver a intentarlo");
            }
        }

    }

    private static void asignarMeta(Empleado empleado) {
        System.out.println("Las metas del empleado + " + empleado.getNombre() + " son: ");
        for (Meta m : empleado.getMetas()) {
            System.out.println(m.toString());
        }
        System.out.println("Presione enter para continuar");
        sc.nextLine();
        sc.nextLine();

        System.out.println("Ingrese el año límite de la meta: ");
        int yearLimite = sc.nextInt();

        System.out.println("Ingrese el mes límite de la meta: ");
        int mesLimite = sc.nextInt();

        System.out.println("Ingrese el día límite de la meta: ");
        int diaLimite = sc.nextInt();

        System.out.println("Ingrese el valor a alcanzar: ");
        int valorAlcanzar = sc.nextInt();

        System.out.println("Ingrese el valor de la bonificación : ");
        int valorBonificacion = sc.nextInt();

    }

    //TODO: Hacer método para consecutivo en clase meta

    //TODO: Hablar con pablito para ver como ingresar bonificación cuando se completa una meta

    //TODO: Corregir los warnings
}
