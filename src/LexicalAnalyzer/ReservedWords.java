package LexicalAnalyzer;

import java.util.Map;
import static java.util.Map.entry;

class ReservedWords {

    static private Map<String,String> wordsMap;

    //Return null if its not a reserved word
    static public String check(String compareWord){
        return wordsMap.get(compareWord);
    }

    static public void laodWords(){

        //Table with all the language reserved words
        wordsMap = Map.ofEntries(
                entry("program", "simb_program"),
                entry("var", "simb_var"),
                entry("begin", "simb_begin"),
                entry("while", "simb_while"),
                entry("integer", "simb_type_integer"),
                entry("end", "simb_end"),
                entry("(", "simb_opar"),
                entry(")", "simb_cpara"),
                entry("do", "simb_do"),
                entry("+", "simb_add"),
                entry("-", "simb_minus"),
                entry(";", "simb_pv"),
                entry(":", "simb_dp"),
                entry(".", "simb_ponto")
        );

    }

}
