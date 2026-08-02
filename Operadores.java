public class Operadores {

    /**
     * Verifica si un carácter es un operador
     * de una expresión regular.
     */
    public static boolean esOperador(char c) {

        return c == '|'
                || c == '.'
                || c == '*'
                || c == '+'
                || c == '?';

    }

    /**
     * Verifica si un carácter es un paréntesis.
     */
    public static boolean esParentesis(char c) {

        return c == '(' || c == ')';

    }

    /**
     * Devuelve la precedencia de un operador.
     *
     * Mayor número = mayor prioridad.
     */
    public static int precedencia(char operador) {

        switch (operador) {

            case '*':
            case '+':
            case '?':
                return 3;

            case '.':
                return 2;

            case '|':
                return 1;

            default:
                return 0;

        }

    }

    /**
     * Determina si un carácter debe tratarse como operando.
     *
     * Todo lo que no sea operador ni paréntesis
     * se considera un operando.
     */
    public static boolean esOperando(char c) {

        return !esOperador(c)
                && !esParentesis(c);

    }

}