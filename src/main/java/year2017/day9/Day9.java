package year2017.day9;

import main.ReadLines;

import java.util.List;

public class Day9 {
    private final ReadLines reader = new ReadLines(2017, 9, 2);
    private List<String> fileLines; // lines from txt file

    // puzzle:
    private String stream;

    private void readData() {
        fileLines = reader.readFile();
        stream = fileLines.getFirst();
    }

    public void part1(){
        readData();
        int groupMultiplier = 0;
        int globalCounter = 0;
        boolean insideGarbage = false;
        for (int i = 0; i < stream.length(); i++) {
            char c = stream.charAt(i);
            if(!checkIfSafe(i)){
                continue;
            }

            if(c == '<' && !insideGarbage){
                insideGarbage = true;
            }

            else if(c == '>' && insideGarbage){
                insideGarbage = false;
            }

            if(c == '{' && !insideGarbage){
                groupMultiplier ++;
                globalCounter += groupMultiplier;
            }

            // every '{' will have ending '}'
            if(c == '}' && !insideGarbage){
                groupMultiplier --;
            }
        }
        System.out.println(globalCounter);
    }

    public void part2(){
        readData();
        int trashCounter = 0;
        boolean insideGarbage = false;
        for (int i = 0; i < stream.length(); i++) {
            char c = stream.charAt(i);
            if(!checkIfSafe(i)){
                continue;
            }

            // starting and ending <> are not counted
            if(c == '<' && !insideGarbage){
                insideGarbage = true;
            }

            else if(c == '>' && insideGarbage){
                insideGarbage = false;
            }

            // inside counted: other >>><<<
            // not counted: cancelled characters and '!' itself
            else if (c != '!' && insideGarbage){
                trashCounter ++;
            }
        }
        System.out.println(trashCounter);

    }

    // no stream starts with '!'
    private boolean checkIfSafe(int index){
        if(index == 0){
            return true;
        }

        int counter = 0;
        index --; // check before the affected character first
        while (stream.charAt(index) == '!'){
            index --;
            counter ++;
            if(index == 0){
                break;
            }
        }

        // exclamation cancels effect if number of exclamation marks right before character are odd
        return counter % 2 == 0;
    }
}
