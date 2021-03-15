package explore;

import com.sun.javafx.image.IntPixelGetter;
import com.sun.org.apache.xml.internal.utils.StopParseException;
import javassist.gluonj.Reviser;
import stone.Environment;
import stone.Parser;
import stone.StoneException;
import stone.Token;
import stone.ast.*;

import java.sql.ClientInfoStatus;
import java.util.List;

@Reviser
public class BasicEvaluator {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    @Reviser
    public static abstract class ASTreeEx extends ASTree {
        public abstract Object eval(Environment env);
    }

    @Reviser
    public static class ASTListEx extends ASTList {

        public ASTListEx(List<ASTree> l) {
            super(l);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class ASTLeafEx extends ASTLeaf {
        public ASTLeafEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }

    @Reviser
    public static class NumberLiteralEx extends NumberLiteral {

        public NumberLiteralEx(Token t) {
            super(t);
        }

        public Object eval(Environment env) {
            return value();
        }
    }

    @Reviser
    public static class StringEx extends StringLiteral {
        public StringEx(Token t) {
            super(t);
        }

        public Object eval(Environment e) {
            return value();
        }
    }

    @Reviser
    public static class NameEx extends Name {

        public NameEx(Token token) {
            super(token);
        }

        public Object eval(Environment e) {
            Object value = e.get(name());
            if (value == null) {
                throw new StoneException("undefined name: " + name(), this);
            } else {
                return value;
            }
        }
    }

    @Reviser
    public static class NegativeExprEx extends NegativeExpr {

        public NegativeExprEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment e) {
            Object value = ((ASTreeEx) operand()).eval(e);
            if (value instanceof Integer) {
                return -((Integer) value);
            } else {
                throw new StoneException("bad type for -", this);
            }
        }
    }

    @Reviser
    public static class BinaryEx extends BinaryExpr {

        public BinaryEx(List<ASTree> l) {
            super(l);
        }

        public Object eval(Environment e) {
            String operator = operator();
            if ("=".equals(operator)) {
                Object value = ((ASTreeEx) right()).eval(e);
                return computeAssign(e, value);
            } else {
                Object leftValue = ((ASTreeEx) left()).eval(e);
                Object rightValue = ((ASTreeEx) right()).eval(e);
                return computeOp(leftValue, operator, rightValue);
            }
        }

        protected Object computeAssign(Environment e, Object value) {
            ASTree left = left();
            if (left instanceof Name) {
                e.put(((Name) left).name(), value);
                return value;
            } else {
                throw new StoneException("bad assignment", this);
            }
        }

        private Object computeOp(Object left, String op, Object right) {
            if (left instanceof Integer && right instanceof Integer) {
                return computeNumber((Integer) left, op, (Integer) right);
            } else if ("+".equals(op)) {
                return String.valueOf(left) + String.valueOf(right);
            } else if ("==".equals(op)) {
                if (left == null) {
                    return right == null ? TRUE : FALSE;
                } else {
                    return left.equals(right) ? TRUE : FALSE;
                }
            } else {
                throw new StoneException("bad type", this);
            }
        }

        protected Object computeNumber(Integer left, String op, Integer right) {
            int l = left.intValue();
            int r = right.intValue();
            if ("+".equals(op)) {
                return l + r;
            } else if ("-".equals(op)) {
                return l - r;
            } else if ("*".equals(op)) {
                return l * r;
            } else if ("/".equals(op)) {
                return l / r;
            } else if ("%".equals(op)) {
                return l % r;
            } else if ("==".equals(op)) {
                return l == r ? TRUE : FALSE;
            } else if (">".equals(op)) {
                return l > r ? TRUE : FALSE;
            } else if ("<".equals(op)) {
                return l < r ? TRUE : FALSE;
            } else {
                throw new StoneException("bad operator", this);
            }
        }
    }

    @Reviser
    public static class BlockEx extends BlockStmnt {

        public BlockEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object result = 0;
            for (ASTree t : this) {
                if (!(t instanceof NullStmnt)) {
                    result = ((ASTreeEx) t).eval(env);
                }
            }
            return result;
        }
    }


    @Reviser
    public static class IfEx extends IfStmnt {

        public IfEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object cond = ((ASTreeEx) condition()).eval(env);
            if (cond instanceof Integer && (Integer) cond != FALSE) {
                return ((ASTreeEx) thenBlock()).eval(env);
            } else {
                ASTree elseBlock = elseBlock();
                if (elseBlock == null) {
                    return 0;
                } else {
                    return ((ASTreeEx) elseBlock).eval(env);
                }
            }
        }
    }

    @Reviser
    public static class WhileEx extends WhileStmnt {

        public WhileEx(List<ASTree> c) {
            super(c);
        }

        public Object eval(Environment env) {
            Object result = 0;
            for (; ; ) {
                Object cond = ((ASTreeEx) condition()).eval(env);
                if (cond instanceof Integer && (Integer) cond == FALSE) {
                    return result;
                } else {
                    result = ((ASTreeEx) body()).eval(env);
                }
            }
        }
    }

}
