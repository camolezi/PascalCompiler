package SyntacticAnalyzer;

import java.util.EnumMap;
import java.util.Map;

enum NodeList
{
    var,
    ident,
    real,
    integer
}

public class NodeManager {

    private NodeManager(){}

    private static Map<NodeList, Node> nodeList;

    public static Node getNode(NodeList node){
        return nodeList.get(node);
    }

    public static void setUpNodes(){
        //Hash map with all the nodes
        nodeList = new EnumMap<NodeList, Node>(NodeList.class);

        addNode (NodeList.var, Node.newNode().setNodeFunction(
                (Node parent)->{
                    System.out.println("variable recognized finished");

                    if(Token.getNext().equals("id")){
                        NodeManager.getNode(NodeList.ident).executeNextNode(NodeManager.getNode(NodeList.var));
                    }else{
                        //error re
                    }
                }
        ));

        addNode (NodeList.ident, Node.newNode().setNodeFunction(
                (Node parent)->{
                    String nextToken = Token.getNext();
                    if(nextToken.equals("simb_comma")){
                        NodeManager.getNode(NodeList.ident).executeNextNode(NodeManager.getNode(NodeList.ident));
                    }else if(nextToken.equals ("simb_colon")){
                        nextToken = Token.getNext();;
                        if(nextToken.equals("simb_type_integer")) {
                            NodeManager.getNode(NodeList.integer).executeNextNode(NodeManager.getNode(NodeList.ident));
                        }
                    }else{
                        //error
                    }
                    //return null;
                }
        ));

        addNode (NodeList.integer, Node.newNode().setNodeFunction(
                (Node parent)->{
                    String nextToken = Token.getNext();
                    if(nextToken.equals("simb_semicolon")){
                        System.out.println("variable recognized finished true");
                    }else {
                    }
                    //return null;
                }
        ));


    }

    private static void addNode( NodeList name ,Node node){
        nodeList.put(name,node);
    }

}
