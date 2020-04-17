package LexicalAnalyzer;

import FileInput.FileInput;


public class LexicalAnalyzer {

    private FileInput fileToRead;

    public LexicalAnalyzer(FileInput fileToRead){

        StateManager.setUpStateTable();
        this.fileToRead = fileToRead;

    }

    public String nextToken(){

        State currentState = StateManager.state(StateList.Initial);
        //System.out.println(currentState);
        String wordRead = "";

        while(!currentState.isFinalState()){

            char nextInput = fileToRead.getNextChar();
            wordRead = wordRead + nextInput;

            currentState = StateManager.state( currentState.next(nextInput) );
          //  System.out.println("Input:" + nextInput + " -> " + currentState.toDebugString());
        }

        return wordRead + ", " + currentState;

    }


}


