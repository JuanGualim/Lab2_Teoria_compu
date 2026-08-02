import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Nombre del archivo que contiene las expresiones
        String nombreArchivo = "expresiones.txt";

        // Leer todas las expresiones del archivo
        List<String> expresiones = Archivo.leerArchivo(nombreArchivo);

        int balanceadas = 0;
        int noBalanceadas = 0;
        int numeroExpresion = 1;

        // Procesar cada expresión del archivo
        for (String expresion : expresiones) {

            System.out.println("==================================================");
            System.out.println("EXPRESIÓN #" + numeroExpresion);
            System.out.println("Cadena: " + expresion);
            System.out.println("==================================================");

            // Llamar al balanceador
            Resultado resultado = Balanceador.balancear(expresion);

            // Mostrar todos los pasos realizados
            System.out.println("\nPasos realizados:");

            for (String paso : resultado.getPasos()) {
                System.out.println(paso);
            }

            // Mostrar el resultado final
            if (resultado.isBalanceada()) {
                System.out.println("\nResultado: EXPRESIÓN BALANCEADA");
                balanceadas++;
            } else {
                System.out.println("\nResultado: EXPRESIÓN NO BALANCEADA");
                noBalanceadas++;
            }

            System.out.println();
            numeroExpresion++;
        }

        // Mostrar un resumen al finalizar
        System.out.println("==================================================");
        System.out.println("RESUMEN");
        System.out.println("==================================================");
        System.out.println("Total de expresiones: " + expresiones.size());
        System.out.println("Balanceadas: " + balanceadas);
        System.out.println("No balanceadas: " + noBalanceadas);
    }
}