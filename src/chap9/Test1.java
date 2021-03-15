package chap9;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {

    public static void main(String[] args) {
        testAST1();
    }

    public static void testAST1() {
        TokenNode node1 = new TokenNode("1");
        TokenNode node2 = new TokenNode("9");
        TokenNode node3 = new TokenNode("+");
        ASTNode astNode1 = new ASTNode(ASTNodeType.Num, node1);
        ASTNode astNode2 = new ASTNode(ASTNodeType.Num, node2);
        ASTNode astNode3 = new ASTNode(ASTNodeType.Op, node3);
        astNode3.addChild(astNode1);
        astNode3.addChild(astNode2);

        Object eval = eval(astNode3);
        System.out.println(eval);
    }

    public static Object eval(ASTNode node) {
        switch (node.type) {
            case Num:
                return evalNum(node);
            case Op:
                return evalOp(node);
            default:
                throw new RuntimeException("ast node type is error");
        }
    }

    public static Integer evalNum(ASTNode node) {
        return Integer.parseInt(node.token.text);
    }

    public static Integer evalOp(ASTNode node) {
        switch (node.token.text) {
            case "+":
                return evalNum(node.getChild(0)) + evalNum(node.getChild(1));
            case "-":
                return evalNum(node.getChild(0)) - evalNum(node.getChild(1));
            default:
                throw new RuntimeException("operation type is error");
        }
    }


    public static void apply() {

    }

    private enum ASTNodeType {
        Num,
        Str,
        Ide,
        Op,
    }

    private static class ASTNode {
        public ASTNodeType type;

        public TokenNode token;

        private final List<ASTNode> children = new ArrayList<>();

        public ASTNode(ASTNodeType type, TokenNode token) {
            this.type = type;
            this.token = token;
        }

        public void addChild(ASTNode child) {
            this.children.add(child);
        }

        public ASTNode getChild(int i) {
            return this.children.get(i);
        }
    }

    private static class TokenNode {
        private String text;

        public TokenNode(String text) {
            this.text = text;
        }
    }

    private static class Env {
        private Env outer = null;
        private Map<String, Object> values = new HashMap<>();
    }

}
