package year2026.day2;

import main.ReadLines;

import java.util.List;

public class Day2 {
    List<String> fileLines;
    Long[] IDNumbers;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void getNumbers(){
        readData();
        String[] IDRanges = fileLines.get(0).split(","); // txt is one line only
        IDNumbers = new Long[IDRanges.length * 2];
        for (int i = 0; i < IDRanges.length; i++) {
            String[] temp = IDRanges[i].split("-");
            IDNumbers[2 * i] = Long.valueOf(temp[0]);
            IDNumbers[2 * i + 1] = Long.valueOf(temp[1]);
        }
    }

    public void part1(){
        getNumbers();
        long result = 0;
        for (int i = 0; i < IDNumbers.length / 2; i++) {

            Long first = IDNumbers[i * 2];
            Long second = IDNumbers[i * 2 + 1];
            while(first < second){
                if(isRepeatedTwice(first)){
                    result = result + first;
                }
                first ++;
            }
        }
        System.out.println(result);
    }

    public void part2(){
        getNumbers();
        long result = 0;
        for (int i = 0; i < IDNumbers.length / 2; i++) {

            Long first = IDNumbers[i * 2];
            Long second = IDNumbers[i * 2 + 1];
            while(first < second){
                if(isRepeatedAtLeastTwice(first)){
                    result = result + first;
                }
                first ++;
            }
        }
        System.out.println(result);
    }

    public boolean isRepeatedTwice(long n) {
        int totalDigits = 0;
        long temp = n;
        while (temp > 0) {
            totalDigits++;
            temp = temp / 10;
        }

        if (totalDigits % 2 != 0){
            return false;
        }

        int halfDigits = totalDigits / 2;

        long divider = 1;
        for (int i = 0; i < halfDigits; i++) {
            divider = divider * 10;
        }

        long firstHalf  = n / divider;
        long secondHalf = n % divider;

        return firstHalf == secondHalf;
    }

    public boolean isRepeatedAtLeastTwice(long input){
        //TODO numbers only version, no string
        String numberAsString = Long.toString(input);
        int totalLength = numberAsString.length();

        // try every possible pattern length that divides the total length
        for (int patternLength = 1; patternLength <= totalLength / 2; patternLength++) {

            // only try lengths that divide evenly
            if (totalLength % patternLength != 0) {
                continue;
            }

            // repeating part
            String pattern = numberAsString.substring(0, patternLength);

            // build full number if repeated
            String fullRepeated = pattern.repeat(totalLength / patternLength);

            // If they match exactly
            if (fullRepeated.equals(numberAsString)) {
                return true;
            }
        }

        return false;
    }

    private static long pow10(int exp) {
        long result = 1;
        for (int i = 0; i < exp; i++) result *= 10;
        return result;
    }
}
