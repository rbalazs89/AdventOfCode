package year2015.day19;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    List<String> fileLines;
    int inputFileIndex = 2;
    ArrayList<String[]> table = new ArrayList<>();
    String line = "";

    private final ReadLines reader = new ReadLines(2015, 19);
    int inputNumber = 2; // use 1 for mock data, 2 for real data
    public void readData(){
        // READ INPUT
        fileLines = reader.readFile(inputNumber);
    }

    public void processData(){
        readData();
        for (int i = 0; i < fileLines.size(); i++) {
            if(fileLines.get(i).isEmpty()){
                line = fileLines.get(i + 1);
                break;
            }

            String current = fileLines.get(i);
            current = current.replaceAll(" ", "");

            String[] parts = current.split("=>");
            table.add(new String[] {parts[0], parts[1]});
        }
    }

    public void part1(){
        processData();

        Set<String> names = new HashSet<>();

        for (int i = 0; i < table.size(); i++) {
            String convertThis = table.get(i)[0];
            String intoThis = table.get(i)[1];

            Pattern p = Pattern.compile(convertThis);
            Matcher m = p.matcher(line);

            List<Integer> positions = new ArrayList<>();
            while (m.find()) {
                positions.add(m.start());
            }

            for (int pos : positions) {
                StringBuilder sb = new StringBuilder(line);
                sb.replace(pos, pos + convertThis.length(), intoThis);
                String temp = sb.toString();
                names.add(temp);
            }
        }
        System.out.println(names.size());

    }

    public void part2(){

    }
}
