package LexicalAnalyzer;

import FileInput.FileInput;


public class LexicalAnalyzer {

    //Table for looking in next states

    public LexicalAnalyzer(){
        StateManager.setUpStateTable();

        State currentState = StateManager.state(StateList.A);
        System.out.println(currentState);

        FileInput myFile = new FileInput("demos/inputTest.txt");

        while(!currentState.isFinalState()){
            char nextInput = myFile.getNextChar();
            currentState = StateManager.state( currentState.next(nextInput) );
            System.out.println("Input:" + nextInput + " -> " + currentState);

        }



        System.out.println("FinalState reached");


    }


}


