package explore;

import javassist.gluonj.util.Loader;

public class FuncRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(FunInterpreter.class, args, FuncEvaluator.class);
    }
}
