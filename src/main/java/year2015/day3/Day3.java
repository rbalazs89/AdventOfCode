package year2015.day3;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    private final ReadLines reader = new ReadLines(2015, 3, 2);
    private List<String> fileLines;

    // Stores all visited houses, unique by coordinate
    private final ArrayList<House> houses = new ArrayList<>();

    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();

        int currentX = 0;
        int currentY = 0;

        // Starting house
        newHouse(0, 0);

        String instructions = fileLines.getFirst();

        for (int i = 0; i < instructions.length(); i++) {
            char move = instructions.charAt(i);

            // Update coordinates based on direction
            if (move == '<') {
                currentX--;
            } else if (move == '>') {
                currentX++;
            } else if (move == '^') {
                currentY++;
            } else if (move == 'v') {
                currentY--;
            }

            // Record the visit
            newHouse(currentX, currentY);
        }

        System.out.println(houses.size());
    }

    public void part2(){
        readData();
        houses.clear();

        int santaX = 0, santaY = 0;
        int roboSantaX = 0, roboSantaY = 0;

        // Starting house
        newHouse(0, 0);
        String instructions = fileLines.getFirst();

        for (int i = 0; i < instructions.length(); i++) {

            // Santa moves first
            char santaMove = instructions.charAt(i);
            if (santaMove == '<') {
                santaX--;
            } else if (santaMove == '>') {
                santaX++;
            } else if (santaMove == '^') {
                santaY++;
            } else if (santaMove == 'v') {
                santaY--;
            }
            newHouse(santaX, santaY);

            // RoboSanta moves next if there is a next instruction
            if (i + 1 < instructions.length()) {
                i++; // manually advance to RoboSanta instruction
                char roboMove = instructions.charAt(i);

                if (roboMove == '<') {
                    roboSantaX--;
                } else if (roboMove == '>') {
                    roboSantaX++;
                } else if (roboMove == '^') {
                    roboSantaY++;
                } else if (roboMove == 'v') {
                    roboSantaY--;
                }

                newHouse(roboSantaX, roboSantaY);
            }
        }
        System.out.println(houses.size());
    }

    private void newHouse(int x, int y){
        boolean houseExists = false;
        House house = new House(x, y);

        // Check if this coordinate already exists in the list
        for (int i = 0; i < houses.size(); i++) {
            House existing = houses.get(i);
            if (existing.x == x && existing.y == y) {
                houseExists = true;
                house = existing; // reuse existing house
                break;
            }
        }

        // If its a new coordinate, add it
        if (!houseExists) {
            houses.add(house);
        }

        house.visited++;
    }
}