package gestorAplicacion.manejoLocal;
import java.io.Serializable;
import java.io.Serial;
import java.util.Scanner;

public class Fecha implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private int totalDias;
    private int year;
    private int mes;
    private int dia;

    // Constructor por dia, mes y año
    public Fecha (int dia, int mes, int year) {
        this.dia = dia;
        this.mes = mes;
        this.year = year;

        this.totalDias = fechaADias(dia, mes, year);
    }

    // Constructor por total de dias
    public Fecha (int dias) {
        this.totalDias = dias;

        diasAFecha(dias);
    }

    public int fechaADias(int dia, int mes, int year) {
        boolean bisiesto = esBisiesto(year);

        // Años
        this.totalDias += (year * 365)
                        // sumar un dia por cada año bisiesto
                        + (year / 4) - (year / 100) + (year / 400) ;

        // Añadir +1 por el año 0 (que es bisiesto)
        if (year != 0) { this.totalDias++; }

        // Si el año actual es bisiesto, se resta un día
        if (bisiesto && this.year != 0) {
            this.totalDias--;
        }

        // Meses
        // Sumar la cantidad de días que corresponde por cada mes
        for (int mesTemp = 1; mesTemp < mes; mesTemp++) {
            switch (mesTemp) {
                case 1, 3, 5, 7, 8, 10, 12:
                    this.totalDias += 31;
                    break;
                case 4, 6, 9, 11:
                    this.totalDias += 30;
                    break;
                case 2:
                    if (bisiesto) {
                        this.totalDias += 29;
                    } else {
                        this.totalDias += 28;
                    }
                    break;
            }
        }

        // Dias
        this.totalDias += dia;

        return this.totalDias;
    }

    void diasAFecha(int totalDias) {
        int year = 0;
        int mes = 1;
        int dia = 0;

        // Sumar años restando 365 o 366 días hasta que el total de días sea menor a 365
        while (true) {
            if (esBisiesto(year)) {
                if (totalDias - 366 <= 0) { break; }
                totalDias -= 366;
            }
            else {
                if (totalDias - 365 <= 0) { break; }
                totalDias -= 365;
            }
            year++;
        }

        // Restar la cantidad de días que corresponde a cada mes hasta que
        // el total de días sea menor a la cantidad de días que tiene el mes
        while (totalDias > 0) {
            switch (mes) {
                case 1, 3, 5, 7, 8, 10, 12:
                    if (totalDias - 31 > 0) {
                        totalDias -= 31;
                        mes++;
                    } else {
                        dia = totalDias;
                        totalDias = 0;
                    }
                    break;
                case 4, 6, 9, 11:
                    if (totalDias - 30 > 0) {
                        totalDias -= 30;
                        mes++;
                    } else {
                        dia = totalDias;
                        totalDias = 0;
                    }
                    break;
                case 2:
                    if (esBisiesto(year)) {
                        if (totalDias - 29 > 0) {
                            totalDias -= 29;
                            mes++;
                        } else {
                            dia = totalDias;
                            totalDias = 0;
                        }
                    } else {
                        if (totalDias - 28 > 0) {
                            totalDias -= 28;
                            mes++;
                        } else {
                            dia = totalDias;
                            totalDias = 0;
                        }
                    }
                    break;
            }

        }

        this.dia = dia;
        this.mes = mes;
        this.year = year;
    }



    boolean esBisiesto(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    // método tostring
    public String toString() {
        return dia + "/" + mes + "/" + year;
    }

    // Getter para el total de dias
    public int getTotalDias() {
        return totalDias;
    }

    //Setters
    public void setDia(int dia) {
        this.dia = dia;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setTotalDias(int totalDias) {
        this.totalDias = totalDias;
    }
    // TODO: Método para restar fechas y que sea de doble filo (funcione con fecha1 - fecha2 y fecha2 - fecha1)
    // - aunque puede que no sea necesario restar fechas, solo tener en cuenta las ventas (al menos para análisis)
    // hechas en un rango de fechas (en total de dias)

    // Clase scanner para método que recibe información
    static Scanner sc = new Scanner(System.in);
}
