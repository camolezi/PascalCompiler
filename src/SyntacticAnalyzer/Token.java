package SyntacticAnalyzer;

import LexicalAnalyzer.LexicalAnalyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Token {

    static private ArrayList<String> tokensList;
    static private LexicalAnalyzer tokenizer;
    static private ListIterator<String> it;

    private Token(){}


    public static void StartTokenizer(LexicalAnalyzer _tokenizer){
        tokenizer = _tokenizer;
        tokensList = new ArrayList<String>();

        String token = tokenizer.nextToken();

        while (!token.isBlank() && !token.isEmpty()){
            tokensList.add(token);
            token = tokenizer.nextToken();
        }
        it = tokensList.listIterator();

    }

    public static void retrocedeToken(){
        if(it.hasPrevious()){
            it.previous();
        }
    }

    public static String getNext(){
        String nextToken = "";
        if(it.hasNext()){
            nextToken = it.next();
        }
        if(nextToken.isBlank() || nextToken.isEmpty()){
            return nextToken;
        }
        int startIndex = nextToken.indexOf(",") + 2; //+2 to get rid of the space;
        return nextToken.substring(startIndex).replace(" ","");
    }

}
