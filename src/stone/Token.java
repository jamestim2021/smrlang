package stone;

public abstract class Token {

    public static final Token EOF = new Token(-1) {
    };

    public static final String EOL = "\\n";

    private int line;

    protected Token(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    protected boolean isIdentifier() {
        return false;
    }

    protected boolean isNumber() {
        return false;
    }

    protected boolean isString() {
        return false;
    }

    public int getNumber() {
        throw new StoneException("not number token");
    }

    public String getText() {
        return "";
    }

}
