package year2015.day1;

import main.ReadLines;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    private final ReadLines reader = new ReadLines(2015, 1, 2);
    private List<String> fileLines = new ArrayList<>();

    // Read file input, and refreshes fileLines list
    private void readData(){
        fileLines.clear();
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();

        int solution = 0;
        String line = fileLines.getFirst();

        // Process each character in line:
        for (int k = 0; k < line.length(); k++) {
            char c = line.charAt(k);
            if(c == '('){
                solution++;
            } else if (c == ')'){
                solution--;
            }
        }
        System.out.println(solution);
    }

    public void part2(){
        readData();

        int solution = 0;
        int part2Solution = 0;

        String line = fileLines.getFirst();

        for (int k = 0; k < line.length(); k++) {
            char c = line.charAt(k);

            if(c == '('){
                solution++;
            } else if (c == ')'){
                solution--;
            }

            if(solution < 0){
                part2Solution = k + 1;
                break;
            }
        }
        System.out.println(part2Solution);
    }
}
