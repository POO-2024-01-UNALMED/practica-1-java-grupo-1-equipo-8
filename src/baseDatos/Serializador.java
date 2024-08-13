package baseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Tienda;

public class Serializador {
    private static  File archivo = new File("");

    public static void serializar(ArrayList<Tienda> locales) {
        try {
            FileOutputStream f = new FileOutputStream(new File(archivo.getAbsolutePath() + "\\src\\baseDatos\\temp\\locales.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(locales);

            System.out.println("Serializando..........");

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
