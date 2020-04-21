package LexicalAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//Testing for now
enum StateList{
    Initial,

    intermediate_id,
    id,

    //numbers
    intermediate_int,
    intermediate_real,
    integerNumber,
    realNumber,

    //Error states
    errorRealNumber, //Error in defining a real number

    Error
}

class StateManager {

    private static Map<StateList, State> stateList;

    public static State state(StateList getState){
        return stateList.get(getState);
    }


    public static void setUpStateTable(){

        stateList = new EnumMap<StateList, State>(StateList.class);


        /*
              Transitions:

              Transition.letter = triggered when the input char is a letter- Uppercase or lowercase
              Transition.number = triggered when the input char is a number- 0-9
              Transtion.other = triggered when the input char does not pass in any other transition tests
         */

        //Create all states and is transitions
        addState(new State(StateList.Initial,false)
                .addTransition(Transition.letter, StateList.intermediate_id)
                .addTransition(Transition.number, StateList.intermediate_int)
                .addTransition(' ', StateList.Initial));

        // ---------------------------------Identifier------------------------------------------
        addState(new State(StateList.intermediate_id,false)
                .addTransition(Transition.letter, StateList.intermediate_id)
                .addTransition(Transition.number, StateList.intermediate_id)
                .addTransition(Transition.other, StateList.id));

        addState(new State(StateList.id, true,true));

        // ---------------------------------Numbers------------------------------------------

        addState(new State(StateList.intermediate_int,false)
                .addTransition(Transition.number,StateList.intermediate_int)
                .addTransition('.',StateList.intermediate_real)
                .addTransition(Transition.other,StateList.integerNumber));

        addState(new State(StateList.integerNumber, true, true));


        addState(new State(StateList.intermediate_real,false)
                .addTransition(Transition.number,StateList.intermediate_real)
                .addTransition(Transition.other,StateList.realNumber)
                .addTransition('.',StateList.errorRealNumber));

        addState(new State(StateList.realNumber, true, true));


        // -------------------------------Error-----------------------------
        addState(new State(StateList.Error, true,false, true));
        addState(new State(StateList.errorRealNumber, true, false, true));

    }


    private static void addState(State state){
        stateList.put(state.name(),state);
    }

}
