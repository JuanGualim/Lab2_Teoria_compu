public class Utilidades {

    /**
     * Verifica si un carácter es un símbolo de apertura.
     *
     * @param c Carácter a evaluar.
     * @return true si es (, [ o {
     */
    public static boolean esApertura(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    /**
     * Verifica si un carácter es un símbolo de cierre.
     *
     * @param c Carácter a evaluar.
     * @return true si es ), ] o }
     */
    public static boolean esCierre(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    /**
     * Verifica si un símbolo de apertura coincide
     * con su símbolo de cierre correspondiente.
     *
     * @param apertura Símbolo que salió de la pila.
     * @param cierre Símbolo leído de la expresión.
     * @return true si forman una pareja válida.
     */
    public static boolean coincide(char apertura, char cierre) {

        return (apertura == '(' && cierre == ')') ||
               (apertura == '[' && cierre == ']') ||
               (apertura == '{' && cierre == '}');

    }

}