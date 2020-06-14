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
        int startIndex = nextToken.indexOf(",") + 2; //+2 to get rid of the space;
        String parsedToken = nextToken.substring(startIndex).replace(" ","");

        String original = nextToken.substring(0,nextToken.indexOf(","));

        VerifyLexicalError(parsedToken,original);
        return parsedToken;
    }

    private static void VerifyLexicalError(String parsed, String original){
        switch (parsed){
            case("errorRealNumber"):
                System.out.println("Ill formed real number: " + original);
                break;
            case("invalidCharError"):
                System.out.println("Invalid character in identifier: "+ original);
                break;
            case("lexicalError"):
                System.out.println("Ill formed word or invalid character: "+ original);
                break;
            default:
                break;
        }

    }

}
