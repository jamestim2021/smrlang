package explore;

import stone.*;
import stone.ast.ASTree;
import stone.ast.NullStmnt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;

public class BasicInterpreter {

    public static void main(String[] args) {
        run(new BasicParser(), new BasicEnv());
    }

    public static void run(BasicParser parser, Environment env) {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader("hello.su"));
        } catch (FileNotFoundException e) {
            throw new ParseException("file not file");
        }

        Lexer lexer = new Lexer(reader);
        while (lexer.peek(0) != Token.EOF) {
            ASTree t = parser.parse(lexer);
            if (!(t instanceof NullStmnt)) {
                Object result = ((BasicEvaluator.ASTreeEx) t).eval(env);
                System.out.println("=> " + result);
            }
        }
    }
}
