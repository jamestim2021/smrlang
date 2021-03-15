package explore;

import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import stone.Environment;
import stone.StoneException;
import stone.ast.*;
import sun.util.locale.provider.LocaleProviderAdapter;

import java.util.List;

@Require(BasicEvaluator.class)
@Reviser
public class FuncEvaluator {
    @Reviser
    public interface EnvEx extends Environment {

        void putNew(String name, Object value);

        Environment where(String name);

        void setOuter(Environment env);
    }

    @Reviser
    public static class DefEx extends DefStmnt {

        public DefEx(List<ASTree> l) {
            super(l);
        }

        public Object eval(Environment env) {
            ((EnvEx) env).putNew(name(), new Function(parameters(), body(), env));
            return name();
        }
    }

    @Reviser
    public static class PrimaryEx extends PrimaryExpr {

        public PrimaryEx(List<ASTree> l) {
            super(l);
        }

        public ASTree operand() {
            return child(0);
        }

        public Postfix postfix(int nest) {
            return (Postfix) child(numChildren() - nest - 1);
        }

        public boolean hasPostfix(int nest) {
            return numChildren() - nest > 1;
        }

        public Object eval(Environment env) {
            return evalSubExpr(env, 0);
        }

        public Object evalSubExpr(Environment env, int nest) {
            if (hasPostfix(nest)) {
                Object target = evalSubExpr(env, nest + 1);
                return ((PostfixEx) postfix(nest)).eval(env, target);
            } else {
                return ((BasicEvaluator.ASTreeEx) operand()).eval(env);
            }

        }
    }

    @Reviser
    public abstract static class PostfixEx extends Postfix {

        public PostfixEx(List<ASTree> c) {
            super(c);
        }

        public abstract Object eval(Environment env, Object value);
    }


    @Reviser
    public static class ArgumentsEx extends Arguments {

        public ArgumentsEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env, Object value) {

            if (!(value instanceof Function)) {
                throw new StoneException("bad function", this);
            }
            Function fun = (Function) value;
            ParameterList parameters = fun.parameters();
            if (parameters.size() != size()) {
                throw new StoneException("bad number of arguments", this);
            }
            Environment newEnv = fun.makeEnv();
            int num = 0;
            for (ASTree t : this) {
                ((ParamsEx) parameters).eval(newEnv, num++, ((BasicEvaluator.ASTreeEx) t).eval(env));
            }
            return ((BasicEvaluator.BlockEx) fun.body()).eval(newEnv);
        }
    }

    @Reviser
    public static class ParamsEx extends ParameterList {

        public ParamsEx(List<ASTree> l) {
            super(l);
        }

        public void eval(Environment env, int index, Object value) {
            ((EnvEx) env).putNew(name(index), value);
        }
    }

}
