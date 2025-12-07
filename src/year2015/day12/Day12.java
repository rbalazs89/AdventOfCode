package year2015.day12;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    //9095 too low

    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        readData();
        String line = fileLines.get(0);

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(line);

        long result = 0;
        while(m.find()){
            result = result + Integer.parseInt(m.group());
        }
        System.out.println(result);
    }

    public void part2() {
        readData();
        String line = fileLines.get(0);


    }

}
