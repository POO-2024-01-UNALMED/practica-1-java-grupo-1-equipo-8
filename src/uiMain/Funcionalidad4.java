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

        /* ~~~ Mostrar metas alcanzadas ~~~ */
        mostrarMetasAlcanzadas(empleado);

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
            byte decision;

            int rendimiento = verRendimiento(empleado, fechaActual);
            compararRendimiento(empleado, fechaActual, rendimiento);
            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Ver rendimiento en otro periodo");
            System.out.println("2. Asignar sueldo");

            try {
                decision = sc.nextByte();
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }
            if (decision ==  2){
                break;
            }
        }

        /* ~~~ Modificar Salarios o días laborales ~~~ */
        while (true) {
            byte decision;

            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Modificar salarios");
            System.out.println("2. Modificar días laborales");

            try {
                decision = sc.nextByte();
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }


            switch (decision) {
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
            if (!pregunta) {
                break;
            }

        }
        /* ~~~ Asignar una meta ~~~ */

        while (true){
            byte pregunta;

            System.out.println("¿Qué desea hacer?");
            System.out.println("1. Asignar una meta");
            System.out.println("2. Terminar");

            try {
                pregunta = sc.nextByte();
            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            if (pregunta == 1){
                asignarMeta(empleado);
            } else if (pregunta == 2) {
                break;
            } else {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
            }
        }
    }


    private static boolean siNo(String pregunta) {
        System.out.println( pregunta + " (S/n)");
        char respuesta = sc.next().charAt(0);
        sc.nextLine();  // Limpiar el buffer

        return !(respuesta == 'n' || respuesta == 'N');
    }

    private static Empleado identificarEmpleado(Tienda local) {
        /* ~~ Elegir con qué empleado se desea usar la funcionalidad ~~ */

        imprimirSeparador();

        Empleado empleado;
        int cedula;

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

                for (Empleado e : local.getEmpleados()) {
                    if (e.getCedula() == cedula) {
                        empleado = e;
                        return empleado;
                    } else {
                        cedula = 0;
                    }
                }
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            // En caso de que el empleado no sea encontrado dar la opción de intentar de nuevo
            if (cedula == 0) {
                imprimirSeparador();
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

    /* ~~ Calcular porcentaje de las metas. Recibe empleado y fecha ~~ */
    private static void gestionarMeta(Empleado empleado, Fecha fechaActual) {

        imprimirSeparador();

        System.out.println("Porcentaje de progreso de las metas del empleado " + empleado.getNombre());
        for (Meta m : empleado.getMetas()) {
            int porcentajeProgreso = (m.getAcumulado() * 100) / m.getValorAlcanzar();
            System.out.println("* Código: " + m.getCodigo() + " - Porcentaje de progreso: " + porcentajeProgreso + "%");

            if (porcentajeProgreso >= 100 && fechaActual.getTotalDias() <= m.getFecha().getTotalDias()) { //Si el porcentaje de la meta se completó:
                empleado.getMetas().remove(m);
                empleado.ingresarMetasAlcanzdas(m);
                m.setEstado("Meta cumplida");
                empleado.setAcumuladoMensual(m.getValorBonificacion());
            }

            if (fechaActual.getTotalDias() > m.getFecha().getTotalDias()) { //Si la fecha de la meta caducó:
                empleado.getMetas().remove(m);
                empleado.ingresarMetasCaducadas(m);
                m.setEstado("Meta caducada");
            }
        }
    }

    /* ~~ Imprimir si hay metas alcanzadas ~~ */
    private static void mostrarMetasAlcanzadas(Empleado empleado) {

        if (empleado.getMetasAlcanzadas().isEmpty()) { //No se ha cumplido con ninguna meta aún
            imprimirSeparador();
            System.out.println("El empleado " + empleado.getNombre() + " todavía no ha cumplido con ninguna meta. Ánimo.");
            System.out.println("Presione enter para continuar");
            sc.nextLine();
        } else { //Si se han cumplido metas
            imprimirSeparador();
            System.out.println("Las metas alcanzadas por el empleado: " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetasAlcanzadas()) {
                System.out.println(m.toString());
            }
        }
    }

    /* ~~ Imprimir metas caducadas. Recibe empleado ~~ */
    private static void mostrarMetasCaducadas(Empleado empleado){

        if (empleado.getMetasCaducadas().isEmpty()) { //Si no hay metas caducadas:
            imprimirSeparador();
            System.out.println("El empleado " + empleado.getNombre() + " no tiene metas caducadas");
            System.out.println("Presione enter para continuar");
            sc.nextLine();
            sc.nextLine();
        } else { //Si sí hay metas caducadas:
            imprimirSeparador();
            System.out.println("Las metas caducadas por el empleado " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetasCaducadas()) {
                System.out.println(m.toString());
            }
        }
    }

    /* ~~ Elegir meta que se desea modificar. Recibe empleado ~~ */
    private static Meta revisarMetasCaducadas(Empleado empleado) {
        while (true){
            int codigo;

            imprimirSeparador();
            System.out.println("Escriba el codigo de la meta que desea modificar");

            try {
                codigo = sc.nextByte();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            for (Meta m : empleado.getMetasCaducadas()) {
                if (m.getCodigo() == codigo) {
                    return m;
                }
            }

            //En caso tal de que se ingrese un código de una meta que no existe
            imprimirSeparador();
            System.out.println("\n### ERROR ###");
            System.out.println("Meta no encontrada. ¿Desea intentar de nuevo? (Y/n).\n");
            char decision1 = 'y';
            decision1 = sc.next().charAt(0);
            if (decision1 == 'n' || decision1 == 'N') {
                return null;
            }
        }
    }

    /* ~~ Metodo para ampliar plazo de meta caducada ~~ */
    private static void ampliarMeta(Empleado empleado, Meta meta, Fecha fechaActual) {
        while (true) {
            int yearAjuste;
            int mesAjuste;
            int diaAjuste;

            try { //Ingresar año que se desea modificar
                imprimirSeparador();
                System.out.println("Ingrese el año en que desea ampliar la meta: ");
                yearAjuste = sc.nextInt();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }
            //Hacer ajuste de la fecha de la meta
            meta.getFecha().setYear(yearAjuste);

            try { //Ingresar el mes que se desea modificar
                imprimirSeparador();
                System.out.println("Ingrese el mes en que desea ampliar la meta: ");
                mesAjuste = sc.nextInt();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }
            //Hacer ajuste del mes que se desea modificar
            meta.getFecha().setMes(mesAjuste);

            try { //Ingresar el día que se desea modificar
                imprimirSeparador();
                System.out.println("Ingrese el día que desea ampliar la meta");
                diaAjuste = sc.nextInt();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }
            //Hacer ajuste del día que se desea modificar
            meta.getFecha().setDia(diaAjuste);

            //Cambiar total de días de la meta a 0 para que luego no genere problemas
            meta.getFecha().setTotalDias(0);
            //Hacer ajuste de total de días de la meta
            meta.getFecha().setTotalDias(meta.getFecha().fechaADias(diaAjuste, mesAjuste, yearAjuste));

            if (meta.getFecha().getTotalDias() < fechaActual.getTotalDias()) { //Si la fecha de la meta es antes de la actual
                imprimirSeparador();
                System.out.println("Fecha no válida, presione enter para volver a intentar");
                sc.nextLine();
                sc.nextLine();
            } else { //Si la fecha de la meta es después de la actual
                imprimirSeparador();
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
        /* ~~ Ver la cantidad de ventas del empleado ~~ */
        while (true){
            byte opcion;
            byte decision;

            try { //Elegir si se desea ver las ventas del empleado semanal, mensual o anualmente
                imprimirSeparador();
                System.out.println("Desea ver el rendimiento del empleado " + empleado.getNombre() + ":");
                System.out.println("1. Semanal");
                System.out.println("2. Mensual");
                System.out.println("3. Anual");
                opcion = sc.nextByte();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            switch (opcion) {
                case 1: //Ver la cantidad de ventas del empleado en la semana
                    for (Transaccion t : empleado.getTransacciones()) {
                        //Sumar uno al total de ventas de la semana actual si la fecha de la venta es mayor al de la fecha actual -7
                        if (fechaActual.getTotalDias() - 7 <= t.getFecha().getTotalDias()) {
                            totalVentasSemanaActual++;
                        }
                    }
                    imprimirSeparador();
                    System.out.println("El total de ventas semanales del empleado " + empleado.getNombre() + " son: " + totalVentasSemanaActual);
                    break;

                case 2: //Ver la cantidad de ventas del empleado en el mes
                    for (Transaccion t : empleado.getTransacciones()) {
                        //Sumar uno al total de ventas de la semana actual si la fecha de la venta es mayor al de la fecha actual -31
                        if (fechaActual.getTotalDias() - 31 <= t.getFecha().getTotalDias()) {
                            totaVentasMesActual++;
                        }
                    }
                    imprimirSeparador();
                    System.out.println("El total de ventas mensuales del empleado " + empleado.getNombre() + " son: " + totaVentasMesActual);
                    break;

                case 3: //Ver la cantidad de ventas del empleado en el año
                    for (Transaccion t : empleado.getTransacciones()) {
                        //Sumar uno al total de ventas de la semana actual si la fecha de la venta es mayor al de la fecha actual -365
                        if (fechaActual.getTotalDias() - 365 <= t.getFecha().getTotalDias()) {
                            totalVentasYearActual++;
                        }
                    }
                    imprimirSeparador();
                    System.out.println("El total de ventas anuales del empleado " + empleado.getNombre() + " son: " + totalVentasYearActual);
                    break;

                default: //Si se ingresa un número no válido
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    break;
            }

            while (true){
                try { //Elegir si se desea ver el rendimiento en otro periodo o pasar a compararlo
                    imprimirSeparador();
                    System.out.println("¿Qué desea hacer?");
                    System.out.println("1. Ver el rendimiento en otro periodo de tiempo");
                    System.out.println("2. Comparar rendimiento con periodo anterior");

                    decision = sc.nextByte();
                } catch (InputMismatchException error) {
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                    sc.nextLine();  // nextLine para limpiar el buffer
                    sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                    continue;
                }
                if (decision == 1){ //Si elige 1, romper el while y que entre al otro while de nuevo
                    break;
                } else if (decision == 2) { //Si elige 2, retornar la opción de rango en que se vió el rendimiento para compararlo con el del periodo anterior
                    return opcion;
                } else { //Presentar error si elige un número distinto a 1 ó 2
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                    sc.nextLine();  // nextLine para limpiar el buffer
                    sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                }
            }
        }
   }

    private static void compararRendimiento(Empleado empleado, Fecha fechaActual, int decision){
        /* ~~ Comparar cantidad de ventas del empleado ~~ */

        switch (decision) {
            case 1: //Comparar la cantidad de ventas con la semana pasada
                int totalSemana = 0;
                for (Transaccion t : empleado.getTransacciones()) {
                    //Sumar 1 al total de ventas de la semana pasada si la fecha de la venta es mayor a la fecha actual -14 y además es menor al de la fecha actual -7
                    if (fechaActual.getTotalDias() - 14 <= t.getFecha().getTotalDias() && fechaActual.getTotalDias() - 7 >= t.getFecha().getTotalDias()){
                        totalSemana++;
                    }
                }
                imprimirSeparador();
                System.out.println("El total de ventas en la semana anterior del empleado " + empleado.getNombre() + " fueron:" + totalSemana);
                System.out.println("Presione Enter para continuar.\n");
                sc.nextLine(); // Limpiar el buffer
                sc.nextLine(); // Esperar a que el usuario presione Enter

                //Si el total de ventas de la semana actual es mayor al total de ventas de la semana pasada, calcular porcentaje de incremento
                if (totalSemana < totalVentasSemanaActual){
                    int calculo = ((totalVentasSemanaActual - totalSemana)/totalSemana)*100;
                    imprimirSeparador();
                    System.out.println("El total de ventas en esta semana incrementó en un " + calculo + "%");
                    if (calculo > 30 ){
                        System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado debería tener una bonificación remunerada.");
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                //Si el total de ventas de la semana actual es igual al total de ventas de la semana pasada
                } else if (totalSemana == totalVentasSemanaActual) {
                    imprimirSeparador();
                    System.out.println("El total de ventas fue igual al de la semana pasada " + totalSemana + " ventas.");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter

                //Si el total de ventas de la semana actual es menor al total de ventas de la semana pasada, calcular porcentaje de decremento
                } else {
                    int calculo = ((totalSemana - totalVentasSemanaActual)/totalSemana)*100;
                    imprimirSeparador();
                    System.out.println("El total de ventas en esta semana disminuyó en un " + calculo +"%");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                }
                break;

            case 2:
                int totalMes = 0;
                for (Transaccion t : empleado.getTransacciones()) {
                    //Sumar 1 al total de ventas de la semana pasada si la fecha de la venta es mayor a la fecha actual -62 y además es menor al de la fecha actual -31
                    if (fechaActual.getTotalDias() - 62 <= t.getFecha().getTotalDias() && fechaActual.getTotalDias() - 31 >= t.getFecha().getTotalDias()) {
                        totalMes++;
                        imprimirSeparador();
                        System.out.println("El total de ventas en el mes anterior del empleado " + empleado.getNombre() + " fueron: " + totalMes);
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                }

                //Si el total de ventas del mes actual es mayor al total de ventas del mes pasado, calcular porcentaje de incremento
                if (totalMes < totaVentasMesActual){
                    int calculo = ((totaVentasMesActual - totalMes)/totalMes)*100;
                    imprimirSeparador();
                    System.out.println("El total de ventas en este mes incrementó en un " + calculo + "%");
                    if (calculo > 30 ){
                        System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado debería tener una bonificación remunerada.");
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter

                    }

                //Si el total de ventas del mes actual es igual al total de ventas del mes pasado
                } else if (totalMes == totaVentasMesActual) {
                    imprimirSeparador();
                    System.out.println("El total de ventas fue igual al del mes pasado: " + totalMes + " ventas.");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter

                //Si el total de ventas del mes actual es menor al total de ventas del mes pasado, calcular porcentaje de decremento
                } else {
                    int calculo = ((totalMes - totaVentasMesActual)/totalMes)*100;
                    imprimirSeparador();
                    System.out.println("El total de ventas en este mes disminuyó en un " + calculo +"%");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                }
                break;

            case 3:
                int totalYear = 0;
                for (Transaccion t : empleado.getTransacciones()) {
                    //Sumar 1 al total de ventas de la semana pasada si la fecha de la venta es mayor a la fecha actual -730 y además es menor al de la fecha actual -365
                    if (fechaActual.getTotalDias() - 730 <= t.getFecha().getTotalDias() && fechaActual.getTotalDias() - 365 >= t.getFecha().getTotalDias()) {
                        totalYear++;
                        imprimirSeparador();
                        System.out.println("El total de ventas en la semana anterior del empleado " + empleado.getNombre() + " fueron:" + totalYear);
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }
                }

                //Si el total de ventas del año actual es mayor al total de ventas del año pasado, calcular porcentaje de decremento
                if (totalYear < totalVentasYearActual){
                    int calculo = ((totalVentasYearActual - totalYear)/totalYear)*100;
                    imprimirSeparador();
                    System.out.println("El total de ventas en esta semana incrementó en un " + calculo + "%");
                    if (calculo > 30 ){
                        System.out.println("El empleado tuvo un incremento en ventas mayor al 30%. El empleado debería tener una bonificación remunerada.");
                        System.out.println("Presione Enter para continuar.\n");
                        sc.nextLine(); // Limpiar el buffer
                        sc.nextLine(); // Esperar a que el usuario presione Enter
                    }

                //Si el total de ventas del año actual es igual al total de ventas del año pasado
                } else if (totalYear == totalVentasYearActual) {
                    imprimirSeparador();
                    System.out.println("El total de ventas fue igual al de la semana pesada " + totalYear + " ventas.");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter

                //Si el total de ventas del año actual es menor al total de ventas del año pasado, calcular porcentaje de decremento
                } else {
                    int calculo = ((totalYear - totalVentasYearActual)/totalYear)*100;
                    imprimirSeparador();
                    System.out.println("El total de ventas en esta semana disminuyó en un " + calculo +"%");
                    System.out.println("Presione Enter para continuar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                }
                break;

            default:
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                sc.nextLine(); // Limpiar el buffer
                sc.nextLine(); // Esperar a que el usuario presione Enter
                break;
        }
    }

    private static void modificarSalario(Empleado empleado) {
        /* ~~ Modificar salario del empleado ~~ */

        int salario;
        while (true){
            try { //Elegir si se desea modificar el salario fijo del empleado o el porcentaje por ventas
                imprimirSeparador();
                System.out.println("Desea modificar:");
                System.out.println("1. Salario fijo");
                System.out.println("2. Porcentaje por ventas");

                salario = sc.nextInt();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            switch (salario) {
                case 1:
                    int salarioNuevo;

                    try { //Mostrar cuanto es el salario actual del empleado e ingresar el nuevo salario del empleado
                        imprimirSeparador();
                        System.out.println("El salario fijo del empleado es: " + empleado.getSalario());
                        System.out.println("Ingrese el nuevo salario del empleado");

                        salarioNuevo = sc.nextInt();
                    } catch (InputMismatchException error) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                        sc.nextLine();  // nextLine para limpiar el buffer
                        sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                        continue;
                    }

                    empleado.setSalario(salarioNuevo);
                    imprimirSeparador();
                    System.out.println("El nuevo salario fijo del empleado es: " + empleado.getSalario());
                    return;

                case 2:
                    int salarioPorcentualNuevo;
                    try { //Mostrar cuanto es el salario actual del empleado e ingresar el nuevo salario del empleado
                        imprimirSeparador();
                        System.out.println("El salario porcentual de empleado es: " + empleado.getSalarioPorcentual());
                        System.out.println("Ingrese el nuevo salario porcentual del empleado: ");

                        salarioPorcentualNuevo = sc.nextInt();
                    } catch (InputMismatchException error) {
                        imprimirSeparador();
                        System.out.println("\n### ERROR ###");
                        System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                        sc.nextLine();  // nextLine para limpiar el buffer
                        sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                        continue;
                    }

                    empleado.setSalarioPorcentual(salarioPorcentualNuevo);
                    imprimirSeparador();
                    System.out.println("El nuevo salario porcentual del empleado es: " + empleado.getSalarioPorcentual());
                    return;

                default: //En caso tal de que se ingrese una opción incorrecta
                    imprimirSeparador();
                    System.out.println("\n### ERROR ###");
                    System.out.println("Opción fuera del rango. Presione Enter para volver a intentar.\n");
                    sc.nextLine(); // Limpiar el buffer
                    sc.nextLine(); // Esperar a que el usuario presione Enter
                    return;
            }
        }
    }

    private static void modificarDiasLaborales(Empleado empleado) {
        /* ~~ Modificar días laborales del empleado ~~ */

        while (true){
            byte nuevoDias;

            try { //Mostrar número de días ue trabaja actualmente en la tienda e ingresar el nuevo número de días que trabajará
                imprimirSeparador();
                System.out.println("El empleado " + empleado.getNombre() + "trabaja " + empleado.getDiasLaborales() + " a la semana");
                System.out.println("Ingrese el número de días que " + empleado + " trabajará a la semana");

                nuevoDias = sc.nextByte();
            } catch (InputMismatchException error) {
                imprimirSeparador();
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            if (nuevoDias < 6 && nuevoDias > 0){ //Si el número ingresado es menor a 6 días
                empleado.setDiasLaborales(nuevoDias);
                imprimirSeparador();
                System.out.println("Ahora el empleado trabajará " + empleado.getDiasLaborales() + " días a la semana");
                return;
            }
            else { //Si se ingresa un número mayo a 6 días
                imprimirSeparador();
                System.out.println("Cantidad inhumana de días. Presione enter para volver a intentarlo");
            }
        }

    }

    private static void asignarMeta(Empleado empleado) {
        /* ~~ Asignar una meta nueva al empleado ~~ */

        while (true){
            int yearLimite;
            int mesLimite;
            int diaLimite;
            int valorAlcanzar;
            int valorBonificacion;

            //Mostrar cuáles son las metas del empleado
            System.out.println("Las metas del empleado + " + empleado.getNombre() + " son: ");
            for (Meta m : empleado.getMetas()) {
                System.out.println(m.toString());
            }
            System.out.println("Presione enter para continuar");
            sc.nextLine();
            sc.nextLine();


            try { //Ingresar el año límite de la meta
                System.out.println("Ingrese el año límite de la meta: ");
                yearLimite = sc.nextInt();

            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            try { //Ingresar el mes límite de la meta
                System.out.println("Ingrese el mes límite de la meta: ");
                mesLimite = sc.nextInt();

            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            try { //Ingresar el día límite de la meta
                System.out.println("Ingrese el día límite de la meta: ");
                diaLimite = sc.nextInt();

            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            try { //Ingresar el valor de ventas a alcanzar
                System.out.println("Ingrese el valor a alcanzar: ");
                valorAlcanzar = sc.nextInt();

            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            try { //Ingresar el valor de la bonificación a ganar
                System.out.println("Ingrese el valor de la bonificación : ");
                valorBonificacion = sc.nextInt();

            } catch (InputMismatchException error) {
                System.out.println("\n### ERROR ###");
                System.out.println("Ingrese un número válido. Presiona enter para volver a intentar.\n");
                sc.nextLine();  // nextLine para limpiar el buffer
                sc.nextLine();  // nextLine para esperar a que el usuario presione Enter
                continue;
            }

            //LLamar a constructor para crear meta
            new Meta(empleado, diaLimite, mesLimite, yearLimite, valorAlcanzar, valorBonificacion);
            return;
        }
    }
}
