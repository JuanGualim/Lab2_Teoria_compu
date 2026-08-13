/** Almacena una transición etiquetada entre dos estados. */
public class Transicion {
    public static final String EPSILON = "ε";

    private final Estado origen;
    private final Estado destino;
    private final String simbolo;

    public Transicion(Estado origen, Estado destino, String simbolo) {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException(
                    "Una transición necesita estado de origen y destino.");
        }
        if (simbolo == null || simbolo.isEmpty()) {
            throw new IllegalArgumentException(
                    "Una transición necesita un símbolo.");
        }
        this.origen = origen;
        this.destino = destino;
        this.simbolo = simbolo;
    }

    public Estado getOrigen() {
        return origen;
    }

    public Estado getDestino() {
        return destino;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public boolean esEpsilon() {
        return EPSILON.equals(simbolo);
    }

    @Override
    public String toString() {
        return origen + " --" + simbolo + "--> " + destino;
    }
}
