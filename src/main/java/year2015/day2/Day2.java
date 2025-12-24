package year2015.day2;

import main.ReadLines;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.lang.Character.isDigit;

public class Day2 {

    private final ReadLines reader = new ReadLines(2015, 2, 2);
    private List<String> fileLines = new ArrayList<>();

    private void readData(){
        fileLines.clear();
        fileLines = reader.readFile();
    }

    // For each present compute the smallest wrapping paper:
    public void part1(){
        readData();
        int solution = 0;

        for(int i = 0; i < fileLines.size(); i ++){
            String line = fileLines.get(i);

            List<Integer> dimensions = parseDimensions(line);
            int l = dimensions.get(0);
            int w = dimensions.get(1);
            int h = dimensions.get(2);

            // wrap paper:
            int surfaceArea = 2 * (l * w + w * h + h * l);
            int smallest = l * w; // smallest side area
            solution += surfaceArea + smallest;
            dimensions.clear();
        }
        System.out.println(solution);
    }

    // Get the ribbon needed for each present:
    public void part2(){
        readData();

        int solution = 0;
        for(int i = 0; i < fileLines.size(); i ++){
            String line = fileLines.get(i);

            List<Integer> dimensions = parseDimensions(line);
            int l = dimensions.get(0);
            int w = dimensions.get(1);
            int h = dimensions.get(2);

            // get total wrap needed:
            int ribbon = 2 * l + 2 * w;
            int bow = l * w * h;
            solution += ribbon + bow;
            dimensions.clear();

        }
        System.out.println(solution);
    }

    // helper method, extract integers and return an ordered list
    private List<Integer> parseDimensions(String line) {
        ArrayList<Integer> dimensions = new ArrayList<>();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (isDigit(c)) {
                int start = i;
                int end = i + 1;

                // try to consume up to 3 digits
                i++;
                if (i < line.length() && isDigit(line.charAt(i))) {
                    end++;
                    i++;

                    if (i < line.length() && isDigit(line.charAt(i))) {
                        end++;
                        i++;
                    }
                }
                int value = Integer.parseInt(line.substring(start, end));
                dimensions.add(value);
            }
        }
        Collections.sort(dimensions);
        return dimensions;
    }
}