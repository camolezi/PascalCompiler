package Main;

import FileInput.*;
import LexicalAnalyzer.LexicalAnalyzer;


public class Main {
    public static void main( String [] args ) {

        System.out.println("Pascal Compiler");

        FileInput myFile = new FileInput("demos/inputTest.txt");

        LexicalAnalyzer tokenizer = new LexicalAnalyzer(myFile);


        while(!myFile.isFileFinished())
            System.out.println(tokenizer.nextToken());

    }
}
