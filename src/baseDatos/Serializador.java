package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Fecha;
import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;

public class Serializador {
    public static void serializarTiendas(ArrayList<Tienda> locales) {
        try {
            FileOutputStream f = new FileOutputStream(new File("src\\baseDatos\\temp\\locales.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(locales);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }

    public static void serializarClientes(ArrayList<Cliente> clientes) {
        try {
            FileOutputStream f = new FileOutputStream(new File("src\\baseDatos\\temp\\clientes.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(clientes);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }

    public static void serializarUltimaFecha(Fecha fecha) {
        try {
            FileOutputStream f = new FileOutputStream(new File("src\\baseDatos\\temp\\ultimaFecha.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(fecha);

            o.close();
            f.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        }
        catch (IOException e) {
            System.out.println("Error inicializando flujo de salida");
        }
    }
}
