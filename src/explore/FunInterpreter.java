package explore;

import stone.FuncParser;

public class FunInterpreter extends BasicInterpreter {
    public static void main(String[] args) {
        run(new FuncParser(), new NestedEnv());
    }
}
