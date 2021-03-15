package stone.ast;

import explore.NestedEnv;
import stone.Environment;

public class Function {
    private ParameterList parameterList;
    private BlockStmnt body;
    private Environment env;

    public Function(ParameterList parameterList, BlockStmnt body, Environment env) {
        this.parameterList = parameterList;
        this.body = body;
        this.env = env;
    }

    public ParameterList parameters() {
        return parameterList;
    }

    public BlockStmnt body() {
        return body;
    }

    public Environment makeEnv() {
        return new NestedEnv(env);
    }

    @Override
    public String toString() {
        return "<fun:" + hashCode() + ">";
    }
}

