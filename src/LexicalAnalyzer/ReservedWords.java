package LexicalAnalyzer;

import java.util.Map;
import static java.util.Map.entry;

class ReservedWords {

    static private Map<String,String> wordsMap;

    //Return null if its not a reserved word
    static public String check(String compareWord){
        return wordsMap.get(compareWord);
    }

    static public void loadWords(){

        //Table with all the language reserved words
        wordsMap = Map.ofEntries(
                entry("program", "simb_program"),
                entry("begin", "simb_begin"),
                entry("end", "simb_end"),
                entry("var", "simb_var"),
                entry("const","simb_const"),
                entry("real","simb_type_real"),
                entry("integer", "simb_type_integer"),
                entry("procedure","simb_procedure"),
                entry("else","simb_else"),
                entry("read","simb_read"),
                entry("write","simb_write"),
                entry("while","simb_while"),
                entry("do","simb_do"),
                entry("if","simb_if"),
                entry("then","simb_then"),
                entry("for","simb_for"),
                entry("to","simb_to")
        );

    }

}
