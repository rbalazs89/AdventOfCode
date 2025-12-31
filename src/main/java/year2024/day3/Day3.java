package year2024.day3;

import main.ReadLines;

import java.util.List;

public class Day3 {
    private final ReadLines reader = new ReadLines(2024, 3, 2);

    public void part1(){
        List<String> memory = reader.readFile();
        int result = 0;

        for (int i = 0; i < memory.size(); i++) {
            String currentString = memory.get(i);

            while(currentString.length() > 8){
                String investigate;
                int number1;
                int number2;

                if(!currentString.contains("mul(")){
                    break;
                } else {
                    investigate = currentString.substring(currentString.indexOf("mul(") + 4);
                }
                int temp = investigate.indexOf(",");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number1 = Integer.parseInt(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }

                investigate = investigate.substring(temp + 1);
                temp = investigate.indexOf(")");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number2 = Integer.parseInt(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }
                investigate = investigate.substring(temp);
                currentString = investigate;
                result = result + number1 * number2;
            }
        }
        System.out.println(result);
    }


    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void part2(){
        List<String> memory = reader.readFile();

        int result = 0;
        boolean valid = true;
        for (int i = 0; i < memory.size(); i++) {

            String currentString = memory.get(i);
            while(currentString.length() > 8){
                String investigate = "";
                int number1;
                int number2;

                int doIndex = currentString.indexOf("do()");
                int dontIndex = currentString.indexOf("don't()");
                int mulIndex = currentString.indexOf("mul(");

                if(mulIndex == -1){
                    mulIndex = Integer.MAX_VALUE;
                }

                if(doIndex == -1){
                    doIndex = Integer.MAX_VALUE;
                }

                if(dontIndex == -1){
                    dontIndex = Integer.MAX_VALUE;
                }

                if(doIndex < mulIndex && doIndex < dontIndex){
                    valid = true;
                    currentString = currentString.substring(doIndex + 4);
                    continue;
                }

                if(dontIndex < mulIndex){
                    valid = false;
                    currentString = currentString.substring(dontIndex + 7);
                    continue;
                }

                if(mulIndex < doIndex && mulIndex < dontIndex){
                    investigate = currentString.substring(currentString.indexOf("mul(") + 4);
                }
                if(mulIndex == Integer.MAX_VALUE){
                    break;
                }

                int temp = investigate.indexOf(",");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number1 = Integer.parseInt(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }

                investigate = investigate.substring(temp + 1);
                temp = investigate.indexOf(")");
                if(temp <= 3 && temp >= 1){
                    if(isNumeric(investigate.substring(0, temp))){
                        number2 = Integer.parseInt(investigate.substring(0, temp));
                    } else {
                        currentString = investigate;
                        continue;
                    }
                } else {
                    currentString = currentString.substring(1);
                    continue;
                }
                investigate = investigate.substring(temp);
                currentString = investigate;
                if(!valid){
                    number1 = 0;
                    number2 = 0;
                }
                result = result + number1 * number2;
            }
        }
        System.out.println(result);
    }
}