package year2023.day18;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day18 {
    private final ReadLines reader = new ReadLines(2023, 18, 2);
    private final ArrayList<Command> instructions = new ArrayList<>();
    private final ArrayList<Coordinate> coordinates = new ArrayList<>();

    private void prepare(){
        instructions.clear();
        coordinates.clear();

        List<String> input = reader.readFile();
        for (int i = 0; i < input.size(); i++) {
            String[] tempString = input.get(i).split(" ");
            Command command = new Command(tempString[0], Integer.parseInt(tempString[1]), tempString[2].substring(2,tempString[2].length()-1));
            instructions.add(command);
        }

        int x = 0;
        int y = 0;

        Coordinate coordinate = new Coordinate(0,0);
        coordinates.add(coordinate);
        for (int i = 0; i < instructions.size(); i++) {
            switch (instructions.get(i).direction) {
                case "U" -> y -= instructions.get(i).number;
                case "R" -> x += instructions.get(i).number;
                case "D" -> y += instructions.get(i).number;
                case "L" -> x -= instructions.get(i).number;
            }
            Coordinate tempCoordinate = new Coordinate(x,y);
            coordinates.add(tempCoordinate);
        }
    }

    public void part1(){
        prepare();

        //calculating area based on coordinates:
        int area = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            area = area + (coordinates.get(i).x - coordinates.get(i + 1).x) * (coordinates.get(i).y + coordinates.get(i + 1).y);
        }
        area = area / 2;

        //calculating fence area:
        int fenceArea = 0;
        for (int i = 0; i < instructions.size(); i++) {
            fenceArea += instructions.get(i).number;
        }

        //adjustment:
        area = area - ((fenceArea - 4)/2) + fenceArea - 1;

        System.out.println(area);
    }

    public void part2(){
        //calculating path and saving coordinates
        int x = 0;
        int y = 0;

        //everything same as part1 but need to fix instructions
        for (int i = 0; i < instructions.size(); i++) {
            instructions.get(i).number = Integer.parseInt(instructions.get(i).hashcode.substring(0,instructions.get(i).hashcode.length()-1),16);
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("0")){
                instructions.get(i).direction = "R";
            }
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("1")){
                instructions.get(i).direction = "D";
            }
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("2")){
                instructions.get(i).direction = "L";
            }
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("3")){
                instructions.get(i).direction = "U";
            }
        }

        Coordinate coordinate = new Coordinate(0,0);
        coordinates.add(coordinate);
        for (int i = 0; i < instructions.size(); i++) {
            switch (instructions.get(i).direction) {
                case "U" -> y -= instructions.get(i).number;
                case "R" -> x += instructions.get(i).number;
                case "D" -> y += instructions.get(i).number;
                case "L" -> x -= instructions.get(i).number;
            }
            Coordinate tempCoordinate = new Coordinate(x,y);
            coordinates.add(tempCoordinate);
        }

        //calculating area based on coordinates:
        long area = getArea();

        System.out.println(area);
    }

    private long getArea() {
        long area = 0L;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            area = area +  ((long)coordinates.get(i).x - (long)coordinates.get(i + 1).x) * ((long)coordinates.get(i).y + (long)coordinates.get(i + 1).y);
        }
        area = area / 2;

        //calculating fence area:
        long fenceArea = 0;
        for (int i = 0; i < instructions.size(); i++) {
            fenceArea += instructions.get(i).number;
        }

        //adjustment:
        area = area - ((fenceArea - 4)/2) + fenceArea - 1;
        return area;
    }
}