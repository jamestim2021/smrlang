package explore;

import stone.Environment;

import java.util.HashMap;

public class BasicEnv implements Environment {
    private HashMap<String, Object> values = new HashMap();

    @Override
    public Object get(String name) {
        return values.get(name);
    }

    @Override
    public void put(String name, Object object) {
        values.put(name, object);
    }
}
