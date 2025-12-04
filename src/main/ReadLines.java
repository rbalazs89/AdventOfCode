package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static main.Main.YEAR;
import static main.Main.DAY;

public class ReadLines {

    public List<String> readFile(){
        Path filePath = Paths.get("src/year" + YEAR + "/day" + DAY + "/input1.txt");
        try{
            return Files.readAllLines(filePath);
        }
        catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }

    public List<String> readFile(int number){
        Path filePath = Paths.get("src/year" + YEAR + "/day" + DAY + "/input" + number + ".txt");
        try{
            return Files.readAllLines(filePath);
        }
        catch (IOException e){
            if(number != 1){
                readFile(1);
                System.err.println("reading input1.txt");
            }
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}
