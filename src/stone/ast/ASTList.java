package stone.ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
    private List<ASTree> children;

    public ASTList(List<ASTree> l) {
        this.children = l;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int numChildren() {
        return children.size();
    }

    @Override
    public Iterator<ASTree> children() {
        return children.iterator();
    }

    @Override
    public String location() {
        for (ASTree t : children) {
            String location = t.location();
            if (location != null) {
                return location;
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        String sep = "";
        for (ASTree t : children) {
            sb.append(sep);
            sep = " ";
            sb.append(t.toString());
        }
        sb.append(")");
        return sb.toString();
    }
}
