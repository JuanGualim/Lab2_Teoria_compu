import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Archivo {

    /**
     * Lee un archivo de texto y devuelve todas las líneas
     * en una lista de expresiones.
     *
     * @param nombreArchivo Nombre del archivo a leer.
     * @return Lista con todas las expresiones encontradas.
     */
    public static List<String> leerArchivo(String nombreArchivo) {

        List<String> expresiones = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                // Ignorar líneas vacías
                if (!linea.trim().isEmpty()) {
                    expresiones.add(linea);
                }

            }

        } catch (IOException e) {

            System.out.println("Error al leer el archivo: " + nombreArchivo);
            System.out.println(e.getMessage());

        }

        return expresiones;

    }

}