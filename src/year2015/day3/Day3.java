package year2015.day3;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day3 {
    ArrayList<House> houses = new ArrayList<>();
    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        readData();

        int currentX = 0;
        int currentY = 0;
        newHouse(0, 0);
        for(int i = 0; i < fileLines.get(0).length(); i ++){
            if(fileLines.get(0).charAt(i) == '<'){
                currentX--;
            } else if(fileLines.get(0).charAt(i) == '>'){
                currentX++;
            } else if(fileLines.get(0).charAt(i) == '^'){
                currentY++;
            } else if(fileLines.get(0).charAt(i) == 'v'){
                currentY--;
            }
            newHouse(currentX,currentY);
        }
        System.out.println(houses.size());
    }

    public void newHouse (int x,int y){
        boolean houseExists = false;
        House house = new House(x, y);
        for(int i = 0; i < houses.size(); i ++){
            if(houses.get(i).x == x && houses.get(i).y == y){
                houseExists = true;
                house = houses.get(i);
            }
        }
        if (!houseExists){
            houses.add(house);
        }

        house.visited ++;
    }

    public void part2(){
        readData();
        houses.clear();

        int santaX = 0;
        int santaY = 0;
        int roboSantaX = 0;
        int roboSantaY = 0;
        newHouse(0, 0);
        for(int i = 0; i < fileLines.get(0).length(); i ++){
            if(fileLines.get(0).charAt(i) == '<'){
                santaX--;
            } else if(fileLines.get(0).charAt(i) == '>'){
                santaX++;
            } else if(fileLines.get(0).charAt(i) == '^'){
                santaY++;
            } else if(fileLines.get(0).charAt(i) == 'v'){
                santaY--;
            }
            //System.out.println( "santa " + santaY + " " +  santaX);
            newHouse(santaX,santaY);
            if(i + 1 < fileLines.get(0).length()){
                i ++;
                if(fileLines.get(0).charAt(i) == '<'){
                    roboSantaX--;
                } else if(fileLines.get(0).charAt(i) == '>'){
                    roboSantaX++;
                } else if(fileLines.get(0).charAt(i) == '^'){
                    roboSantaY++;
                } else if(fileLines.get(0).charAt(i) == 'v'){
                    roboSantaY--;
                }
                //System.out.println( "ROBOSANTA " + roboSantaY + " " +  roboSantaX);
                newHouse(roboSantaX, roboSantaY);
            }
        }
        //for(int i = 0; i < houses.size(); i ++){
        //    System.out.println(houses.get(i).x + " " + houses.get(i).y);
        //}
        System.out.println(houses.size());
    }
}
