package year2015.day19;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {
    private final ReadLines reader = new ReadLines(2015, 19, 2);
    private List<String> fileLines;
    private final ArrayList<String[]> table = new ArrayList<>();
    private String line = "";


    private void readData(){
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        for (int i = 0; i < fileLines.size(); i++) {
            if(fileLines.get(i).isEmpty()){
                line = fileLines.get(i + 1);
                break;
            }

            String current = fileLines.get(i);
            current = current.replaceAll(" ", "");

            String[] parts = current.split("=>");

            if(parts.length != 1){
                table.add(new String[] {parts[0], parts[1]});
            }
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
