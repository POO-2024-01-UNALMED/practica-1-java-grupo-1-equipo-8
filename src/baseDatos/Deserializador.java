package baseDatos;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.ArrayList;

import gestorAplicacion.manejoLocal.Tienda;

public class Deserializador {
    private static File path = new File("");

    public static void deserealizar() {
        try {
            //ELIMINAR
            System.out.println("Leyendo..........");

            FileInputStream f = new FileInputStream(new File(path.getAbsolutePath() + "\\src\\baseDatos\\temp\\locales.txt"));
            ObjectInputStream o = new ObjectInputStream(f);

            ArrayList<Tienda> locales = (ArrayList<Tienda>) o.readObject();

            Tienda.setLocales(locales);

            //ELIMINAR
            Tienda.getLocales().forEach(local -> {
                System.out.println(local.getNombre());
            });


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
