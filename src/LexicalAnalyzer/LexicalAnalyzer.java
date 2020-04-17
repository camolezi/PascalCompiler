package LexicalAnalyzer;

import FileInput.FileInput;


public class LexicalAnalyzer {

    //Special caracthers
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String SPACE = " ";


    private FileInput fileToRead;
    public LexicalAnalyzer(FileInput fileToRead){
        this.fileToRead = fileToRead;

        //Start subsystems
        StateManager.setUpStateTable();
        ReservedWords.laodWords();
    }

    public String nextToken(){

        State currentState = StateManager.state(StateList.Initial);
        //System.out.println(currentState);
        String wordRead = "";

        while(!currentState.isFinalState()){

            char nextInput = fileToRead.getNextChar();

            if(nextInput != ' ' && nextInput != '\n'){
                wordRead = wordRead + nextInput;
            }

            currentState = StateManager.state( currentState.next(nextInput) );
          //  System.out.println("Input:" + nextInput + " -> " + currentState.toDebugString());
        }


        //check if its a reserved word
        String Reserved = ReservedWords.check(wordRead);
        if( Reserved != null){
            return wordRead + ", " + Reserved;
        }

        return wordRead + ", " + currentState;
    }


}


