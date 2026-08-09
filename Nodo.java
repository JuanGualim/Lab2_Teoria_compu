/**
 * Representa un nodo del árbol sintáctico.
 * Los operandos son hojas y los operadores tienen uno o dos hijos.
 */
public class Nodo {
    private final String valor;
    private final Nodo izquierdo;
    private final Nodo derecho;

    public Nodo(String valor) {
        this(valor, null, null);
    }

    public Nodo(String valor, Nodo izquierdo, Nodo derecho) {
        this.valor = valor;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public String getValor() {
        return valor;
    }

    public Nodo getIzquierdo() {
        return izquierdo;
    }

    public Nodo getDerecho() {
        return derecho;
    }

    @Override
    public String toString() {
        return valor;
    }
}
