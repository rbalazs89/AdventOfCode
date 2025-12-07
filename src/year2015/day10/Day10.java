package year2015.day10;

import main.ReadLines;

import java.util.List;

public class Day10 {

    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        readData();
        String input = fileLines.get(0);
        String processedString = "";
        char current = ' ';

        for (int j = 0; j < 40; j++) {
            while (true){
                int counter = 0;
                for (int i = 0; i < input.length(); i++) {
                    current = input.charAt(i);
                    if(i + 1 < input.length()){
                        if(current == input.charAt(i + 1)){
                            counter ++;
                        } else {
                            counter++;
                            break;
                        }
                    } else {
                        counter++;
                        break;
                    }
                }
                processedString = processedString + counter + current;
                input = input.substring(counter);
                if(input.isEmpty() || counter == 0){
                    break;
                }

            }

            input = processedString;
            processedString = "";
        }
        System.out.println(input.length());
    }

    public void part2() {
        // same as part1
        readData();
        String input = fileLines.get(0);

        for (int j = 0; j < 50; j++) {
            StringBuilder sb = new StringBuilder(input.length() * 2);

            int i = 0;
            while (i < input.length()) {
                char current = input.charAt(i);
                int count = 1;

                while (i + 1 < input.length() && input.charAt(i + 1) == current) {
                    i++;
                    count++;
                }

                sb.append(count).append(current);
                i++;
            }
            input = sb.toString();
        }
        //System.out.println(input);

        System.out.println(input.length());
    }
}
