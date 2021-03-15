package stone;

public interface Environment {
    Object get(String name);

    void put(String name, Object object);
}
