package example.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Double.parseDouble;


public class CalcV2 {

    private String screen;
    private String result;

    public CalcV2() {
        screen = "";
        result = "0";
    }

    public CalcV2(String result, String scren) {
        this.screen = scren;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public static CalcV2 insertScreen(String screen) {

        CalcV2 calcV2 = new CalcV2();
        calcV2.setScreen(screen);

        return calcV2;
    }

    public void changeSign() {
        resolveEquation();
        Double num = Double.valueOf(getResult());
        Double signNegative;
        signNegative = num * -1;
        setResult(String.valueOf(signNegative));
    }

    //modificar
    public void resolvePercentage(String percentage) {
        resolveEquation();
        Double num = 0D;
        num = (Double.valueOf(getResult()) * Double.valueOf(percentage) / 100);
        setResult(String.valueOf(num));
    }

    public void resolveEquation() {

        setResult(getScreen().replace("-", "r"));

        if (getResult().contains(")")) {
            setResult(parenthesis(getResult()));
        }
        if (getResult().contains("^")) {
            setResult(power(getResult()));
        }
        if (getResult().contains("/")) {
            setResult(division(getResult()));
        }
        if (getResult().contains("*")) {
            setResult(multiplication(getResult()));
        }
        if (getResult().contains("+")) {
            setResult(addition(getResult()));
        }
        if (getResult().contains("r")) {
            setResult(subtraction(getResult()));
        }

    }

    private static String resolveParenthesis(String operation) {
        operation = (operation.replace("-", "r"));

        if (operation.contains("^")) {
            operation = (power(operation));
        }
        if (operation.contains("/")) {
            operation = (division(operation));
        }
        if (operation.contains("*")) {
            operation = (multiplication(operation));
        }
        if (operation.contains("+")) {
            operation = (addition(operation));
        }
        if (operation.contains("r")) {
            operation = (subtraction(operation));
        }

        return operation;
    }

    private String parenthesis(String operation) {
        while (operation.contains(")")) {
            String right = extractOperationRight(operation, ")");

            String extractedOperation = obtenerEcuacion(operation);

            String left = operation.substring(0, operation.length() - (extractedOperation.length() + 2 + right.length()));

            operation = resolveParenthesis(extractedOperation);

            operation = (left + operation + right);

        }
        return operation;
    }

    public static String multiplication(String operation) {
        //operation = operation.replace( "r","-" );
        while (operation.contains("*")) {
            String left = extractNumberLeftMul(operation, "*");
            String right = extractNumberRightMul(operation, "*");
            if (left.contains("r")) {
                left = extractNumberRightEre(left, "r");
            }
            if (right.contains("r")) {
                right = right.replace("r", "-");
            }
            if (left.contains("r")) {
                left = left.replace("r", "-");
            }
            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("*") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("*") + right.length() + 1);

            if (fragmentedLeft.contains("0.0")) {
                fragmentedLeft = fragmentedLeft.replace("0.0", "");
            }
            if (fragmentedRight.contains("0.0")) {
                fragmentedLeft = fragmentedLeft.replace("0.0", "");
            }

            if (left == "") {
                left = "0";
            }

            if (right == "") {
                right = "0";
            }

            operation = (fragmentedLeft + (parseDouble(left) * parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String division(String operation) {
        while (operation.contains("/")) {
            String left = extractNumberLeft(operation, "/");
            String right = extractNumberRight(operation, "/");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("/") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("/") + right.length() + 1);

            if (left == "") {
                left = "0";
            }

            if (right == "") {
                right = "0";
            }

            operation = (fragmentedLeft + (parseDouble(left) / parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String power(String operation) {
        while (operation.contains("^")) {
            String left = extractNumberLeftMul(operation, "^");
            String right = extractNumberRightMul(operation, "^");

            if (left.contains("r")) {
                left = extractNumberRightEre(left, "r");
            }
            if (right.contains("r")) {
                right = right.replace("r", "-");
            }
            if (left.contains("r")) {
                left = left.replace("r", "-");
            }

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("^") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("^") + right.length() + 1);

/*            if (fragmentedLeft.contains("0.0")) {
                fragmentedLeft = fragmentedLeft.replace("0.0", "");
            }
            if (fragmentedRight.contains("0.0")) {
                fragmentedLeft = fragmentedLeft.replace("0.0", "");
            }*/

            if (left == "") {
                left = "0";
            }

            if (right == "") {
                right = "0";
            }

            String nume;
            BigDecimal num;

            num = new BigDecimal(Math.pow(parseDouble(left), parseDouble(right))).stripTrailingZeros();
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(num)).setScale(5, RoundingMode.UP);

            operation = (fragmentedLeft + bigDecimal + fragmentedRight);
        }
        return operation;
    }

    public static String addition(String operation) {
        while (operation.contains("+")) {
            String left = extractNumberLeft(operation, "+");
            String right = extractNumberRight(operation, "+");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("+") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("+") + right.length() + 1);

            if (left == "") {
                left = "0";
            }

            if (right == "") {
                right = "0";
            }

            operation = (fragmentedLeft + (parseDouble(left) + parseDouble(right)) + fragmentedRight);
        }
        if (operation.contains("-")) {
            operation = operation.replace("-", "r");
            operation = subtraction(operation);
        }
        return operation;
    }

    public static String subtraction(String operation) {

        while (operation.contains("r")) {
            String left = extractNumberLeft(operation, "r");
            String right = extractNumberRight(operation, "r");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("r") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("r") + right.length() + 1);

            if (left == "") {
                left = "0";
            }

            if (right == "") {
                right = "0";
            }
            operation = (fragmentedLeft + (parseDouble(left) - parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String extractNumberLeft(String operation, String parameter) {
        String operationExtract = operation.substring(0, operation.indexOf(parameter));
        String number = "";

        for (int a = operationExtract.length() - 1; a >= 0; a--) {
            if ((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.') || (operationExtract.charAt(a) == '-')) {

                number = operationExtract.charAt(a) + number;
            }

            if (!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.') && (operationExtract.charAt(a) != '-')) {
                break;
            }
        }
        return number;
    }

    public static String extractNumberRightMul(String operation, String parameter) {
        String operationExtract = operation.substring(operation.indexOf(parameter) + 1);
        String number = "";
        for (int a = 0; a < operationExtract.length(); a++) {
            if ((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.') || (operationExtract.charAt(a) == 'r')) {
                number = number + operationExtract.charAt(a);
            }

            if (!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.') && (operationExtract.charAt(a) != 'r')) {
                break;
            }
        }
        return number;
    }

    public static String extractNumberLeftMul(String operation, String parameter) {
        String operationExtract = operation.substring(0, operation.indexOf(parameter));
        String number = "";

        for (int a = operationExtract.length() - 1; a >= 0; a--) {
            if ((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.') || (operationExtract.charAt(a) == '-') || (operationExtract.charAt(a) == 'r')) {

                number = operationExtract.charAt(a) + number;
            }

            if (!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.') && (operationExtract.charAt(a) != '-') && (operationExtract.charAt(a) != 'r')) {
                break;
            }
        }
        return number;
    }

    public static String extractNumberRight(String operation, String parameter) {
        String operationExtract = operation.substring(operation.indexOf(parameter) + 1);
        String number = "";
        for (int a = 0; a < operationExtract.length(); a++) {
            if ((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.')) {
                number = number + operationExtract.charAt(a);
            }

            if (!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.')) {
                break;
            }
        }
        return number;
    }

    public static String extractNumberRightEre(String operation, String parameter) {
        String operationExtract = operation.substring(operation.indexOf(parameter));
        String number = "";
        for (int a = 0; a < operationExtract.length(); a++) {
            if ((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.') || (operationExtract.charAt(a) == 'r')) {
                number = number + operationExtract.charAt(a);
            }

            if (!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != 'r') && (operationExtract.charAt(a) != '.')) {
                break;
            }
        }
        return number;
    }

    public static String obtenerEcuacion(String operation) {
        String cadena = "";
        String number = "";

        cadena = operation.substring(0, operation.length() - extractOperationRight(operation, ")").length() - 1);

        for (int a = cadena.length() - 1; a >= 0; a--) {
            if (operation.charAt(a) == '(') {
                break;
            }
            number = cadena.charAt(a) + number;

        }
        return number;
    }

    public static String extractOperationLeft(String operation, String parameter) {
        String operationExtract = operation.substring(0, operation.lastIndexOf(parameter));
        return operationExtract;
    }

    public static String extractOperationRight(String operation, String parameter) {
        String operationExtract = operation.substring(operation.indexOf(parameter) + 1);
        return operationExtract;
    }

    public String toString() {
        return "Calculer{" +
                "screen = '" + getScreen() + '\'' +
                ", result = '" + getResult() + '\'' +
                '}' + "\n";
    }
}
