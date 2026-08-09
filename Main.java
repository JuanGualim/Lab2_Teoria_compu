import java.util.List;

/** Coordina la lectura, conversión, construcción y visualización del árbol. */
public class Main {

    public static void main(String[] args) {
        String nombreArchivo = args.length > 0 ? args[0] : "expresiones.txt";
        List<String> expresiones = Archivo.leerArchivo(nombreArchivo);

        if (expresiones.isEmpty()) {
            System.out.println("No hay expresiones para procesar.");
            return;
        }

        int numero = 1;
        for (String expresion : expresiones) {
            procesarExpresion(expresion, numero);
            numero++;
        }
    }

    private static void procesarExpresion(String expresion, int numero) {
        System.out.println("==========================================");
        System.out.println("EXPRESIÓN " + numero);
        System.out.println("==========================================");
        System.out.println();
        System.out.println("INFIX:");
        System.out.println(expresion);
        System.out.println();

        try {
            String formateada = ShuntingYard.insertarConcatenacion(expresion);
            System.out.println("CONCATENACIÓN EXPLÍCITA:");
            System.out.println(formateada);
            System.out.println();

            // false evita repetir la traza detallada del laboratorio anterior.
            String postfix = ShuntingYard.convertirAPostfix(formateada, false);
            System.out.println("POSTFIX:");
            System.out.println(postfix);
            System.out.println();

            System.out.println("CONSTRUCCIÓN DEL ÁRBOL:");
            System.out.println();
            Nodo raiz = ArbolSintactico.construir(postfix);

            System.out.println("RAÍZ:");
            System.out.println(raiz.getValor());
            System.out.println();
            System.out.println("ÁRBOL:");
            System.out.println();
            System.out.println(ArbolSintactico.comoTexto(raiz));

            VisualizadorArbol.mostrar(raiz, "Expresión " + numero);
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo procesar la expresión: "
                    + e.getMessage());
        }
    }
}
