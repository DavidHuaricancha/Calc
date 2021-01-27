package example.calculer;

import example.models.Calc;
import example.models.CalcV1;
import example.models.CalcV2;
import javax.script.*;

public class main {
    public static void main(String[] args) {

/*        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        String res = "0.2+0.8-0.20479999999999998+0.0221184-9.437184000000002E-4+1.34217728E-5";
        try {
            System.out.println(engine.eval(res));
        } catch (ScriptException e) {
            e.printStackTrace();
        }*/

        Calc c = Calc.insertScreen("0.2+(25*0.192)-(200*0.192^2)+(675*0.192^3)-(900*0.192^4)+(400*0.192^5)");
        c.resolveEquation();
        System.out.println(c);




    }
}
