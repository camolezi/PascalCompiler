package LexicalAnalyzer;

import java.util.HashMap;
import java.util.Map;

//Represent a state in the finite automata machine
class State {

    private StateList name;

    private boolean isFinalState;

    //Represent the transitions from this state
    private Map<Character,StateList> nextStates;

    public State(StateList name, boolean isFinalState){
        this.name = name;
        this.isFinalState = isFinalState;
        nextStates = new HashMap<Character, StateList>();
    }

    public StateList next(Character input){
        StateList newState = nextStates.get(input);
        if(newState == null){
            return StateList.Error;
        }else{
            return newState;
        }
    }



    public State addTransition(Character input, StateList newState){
        nextStates.put(input,newState);
        return this;
    }


    public boolean isFinalState() {
        return isFinalState;
    }

    public StateList name() {
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }

        if(obj.getClass() == this.getClass()){
            State stateObj = (State)obj;
            if( stateObj.name == this.name){
                return true;
            }
        }

        return false;
    }
}
