package stone.ast;

import java.util.List;

public class PrimaryExpr extends ASTList {

    public PrimaryExpr(List<ASTree> l) {
        super(l);
    }

    public static ASTree create(List<ASTree> l) {
        return l.size() == 1 ? l.get(0) : new PrimaryExpr(l);
    }
}
