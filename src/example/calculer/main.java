package example.calculer;

import example.controllers.CCalculer;
import example.models.Calculer;

public class main {
    public static void main(String[] args) {
        Calculer c = CCalculer.insertScreen("(5-10)");
//        c.ejecutarPercentage("20");//Metodo para porcentaje
//        c.ejecutarChangeSign();//Metodo para cambio de signo
//        c.ejecutarEquation();//Metodo para ejecutar cualquier ecuacion de este formato: ((4+3.6)+5)*89*(45-2)
        System.out.println(c);
    }
}
