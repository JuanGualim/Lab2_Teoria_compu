import java.util.Stack;

/** Construye un árbol sintáctico a partir de una expresión postfix. */
public class ArbolSintactico {

    /**
     * Construye el árbol usando una pila y muestra sus operaciones básicas.
     * Los operadores + y ? se conservan como operadores unarios.
     */
    public static Nodo construir(String postfix) {
        if (postfix == null || postfix.isEmpty()) {
            throw new IllegalArgumentException("La expresión postfix está vacía.");
        }

        Stack<Nodo> pila = new Stack<>();
        int posicion = 0;

        while (posicion < postfix.length()) {
            String simbolo = leerSimbolo(postfix, posicion);
            posicion += simbolo.length();

            System.out.println("Leer: " + simbolo);

            if (esOperadorBinario(simbolo)) {
                if (pila.size() < 2) {
                    throw new IllegalArgumentException(
                            "Faltan operandos para el operador " + simbolo + ".");
                }

                Nodo derecho = pila.pop();
                Nodo izquierdo = pila.pop();
                pila.push(new Nodo(simbolo, izquierdo, derecho));

                System.out.println("Acción: operador binario");
                System.out.println("Izquierdo: " + izquierdo.getValor());
                System.out.println("Derecho: " + derecho.getValor());
            } else if (esOperadorUnario(simbolo)) {
                if (pila.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Falta un operando para el operador " + simbolo + ".");
                }

                Nodo hijo = pila.pop();
                pila.push(new Nodo(simbolo, hijo, null));

                System.out.println("Acción: operador unario");
                System.out.println("Hijo: " + hijo.getValor());
            } else {
                pila.push(new Nodo(simbolo));
                System.out.println("Acción: crear hoja");
            }

            System.out.println("Pila de nodos: " + pila);
            System.out.println();
        }

        if (pila.size() != 1) {
            throw new IllegalArgumentException(
                    "Postfix inválido: quedaron " + pila.size()
                            + " nodos en la pila en vez de una raíz.");
        }

        return pila.pop();
    }

    /** Devuelve una representación textual sencilla del árbol. */
    public static String comoTexto(Nodo raiz) {
        if (raiz == null) {
            return "(árbol vacío)";
        }

        StringBuilder texto = new StringBuilder();
        texto.append(raiz.getValor()).append(System.lineSeparator());
        agregarHijos(raiz, "", texto);
        return texto.toString();
    }

    private static void agregarHijos(Nodo nodo, String prefijo,
                                     StringBuilder texto) {
        Nodo izquierdo = nodo.getIzquierdo();
        Nodo derecho = nodo.getDerecho();

        if (izquierdo != null) {
            boolean tieneHermano = derecho != null;
            texto.append(prefijo)
                    .append(tieneHermano ? "├── " : "└── ")
                    .append(izquierdo.getValor())
                    .append(System.lineSeparator());
            agregarHijos(izquierdo,
                    prefijo + (tieneHermano ? "│   " : "    "), texto);
        }

        if (derecho != null) {
            texto.append(prefijo)
                    .append("└── ")
                    .append(derecho.getValor())
                    .append(System.lineSeparator());
            agregarHijos(derecho, prefijo + "    ", texto);
        }
    }

    private static boolean esOperadorBinario(String simbolo) {
        return simbolo.equals("|") || simbolo.equals(".");
    }

    private static boolean esOperadorUnario(String simbolo) {
        return simbolo.equals("*") || simbolo.equals("+")
                || simbolo.equals("?");
    }

    /** Lee escapados y clases de caracteres como un solo operando. */
    private static String leerSimbolo(String expresion, int posicion) {
        char actual = expresion.charAt(posicion);

        if (actual == '\\' && posicion + 1 < expresion.length()) {
            return expresion.substring(posicion, posicion + 2);
        }

        if (actual == '[') {
            int cierre = expresion.indexOf(']', posicion + 1);
            if (cierre != -1) {
                return expresion.substring(posicion, cierre + 1);
            }
        }

        return String.valueOf(actual);
    }
}
