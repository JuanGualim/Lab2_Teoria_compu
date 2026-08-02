import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Balanceador {

    /**
     * Verifica si una expresión está balanceada.
     *
     * @param expresion Expresión a evaluar.
     * @return Objeto Resultado con el estado y los pasos realizados.
     */
    public static Resultado balancear(String expresion) {

        Stack<Character> pila = new Stack<>();
        List<String> pasos = new ArrayList<>();

        pasos.add("Iniciando análisis...");
        pasos.add("Expresión: " + expresion);
        pasos.add("");

        // Recorrer toda la expresión
        for (int i = 0; i < expresion.length(); i++) {

            char actual = expresion.charAt(i);

            pasos.add("--------------------------------------");
            pasos.add("Leyendo carácter: " + actual);

            // ===============================
            // Símbolo de apertura
            // ===============================
            if (Utilidades.esApertura(actual)) {

                pila.push(actual);

                pasos.add("Push -> " + actual);
                pasos.add("Estado de la pila: " + pila);

            }

            // ===============================
            // Símbolo de cierre
            // ===============================
            else if (Utilidades.esCierre(actual)) {

                // No existe apertura
                if (pila.isEmpty()) {

                    pasos.add("ERROR");
                    pasos.add("Se encontró '" + actual + "' pero la pila está vacía.");

                    return new Resultado(false, pasos);

                }

                char apertura = pila.pop();

                pasos.add("Pop -> " + apertura);

                if (!Utilidades.coincide(apertura, actual)) {

                    pasos.add("ERROR");
                    pasos.add("No coincide '" + apertura + "' con '" + actual + "'.");

                    return new Resultado(false, pasos);

                }

                pasos.add("Los símbolos coinciden.");
                pasos.add("Estado de la pila: " + pila);

            }

            // ===============================
            // Cualquier otro carácter
            // ===============================
            else {

                pasos.add("Carácter ignorado.");

            }

        }

        pasos.add("--------------------------------------");

        // Verificación final
        if (pila.isEmpty()) {

            pasos.add("La pila terminó vacía.");
            pasos.add("La expresión está correctamente balanceada.");

            return new Resultado(true, pasos);

        } else {

            pasos.add("La pila NO quedó vacía.");
            pasos.add("Faltan símbolos de cierre.");
            pasos.add("Estado final de la pila: " + pila);

            return new Resultado(false, pasos);

        }

    }

}