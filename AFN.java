import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** AFN con un único estado inicial y un único estado de aceptación. */
public class AFN {
    private final Estado estadoInicial;
    private final Estado estadoAceptacion;
    private final Set<Estado> estados = new LinkedHashSet<>();
    private final List<Transicion> transiciones = new ArrayList<>();

    public AFN(Estado estadoInicial, Estado estadoAceptacion) {
        if (estadoInicial == null || estadoAceptacion == null) {
            throw new IllegalArgumentException(
                    "El AFN necesita estados inicial y de aceptación.");
        }
        this.estadoInicial = estadoInicial;
        this.estadoAceptacion = estadoAceptacion;
        estados.add(estadoInicial);
        estados.add(estadoAceptacion);
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public Estado getEstadoAceptacion() {
        return estadoAceptacion;
    }

    public Set<Estado> getEstados() {
        return Collections.unmodifiableSet(estados);
    }

    public List<Transicion> getTransiciones() {
        return Collections.unmodifiableList(transiciones);
    }

    /** Agrega todos los estados y transiciones de un fragmento de Thompson. */
    public void agregarAFN(AFN otro) {
        if (otro == null) {
            throw new IllegalArgumentException("No se puede agregar un AFN nulo.");
        }
        estados.addAll(otro.estados);
        transiciones.addAll(otro.transiciones);
    }

    public void agregarTransicion(Estado origen, Estado destino,
                                  String simbolo) {
        Transicion transicion = new Transicion(origen, destino, simbolo);
        estados.add(origen);
        estados.add(destino);
        transiciones.add(transicion);
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        for (Transicion transicion : transiciones) {
            texto.append(transicion).append(System.lineSeparator());
        }
        return texto.toString();
    }
}
