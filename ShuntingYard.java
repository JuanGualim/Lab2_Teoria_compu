import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ShuntingYard {

    /**
     * Convierte una expresión infix a postfix.
     *
     * @param expresion Expresión ya preprocesada.
     * @return Resultado con postfix y pasos.
     */
    public static ResultadoPostfix convertir(String expresion) {

        Stack<Character> pila = new Stack<>();

        StringBuilder postfix = new StringBuilder();

        List<String> pasos = new ArrayList<>();

        pasos.add("Inicio del algoritmo");
        pasos.add("Expresión: " + expresion);
        pasos.add("");

        // Aquí irá el algoritmo

        return new ResultadoPostfix(
                postfix.toString(),
                pasos
        );

    }

}