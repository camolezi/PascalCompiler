package SyntacticAnalyzer;

import FileInput.FileInput;
import LexicalAnalyzer.LexicalAnalyzer;

public class SyntacticAnalyzer {

    public SyntacticAnalyzer(FileInput fileToRead){

        Token.StartTokenizer(new LexicalAnalyzer(fileToRead));
        NodeManager.setUpNodes();
        NodeManager.startFirstNode();
        /*
        while(!fileToRead.isFileFinished()) {
            String token = Token.getNext();
            System.out.println("variable:" + token);
            if(token.equals("simb_var")){
                NodeManager.getNode(NodeList.var).executeNextNode(NodeManager.getNode(NodeList.var));

        }
            */


    }


}
