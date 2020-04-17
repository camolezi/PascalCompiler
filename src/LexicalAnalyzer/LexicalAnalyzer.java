package LexicalAnalyzer;

import FileInput.FileInput;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class LexicalAnalyzer {

    //Table for looking in next states

    public LexicalAnalyzer(){
        StateManager.setUpStateTable();

        StateList initialState = StateList.A;

        State currentState = StateManager.state(initialState);

        FileInput myFile = new FileInput("demos/inputTest.txt");



        while(!currentState.isFinalState()){
            char nextInput = myFile.getNextChar();
            System.out.println(nextInput);
            currentState = StateManager.state( currentState.next(nextInput) );

        }



        System.out.println("FinalState reached");


    }


}


