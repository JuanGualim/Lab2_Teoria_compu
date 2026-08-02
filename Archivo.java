import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga únicamente de leer las expresiones desde un archivo de texto.
 */
public class Archivo {

    /**
     * Lee una expresión por línea. Las líneas vacías se ignoran.
     *
     * @param nombreArchivo ruta del archivo que se desea leer
     * @return lista con las expresiones encontradas
     */
    public static List<String> leerArchivo(String nombreArchivo) {
        List<String> expresiones = new ArrayList<>();

        try (BufferedReader lector =
                     new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    expresiones.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo: " + nombreArchivo);
            System.out.println("Detalle: " + e.getMessage());
        }

        return expresiones;
    }
}
