package LexicalAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//Testing for now
enum StateList{
    A,
    B,
    C,
    F,
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
        addState(new State(StateList.A,false)
                .addTransition('b',StateList.B)
                .addTransition('c',StateList.C));


        addState( new State(StateList.C, false)
                .addTransition('b',StateList.B));


        addState( new State(StateList.B, false)
                .addTransition('f',StateList.F));


        addState( new State(StateList.F, true));

        addState(new State(StateList.Error,true));
    }


    private static void addState(State state){
        stateList.put(state.name(),state);
    }

}
