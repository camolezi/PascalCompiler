package Main;

import FileInput.*;
import FileOutput.FileOutput;
import LexicalAnalyzer.LexicalAnalyzer;
import SyntacticAnalyzer.SyntacticAnalyzer;


public class Main {
    public static void main( String [] args ) {

        String outputName = "output.txt";

        if(args.length < 1){
            System.out.println("Input file not specified");
            return;
        }

        if(args.length >= 2){
            outputName = args[1] + ".txt";
        }

        System.out.println("Compilation started");

        FileInput myFile = new FileInput(args[0]);
        FileOutput outpuFile = new FileOutput(outputName);


        SyntacticAnalyzer tokenizer = new SyntacticAnalyzer(myFile);

        System.out.println("Compilation finished");

        outpuFile.close();
    }
}
