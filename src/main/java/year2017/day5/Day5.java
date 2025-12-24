package year2017.day5;

import main.ReadLines;

import java.util.List;

public class Day5 {
    //setup:
    private final ReadLines reader = new ReadLines(2017, 5, 2);
    private List<String> fileLines; // lines from txt file

    // puzzle:
    private int[] numberArray; // filled from prepare

    private void readData() {
        fileLines = reader.readFile();
    }

    private void prepare(){
        readData();
        numberArray = new int[fileLines.size()];
        for (int i = 0; i < fileLines.size(); i++) {
            numberArray[i] = Integer.parseInt(fileLines.get(i));
        }
    }

    public void part1(){
        prepare();

        int[] numbers = numberArray.clone();
        int counter = 0;
        int index = 0;

        while(true){
            counter ++;
            int nextIndex = index + numbers[index];
            numbers[index]++;
            index = nextIndex;

            if(nextIndex >= numbers.length){
                System.out.println(counter);
                break;
            }
        }
    }

    public void part2(){
        prepare();

        int[] numbers = numberArray.clone();
        int counter = 0;
        int index = 0;

        while(true){
            counter ++;
            int nextIndex = index + numbers[index];
            if(numbers[index] >= 3){
                numbers[index] --;
            } else {
                numbers[index]++;
            }

            index = nextIndex;

            if(nextIndex >= numbers.length){
                System.out.println(counter);
                break;
            }
        }
    }
}
