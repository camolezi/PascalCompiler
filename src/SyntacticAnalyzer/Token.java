package SyntacticAnalyzer;

import LexicalAnalyzer.LexicalAnalyzer;

public class Token {

    static private LexicalAnalyzer tokenizer;
    private Token(){}

    public static void StartTokenizer(LexicalAnalyzer _tokenizer){
        tokenizer = _tokenizer;
    }

    public static String getNext(){
        String nextToken = tokenizer.nextToken();
        if(nextToken.isBlank() || nextToken.isEmpty()){
            return nextToken;
        }
        int startIndex = nextToken.indexOf(",") + 2; //+2 to get rid of the space;
        return nextToken.substring(startIndex);
    }

}
