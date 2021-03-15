package stone;

import stone.ast.ASTree;

public class StoneException extends RuntimeException {

    public StoneException(String msg, ASTree token) {
        super(msg + " " + token.location());
    }

    public StoneException(String msg) {
        super(msg);
    }
}
