package explore;

import javassist.gluonj.util.Loader;

public class EnvRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(BasicInterpreter.class, args, BasicEvaluator.class);
    }
}
