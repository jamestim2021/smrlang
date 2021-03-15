package stone;

import javassist.compiler.Parser;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static String regexPat
            = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
            + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<Token> queue = new ArrayList<>();

    private boolean hasMore = false;

    private LineNumberReader reader;

    public Lexer(LineNumberReader reader) {
        hasMore = true;
        this.reader = reader;
    }

    public Token read() throws ParseException {
        if (fillQueue(0)) {
            return queue.remove(0);
        } else {
            return Token.EOF;
        }
    }

    public Token peek(int i) throws ParseException {
        if (fillQueue(i)) {
            return queue.get(i);
        } else {
            return Token.EOF;
        }
    }

    private boolean fillQueue(int i) throws ParseException {
        // 如果 index 小于 size 的话，所需要的 element 就在 queue 里面，直接取就可以了
        // 如果 index >= size 的话，所需要的 element 就不在 queue 里面，需要送 stream 里面取读取才行
        // 如果没有更多，就返回 false
        while (i >= queue.size()) {
            if (hasMore) {
                readLine();
            } else {
                return false;
            }
        }
        return true;
    }


    public void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }

        int lineNumber = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNumber, matcher);
                pos = matcher.end();
            } else {
                throw new ParseException("bad token at line: " + lineNumber);
            }
        }
        queue.add(new IdToken(lineNumber, Token.EOL));
    }

    private String toStringLiteral(String str) {
        StringBuilder sb = new StringBuilder();
        int len = str.length() - 1;
        for (int i = 1; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '\\' && i < len - 1) {
                char ch2 = str.charAt(i + 1);
                if (ch2 == '\\' || ch2 == '"') {
                    ch = str.charAt(i++);
                } else if (ch2 == 'n') {
                    i++;
                    ch = '\n';
                }
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    private void addToken(int lineNumber, Matcher matcher) {
        String m = matcher.group(1);
        if (m != null) { // not a space
            if (matcher.group(2) == null) {
                Token token;
                if (matcher.group(3) != null) { // number
                    token = new NumberToken(lineNumber, Integer.parseInt(m));
                } else if (matcher.group(4) != null) { // string
                    token = new StringToken(lineNumber, toStringLiteral(m));
                } else { // identifier
                    token = new IdToken(lineNumber, m);
                }
                queue.add(token);
            }
        }
    }

    protected static class NumberToken extends Token {
        private int value;

        protected NumberToken(int lineNumber, int value) {
            super(lineNumber);
            this.value = value;
        }

        public boolean isNumber() {
            return true;
        }

        public int getNumber() {
            return value;
        }

        public String getText() {
            return Integer.toString(value);
        }
    }

    protected static class IdToken extends Token {
        private String text;

        protected IdToken(int lineNumber, String text) {
            super(lineNumber);
            this.text = text;
        }

        protected boolean isIdentifier() {
            return true;
        }

        public String getText() {
            return text;
        }
    }

    private static class StringToken extends Token {
        private String literal;

        public StringToken(int lineNumber, String str) {
            super(lineNumber);
            this.literal = str;
        }

        public boolean isString() {
            return true;
        }

        public String getText() {
            return literal;
        }
    }
}
