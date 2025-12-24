package year2016.day16;

import main.ReadLines;

import java.util.List;

public class Day16 {
    private String startString = "";

    // specified by task description
    private final static int stringLength = 272;
    private final static int part2Length = 35651584;

    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 16, 1);
    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    public void processData(){
        readData();
        startString = fileLines.getFirst();
    }

    public void part1(){
        processData();
        while (startString.length() < stringLength){
            startString = dragon(startString);
        }
        startString = startString.substring(0,stringLength);
        String result = startString;
        while (true){
            result = checkSum(result);
            if(result.length() % 2 == 1){
                break;
            }
        }
        System.out.println(result);
    }

    public void part2(){
        processData();
        while (startString.length() < part2Length){
            startString = dragon(startString);
        }
        startString = startString.substring(0,part2Length);
        String result = startString;
        while (true){
            result = checkSum(result);
            if(result.length() % 2 == 1){
                break;
            }
        }
        System.out.println(result);
    }

    private String dragon(String input){
        StringBuilder temp = new StringBuilder();
        for (int i = input.length() - 1; i >= 0; i--) {
            if(input.charAt(i) == '0'){
                temp.append('1');
            } else{
                temp.append('0');
            }
        }
        String result = temp.toString();
        return input + '0' + result;
    }

    private String checkSum(String input){
        StringBuilder result = new StringBuilder();
        if(input.length() % 2 == 1){
            System.out.println("beep beep");
            return "";
        }

        for (int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == input.charAt(i + 1)){
                result.append('1');
            } else {
                result.append('0');
            }
            i ++;
        }
        return result.toString();
    }
}
