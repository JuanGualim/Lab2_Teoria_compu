import java.util.ArrayList;
import java.util.List;

public class Preprocesador {

    /**
     * Preprocesa una expresión regular.
     *
     * Actualmente:
     * - Inserta concatenaciones explícitas.
     * - Registra todos los pasos.
     */
    public static ResultadoPreprocesamiento preprocesar(String expresion) {

        List<String> pasos = new ArrayList<>();

        pasos.add("Expresión original:");
        pasos.add(expresion);
        pasos.add("");

        String expandida = expandirOperadores(expresion, pasos);

        String procesada =
                insertarConcatenacion(expandida, pasos);

        pasos.add("");
        pasos.add("Expresión después del preprocesamiento:");
        pasos.add(procesada);

        return new ResultadoPreprocesamiento(
                expresion,
                procesada,
                pasos
        );

    }

    /**
     * Inserta el operador .
     */
    private static String insertarConcatenacion(String expresion,
                                                List<String> pasos) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < expresion.length(); i++) {

            char actual = expresion.charAt(i);

            resultado.append(actual);

            if (i == expresion.length() - 1)
                continue;

            char siguiente = expresion.charAt(i + 1);

            if (actual == '\\')
                continue;

            if (debeConcatenar(actual, siguiente)) {

                resultado.append('.');

                pasos.add("Se inserta '.' entre '" +
                        actual +
                        "' y '" +
                        siguiente +
                        "'");

            }

        }

        return resultado.toString();

    }

    /**
     * Convierte operadores extendidos.
     *
     * a+  -> aa*
     * a?  -> (a|ε)
     */
    private static String expandirOperadores(String expresion,
                                            List<String> pasos) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < expresion.length(); i++) {

            char actual = expresion.charAt(i);

            // Operador +
            if (actual == '+') {

                if (resultado.length() > 0) {

                    char anterior = resultado.charAt(resultado.length() - 1);

                    resultado.append(anterior);
                    resultado.append('*');

                    pasos.add("Expansión '+' : "
                            + anterior
                            + "+"
                            + " -> "
                            + anterior
                            + anterior
                            + "*");

                }

            }

            // Operador ?
            else if (actual == '?') {

                if (resultado.length() > 0) {

                    char anterior = resultado.charAt(resultado.length() - 1);

                    resultado.deleteCharAt(resultado.length() - 1);

                    resultado.append("(");
                    resultado.append(anterior);
                    resultado.append("|ε)");

                    pasos.add("Expansión '?' : "
                            + anterior
                            + "?"
                            + " -> ("
                            + anterior
                            + "|ε)");

                }

            }

            else {

                resultado.append(actual);

            }

        }

        return resultado.toString();

    }

    /**
     * Decide si entre dos símbolos
     * debe agregarse una concatenación.
     */
    private static boolean debeConcatenar(char actual,
                                          char siguiente) {

        boolean izquierda =
                Operadores.esOperando(actual)
                        || actual == ')'
                        || actual == '*'
                        || actual == '+'
                        || actual == '?';

        boolean derecha =
                Operadores.esOperando(siguiente)
                        || siguiente == '(';

        return izquierda && derecha;

    }

}