package LexicalAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//Testing for now
enum StateList{
    Initial,
    id,
    intermediate_1,
    Error
}

class StateManager {

    private static Map<StateList, State> stateList;

    public static State state(StateList getState){
        return stateList.get(getState);
    }


    public static void setUpStateTable(){

        stateList = new EnumMap<StateList, State>(StateList.class);

        //Create all states and is transitions
        addState(new State(StateList.Initial,false)
                .addTransition(Transition.letter, StateList.intermediate_1));

        addState(new State(StateList.intermediate_1,false)
                .addTransition(Transition.letter, StateList.intermediate_1)
                .addTransition(Transition.number, StateList.intermediate_1)
                .addTransition(' ', StateList.id));

        addState(new State(StateList.id, true));

        addState(new State(StateList.Error, true));


    }


    private static void addState(State state){
        stateList.put(state.name(),state);
    }

}
