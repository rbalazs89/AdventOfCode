package year2023.day15;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    private final ReadLines reader = new ReadLines(2023, 15, 2);
    private final ArrayList<Command> commands = new ArrayList<>();
    private final ArrayList<ArrayList<Lens>> boxes = new ArrayList<>();

    private void prepare(){
        for (int i = 0; i < 256; i++) {
            ArrayList<Lens> box = new ArrayList<>();
            boxes.add(box);
        }

        //PROCESS FILE:
        List<String> input = reader.readFile();
        String[] items = input.getFirst().split(",\\s*");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(items));
        Pattern pattern = Pattern.compile("^([A-Za-z]+)([-=]?)(\\d*)$");

        for (int i = 0; i < arrayList.size(); i++) {
            Matcher matcher = pattern.matcher(arrayList.get(i));
            String[] result = new String[3];
            if (matcher.matches()) {
                result[0] = matcher.group(1);
                result[1] = matcher.group(2);
                result[2] = matcher.group(3);
            }
            int type = 0;
            if (result[1].equals("=")){
                type = 1;
            }
            Command command = new Command(result[0], hashing(result[0]), type);
            if(type == 1){
                command.number = Integer.parseInt(result[2]);
            }
            commands.add(command);
        }
    }

    public void part1(){
        List<String> input = reader.readFile();
        String[] items = input.getFirst().split(",\\s*");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(items));

        ArrayList<Integer> solutions = new ArrayList<>();
        for (int j = 0; j < arrayList.size(); j++) {
            int value = 0;
            for (int i = 0; i < arrayList.get(j).length(); i++) {
                value = function(value, arrayList.get(j).substring(i,i+1));
            }
            solutions.add(value);
        }
        int result = 0;
        for (int i = 0; i < solutions.size(); i++) {
            result += solutions.get(i);
        }
        System.out.println(result);
    }

    public void part2(){
        prepare();
        for (int i = 0; i < commands.size(); i++) {
            // type 1:
            if (commands.get(i).type == 1){
                Lens lens = new Lens();
                lens.boxNumber = commands.get(i).boxNumber;
                lens.label = commands.get(i).label;
                lens.number = commands.get(i).number;

                boolean contains = false;
                int position = -1;
                for (int j = 0; j < boxes.get(lens.boxNumber).size(); j++) {
                    if(boxes.get(lens.boxNumber).get(j).label.equals(lens.label)){
                        contains = true;
                        position = j;
                    }
                }
                if(contains){
                    boxes.get(lens.boxNumber).get(position).number = lens.number;
                }
                if(!contains){
                    boxes.get(lens.boxNumber).add(lens);
                }
            }

            //type 0:
            if(commands.get(i).type == 0){
                boolean contains = false;
                int position = -1;
                for (int j = 0; j < boxes.get(commands.get(i).boxNumber).size(); j++) {
                    if(boxes.get(commands.get(i).boxNumber).get(j).label.equals(commands.get(i).label)){
                        contains = true;
                        position = j;
                    }
                }
                if(contains){
                    boxes.get(commands.get(i).boxNumber).remove(position);
                }
            }
        }

        //CALCULATE FOCUSING POWER
        int solution = 0;
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).size(); j++) {
                solution = solution + boxes.get(i).get(j).number * (i+1) * (j+1);
            }
        }

        System.out.println(solution);
    }

    private int hashing(String s){
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = function(result, s.substring(i,i+1));
        }
        return result;
    }

    private int function(int number, String s){
        number = number + (int)(s.charAt(0));
        number = number * 17;
        number = number % 256;
        return number;
    }
}