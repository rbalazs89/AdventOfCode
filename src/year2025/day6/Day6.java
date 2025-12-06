package year2025.day6;

import main.ReadLines;

import java.util.List;

public class Day6 {
    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        // process file:
        readData();

        for (int i = 0; i < fileLines.size(); i++) {
            fileLines.set(i, fileLines.get(i).trim());
            fileLines.set(i, fileLines.get(i).replaceAll(" {2,}", " "));
        }

        long[][] numbers = new long[fileLines.size() - 1][fileLines.get(0).split(" ").length];

        for (int i = 0; i < fileLines.size() - 1; i++) {
            String[] parts = fileLines.get(i).split(" ");
            for (int j = 0; j < parts.length; j++) {
                numbers[i][j] = Integer.parseInt(parts[j]);
            }
        }
        String[] symbols = fileLines.get(fileLines.size() - 1).split(" ");

        //solution:
        long[] result = new long[numbers[0].length];

        for (int i = 0; i < numbers[0].length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if(symbols[i].equals("+")){
                    result[i] = result[i] + numbers[j][i];
                } else if (symbols[i].equals("*")){
                    if(j == 0){
                        result[i] = numbers[j][i];
                    } else {
                        result[i] = result[i] * numbers[j][i];
                    }
                }
            }
        }

        long part1 = 0;
        for (int i = 0; i < result.length; i++) {
            part1 = part1 + result[i];
        }
        System.out.println(part1);

    }

    public void part2(){
        readData();
        int y = fileLines.size();
        int x = 0;
        for (int i = 0; i < fileLines.size(); i++) {
            x = Math.max(x, fileLines.get(i).length());
        }

        char[][] inputField = new char[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if(j < fileLines.get(i).length()){
                    inputField[i][j] = fileLines.get(i).charAt(j);
                } else {
                    inputField[i][j] = ' ';
                }
            }
        }

        int counter = 0;
        int batchEnd = 1;
        char currentSymbol;
        long part2result = 0;

        while(counter < x){
            // get current symbol
            currentSymbol = inputField[y - 1][batchEnd - 1];

            // find how many numbers in batch:
            int batchStart = batchEnd;
            if(batchStart >= x){
                break;
            }

            for (int i = batchEnd; i < x; i++) {
                batchEnd ++;
                if(inputField[y - 1][i] != ' '){
                    break;
                }
                if(batchEnd >= x - 1){ // adjusting for the last batch of numbers
                    batchEnd++;
                    batchEnd++;
                }
            }

            // build the numbers
            // remove 1 from start to include numbers above sign
            // remove 1 from end to not include separating line and one more because of previous removal
            int thisManyNumbersInThisBatch = batchEnd - 1 - batchStart;

            String[] numbersString = new String[thisManyNumbersInThisBatch];

            int loopCounter = 0;
            for (int i = batchStart - 1; i < batchEnd - 2; i++) { // this many numbers
                for (int j = 0; j < y - 1; j++) { // build numbers digits
                    if(numbersString[loopCounter] != null){
                        numbersString[loopCounter] = numbersString[loopCounter] + (inputField[j][i]);
                    } else {
                        numbersString[loopCounter] = String.valueOf(inputField[j][i]);
                    }
                }
                loopCounter++;
            }

            //make numbers from string
            long[] numbers = new long[thisManyNumbersInThisBatch];
            for (int i = 0; i < numbersString.length; i++) {
                numbersString[i] = numbersString[i].trim();
                numbers[i] = Long.parseLong(numbersString[i]);
            }

            long batchResult = 0;
            for (int i = 0; i < numbers.length; i++) {
                if(currentSymbol == '*'){
                    if(i == 0){
                        batchResult = numbers[i];
                    } else {
                        batchResult = batchResult * numbers[i];
                    }
                } else {
                    batchResult = batchResult + numbers[i];
                }
            }

            part2result = part2result + batchResult;
            counter = counter + thisManyNumbersInThisBatch + 1;
        }

        System.out.println(part2result);
    }
}
