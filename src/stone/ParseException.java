package stone;

public class ParseException extends RuntimeException {
    private Token t;

    public ParseException(String msg, Token t) {
        super(msg + ",\"" + t.getText() + "\" at line: " + t.getLine());
    }

    public ParseException(Token t) {
        this.t = t;
    }

    public ParseException(Exception e) {
        super(e);
    }

    public ParseException(String msg) {
        super(msg);
    }
}
