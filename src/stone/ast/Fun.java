package stone.ast;

import java.util.List;

public class Fun extends ASTList {
    public Fun(List<ASTree> l) {
        super(l);
    }

    public ParameterList parameters() {
        return (ParameterList) child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt) child(1);
    }

    @Override
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }
}
