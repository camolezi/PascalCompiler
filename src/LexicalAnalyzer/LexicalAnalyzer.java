package LexicalAnalyzer;

import FileInput.FileInput;


public class LexicalAnalyzer {
    //Constants
    private static State initialState;

    private FileInput fileToRead;
    private int lineNumber = 0;

    public LexicalAnalyzer(FileInput fileToRead){
        this.fileToRead = fileToRead;

        //Start subsystems
        StateManager.setUpStateTable();
        ReservedWords.loadWords();

        initialState = StateManager.state(StateList.Initial);
    }

    public int getLineNumber(){return lineNumber;}

    public String nextToken(){

        State currentState = initialState;
        String wordRead = "";

        char nextInput = fileToRead.getCurrentChar();

        while(!currentState.isFinalState()){

            if(fileToRead.isFileFinished() && currentState == initialState)
                return "";

            //Turns new line in space- For the lexical new lines and spaces are the same
            if(nextInput == '\n' || nextInput == '\r' || nextInput == '\t'){
                lineNumber++;
                nextInput = ' ';
            }
            currentState = StateManager.state( currentState.next(nextInput));

            //Consuming any spaces and new lines in the initial state
            if(currentState != initialState && currentState.name() != StateList.comment){
                wordRead = wordRead + nextInput;
            }

            nextInput = fileToRead.getNextChar();
        }

        //check if the state needs to retrocede the file
        if(currentState.needToRetrocede()){
            fileToRead.retrocede();
            if(wordRead.length() >= 2)
                wordRead = wordRead.substring(0, wordRead.length() - 1);
        }

        //check if its a reserved word
        if(currentState.name() == StateList.id){
            String Reserved = ReservedWords.check(wordRead);
            if( Reserved != null){
                return wordRead + ", " + Reserved;
            }
        }

        return wordRead + ", " + currentState;
    }


}


