package stone;

import java.io.*;

public class TokenTest {
    public static void main(String[] args) throws IOException, ParseException {
        File file = new File("hello.su");
        System.out.println(file.getAbsolutePath());
        LineNumberReader reader =
                new LineNumberReader(new BufferedReader(new FileReader("hello.su")));
        Lexer lexer = new Lexer(reader);
        Token token;
        while ((token = lexer.read()) != Token.EOF) {
            System.out.println(token.getText());
        }
    }
}
