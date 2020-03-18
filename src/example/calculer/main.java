package example.calculer;

import example.controllers.CCalculer;
import example.models.Calculer;

public class main {
    public static void main(String[] args) {
        int temp = 0;
        Calculer c = CCalculer.insertScreen("5-10*3");
        c.ejecutar();
        System.out.println(c);

//        String left = "5";
//        String right = "10";
//        String resul = String.valueOf(Double.parseDouble(left)- Double.parseDouble(right));
//        System.out.println(4+resul);


//        String a = "10.0";
//        String b = "5.0";
//
//
//        System.out.println(Double.parseDouble(b)-Double.parseDouble(a));

//        Double num1 = Double.parseDouble("5");
//        Double num2 = Double.parseDouble("10");
//        String a ="";
//        String b ="";
//
//        String u ="a";
//
//        String resultado= (String.valueOf(num1-num2));
//
//        System.out.println(Double.parseDouble(u));

    }
}
