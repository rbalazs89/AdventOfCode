package year2015.day12;

import main.ReadLines;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {
    private final ReadLines reader = new ReadLines(2015, 12, 2);
    private List<String> fileLines;

    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        String line = fileLines.getFirst();

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
        String line = fileLines.getFirst();
        // find red
        // find if its inside [] then ignore it
        // if its inside {}
        String redString = (":" + '"' + "red" + '"');
        while(line.contains(redString)) {
            int index = line.indexOf(redString);

            // start going right
            int rightSideIndex = -1;
            int rightBracketsToFind = 1;
            for (int i = index; i < line.length(); i++) {
                if (line.charAt(i) == '}') {
                    rightBracketsToFind--;
                    if (rightBracketsToFind == 0) {
                        rightSideIndex = i;
                        break;
                    }
                }
                if (line.charAt(i) == '{') {
                    rightBracketsToFind++;
                }
            }

            // going left:
            int leftSideIndex = -1;
            int leftBracketsToFind = 1;
            for (int i = index; i > 0; i--) {
                if (line.charAt(i) == '{') {
                    leftBracketsToFind--;
                    if (leftBracketsToFind == 0) {
                        leftSideIndex = i;
                        break;
                    }
                }
                if (line.charAt(i) == '}') {
                    leftBracketsToFind ++;
                }
            }
            leftSideIndex = Math.max(leftSideIndex, 0);
            line = line.substring(0, leftSideIndex) + line.substring(rightSideIndex + 1);
        }

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(line);

        long result = 0;
        while(m.find()){
            result = result + Integer.parseInt(m.group());
        }
        System.out.println(result);
    }
}
