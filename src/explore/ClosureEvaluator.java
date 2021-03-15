package explore;

import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.Environment;
import stone.ast.ASTree;
import stone.ast.Fun;
import stone.ast.Function;

import java.util.List;

@Require(FuncEvaluator.class)
@Reviser
public class ClosureEvaluator {

    @Reviser
    public static class FunEx extends Fun {

        public FunEx(List<ASTree> l) {
            super(l);
        }

        public Object eval(Environment env) {
            return new Function(parameters(), body(), env);
        }
    }
}
