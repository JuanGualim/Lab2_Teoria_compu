import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ShuntingYard {

    public static ResultadoPostfix convertir(List<Token> tokens) {

        Stack<Token> pila = new Stack<>();

        List<String> pasos = new ArrayList<>();

        StringBuilder salida = new StringBuilder();

        pasos.add("Inicio de Shunting Yard");
        pasos.add("");

        for (Token token : tokens) {

            pasos.add("-------------------------------------");
            pasos.add("Token leído: " + token.getValor());

            switch (token.getTipo()) {

                //-------------------------------------------------
                // OPERANDO
                //-------------------------------------------------

                case OPERANDO:

                    salida.append(token.getValor());

                    pasos.add("Operando -> pasa directamente a la salida.");

                    break;

                //-------------------------------------------------
                // (
                //-------------------------------------------------

                case PARENTESIS_IZQUIERDO:

                    pila.push(token);

                    pasos.add("Push (");

                    break;

                //-------------------------------------------------
                // )
                //-------------------------------------------------

                case PARENTESIS_DERECHO:

                    while (!pila.isEmpty()
                            && pila.peek().getTipo()
                            != TipoToken.PARENTESIS_IZQUIERDO) {

                        salida.append(pila.pop().getValor());

                    }

                    if (!pila.isEmpty()) {

                        pila.pop();

                    }

                    pasos.add("Se procesó ')'.");

                    break;

                //-------------------------------------------------
                // OPERADOR
                //-------------------------------------------------

                case OPERADOR:

                    while (!pila.isEmpty()

                            && pila.peek().getTipo()
                            == TipoToken.OPERADOR

                            && Operadores.precedencia(
                                    pila.peek().getValor())

                            >=

                            Operadores.precedencia(
                                    token.getValor())) {

                        salida.append(
                                pila.pop().getValor());

                    }

                    pila.push(token);

                    pasos.add("Push operador "
                            + token.getValor());

                    break;

            }

            pasos.add("Pila: " + pila);

            pasos.add("Salida: " + salida);

        }

        //-----------------------------------------------------

        while (!pila.isEmpty()) {

            salida.append(
                    pila.pop().getValor());

        }

        pasos.add("-------------------------------------");
        pasos.add("Fin del algoritmo");

        pasos.add("Postfix final: "
                + salida);

        return new ResultadoPostfix(
                salida.toString(),
                pasos
        );

    }

}