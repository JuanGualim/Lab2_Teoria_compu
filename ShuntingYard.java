import java.util.Stack;

/**
 * Implementación sencilla del algoritmo de Shunting Yard para las expresiones
 * regulares del laboratorio.
 *
 * No intenta ser un compilador completo. Solamente reconoce operandos,
 * paréntesis, los operadores |, ., *, + y ?, caracteres escapados y clases de
 * caracteres sencillas como [ae]. Todo el procesamiento está en esta clase.
 */
public class ShuntingYard {

    /**
     * Inserta el operador punto cuando hay una concatenación implícita.
     *
     * Ejemplo: (a|b)c se transforma en (a|b).c
     *
     * Para decidir si hace falta un punto se observan dos símbolos vecinos:
     * el primero debe poder terminar un operando y el segundo debe poder
     * comenzar uno.
     *
     * @param expresion expresión regular original
     * @return expresión con todas las concatenaciones visibles
     */
    public static String insertarConcatenacion(String expresion) {
        StringBuilder resultado = new StringBuilder();
        int posicion = 0;

        while (posicion < expresion.length()) {
            // Un símbolo puede ser un carácter, un escapado (\x) o una clase
            // completa ([ae]). No se crea una clase Token para mantener simple
            // la solución.
            String actual = leerSimbolo(expresion, posicion);
            resultado.append(actual);
            posicion += actual.length();

            if (posicion < expresion.length()) {
                String siguiente = leerSimbolo(expresion, posicion);

                if (puedeTerminarOperando(actual)
                        && puedeComenzarOperando(siguiente)) {
                    resultado.append('.');
                }
            }
        }

        return resultado.toString();
    }

    /**
     * Convierte a postfix una expresión que ya contiene los puntos de
     * concatenación. Además imprime el estado del algoritmo después de leer
     * cada símbolo.
     *
     * @param expresion expresión con concatenaciones explícitas
     * @return expresión equivalente en formato postfix
     */
    public static String convertirAPostfix(String expresion) {
        Stack<Character> pila = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        int posicion = 0;

        while (posicion < expresion.length()) {
            String simbolo = leerSimbolo(expresion, posicion);
            posicion += simbolo.length();

            // Los escapados y las clases de caracteres son operandos completos.
            // Por ejemplo, \( no se confunde con un paréntesis de agrupación.
            if (esSimboloCompuesto(simbolo)) {
                postfix.append(simbolo);
            } else {
                char caracter = simbolo.charAt(0);

                if (caracter == '(') {
                    // El paréntesis izquierdo se guarda como separador.
                    pila.push(caracter);
                } else if (caracter == ')') {
                    // Se vacían los operadores hasta encontrar el '('.
                    while (!pila.isEmpty() && pila.peek() != '(') {
                        postfix.append(pila.pop());
                    }

                    // Los paréntesis no forman parte de la salida postfix.
                    if (!pila.isEmpty()) {
                        pila.pop();
                    }
                } else if (esOperador(caracter)) {
                    // Antes de apilar el operador actual, pasan a la salida los
                    // operadores de igual o mayor precedencia.
                    while (!pila.isEmpty()
                            && pila.peek() != '('
                            && precedencia(pila.peek()) >= precedencia(caracter)) {
                        postfix.append(pila.pop());
                    }

                    pila.push(caracter);
                } else {
                    // Cualquier otro carácter es un operando y va directamente
                    // a la salida.
                    postfix.append(caracter);
                }
            }

            mostrarPaso(simbolo, pila, postfix);
        }

        // Al terminar la lectura, los operadores pendientes pasan al postfix.
        while (!pila.isEmpty()) {
            char caracter = pila.pop();

            // Esta condición evita copiar un '(' si la entrada estuviera mal
            // balanceada. No se realiza validación sintáctica completa.
            if (caracter != '(') {
                postfix.append(caracter);
            }
        }

        return postfix.toString();
    }

    /**
     * Obtiene el símbolo que comienza en la posición indicada.
     *
     * Un carácter escapado, por ejemplo \( o \n, se devuelve junto con su
     * barra. Una clase como [ae03] también se devuelve completa para tratarla
     * como un único operando.
     */
    private static String leerSimbolo(String expresion, int posicion) {
        char actual = expresion.charAt(posicion);

        // La barra protege exactamente al carácter que aparece después.
        if (actual == '\\' && posicion + 1 < expresion.length()) {
            return expresion.substring(posicion, posicion + 2);
        }

        // Soporte básico para las clases usadas en las expresiones del PDF.
        if (actual == '[') {
            int cierre = expresion.indexOf(']', posicion + 1);

            if (cierre != -1) {
                return expresion.substring(posicion, cierre + 1);
            }
        }

        return String.valueOf(actual);
    }

    /** Indica si el símbolo puede aparecer antes de una concatenación. */
    private static boolean puedeTerminarOperando(String simbolo) {
        if (esSimboloCompuesto(simbolo)) {
            return true;
        }

        char caracter = simbolo.charAt(0);
        return caracter == ')'
                || caracter == '*'
                || caracter == '+'
                || caracter == '?'
                || !esOperador(caracter) && caracter != '(';
    }

    /** Indica si el símbolo puede aparecer después de una concatenación. */
    private static boolean puedeComenzarOperando(String simbolo) {
        if (esSimboloCompuesto(simbolo)) {
            return true;
        }

        char caracter = simbolo.charAt(0);
        return caracter == '(' || !esOperador(caracter) && caracter != ')';
    }

    /**
     * Los operadores soportados son unión, concatenación y los tres operadores
     * unarios postfix usados por las expresiones del laboratorio.
     */
    private static boolean esOperador(char caracter) {
        return caracter == '|'
                || caracter == '.'
                || caracter == '*'
                || caracter == '+'
                || caracter == '?';
    }

    /**
     * Precedencias solicitadas:
     * *, + y ? tienen prioridad 3; . prioridad 2; | prioridad 1.
     */
    private static int precedencia(char operador) {
        if (operador == '*' || operador == '+' || operador == '?') {
            return 3;
        }
        if (operador == '.') {
            return 2;
        }
        if (operador == '|') {
            return 1;
        }
        return 0;
    }

    /** Reconoce escapados y clases de caracteres, ambos operandos atómicos. */
    private static boolean esSimboloCompuesto(String simbolo) {
        return simbolo.length() > 1
                && (simbolo.charAt(0) == '\\' || simbolo.charAt(0) == '[');
    }

    /** Imprime el estado solicitado después de procesar un símbolo. */
    private static void mostrarPaso(String simbolo,
                                    Stack<Character> pila,
                                    StringBuilder postfix) {
        System.out.println("Leer " + simbolo);
        System.out.println("Pila:");
        System.out.println(contenidoPila(pila));
        System.out.println("Postfix:");
        System.out.println(postfix);
        System.out.println();
        System.out.println("----------------");
        System.out.println();
    }

    /** Convierte la pila en texto sin corchetes ni comas adicionales. */
    private static String contenidoPila(Stack<Character> pila) {
        StringBuilder contenido = new StringBuilder();

        // Stack itera desde el fondo hasta el tope, que es suficiente para
        // observar claramente el contenido durante la demostración.
        for (char caracter : pila) {
            contenido.append(caracter);
        }

        return contenido.toString();
    }
}
