package year2025.day3;

import main.ReadLines;

import java.util.List;

public class Day3 {

    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2025, 3, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        int sum = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            int currentMax = 0;
            for (int j = 0; j < fileLines.get(i).length(); j++) {
                for (int k = j + 1; k < fileLines.get(i).length(); k++) {

                    int current = Integer.parseInt(
                            fileLines.get(i).charAt(j) +
                                    fileLines.get(i).substring(k, k + 1));
                    if(currentMax < current){
                        currentMax = current;
                    }
                }
            }
            sum = sum + currentMax;
        }
        System.out.println(sum);
    }

    public void part2(){
        readData();
        long sum = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            String s = "";
            int joltDigits = 11; //incl 0
            int oneLineMaxDigits = fileLines.getFirst().length();
            int currentMax;
            int adjustValue = 0;

            while(joltDigits >= 0){

                String currentString = fileLines.get(i).substring(11 - joltDigits + adjustValue,oneLineMaxDigits - joltDigits);
                System.out.println("currentString: " + currentString);
                currentMax = 0;

                //get max digits from the available range
                for (int j = 0; j < currentString.length(); j++) {
                    int current = Integer.parseInt(currentString.substring(j, j + 1));
                    if(current > currentMax){
                        currentMax = current;
                    }
                }

                //get the first appearance of the max digit
                for (int j = 0; j < currentString.length(); j++) {
                    if(currentString.substring(j, j + 1).equals(String.valueOf(currentMax))){
                        adjustValue = adjustValue + j;
                        break;
                    }
                }

                s = s + (currentMax);

                joltDigits--;
                System.out.println(s);
            }
            sum = sum + Long.parseLong(s);
        }

        System.out.println(sum);
    }
}
