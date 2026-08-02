import java.util.List;

public class Main {

    public static void main(String[] args) {

        String archivo = "expresiones.txt";

        List<String> expresiones =
                Archivo.leerArchivo(archivo);

        int contador = 1;

        for (String expresion : expresiones) {

            System.out.println();
            System.out.println("===========================================");
            System.out.println("EXPRESIÓN " + contador);
            System.out.println("===========================================");

            System.out.println("Entrada:");
            System.out.println(expresion);

            //-----------------------------------------
            // TOKENIZACIÓN
            //-----------------------------------------

            List<Token> tokens =
                    Tokenizador.tokenizar(expresion);

            System.out.println();
            System.out.println("TOKENS");

            for (Token t : tokens) {

                System.out.println(
                        t.getValor()
                        + " -> "
                        + t.getTipo());

            }

            //-----------------------------------------
            // SHUNTING YARD
            //-----------------------------------------

            ResultadoPostfix resultado =
                    ShuntingYard.convertir(tokens);

            System.out.println();
            System.out.println("PASOS");

            for (String paso :
                    resultado.getPasos()) {

                System.out.println(paso);

            }

            //-----------------------------------------
            // RESULTADO
            //-----------------------------------------

            System.out.println();
            System.out.println("POSTFIX");

            System.out.println(
                    resultado.getPostfix());

            contador++;

        }

    }

}