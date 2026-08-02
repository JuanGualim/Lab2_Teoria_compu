public class Operadores {

    public static boolean esOperador(String operador) {

        return operador.equals("|")
                || operador.equals(".")
                || operador.equals("*")
                || operador.equals("+")
                || operador.equals("?");

    }

    public static int precedencia(String operador) {

        switch (operador) {

            case "*":
            case "+":
            case "?":
                return 3;

            case ".":
                return 2;

            case "|":
                return 1;

            default:
                return 0;

        }

    }

}