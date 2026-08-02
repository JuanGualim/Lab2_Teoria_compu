public class Token {

    private String valor;

    private TipoToken tipo;

    public Token(String valor, TipoToken tipo) {

        this.valor = valor;
        this.tipo = tipo;

    }

    public String getValor() {
        return valor;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return valor;
    }

}