package LexicalAnalyzer;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


//Especial transitions for group of characters
enum Transition{
    other, //Other is for anything not defined, the default other transtion is the error state
    uppercaseLetter,
    lowercaseLetter,
    letter,
    number
}



//Represent a state in the finite automata machine
class State {

    private StateList name;

    //State configs
    private boolean isFinalState;
    private boolean isErrorState;
    private boolean needToRetrocede;


    //Represent the transitions from this state for a character
    private Map<Character,StateList> nextStates;

    //Especial type of transitions for groups of characters
    private Map<Transition,StateList> especialNextStates;

    public State(StateList name){
        this(name,false,false,false);
    }

    public State(StateList name, boolean isFinalState){
        this(name,isFinalState,false,false);
    }

    public State(StateList name, boolean isFinalState, boolean needToRetrocede){
        this(name,isFinalState,needToRetrocede,false);
    }


    public State(StateList name, boolean isFinalState, boolean needToRetrocede ,boolean isErrorState){
        this.needToRetrocede = needToRetrocede;
        this.name = name;
        this.isErrorState = isErrorState;
        this.isFinalState = isFinalState;
        nextStates = new HashMap<Character, StateList>();
        especialNextStates = new EnumMap<Transition, StateList>(Transition.class);
    }



    public StateList next(Character input){
        StateList newState = nextStates.get(input);

        if(newState == null){
            //Check Especial Transitions and Error
            StateList newSpecialState = StateList.Error;

            if( especialNextStates.get(Transition.other) != null)
                newSpecialState = especialNextStates.get(Transition.other);

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


    public boolean needToRetrocede() {
        return needToRetrocede;
    }

    public boolean isErrorState(){
        return isErrorState;
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
