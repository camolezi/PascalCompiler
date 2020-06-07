package SyntacticAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//List with all nodes
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

        /*
        README

        HOW TO ADD NEW NODES

        1-Add the node name in the NodeList enum

        2-Use the function: addNote (nodeName,nodeFunction);
            nodeName is the name that you added in the enum;
            nodeFunction is a arrow function with template (Node thisNode , Node parent)->{doSomething}
                thisNode is a reference to the current node executing the code. Parent is a reference to the node that called
                the current executing node.

        3-How to write a nodeFunction
        -Use the auxiliary methods
            nextToken() -> use to receive the next parsed token from the file
            getNode(name) -> use to get a reference to a node passing its name as parameter.Eg: getNode(NodeList.id)-> get the node with "id" name
            nextNode(nextNodeReference,parentNode) -> use this to pass the control to a next node and execute its function.
                nextNodeReference is a reference to the next node that will be executed (use getNode() to get this reference)
                ParentNode is who is calling the nextNode. Generally you will pass thisNode as argument. (Since is you who is calling)
        */


        addNode (NodeList.var,
                //Node function
                (Node thisNode , Node parent)->{

                    if(nextToken().equals("id") ){
                        nextNode(getNode(NodeList.ident), thisNode);
                    }else{
                        //error re
                    }
                }
        );

        addNode (NodeList.ident,

                (Node thisNode, Node parent)->{

                    String nextToken = nextToken();

                    if(nextToken.equals("simb_comma")){
                        //Easy loops
                        nextNode(thisNode,thisNode);
                    }else if(nextToken.equals ("simb_colon")){

                        nextToken = nextToken();
                        if(nextToken.equals("simb_type_integer")) {
                            nextNode(getNode(NodeList.integer) , thisNode);
                        }

                    }else{
                        //error
                    }
                    //return null;
                }
        );

        addNode (NodeList.integer,

                (Node thisNode, Node parent)->{

                    String nextToken = nextToken();
                    if(nextToken.equals("simb_semicolon")){
                        System.out.println("variable recognized finished TRUEE");
                    }else {

                    }
                    //return null;
                }
        );



    }


    // ---------------------Aux functions to help building new nodes ------------------------
    private static void nextNode(Node nextNode,Node parent){
        nextNode.executeNextNode(parent);
    }

    private static String nextToken(){
        return Token.getNext();
    }

    private static void addNode(NodeList name, NodeFunction func){
        Node newNode = Node.newNode(name,func);
        nodeList.put(newNode.name(),newNode);
    }

}
