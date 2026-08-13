import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.Set;

/** Simula un AFN incluyendo todas sus transiciones epsilon. */
public class SimuladorAFN {
    private Set<Estado> estadosFinales = Collections.emptySet();

    public boolean simular(AFN afn, String cadena) {
        validarAFN(afn);
        if (cadena == null) {
            throw new IllegalArgumentException("La cadena w no puede ser nula.");
        }

        Set<Estado> inicial = new LinkedHashSet<>();
        inicial.add(afn.getEstadoInicial());
        Set<Estado> actuales = epsilonClosure(afn, inicial);

        // Se recorre por puntos de código para que cada símbolo Unicode de w
        // sea tratado como una unidad.
        for (int posicion = 0; posicion < cadena.length();) {
            int codigo = cadena.codePointAt(posicion);
            String simbolo = new String(Character.toChars(codigo));
            posicion += Character.charCount(codigo);

            Set<Estado> siguientes = new LinkedHashSet<>();
            for (Estado estado : actuales) {
                for (Transicion transicion : afn.getTransiciones()) {
                    if (transicion.getOrigen() == estado
                            && !transicion.esEpsilon()
                            && coincide(transicion.getSimbolo(), simbolo)) {
                        siguientes.add(transicion.getDestino());
                    }
                }
            }
            actuales = epsilonClosure(afn, siguientes);
        }

        estadosFinales = Collections.unmodifiableSet(actuales);
        return actuales.contains(afn.getEstadoAceptacion());
    }

    /** Estados alcanzados después de la última llamada a simular. */
    public Set<Estado> getEstadosFinales() {
        return estadosFinales;
    }

    /** Calcula el cierre epsilon evitando ciclos mediante un conjunto. */
    public Set<Estado> epsilonClosure(AFN afn, Set<Estado> estados) {
        validarAFN(afn);
        if (estados == null) {
            throw new IllegalArgumentException(
                    "El conjunto de estados no puede ser nulo.");
        }
        for (Estado estado : estados) {
            if (estado == null || !afn.getEstados().contains(estado)) {
                throw new IllegalArgumentException(
                        "El cierre contiene un estado que no pertenece al AFN.");
            }
        }

        Set<Estado> cierre = new LinkedHashSet<>(estados);
        Deque<Estado> pendientes = new ArrayDeque<>(estados);

        while (!pendientes.isEmpty()) {
            Estado actual = pendientes.pop();
            for (Transicion transicion : afn.getTransiciones()) {
                if (transicion.getOrigen() == actual
                        && transicion.esEpsilon()
                        && cierre.add(transicion.getDestino())) {
                    pendientes.push(transicion.getDestino());
                }
            }
        }
        return cierre;
    }

    private static boolean coincide(String etiqueta, String simbolo) {
        // Los escapados existentes representan literalmente el carácter
        // posterior a la barra, por ejemplo \*.
        if (etiqueta.length() == 2 && etiqueta.charAt(0) == '\\') {
            return etiqueta.substring(1).equals(simbolo);
        }

        // Soporte sencillo para las clases ya aceptadas por Shunting Yard.
        if (etiqueta.startsWith("[") && etiqueta.endsWith("]")) {
            String contenido = etiqueta.substring(1, etiqueta.length() - 1);
            return contenido.contains(simbolo);
        }
        return etiqueta.equals(simbolo);
    }

    private static void validarAFN(AFN afn) {
        if (afn == null || afn.getEstados().isEmpty()
                || afn.getEstadoInicial() == null
                || afn.getEstadoAceptacion() == null) {
            throw new IllegalArgumentException("El AFN está vacío o es inválido.");
        }
    }
}
