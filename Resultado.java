import java.util.List;

public class Resultado {

    // Indica si la expresión está balanceada o no
    private boolean balanceada;

    // Guarda todos los pasos realizados por el algoritmo
    private List<String> pasos;

    /**
     * Constructor de la clase.
     *
     * @param balanceada true si la expresión está balanceada.
     * @param pasos Lista con todos los pasos realizados.
     */
    public Resultado(boolean balanceada, List<String> pasos) {
        this.balanceada = balanceada;
        this.pasos = pasos;
    }

    /**
     * Devuelve si la expresión está balanceada.
     */
    public boolean isBalanceada() {
        return balanceada;
    }

    /**
     * Devuelve la lista de pasos realizados.
     */
    public List<String> getPasos() {
        return pasos;
    }

}