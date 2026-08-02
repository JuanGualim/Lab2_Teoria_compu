import java.util.List;

public class ResultadoPostfix {

    // Expresión convertida a postfix
    private String postfix;

    // Lista con todos los pasos realizados
    private List<String> pasos;

    /**
     * Constructor de la clase.
     *
     * @param postfix Expresión en formato postfix.
     * @param pasos Lista con todos los pasos realizados.
     */
    public ResultadoPostfix(String postfix, List<String> pasos) {
        this.postfix = postfix;
        this.pasos = pasos;
    }

    /**
     * Devuelve la expresión en postfix.
     */
    public String getPostfix() {
        return postfix;
    }

    /**
     * Devuelve todos los pasos realizados.
     */
    public List<String> getPasos() {
        return pasos;
    }

}