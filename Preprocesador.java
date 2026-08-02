public class Preprocesador {

    /**
     * Agrega el operador de concatenación (.)
     * donde sea necesario.
     *
     * Ejemplos:
     *
     * ab      -> a.b
     * (a|b)c  -> (a|b).c
     * a(b|c)  -> a.(b|c)
     * a*b     -> a*.b
     */
    public static String insertarConcatenacion(String expresion) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < expresion.length(); i++) {

            char actual = expresion.charAt(i);

            resultado.append(actual);

            // Último carácter
            if (i == expresion.length() - 1) {
                continue;
            }

            char siguiente = expresion.charAt(i + 1);

            // No insertar después del carácter de escape
            if (actual == '\\') {
                continue;
            }

            if (debeConcatenar(actual, siguiente)) {
                resultado.append('.');
            }

        }

        return resultado.toString();

    }

    /**
     * Determina si entre dos caracteres
     * debe agregarse un operador de concatenación.
     */
    private static boolean debeConcatenar(char actual, char siguiente) {

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