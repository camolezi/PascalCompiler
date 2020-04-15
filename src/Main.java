

import FileInput.*;



public class Main {
    public static void main( String [] args ) {

        System.out.println("Hello Compiler");

        FileInput myFile = new FileInput("demos/inputTest.txt");

        while(!myFile.isFileFinished()){
            System.out.println(myFile.getNextChar());
        }

    }
}
