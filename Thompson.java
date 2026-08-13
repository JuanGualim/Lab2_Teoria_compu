/** Construye un AFN recorriendo recursivamente el árbol sintáctico. */
public class Thompson {
    private int siguienteId;

    /**
     * Construye un AFN y reinicia la numeración en q0 para cada expresión.
     */
    public AFN construir(Nodo raiz) {
        if (raiz == null) {
            throw new IllegalArgumentException(
                    "No existe un árbol sintáctico para construir el AFN.");
        }
        siguienteId = 0;
        return construirRecursivamente(raiz);
    }

    private AFN construirRecursivamente(Nodo nodo) {
        if (nodo == null || nodo.getValor() == null
                || nodo.getValor().isEmpty()) {
            throw new IllegalArgumentException(
                    "El árbol contiene un nodo vacío o inválido.");
        }
        String valor = nodo.getValor();

        return switch (valor) {
            case "." -> concatenar(nodo);
            case "|" -> unir(nodo);
            case "*" -> cerraduraKleene(nodo);
            case "+" -> cerraduraPositiva(nodo);
            case "?" -> opcional(nodo);
            default -> crearSimbolo(nodo);
        };
    }

    private AFN crearSimbolo(Nodo nodo) {
        validarHoja(nodo);
        Estado inicio = nuevoEstado();
        Estado fin = nuevoEstado();
        AFN resultado = new AFN(inicio, fin);
        resultado.agregarTransicion(inicio, fin, nodo.getValor());
        return resultado;
    }

    private AFN concatenar(Nodo nodo) {
        validarBinario(nodo);
        AFN izquierdo = construirRecursivamente(nodo.getIzquierdo());
        AFN derecho = construirRecursivamente(nodo.getDerecho());
        AFN resultado = new AFN(izquierdo.getEstadoInicial(),
                derecho.getEstadoAceptacion());
        resultado.agregarAFN(izquierdo);
        resultado.agregarAFN(derecho);
        resultado.agregarTransicion(izquierdo.getEstadoAceptacion(),
                derecho.getEstadoInicial(), Transicion.EPSILON);
        return resultado;
    }

    private AFN unir(Nodo nodo) {
        validarBinario(nodo);
        Estado inicio = nuevoEstado();
        AFN izquierdo = construirRecursivamente(nodo.getIzquierdo());
        AFN derecho = construirRecursivamente(nodo.getDerecho());
        Estado fin = nuevoEstado();

        AFN resultado = new AFN(inicio, fin);
        resultado.agregarAFN(izquierdo);
        resultado.agregarAFN(derecho);
        resultado.agregarTransicion(inicio, izquierdo.getEstadoInicial(),
                Transicion.EPSILON);
        resultado.agregarTransicion(inicio, derecho.getEstadoInicial(),
                Transicion.EPSILON);
        resultado.agregarTransicion(izquierdo.getEstadoAceptacion(), fin,
                Transicion.EPSILON);
        resultado.agregarTransicion(derecho.getEstadoAceptacion(), fin,
                Transicion.EPSILON);
        return resultado;
    }

    private AFN cerraduraKleene(Nodo nodo) {
        validarUnario(nodo);
        Estado inicio = nuevoEstado();
        AFN interno = construirRecursivamente(nodo.getIzquierdo());
        Estado fin = nuevoEstado();

        AFN resultado = new AFN(inicio, fin);
        resultado.agregarAFN(interno);
        resultado.agregarTransicion(inicio, interno.getEstadoInicial(),
                Transicion.EPSILON);
        resultado.agregarTransicion(inicio, fin, Transicion.EPSILON);
        resultado.agregarTransicion(interno.getEstadoAceptacion(),
                interno.getEstadoInicial(), Transicion.EPSILON);
        resultado.agregarTransicion(interno.getEstadoAceptacion(), fin,
                Transicion.EPSILON);
        return resultado;
    }

    /** Construcción directa de R+: se ejecuta R al menos una vez. */
    private AFN cerraduraPositiva(Nodo nodo) {
        validarUnario(nodo);
        Estado inicio = nuevoEstado();
        AFN interno = construirRecursivamente(nodo.getIzquierdo());
        Estado fin = nuevoEstado();

        AFN resultado = new AFN(inicio, fin);
        resultado.agregarAFN(interno);
        resultado.agregarTransicion(inicio, interno.getEstadoInicial(),
                Transicion.EPSILON);
        resultado.agregarTransicion(interno.getEstadoAceptacion(),
                interno.getEstadoInicial(), Transicion.EPSILON);
        resultado.agregarTransicion(interno.getEstadoAceptacion(), fin,
                Transicion.EPSILON);
        return resultado;
    }

    /** Construcción directa de R?: permite ejecutar R o saltarlo con epsilon. */
    private AFN opcional(Nodo nodo) {
        validarUnario(nodo);
        Estado inicio = nuevoEstado();
        AFN interno = construirRecursivamente(nodo.getIzquierdo());
        Estado fin = nuevoEstado();

        AFN resultado = new AFN(inicio, fin);
        resultado.agregarAFN(interno);
        resultado.agregarTransicion(inicio, interno.getEstadoInicial(),
                Transicion.EPSILON);
        resultado.agregarTransicion(inicio, fin, Transicion.EPSILON);
        resultado.agregarTransicion(interno.getEstadoAceptacion(), fin,
                Transicion.EPSILON);
        return resultado;
    }

    private Estado nuevoEstado() {
        return new Estado(siguienteId++);
    }

    private static void validarHoja(Nodo nodo) {
        if (nodo.getIzquierdo() != null || nodo.getDerecho() != null
                || nodo.getValor() == null || nodo.getValor().isEmpty()) {
            throw new IllegalArgumentException(
                    "Operando inválido en el árbol sintáctico.");
        }
    }

    private static void validarBinario(Nodo nodo) {
        if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
            throw new IllegalArgumentException(
                    "El operador " + nodo.getValor()
                            + " necesita dos operandos.");
        }
    }

    private static void validarUnario(Nodo nodo) {
        if (nodo.getIzquierdo() == null || nodo.getDerecho() != null) {
            throw new IllegalArgumentException(
                    "El operador " + nodo.getValor()
                            + " necesita un operando.");
        }
    }
}
