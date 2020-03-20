package example.models;

import static java.lang.Double.*;

public class Calculer {

    private String screen;
    private String result;

    public Calculer(){
        result="0";
        screen="";
    }

    public Calculer(String result, String scren) {
        this.result = result;
        this.screen = scren;
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

    public static Calculer insertScreen(String screen){

        Calculer calculer= new Calculer();

        calculer.setScreen(screen);

        return  calculer;
    }

    public void ejecutarChangeSign(){
        Calculer cal = new Calculer();
        cal.setScreen(getScreen());
        cal.ejecutarEquation();
        Double num = Double.valueOf(cal.getResult());
        Double signoNegative;
        signoNegative = num*-1;
        setResult(String.valueOf(signoNegative));
    }

    public void  ejecutarPercentage(String percentage){


        Calculer cal = new Calculer();
        cal.setScreen(getScreen());
        cal.ejecutarEquation();
        Double num=0D;

        num = (Double.valueOf(cal.getResult())*Double.valueOf(percentage)/100);
        setResult(String.valueOf(num));
    }

    public void ejecutarEquation(){
        setResult(getScreen().replace("-","r"));

        if(getResult().contains(")")){
            setResult(parenthesis(getResult()));
        }
        if (getResult().contains("/")){
            setResult(division(getResult()));
        }
        if (getResult().contains("*")){
            setResult(multiplication(getResult()));
        }
        if (getResult().contains("r")){
            setResult(subtraction(getResult()));
        }
        if (getResult().contains("+")){
            setResult(addition(getResult()));
        }
    }

    public static String ejecutarParenthesis(String operation){
        operation = (operation.replace("-","r"));

        if (operation.contains("/")){
            operation =(division(operation));
        }
        if (operation.contains("*")){
            operation = (multiplication(operation));
        }
        if (operation.contains("r")){
            operation =(subtraction(operation));
        }
        if (operation.contains("+")){
            operation =(addition(operation));
        }

        return operation;
    }

    public static String parenthesis(String operation){
        while (operation.contains(")")) {
            String right = extractOperationRight(operation, ")");

            String operationExtract = "";

            operationExtract = obtenerEcuacion(operation);
            String left = operation.substring(0,operation.length()-(operationExtract.length()+2+right.length()));

            operation = ejecutarParenthesis(operationExtract);

            operation = (left + operation + right);

        }
        return operation;
    }

    public static String  multiplication(String operation){
        while (operation.contains("*")) {
            String left = extractNumberLeft(operation, "*");
            String right = extractNumberRight(operation, "*");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("*") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("*") + right.length() + 1);

            if(left == ""){
                left="0";
            }

            if(right == ""){
                right="0";
            }

            operation = (fragmentedLeft + (parseDouble(left) * parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String  division(String operation){
        while (operation.contains("/")) {
            String left = extractNumberLeft(operation, "/");
            String right = extractNumberRight(operation, "/");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("/") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("/") + right.length() + 1);

            if(left == ""){
                left="0";
            }

            if(right == ""){
                right="0";
            }

            operation = (fragmentedLeft + (parseDouble(left) / parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String  addition(String operation){
        while (operation.contains("+")) {
            String left = extractNumberLeft(operation, "+");
            String right = extractNumberRight(operation, "+");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("+") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("+") + right.length() + 1);

            if(left == ""){
                left="0";
            }

            if(right == ""){
                right="0";
            }

            operation = (fragmentedLeft + (parseDouble(left) + parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String  subtraction(String operation){

        while (operation.contains("r")) {
            String left = extractNumberLeft(operation, "r");
            String right = extractNumberRight(operation, "r");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("r") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("r") + right.length() + 1);

            if(left == ""){
                left="0";
            }

            if(right == ""){
                right="0";
            }

            operation = (fragmentedLeft + (parseDouble(left) - parseDouble(right)) + fragmentedRight);

        }
        return operation;
    }

    public static String extractNumberLeft(String operation, String parameter){
        String operationExtract = operation.substring(0, operation.indexOf(parameter));
        String number="";

        for(int a = operationExtract.length()-1; a >= 0; a-- ){
            if((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.') || (operationExtract.charAt(a) == '-')){

                number = operationExtract.charAt(a)+number;
            }

            if(!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.') && (operationExtract.charAt(a) != '-') ) {
               break;
            }
        }
        return number;
    }

    public static String extractNumberRight(String operation, String parameter){
        String operationExtract = operation.substring(operation.indexOf(parameter)+1);
        String number="";

        for(int a = 0; a < operationExtract.length(); a++ ){
            if((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.')){

                number = number+operationExtract.charAt(a);;
            }

            if(!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.')) {
                break;
            }
        }
        return number;
    }

    public static String obtenerEcuacion(String operation){
        String cadena="";
        String number="";

        cadena = operation.substring(0,operation.length()-extractOperationRight(operation,")").length()-1);

        for(int a = cadena.length()-1; a >= 0; a-- ){
            if (operation.charAt(a) == '('){
                break;
            }
            number = cadena.charAt(a)+number;;
        }
        return number;
    }

    public static String extractOperationLeft(String operation, String parameter){
        String operationExtract = operation.substring(0,operation.lastIndexOf(parameter));
        return operationExtract;
    }

    public static String extractOperationRight(String operation, String parameter){
        String operationExtract = operation.substring(operation.indexOf(parameter)+1);
        return operationExtract;
    }

    public String toString() {
        return "Calculer{" +
                "screen = '" + getScreen()  + '\'' +
                ", result = '" + getResult() + '\'' +
                '}' + "\n";
    }
}
