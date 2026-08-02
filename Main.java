import java.util.List;

public class Main {

    public static void main(String[] args) {

        String archivo = "expresiones.txt";

        // Leer todas las expresiones
        List<String> expresiones = Archivo.leerArchivo(archivo);

        int numero = 1;

        for (String expresion : expresiones) {

            System.out.println("\n=================================================");
            System.out.println("EXPRESIÓN #" + numero);
            System.out.println("=================================================");

            //-----------------------------------------------------
            // Expresión original
            //-----------------------------------------------------

            System.out.println("Expresión original:");
            System.out.println(expresion);

            //-----------------------------------------------------
            // PREPROCESAMIENTO
            //-----------------------------------------------------

            ResultadoPreprocesamiento preprocesamiento =
                    Preprocesador.preprocesar(expresion);

            System.out.println("\nPREPROCESAMIENTO");

            for (String paso : preprocesamiento.getPasos()) {

                System.out.println(paso);

            }

            //-----------------------------------------------------
            // SHUNTING YARD
            //-----------------------------------------------------

            ResultadoPostfix resultado =
                    ShuntingYard.convertir(
                            preprocesamiento.getProcesada());

            System.out.println("\nSHUNTING YARD");

            for (String paso : resultado.getPasos()) {

                System.out.println(paso);

            }

            //-----------------------------------------------------
            // RESULTADO FINAL
            //-----------------------------------------------------

            System.out.println("\nPOSTFIX FINAL");

            System.out.println(resultado.getPostfix());

            numero++;

        }

        System.out.println("\n===============================================");
        System.out.println("FIN DEL PROGRAMA");
        System.out.println("===============================================");

    }

}