package explore;

public class ClosureInterpreter extends BasicInterpreter {
    public static void main(String[] args) {
        run(new ClosureParser(), new NestedEnv());
    }
}

