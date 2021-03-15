package explore;

import stone.FuncParser;
import stone.Parser;
import stone.ast.Fun;

public class ClosureParser extends FuncParser {

    public ClosureParser() {
        primary.insertChoice(Parser.rule(Fun.class).sep("fun").ast(paramList).ast(block));
    }

}
