package SyntacticAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//List with all nodes
enum NodeList
{
    Start,
    Decl,
    Decl_const,
    Decl_var,
    Decl_procedure,
    Parameters_list,
    Commands,
    Number,
    VarType,
    Var,
    Semicolon,
    Read_Write,
    While,
    If
}

public class NodeManager {

    private NodeManager(){}
    private static Map<NodeList, Node> nodeList;
    public static Node getNode(NodeList node){
        return nodeList.get(node);
    }

    public static void startFirstNode(){getNode(NodeList.Start).executeNextNode(getNode(NodeList.Start));}

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
                thisNode is a reference to the current node executing the code. Parent is a reference to the node that
                called the current executing node.

        3-How to write a nodeFunction
        -Use the auxiliary methods
            nextToken() -> use to receive the next parsed token from the file
            getNode(name) -> use to get a reference to a node passing its name as parameter.Eg: getNode(NodeList.id)-> get the node with "id" name
            nextNode(nextNodeReference,parentNode) -> use this to pass the control to a next node and execute its function.
                nextNodeReference is a reference to the next node that will be executed (use getNode() to get this reference)
                ParentNode is who is calling the nextNode. Generally you will pass thisNode as argument. (Since is you who is calling)
        */


        //-------------Nodes Declarations Starts here. Check documentation to see a diagram with all the nodes--------------------


        //----------------------Start Node --------------------------------

        addNode(NodeList.Start,
            (Node thisNode , Node parent)->{
                if(nextToken().equals("simb_program")){
                    if(nextToken().equals("id")){
                        nextNode(getNode(NodeList.Semicolon),thisNode);
                        nextNode(getNode(NodeList.Decl),thisNode);
                    }else{
                        addErrorMessage("Missing program identifier");
                    }
                }else{
                    addErrorMessage("Missing program declaration: Expected program keyword");
                }
            }
        );

        //--------------Declarations---------------------------------------------

        addNode(NodeList.Decl,
            (Node thisNode , Node parent)->{
                String token = nextToken();
                switch (token){
                    case("simb_const"):
                        nextNode(getNode(NodeList.Decl_const),thisNode);
                        break;
                    case("simb_var"):
                        nextNode(getNode(NodeList.Decl_var),thisNode);
                        break;
                    case("simb_procedure"):
                        nextNode(getNode(NodeList.Decl_procedure),thisNode);
                        break;
                    case("simb_begin"):
                        nextNode(getNode(NodeList.Commands),thisNode);
                        break;
                    default:
                        addErrorMessage("Expected a declaration or a begin statement");
                }
            }
        );


        addNode(NodeList.Decl_const,
            (Node thisNode , Node parent)->{
                if(nextToken().equals("id")){
                    if(nextToken().equals("simb_equal")){
                        nextNode(getNode(NodeList.Number),thisNode);
                        nextNode(getNode(NodeList.Semicolon), thisNode);
                        nextNode(getNode(NodeList.Decl), thisNode);
                    }else{
                        addErrorMessage("const declaration missing =");
                    }
                }else{
                    addErrorMessage("const declaration missing ID");
                }
            }
        );


        addNode(NodeList.Number,
            (Node thisNode , Node parent)->{
                String token = nextToken();
                if(token.equals("integerNumber") || token.equals("realNumber")){
                    return;
                }else{
                    addErrorMessage("missing type");
                }
            }
        );

        addNode(NodeList.Decl_var,
            (Node thisNode , Node parent)->{
                nextNode(getNode(NodeList.Var),thisNode);
                nextNode(getNode(NodeList.VarType),thisNode);
                nextNode(getNode(NodeList.Semicolon),thisNode);
                nextNode(getNode(NodeList.Decl),thisNode);
            }
        );



        addNode (NodeList.Var,
                (Node thisNode , Node parent)->{
                    if(nextToken().equals("id")){
                        String token = nextToken();
                        if(token.equals("simb_comma")){
                            nextNode(thisNode, thisNode);
                        }else if(token.equals("simb_colon")){
                            return;
                        }else if(token.equals("simb_close_par")){
                            return;
                        }else{
                            addErrorMessage("Expected close parentheses or colon");
                        }
                    }else{
                        addErrorMessage("Missing identifier");
                    }
                }
        );

        addNode(NodeList.Decl_procedure,
            (Node thisNode , Node parent)->{
                if(nextToken().equals("id")){
                    if(nextToken().equals("simb_open_par")){
                        nextNode(getNode(NodeList.Parameters_list),thisNode);
                        nextNode(getNode(NodeList.Semicolon),thisNode);

                        String token = nextToken();
                        if(token.equals("simb_var")){
                            nextNode(getNode(NodeList.Decl_var),thisNode);
                        }else if(token.equals("simb_begin")){
                            nextNode(getNode(NodeList.Commands),thisNode);
                        }else{
                            addErrorMessage("Expected begin");
                        }
                    }else{
                        addErrorMessage("Missing open parentheses");
                    }
                }else{
                    addErrorMessage("Missing procedure name");
                }
            }
        );

        addNode(NodeList.Parameters_list,
            (Node thisNode , Node parent)->{
                nextNode(getNode(NodeList.Var),thisNode);
                nextNode(getNode(NodeList.VarType),thisNode);
                String token = nextToken();
                if(token.equals("simb_semicolon")){
                    nextNode(thisNode,thisNode);
                }else if(token.equals("simb_close_par")){
                    return;
                }else{
                    addErrorMessage("Missing semicolon or close parentheses");
                }
            }
        );



        addNode (NodeList.VarType,
            (Node thisNode , Node parent)->{
                String token = nextToken();
                if(token.equals("simb_type_real") || token.equals("simb_type_integer")){
                    return;
                }else{
                    addErrorMessage("Type not recognized");
                }
            }
        );

        addNode(NodeList.Semicolon,
            (Node thisNode, Node parent)->{
                if(nextToken().equals("simb_semicolon")){
                    return;
                }else {
                    addMissingSemicolonError();
                }
            }
        );

        addNode(NodeList.Commands,
            (Node thisNode, Node parent)->{
                String token = nextToken();
                if(token.isEmpty())
                    return;

                addErrorMessage(token);
                switch (token){
                    case("simb_read"):
                    case("simb_write"):
                        nextNode(getNode(NodeList.Read_Write),thisNode);
                        break;
                    case("simb_while"):
                        nextNode(getNode(NodeList.While),thisNode);
                        break;
                    case("simb_if"):
                        nextNode(getNode(NodeList.Commands),thisNode);
                        break;
                    case("id"):
                        nextNode(getNode(NodeList.VarType),thisNode);
                        break;
                    default:
                        addErrorMessage("Command not recognized");
                }
            }
        );

        addNode(NodeList.Read_Write,
            (Node thisNode, Node parent)->{
                if(nextToken().equals("simb_open_par")){
                    nextNode(getNode(NodeList.Var),thisNode);
                    nextNode(getNode(NodeList.Semicolon),thisNode);
                    nextNode(getNode(NodeList.Commands),thisNode);
                }else {
                    addErrorMessage("Expected parenthesis");
                }
            }
        );

        addNode(NodeList.While,
            (Node thisNode, Node parent)->{
                if(nextToken().equals("simb_open_par")){
                }else {
                    addErrorMessage("Expected parenthesis");
                }
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

    private static void addMissingSemicolonError(){
        addErrorMessage("Missing semicolon");
    }

    private static void addErrorMessage(String errorMsg){
        //For now just print
        System.out.println(errorMsg);
    }


}
