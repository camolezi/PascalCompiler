package LexicalAnalyzer;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


//Especial transitions for group of characters
enum Transition{
    uppercaseLetter,
    lowercaseLetter,
    letter,
    number
}



//Represent a state in the finite automata machine
class State {

    private StateList name;
    private boolean isFinalState;

    //Represent the transitions from this state for a character
    private Map<Character,StateList> nextStates;

    //Especial type of transitions for groups of characters
    private Map<Transition,StateList> especialNextStates;


    public State(StateList name, boolean isFinalState){
        this.name = name;
        this.isFinalState = isFinalState;
        nextStates = new HashMap<Character, StateList>();
        especialNextStates = new EnumMap<Transition, StateList>(Transition.class);
    }

    public StateList next(Character input){
        StateList newState = nextStates.get(input);
        if(newState == null){
            //Check Especial Transitions and Error
            StateList newSpecialState = StateList.Error;
            if(Character.isDigit(input) && especialNextStates.get(Transition.number) != null)
                newSpecialState = especialNextStates.get(Transition.number);

            if(Character.isLetter(input) && especialNextStates.get(Transition.letter) != null)
                newSpecialState = especialNextStates.get(Transition.letter);

            return newSpecialState;
        }else{
            //if a normal transition exists, prioritize it first
            return newState;
        }
    }

    public State addTransition(Character input, StateList newState){
        nextStates.put(input,newState);
        return this;
    }

    public State addTransition(Transition input, StateList newState){
        especialNextStates.put(input,newState);
        return this;
    }



    public boolean isFinalState() {
        return isFinalState;
    }

    public StateList name() {
        return name;
    }


    public String toDebugString(){
        return "State:" + name.name() + " - isFinal:" + isFinalState;
    }

    @Override
    public String toString(){
        return name.name();
    }

}
