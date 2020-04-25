package FileOutput;

import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {

    private FileWriter outputFile;

    public FileOutput(String fileName){
        try {
            outputFile = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void write(String text){
        try {
            outputFile.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeln(String text){
        try {
            outputFile.write(text + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
