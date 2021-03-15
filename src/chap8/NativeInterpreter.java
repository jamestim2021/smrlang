package chap8;

import explore.BasicInterpreter;
import explore.ClosureParser;
import explore.NestedEnv;
import stone.ParseException;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(),
            new Natives().environment(new NestedEnv()));
    }
}
