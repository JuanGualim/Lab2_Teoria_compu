import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ShuntingYard {

    public static ResultadoPostfix convertir(String expresion) {

        Stack<Character> pila = new Stack<>();

        StringBuilder postfix = new StringBuilder();

        List<String> pasos = new ArrayList<>();

        pasos.add("=================================");
        pasos.add("Inicio del algoritmo Shunting Yard");
        pasos.add("Expresión: " + expresion);
        pasos.add("=================================");

        for (int i = 0; i < expresion.length(); i++) {

            char actual = expresion.charAt(i);

            pasos.add("");
            pasos.add("---------------------------------");
            pasos.add("Leyendo: " + actual);

            //-------------------------------------------------
            // OPERANDO
            //-------------------------------------------------

            if (Operadores.esOperando(actual)) {

                postfix.append(actual);

                pasos.add("Es un operando.");
                pasos.add("Se agrega directamente al postfix.");

                pasos.add("Pila: " + pila);
                pasos.add("Postfix: " + postfix);

            }

            //-------------------------------------------------
            // PARÉNTESIS (
            //-------------------------------------------------

            else if (actual == '(') {

                pila.push(actual);

                pasos.add("Paréntesis de apertura.");
                pasos.add("Push -> (");

                pasos.add("Pila: " + pila);
                pasos.add("Postfix: " + postfix);

            }

            //-------------------------------------------------
            // PARÉNTESIS )
            //-------------------------------------------------

            else if (actual == ')') {

                pasos.add("Paréntesis de cierre.");

                while (!pila.isEmpty() && pila.peek() != '(') {

                    char operador = pila.pop();

                    postfix.append(operador);

                    pasos.add("Pop -> " + operador);
                    pasos.add("Postfix: " + postfix);

                }

                if (!pila.isEmpty()) {

                    pila.pop();

                    pasos.add("Se elimina '(' de la pila.");

                }

                pasos.add("Pila: " + pila);
                pasos.add("Postfix: " + postfix);

            }

            //-------------------------------------------------
            // OPERADORES
            //-------------------------------------------------

            else {

                pasos.add("Operador detectado.");
                pasos.add("Pendiente de implementar.");

            }

        }

        pasos.add("");
        pasos.add("---------------------------------");
        pasos.add("Fin de la expresión.");

        while (!pila.isEmpty()) {

            char operador = pila.pop();

            postfix.append(operador);

            pasos.add("Pop final -> " + operador);

        }

        pasos.add("Postfix final: " + postfix);

        return new ResultadoPostfix(
                postfix.toString(),
                pasos
        );

    }

}