package FileInput;
import java.io.*;

public class FileInput {

    private int bufferSize;
    private BufferedReader bufferedReader;
    private boolean finishedFile = false;

    private char nextChar = ' ';
    private char previousChar = ' ';
    private char currentReturn = ' ';

    //Constructors
    public FileInput(String name){
        this(name,1024);
    }


    public FileInput(String name, int size){
        this.bufferSize = size;
        try{
            bufferedReader = new BufferedReader( new FileReader(name),bufferSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getNextFromFile();
    }


    //Public methods
    public boolean isFileFinished(){ return finishedFile; }
    public int getBufferSize(){ return bufferSize; }

    public char getNextChar() {
        previousChar = currentReturn;
        currentReturn = nextChar;


        try {
            bufferedReader.mark(5);
        } catch (IOException e) {
            e.printStackTrace();
        }


        getNextFromFile();
        return currentReturn;
    }

    public void retrocede(){
        try {
            bufferedReader.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        nextChar = currentReturn;
        currentReturn = previousChar;

        finishedFile = false;
    }


    public char getPreviousChar(){
        return previousChar;
    }

    public char getCurrentChar(){
        return currentReturn;
    }

    //Internal implementation
    private void getNextFromFile(){
        if(finishedFile){
            nextChar = ' ';
            return;
        }

        int returnValue;
        try {
            returnValue = bufferedReader.read();
        } catch (IOException e) {
            //Some error
            e.printStackTrace();
            nextChar = ' ';
            return;
        }

        if(returnValue == -1){
            finishedFile = true;
            nextChar = ' ';
        }else{
            nextChar = (char)returnValue;
        }

    }


}
