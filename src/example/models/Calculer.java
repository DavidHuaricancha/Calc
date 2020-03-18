package example.models;

import java.math.BigDecimal;

import static java.lang.Double.*;
import static java.math.BigDecimal.*;
import static java.lang.String.format;

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

    public boolean ejecutar(){
//        while (getScreen().contains(")")){
//
//        }
        setResult(getScreen());

        if (getResult().contains("*")){
            setResult(multiplication(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("/")){
            setResult(division(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("-")){
            setResult(subtraction(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("+")){
            setResult(addition(getResult()));
            System.out.println(getResult());
        }


        return true;
    }

    public static String  multiplication(String operation){
        while (operation.contains("*")) {
            String left = extractNumberLeft(operation, "*");
            String right = extractNumberRight(operation, "*");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("*") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("*") + right.length() + 1);

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
            operation = (fragmentedLeft + (parseDouble(left) + parseDouble(right)) + fragmentedRight);
        }
        return operation;
    }

    public static String  subtraction(String operation){

        while (operation.contains("-")) {
            String left = extractNumberLeft(operation, "-");
            String right = extractNumberRight(operation, "-");

            String fragmentedLeft = "";
            String fragmentedRight = "";

            fragmentedLeft = operation.substring(0, operation.indexOf("-") - left.length());
            fragmentedRight = operation.substring(operation.indexOf("-") + right.length() + 1);
            operation = (fragmentedLeft + (parseDouble(left) - parseDouble(right)) + fragmentedRight);

        }
        return format(operation);
    }

    public static String extractNumberLeft(String operation, String parameter){
        String operationExtract = operation.substring(0, operation.indexOf(parameter));
        String number="";

        for(int a = operationExtract.length()-1; a >= 0; a-- ){
            if((Character.isDigit(operationExtract.charAt(a))) || (operationExtract.charAt(a) == '.')){

                number = operationExtract.charAt(a)+number;
            }

            if(!Character.isDigit(operationExtract.charAt(a)) && (operationExtract.charAt(a) != '.')) {
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

    public String toString() {
        return "Calculer{" +
                "screen = '" + getScreen()  + '\'' +
                ", result = '" + getResult() + '\'' +
                '}' + "\n";
    }
}
