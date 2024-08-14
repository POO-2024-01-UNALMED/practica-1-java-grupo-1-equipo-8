package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Tienda;
import gestorAplicacion.personas.Cliente;

public class Deserializador {
    private static File path = new File("");

    public static void deserializarTiendas() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\locales.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Tienda> locales = (ArrayList<Tienda>) o.readObject();

            Tienda.setLocales(locales);

            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de entrada");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }

    public static void deserializarClientes() {
        try {
            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\clientes.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Cliente> clientes = (ArrayList<Cliente>) o.readObject();

            Cliente.setClientes(clientes);

            f.close();
            o.close();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (IOException e) {
            System.out.println("Error inicializando flujo de entrada");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada");
        }
    }

}
