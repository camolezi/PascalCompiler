package SyntacticAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//List with all nodes
enum NodeList
{
    Start,
    Empty,//this represent a empty array(is used for optional tokens)
    Decl,
    Decl_const,
    Decl_var,
    Decl_procedure,
    Parameters_list,
    Commands,
    CMD,
    Condition,
    Expression,
    OtherExpression,
    Relation,
    Terms,
    OtherTerms,
    Op_Un,
    Op_Mul,
    Factor,
    Number,
    MoreFactor,
    VarType,
    Var,
    Semicolon,
    Read_Write,
    While,
    If,
    ID,
    Args,
    argsIdentifier,
    moreArgsIdentifier
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

        //--------------------------------Empty-----------------------------------------

        addNode(NodeList.Empty,
            (Node thisNode , Node parent)->{
                retrocedeToken();
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
                        nextNode(getNode(NodeList.Semicolon),thisNode);
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
                    addErrorMessage("Expected literal number");
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
                            nextNode(getNode(NodeList.Decl),thisNode);
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

        addNode(NodeList.CMD,
            (Node thisNode, Node parent)-> {
                String token = nextToken();
                if (token.isEmpty()) {
                    //addErrorMessage("Expected command");
                    return;
                }
                switch (token) {
                    case ("simb_read"):
                    case ("simb_write"):
                        nextNode(getNode(NodeList.Read_Write), thisNode);
                        break;
                    case ("simb_while"):
                        nextNode(getNode(NodeList.While), thisNode);
                        break;
                    case ("simb_if"):
                        nextNode(getNode(NodeList.If),thisNode);
                        break;
                    case ("id"):
                        nextNode(getNode(NodeList.ID),thisNode);
                        break;
                    case ("simb_begin"):
                        nextNode(getNode(NodeList.Commands), thisNode);
                        break;
                    default:
                        addErrorMessage("Expected a command, got:" + token);
                }

            }
        );

        addNode(NodeList.Commands,
            (Node thisNode, Node parent)->{
                while(true){
                    String token = nextToken();
                    if(token.equals("simb_end")){
                        return;
                    }else if(token.isBlank() || token.isEmpty()) {
                        addErrorMessage("Missing end stm");
                        return;
                    }else{
                        nextNode(getNode(NodeList.Empty),thisNode);
                        nextNode(getNode(NodeList.CMD),thisNode);

                    }

                }
            }
        );

        addNode(NodeList.Read_Write,
            (Node thisNode, Node parent)->{
                if(nextToken().equals("simb_open_par")){
                    nextNode(getNode(NodeList.Var),thisNode);
                    nextNode(getNode(NodeList.Semicolon),thisNode);
                    return;
                }else {
                    addErrorMessage("Expected parenthesis");
                }
            }
        );

        addNode(NodeList.While,
            (Node thisNode, Node parent)->{
                if(nextToken().equals("simb_open_par")){
                    nextNode(getNode(NodeList.Condition),thisNode);
                    if(nextToken().equals("simb_close_par")){
                        if(nextToken().equals("simb_do")){
                            nextNode(getNode(NodeList.CMD),thisNode);
                        }else{
                            addErrorMessage("missing simb do");
                        }
                    }else{
                        addErrorMessage("mising close parenthesis");
                    }
                }else {
                    addErrorMessage("Expected parenthesis");
                }
            }
        );

        addNode(NodeList.Condition,
            (Node thisNode, Node parent)->{
                nextNode(getNode(NodeList.Expression),thisNode);
                nextNode(getNode(NodeList.Relation),thisNode);
                nextNode(getNode(NodeList.Expression),thisNode);
            }
        );

        addNode(NodeList.Expression,
            (Node thisNode, Node parent)->{
                nextNode(getNode(NodeList.Terms),thisNode);
                nextNode(getNode(NodeList.OtherTerms),thisNode);
            }
        );

        addNode(NodeList.Relation,
            (Node thisNode, Node parent)->{
                String token = nextToken();
                if(token.equals("simb_equal") || token.equals("simb_not_equal")||
                   token.equals("simb_greater_equal") || token.equals("simb_lesser_equal") ||
                   token.equals("simb_greater") || token.equals("simb_lesser")){
                    return;
                }else{
                    addErrorMessage("Expected a relation symbol");
                }
            }
        );

        addNode(NodeList.Terms,
            (Node thisNode, Node parent)->{
                nextNode(getNode(NodeList.Op_Un),thisNode);
                nextNode(getNode(NodeList.Factor),thisNode);
                nextNode(getNode(NodeList.MoreFactor),thisNode);
            }
        );

        addNode(NodeList.Op_Un,
            (Node thisNode, Node parent)->{
                String token = nextToken();
                if(token.equals("simb_plus") || token.equals("simb_minus")){
                    return;
                }else{
                    nextNode(getNode(NodeList.Empty),thisNode);
                }
            }
        );

        addNode(NodeList.OtherTerms,
            (Node thisNode, Node parent)->{
                String token = nextToken();
                if(token.equals("simb_plus") || token.equals("simb_minus")){
                    nextNode(getNode(NodeList.Terms),thisNode);
                    nextNode(getNode(NodeList.OtherTerms),thisNode);
                }else{
                    nextNode(getNode(NodeList.Empty),thisNode);
                }

            }
        );

        addNode(NodeList.MoreFactor,
            (Node thisNode, Node parent)->{
                String token = nextToken();
                if(token.equals("simb_times") || token.equals("simb_division")){
                    nextNode(getNode(NodeList.Factor),thisNode);
                    nextNode(getNode(NodeList.MoreFactor),thisNode);
                }else{
                    nextNode(getNode(NodeList.Empty),thisNode);
                }
            }
        );

        addNode(NodeList.Factor,
            (Node thisNode, Node parent)->{
                String token = nextToken();
                if(token.equals("id")){
                    return;
                }else if(token.equals("simb_open_par")){
                    nextNode(getNode(NodeList.Expression), thisNode);
                    if(nextToken().equals("simb_close_par")){
                        return;
                    }else{
                        addErrorMessage("Missing close parentheses");
                    }
                }else if(token.equals("integerNumber") || token.equals("realNumber")){
                    return;
                }else{
                    addErrorMessage("Expected literal number or variable");
                }
            }
        );

        addNode(NodeList.If,
            (Node thisNode, Node parent)->{
                nextNode(getNode(NodeList.Condition),thisNode);
                if(nextToken().equals("simb_then")){
                    nextNode(getNode(NodeList.CMD),thisNode);
                    String token = nextToken();
                    if(token.equals("simb_else")){
                        nextNode(getNode(NodeList.CMD),thisNode);
                    }else{
                        nextNode(getNode(NodeList.Empty),thisNode);
                    }
                }else{
                    addErrorMessage("Expected symbol then");
                }
            }
        );

        addNode(NodeList.ID,
            (Node thisNode, Node parent)->{
                if(nextToken().equals("simb_attribution")){
                    if(nextToken().equals("simb_open_par")){
                        nextNode(getNode(NodeList.Args),thisNode);
                        if(nextToken().equals("simb_close_par")){
                            nextNode(getNode(NodeList.Semicolon),thisNode);
                            return;
                        }else{
                            addErrorMessage("Missing close parentheses");
                        }
                    }else{
                        nextNode(getNode(NodeList.Empty),thisNode);
                        nextNode(getNode(NodeList.Expression),thisNode);
                        nextNode(getNode(NodeList.Semicolon),thisNode);
                    }
                }else{
                    addErrorMessage("Expected attribution symbol");
                }
            }
        );

        addNode(NodeList.Args,
            (Node thisNode , Node parent)->{
                if(nextToken().equals("id")){
                    nextNode(getNode(NodeList.moreArgsIdentifier),thisNode);
                }else{
                    addErrorMessage("Expected identifier");
                }
            }
        );

        addNode(NodeList.moreArgsIdentifier,
            (Node thisNode , Node parent)->{
                if(nextToken().equals("simb_semicolon")){
                    nextNode(getNode(NodeList.Args),thisNode);
                }else{
                    nextNode(getNode(NodeList.Empty),thisNode);
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
        System.out.println(" :" + errorMsg);
    }

    private static void retrocedeToken(){
        Token.retrocedeToken();
    }


}
