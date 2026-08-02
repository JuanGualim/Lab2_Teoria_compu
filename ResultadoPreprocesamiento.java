import java.util.List;

public class ResultadoPreprocesamiento {

    private String original;
    private String procesada;
    private List<String> pasos;

    public ResultadoPreprocesamiento(String original,
                                     String procesada,
                                     List<String> pasos) {

        this.original = original;
        this.procesada = procesada;
        this.pasos = pasos;

    }

    public String getOriginal() {
        return original;
    }

    public String getProcesada() {
        return procesada;
    }

    public List<String> getPasos() {
        return pasos;
    }

}