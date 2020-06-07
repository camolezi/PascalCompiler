package SyntacticAnalyzer;

public class Node {

    NodeList name;
    private NodeFunction nodeFunction;

    public void executeNextNode(Node parent){ nodeFunction.executeNextNode(this, parent); }

    public Node() {}

    public Node setNodeFunction(NodeFunction function){
        nodeFunction = function;
        return this;
    }

    public Node setNodeName(NodeList name){
        this.name = name;
        return this;
    }

    public NodeList name(){return name;}

    public static Node newNode(NodeList name, NodeFunction nodeFunc){return new Node().setNodeName(name).setNodeFunction(nodeFunc);}
}
