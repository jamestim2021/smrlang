package explore;

import stone.Environment;

import java.util.HashMap;

public class NestedEnv implements Environment {
    private HashMap<String, Object> values;
    private Environment outer;

    public NestedEnv() {
        this(null);
    }

    public NestedEnv(Environment e) {
        values = new HashMap<>();
        outer = e;
    }

    public void setOuter(Environment outer) {
        this.outer = outer;
    }

    @Override
    public Object get(String name) {
        Object value = values.get(name);
        if (value == null && outer != null) {
            return outer.get(name);
        } else {
            return value;
        }
    }

    public void putNew(String name, Object value) {
        values.put(name, value);
    }

    @Override
    public void put(String name, Object object) {

        Environment env = where(name);
        if (env == null) {
            env = this;
        }
        ((FuncEvaluator.EnvEx) env).putNew(name, object);
    }

    public Environment where(String name) {
        if (values.get(name) != null) {
            return this;
        } else if (outer == null) {
            return null;
        } else {
            return ((FuncEvaluator.EnvEx) outer).where(name);
        }
    }
}
