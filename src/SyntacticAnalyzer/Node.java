package SyntacticAnalyzer;

public class Node {

    private NodeFunction nodeFunction;

    public void executeNextNode(Node parent){ nodeFunction.executeNextNode(parent); }

    public Node() {}

    public Node setNodeFunction(NodeFunction function){
        nodeFunction = function;
        return this;
    }

    public static Node newNode(){return new Node();}
}
