package example.models;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calc {

    private String screen;
    private String result;

    public Calc(){
        this.screen = "";
        this.result = "0";
    }

    public Calc(String screen, String result) {
        this.screen = screen;
        this.result = result;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static Calc insertScreen(String screen){
        Calc calc = new Calc();
        calc.setScreen(screen);

        return calc;
    }

    public void resolveEquation() {

        setResult(getScreen().replace("-", "r"));

        if (getResult().contains(")")) {
            setResult(parenthesis(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("^")) {
            setResult(power(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("/")) {
            setResult(division(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("*")) {
            setResult(multiplication(getResult()));
            System.out.println(getResult());
        }
        if (getResult().contains("+")||getResult().contains("r")) {
            setResult(operationBasic(getResult()));
        }
    }

    private String resolveEquation(String equation){

        if (equation.contains("^")) {
            equation = (power(equation));
        }
        if (equation.contains("/")) {
            equation = (division(equation));
        }
        if (equation.contains("*")) {
            equation = (multiplication(equation));
        }
        if (getResult().contains("+")||getResult().contains("r")) {
            equation = (operationBasic(equation));
        }

        return equation;
    }

    private String operationBasic(String equation){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            String res = String.valueOf(engine.eval(equation.replace('r','-')));
            return res;
        } catch (ScriptException e) {
            //e.printStackTrace();
        }
        return null;
    }

    private String power(String equation){
        while (equation.contains("^")){
            String numberRight = extractRight(equation, '^');
            String numberLeft = extractLeft(equation, '^');
            String fragmentedRight = extractRight(equation,'^',numberRight.length()+1);
            String fragmentedLeft = extractLeft(equation,'^',numberLeft.length()*-1);

            numberRight = filter(numberRight);
            numberLeft = filter(numberLeft);

            equation = (fragmentedLeft+(Math.pow(Double.valueOf(numberLeft),Double.valueOf(numberRight)))+fragmentedRight);
        }
        return equation;
    }

    private String division(String equation){
        while (equation.contains("/")){
            String numberRight = extractRight(equation, '/');
            String numberLeft = extractLeft(equation, '/');
            String fragmentedRight = extractRight(equation,'/',numberRight.length()+1);
            String fragmentedLeft = extractLeft(equation,'/',numberLeft.length()*-1);

            numberRight = filter(numberRight);
            numberLeft = filter(numberLeft);

            equation = (fragmentedLeft+Double.valueOf(numberLeft)/Double.valueOf(numberRight)+fragmentedRight);
        }
        return equation;
    }

    private String multiplication(String equation){
        while (equation.contains("*")){
            String numberRight = extractRight(equation, '*');
            String numberLeft = extractLeft(equation, '*');
            String fragmentedRight = extractRight(equation,'*',numberRight.length()+1);
            String fragmentedLeft = extractLeft(equation,'*',numberLeft.length()*-1);


            numberRight = filter(numberRight);
            numberLeft = filter(numberLeft);
            equation = (fragmentedLeft+Double.valueOf(numberLeft)*Double.valueOf(numberRight)+fragmentedRight);
        }
        return equation;
    }


    private String parenthesis(String operation){

        while (operation.contains(")")){
           String right = extractRight(operation, ')', 1);
           String left = extractLeft(operation, '(',0);
           String equation = extractOperation(operation);
           String resultEquationExtracted = resolveEquation(equation);
           operation = (left+resultEquationExtracted+right);
        }

        return operation;
    }

    public String extractNumLeft(String operation){
        String number = "";
        int b=0;
        for (int a = 0; a < operation.length(); a++) {
            if ((operation.charAt(a) == '+') || (operation.charAt(a) == 'r')) {
                return operation.substring(0,a);
            }
        }
        return null;
    }

    public String extractNumRight(String operation){
        String number = "";
        int b=0;
        for (int a = 0; a < operation.length(); a++) {
            if ((operation.charAt(a) == '+') || (operation.charAt(a) == 'r')) {
                number = operation.substring(a,operation.length());
                System.out.println(number);
                number = extractRight(number);
                System.out.println("ok"+number);
                return number;
            }
        }
        return null;
    }

    public String extractRight(String operation, char parameter, int numNex ){
        String operationExtract = operation.substring(operation.indexOf(parameter) + numNex);
        return operationExtract;
    }

    public String extractLeft(String operation, char parameter,int numNex ){
        String operationExtract = operation.substring(0, operation.indexOf(parameter) + numNex);
        return operationExtract;
    }

    public String extractRight(String operation){

        String number = "";
        for (int a = 0; a < operation.length(); a++) {
            if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == 'r') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+')) {
                number = number + operation.charAt(a);
            }else {
                break;
            }
        }

        return number;
    }
    public String extractRight(String operation, char parameter ){
        operation = operation.substring(operation.indexOf(parameter) + 1);
        String number = "";
        if(parameter != '+' && parameter != 'r'){
            for (int a = 0; a < operation.length(); a++) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+') || (operation.charAt(a) == 'r')  ) {
                    number = number + operation.charAt(a);
                }else {
                    break;
                }
            }
        }else {
            for (int a = 0; a < operation.length(); a++) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+')) {
                    number = number + operation.charAt(a);
                }else {
                    break;
                }
            }
        }
        return number;
    }
    public String extractRightSum(String operation, char parameter ){
        operation = operation.substring(operation.indexOf(parameter) );
        String number = "";
        if(parameter != '+' && parameter != 'r'){
            for (int a = 0; a < operation.length(); a++) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+') || (operation.charAt(a) == 'r')  ) {
                    number = number + operation.charAt(a);
                }else {
                    break;
                }
            }
        }else {
            for (int a = 0; a < operation.length(); a++) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+')) {
                    number = number + operation.charAt(a);
                }else {
                    break;
                }
            }
        }
        return number;
    }

    public String extractLeftSum(String operation, char parameter ){
        operation = operation.substring(0, operation.indexOf(parameter));
        String number = "";

        if (parameter != '+' && parameter != 'r') {
            for (int a = operation.length() - 1; a >= 0; a--) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+') || (operation.charAt(a) == 'r')  ) {
                    number = operation.charAt(a) + number;
                }else{
                    break;
                }
            }
        }else {
            for (int a = operation.length() - 1; a >= 0; a--) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+')) {
                    number = operation.charAt(a) + number;
                }else{
                    break;
                }
            }
        }
        return number;
    }

    public String extractLeft(String operation, char parameter ){
        operation = operation.substring(0, operation.indexOf(parameter));
        String number = "";

        if (parameter != '+' && parameter != 'r') {
            for (int a = operation.length() - 1; a >= 0; a--) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+') || (operation.charAt(a) == 'r')  ) {
                    number = operation.charAt(a) + number;
                }else{
                    break;
                }
            }
        }else {
            for (int a = operation.length() - 1; a >= 0; a--) {
                if ((Character.isDigit(operation.charAt(a))) || (operation.charAt(a) == '.') || (operation.charAt(a) == '-') || (operation.charAt(a) == 'E') || (operation.charAt(a) == '+')) {
                    number = operation.charAt(a) + number;
                }else{
                    break;
                }
            }
        }
        return number;
    }

    public String extractOperation(String operation){
        String fragment  = operation.substring(0, operation.length() - extractRight(operation, ')',1).length() - 1);
        fragment = extractRight(fragment,'(',1);

        return fragment.trim();
    }

    public String filter(String operation){
        return operation.replace('r','-');
    }

    @Override
    public String toString() {
        return "Calc{" +
                "screen='" + screen + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
