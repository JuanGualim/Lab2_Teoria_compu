import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/** Coordina desde la lectura de la expresión hasta la simulación del AFN. */
public class Main {

    public static void main(String[] args) {
        String nombreArchivo = args.length > 0 ? args[0] : "expresiones.txt";
        List<String> expresiones = Archivo.leerArchivo(nombreArchivo);

        if (expresiones.isEmpty()) {
            System.out.println("No hay expresiones para procesar.");
            return;
        }

        Scanner entrada = new Scanner(System.in);
        for (int i = 0; i < expresiones.size(); i++) {
            procesarExpresion(expresiones.get(i), i + 1, entrada);
        }
    }

    private static void procesarExpresion(String expresion, int numero,
                                           Scanner entrada) {
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

            Thompson thompson = new Thompson();
            AFN afn = thompson.construir(raiz);

            System.out.println("CONSTRUCCIÓN DEL AFN:");
            System.out.println("Algoritmo de Thompson aplicado correctamente.");
            System.out.println();
            System.out.println("ESTADOS:");
            System.out.println(ordenarEstados(afn.getEstados()));
            System.out.println();
            System.out.println("ESTADO INICIAL:");
            System.out.println(afn.getEstadoInicial());
            System.out.println();
            System.out.println("ESTADO DE ACEPTACIÓN:");
            System.out.println(afn.getEstadoAceptacion());
            System.out.println();
            System.out.println("TRANSICIONES:");
            System.out.println();
            System.out.print(afn);
            System.out.println();

            VisualizadorAFN.mostrar(afn, "Expresión " + numero);

            System.out.println("Ingrese una cadena w para probar:");
            String cadena = entrada.hasNextLine() ? entrada.nextLine() : "";
            System.out.println(cadena.isEmpty() ? "w = ε" : "w = " + cadena);
            System.out.println();
            System.out.println("Simulando...");

            SimuladorAFN simulador = new SimuladorAFN();
            boolean aceptada = simulador.simular(afn, cadena);
            System.out.println();
            System.out.println("Estados finales alcanzados:");
            System.out.println(ordenarEstados(simulador.getEstadosFinales()));
            System.out.println();
            System.out.println("¿w pertenece a L(r)?");
            System.out.println();
            System.out.println(aceptada ? "si" : "no");
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo procesar la expresión: "
                    + e.getMessage());
        }
    }

    /** Ordena únicamente para que la salida q0, q1, q2... sea fácil de leer. */
    private static List<Estado> ordenarEstados(Iterable<Estado> estados) {
        List<Estado> ordenados = new ArrayList<>();
        for (Estado estado : estados) {
            ordenados.add(estado);
        }
        ordenados.sort(Comparator.comparingInt(Estado::getId));
        return ordenados;
    }
}
