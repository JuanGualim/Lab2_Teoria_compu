import java.util.List;

/**
 * Punto de entrada del programa.
 *
 * Esta clase solamente coordina el trabajo: lee el archivo, recorre sus
 * expresiones y llama a ShuntingYard para realizar la conversión.
 */
public class Main {

    public static void main(String[] args) {
        // Si se proporciona un nombre por consola se usa ese archivo.
        // De lo contrario, se utiliza el archivo de ejemplo del proyecto.
        String nombreArchivo = args.length > 0 ? args[0] : "expresiones.txt";
        List<String> expresiones = Archivo.leerArchivo(nombreArchivo);

        for (String expresion : expresiones) {
            System.out.println("==================================");
            System.out.println("Expresión:");
            System.out.println(expresion);
            System.out.println();

            // Antes de aplicar Shunting Yard hacemos explícita la
            // concatenación que normalmente se omite en una expresión regular.
            String expresionFormateada =
                    ShuntingYard.insertarConcatenacion(expresion);

            System.out.println("Después de insertar concatenación:");
            System.out.println(expresionFormateada);
            System.out.println();
            System.out.println("Pasos:");
            System.out.println();

            // El método muestra el estado de la pila y del postfix en cada paso.
            String postfix =
                    ShuntingYard.convertirAPostfix(expresionFormateada);

            System.out.println("Postfix final:");
            System.out.println();
            System.out.println(postfix);
            System.out.println();
        }
    }
}
