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

    public static int getCurrentLine(){return tokenizer.getLineNumber();}

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
        //Parse the string
        int startIndex = nextToken.indexOf("|") ;
        String parsedToken = nextToken.substring(0,startIndex);

        String aux = nextToken.substring(startIndex+1).replace(" ","");

        startIndex = aux.indexOf("|");
        String original = aux.substring(0,startIndex);
        String lineNumber = aux.substring(startIndex+1);

        VerifyLexicalError(parsedToken,original,lineNumber);
        return parsedToken;
    }

    public static String getCurrentTokenInfo(){
        String previousToken = it.previous();
        String currentToken = it.next();

        if(currentToken.isBlank() || currentToken.isEmpty()){
            return currentToken;
        }
        //Parse the string
        int startIndex = currentToken.indexOf("|") ;
        String aux = currentToken.substring(startIndex+1).replace(" ","");
        startIndex = aux.indexOf("|");
        String original = aux.substring(0,startIndex);
        String lineNumber = aux.substring(startIndex+1);

        return "on line:" + lineNumber + " got: '" + original+ "'";
    }

    private static void VerifyLexicalError(String parsed, String original,String lineNumber){
        switch (parsed){
            case("errorRealNumber"):
                System.out.println( "On line:"+lineNumber + " - " + "Ill formed real number: " + original );
                break;
            case("invalidCharError"):
                System.out.println("On line:"+lineNumber + " - " + "Invalid character in identifier: "+ original);
                break;
            case("lexicalError"):
                System.out.println("On line:"+lineNumber + " - " + "Ill formed word or invalid character: "+ original);
                break;
            default:
                break;
        }

    }

}
