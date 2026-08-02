import java.util.ArrayList;
import java.util.List;

public class Tokenizador {

    /**
     * Convierte una expresión regular en una lista de tokens.
     */
    public static List<Token> tokenizar(String expresion) {

        List<Token> tokens = new ArrayList<>();

        int i = 0;

        while (i < expresion.length()) {

            char actual = expresion.charAt(i);

            //-------------------------------------------------
            // Espacios
            //-------------------------------------------------
            if (Character.isWhitespace(actual)) {
                i++;
                continue;
            }

            //-------------------------------------------------
            // Caracteres escapados
            //-------------------------------------------------
            if (actual == '\\') {

                if (i + 1 < expresion.length()) {

                    String escapado =
                            "\\" + expresion.charAt(i + 1);

                    tokens.add(
                            new Token(
                                    escapado,
                                    TipoToken.OPERANDO
                            )
                    );

                    i += 2;
                    continue;

                }

            }

            //-------------------------------------------------
            // Clases de caracteres
            //-------------------------------------------------
            if (actual == '[') {

                StringBuilder conjunto = new StringBuilder();

                conjunto.append(actual);

                i++;

                while (i < expresion.length()) {

                    conjunto.append(expresion.charAt(i));

                    if (expresion.charAt(i) == ']')
                        break;

                    i++;

                }

                tokens.add(
                        new Token(
                                conjunto.toString(),
                                TipoToken.OPERANDO
                        )
                );

                i++;
                continue;

            }

            //-------------------------------------------------
            // Palabras
            //-------------------------------------------------
            if (Character.isLetterOrDigit(actual)
                    || actual == '@'
                    || actual == '_') {

                StringBuilder palabra = new StringBuilder();

                while (i < expresion.length()) {

                    char c = expresion.charAt(i);

                    if (Character.isLetterOrDigit(c)
                            || c == '@'
                            || c == '_') {

                        palabra.append(c);
                        i++;

                    } else {

                        break;

                    }

                }

                tokens.add(
                        new Token(
                                palabra.toString(),
                                TipoToken.OPERANDO
                        )
                );

                continue;

            }

            //-------------------------------------------------
            // Paréntesis
            //-------------------------------------------------
            if (actual == '(') {

                tokens.add(
                        new Token(
                                "(",
                                TipoToken.PARENTESIS_IZQUIERDO
                        )
                );

                i++;
                continue;

            }

            if (actual == ')') {

                tokens.add(
                        new Token(
                                ")",
                                TipoToken.PARENTESIS_DERECHO
                        )
                );

                i++;
                continue;

            }

            //-------------------------------------------------
            // Operadores
            //-------------------------------------------------
            if (actual == '|'
                    || actual == '*'
                    || actual == '+'
                    || actual == '?'
                    || actual == '.') {

                tokens.add(
                        new Token(
                                String.valueOf(actual),
                                TipoToken.OPERADOR
                        )
                );

                i++;
                continue;

            }

            //-------------------------------------------------
            // Cualquier otro símbolo
            //-------------------------------------------------

            tokens.add(
                    new Token(
                            String.valueOf(actual),
                            TipoToken.OPERANDO
                    )
            );

            i++;

        }

        return insertarConcatenaciones(tokens);

    }

    /**
     * Inserta automáticamente los operadores de concatenación.
     */
    private static List<Token> insertarConcatenaciones(List<Token> entrada) {

        List<Token> salida = new ArrayList<>();

        for (int i = 0; i < entrada.size(); i++) {

            Token actual = entrada.get(i);

            salida.add(actual);

            if (i == entrada.size() - 1)
                continue;

            Token siguiente = entrada.get(i + 1);

            if (debeConcatenar(actual, siguiente)) {

                salida.add(
                        new Token(
                                ".",
                                TipoToken.OPERADOR
                        )
                );

            }

        }

        return salida;

    }

    /**
     * Determina si debe insertarse un operador
     * de concatenación.
     */
    private static boolean debeConcatenar(Token actual,
                                        Token siguiente) {

        boolean izquierda =

                actual.getTipo() == TipoToken.OPERANDO

                        ||

                actual.getTipo() == TipoToken.PARENTESIS_DERECHO

                        ||

                (actual.getTipo() == TipoToken.OPERADOR

                        &&

                (actual.getValor().equals("*")
                || actual.getValor().equals("+")
                || actual.getValor().equals("?")));

        boolean derecha =

                siguiente.getTipo() == TipoToken.OPERANDO

                        ||

                siguiente.getTipo() == TipoToken.PARENTESIS_IZQUIERDO;

        return izquierda && derecha;

    }

}