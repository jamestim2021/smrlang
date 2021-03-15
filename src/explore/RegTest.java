package explore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {
    public static void main(String[] args) {

        Pattern compile = Pattern.compile("\\s*(([0-9]+)|([a-zA-Z]+))");
        Matcher matcher = compile.matcher("     123");
        if (matcher.lookingAt()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }

        Matcher matcher1 = compile.matcher("     asdfasdf");
        if (matcher1.lookingAt()) {
            System.out.println(matcher1.group(0));
            System.out.println(matcher1.group(1));
            System.out.println(matcher1.group(2));
            System.out.println(matcher1.group(3));
        }

    }
}
