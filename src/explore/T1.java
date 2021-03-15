package explore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class T1 {
    public static void main(String[] args) {
        System.out.println(
                "fuck you "
        );
    }
}

class TreeNode {
    private List<TreeNode> children = new LinkedList<>();
}

class Env {
    private Env outer;
    private Map<String, Object> values = new HashMap<>();
}
