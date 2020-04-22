package LexicalAnalyzer;

import java.util.EnumMap;
import java.util.Map;

//Testing for now
enum StateList{
    Initial,

    //Comment
    comment, //{}

    //Identifiers
    intermediate_id,
    id,

    //numbers
    intermediate_int,
    intermediate_real,
    integerNumber,
    realNumber,

    errorRealNumber, //Error in defining a real number


    //Relational operators
    intermediate_lesser,
    intermediate_greater,

    simb_lesser_equal,    // <=
    simb_greater_equal,   // >=
    simb_not_equal,       // <>
    simb_lesser,          // <
    simb_greater,         // >
    simb_equal,           // =

    //arithmetic operators
    simb_plus,          // +
    simb_minus,         // -
    simb_times,         // *
    simb_division,      // /
    simb_attribution,   // :=

    //General Symbols

    intermediate_colon,

    simb_period,     // .
    simb_colon,      // :
    simb_comma,      // ,
    simb_semicolon,  // ;
    simb_open_par,   // (
    simb_close_par,  // )

    //Error states
    invalidCharError,

    //Generic lexical error,
    lexicalError
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

        // ---------------------Initial state----------------------------------

        addState(new State(StateList.Initial,false)
                .addTransition(Transition.letter, StateList.intermediate_id)
                .addTransition(Transition.number, StateList.intermediate_int)
                .addTransition(' ', StateList.Initial)

                //Comments
                .addTransition('{' ,StateList.comment)

                // Relational operators states
                .addTransition('<',StateList.intermediate_lesser)
                .addTransition('>',StateList.intermediate_greater)
                .addTransition('=',StateList.simb_equal)

                //arithmetic
                .addTransition('+',StateList.simb_plus)
                .addTransition('-',StateList.simb_minus)
                .addTransition('*',StateList.simb_times)
                .addTransition('/',StateList.simb_division)

                //General symbols
                .addTransition(':',StateList.intermediate_colon)
                .addTransition(';',StateList.simb_semicolon)
                .addTransition('.',StateList.simb_period)
                .addTransition(',',StateList.simb_comma)
                .addTransition('(',StateList.simb_open_par)
                .addTransition(')',StateList.simb_close_par)

                .addTransition(Transition.other, StateList.invalidCharError)
        );


        // ---------------------------------Identifier------------------------------------------
        addState(new State(StateList.intermediate_id,false)
                .addTransition(Transition.letter, StateList.intermediate_id)
                .addTransition(Transition.number, StateList.intermediate_id)
                .addTransition(Transition.other, StateList.id)
        );

        addState(new State(StateList.id, true,true));

        // ---------------------------------Numbers------------------------------------------

            //Int
        addState(new State(StateList.intermediate_int,false)
                .addTransition(Transition.number,StateList.intermediate_int)
                .addTransition('.',StateList.intermediate_real)
                .addTransition(Transition.other,StateList.integerNumber)
        );

        addState(new State(StateList.integerNumber, true, true));

            //Real
        addState(new State(StateList.intermediate_real,false)
                .addTransition(Transition.number,StateList.intermediate_real)
                .addTransition(Transition.other,StateList.realNumber)
                .addTransition('.',StateList.errorRealNumber)
        );

        addState(new State(StateList.realNumber, true, true));


        //---------------------- Relational operators -----------------------------------------
        // = , <>, >, < , >= , <=

        //equal, =
        addState(new State(StateList.simb_equal,true,false));

        //Greater: >, >=
        addState(new State(StateList.intermediate_greater,false)
                .addTransition('=',StateList.simb_greater_equal)
                .addTransition(Transition.other, StateList.simb_greater)
        );

        addState(new State(StateList.simb_greater,true,true));
        addState(new State(StateList.simb_greater_equal,true,false));

        //Lesser:  < , <=, <>
        addState(new State(StateList.intermediate_lesser,false)
                .addTransition('=',StateList.simb_lesser_equal)
                .addTransition('>',StateList.simb_not_equal)
                .addTransition(Transition.other, StateList.simb_lesser)
        );

        addState(new State(StateList.simb_lesser,true,true));
        addState(new State(StateList.simb_not_equal,true,false));
        addState(new State(StateList.simb_lesser_equal,true,false));


        // ------------------Arithmetics-----------------------------------------

        addState(new State(StateList.simb_plus,true));
        addState(new State(StateList.simb_minus,true));
        addState(new State(StateList.simb_times,true));
        addState(new State(StateList.simb_division,true));
        addState(new State(StateList.simb_attribution,true));


        //-------------------------General Symbols -------------------------------------------
        addState(new State(StateList.simb_colon,true,true));
        addState(new State(StateList.simb_comma,true));
        addState(new State(StateList.simb_open_par,true));
        addState(new State(StateList.simb_close_par,true));
        addState(new State(StateList.simb_semicolon,true));
        addState(new State(StateList.simb_period,true));


        addState(new State(StateList.intermediate_colon,false)
                .addTransition('=',StateList.simb_attribution)
                .addTransition(Transition.other, StateList.simb_colon)
        );

        //--------------------Comment--------------------------------

        addState(new State(StateList.comment,false)
                .addTransition('}',StateList.Initial)
                .addTransition(Transition.other, StateList.comment)
        );


        // -------------------------------Errors-----------------------------

        addState(new State(StateList.invalidCharError, true,false, true));
        addState(new State(StateList.errorRealNumber, true, false, true));

    }


    private static void addState(State state){
        stateList.put(state.name(),state);
    }

}
