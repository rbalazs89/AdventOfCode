package year2023.day11;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day11 {
    private final ReadLines reader = new ReadLines(2023, 11, 2);
    private final ArrayList<Galaxy> galaxies = new ArrayList<>();
    private int sizeX;
    private int sizeY;

    private void prepare(){
        galaxies.clear();
        List<String> input = reader.readFile();
        sizeX = input.getFirst().length();
        sizeY = input.size();
        for(int i = 0; i < sizeY; i ++){
            for (int j = 0; j < sizeX; j++) {
                if(input.get(i).charAt(j) == '#'){
                    galaxies.add(new Galaxy(j,i));
                }
            }
        }
    }

    public void part1(){
        prepare();

        List<String> input = reader.readFile();
        int yExpansion = 0;
        for(int i = 0; i < sizeY; i ++){
            int tempCounter = 0;
            for (int j = 0; j < sizeX; j++) {
                if(input.get(i).charAt(j) != '#'){
                    tempCounter++;
                }
            }

            if (tempCounter == sizeX){
                yExpansion++;
                for (int k = 0; k < galaxies.size(); k++) {
                    if(galaxies.get(k).y >= i + yExpansion){
                        galaxies.get(k).y ++;
                    }
                }
            }
        }

        //Horizontal expansion:
        int xExpansion = 0;
        for(int i = 0; i < sizeX; i ++){

            int tempCounter = 0;
            for (int j = 0; j < sizeY; j++) {
                if(input.get(j).charAt(i) != '#'){
                    tempCounter++;
                }
            }

            if (tempCounter == sizeY){
                xExpansion++;
                for (int k = 0; k < galaxies.size(); k++) {
                    if(galaxies.get(k).x >= i + xExpansion){
                        galaxies.get(k).x ++;
                    }
                }
            }
        }
        int result = 0;

        //CALCULATING DISTANCES
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = 0; j < galaxies.size(); j++) {
                result += Math.abs(galaxies.get(i).x - galaxies.get(j).x) + Math.abs(galaxies.get(i).y - galaxies.get(j).y);
            }
        }
        System.out.println(result/2);
    }

    public void part2(){
        prepare();
        List<String> input = reader.readFile();

        int yEnlargedExpansion = 999999;
        int yExpansion = 0;
        for(int i = 0; i < sizeY; i ++){
            int tempCounter = 0;
            for (int j = 0; j < sizeX; j++) {
                if(input.get(i).charAt(j) != '#'){
                    tempCounter++;
                }
            }

            if (tempCounter == sizeX){
                yExpansion++;
                for (int k = 0; k < galaxies.size(); k++) {
                    if(galaxies.get(k).y >= i + yExpansion){
                        galaxies.get(k).y ++;
                        galaxies.get(k).enlargedY = galaxies.get(k).enlargedY + yEnlargedExpansion;
                    }
                }
            }
        }

        //Horizontal expansion:
        int xExpansion = 0;
        int xEnlargedExpansion = 999999;
        for(int i = 0; i < sizeX; i ++){
            int tempCounter = 0;
            for (int j = 0; j < sizeY; j++) {
                if(input.get(j).charAt(i) != '#'){
                    tempCounter++;
                }
            }

            if (tempCounter == sizeY){
                xExpansion++;
                for (int k = 0; k < galaxies.size(); k++) {
                    if(galaxies.get(k).x >= i + xExpansion){
                        galaxies.get(k).x ++;
                        galaxies.get(k).enlargedX = galaxies.get(k).enlargedX + xEnlargedExpansion;
                    }
                }
            }
        }

        long result = 0;
        //CALCULATING DISTANCES
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = 0; j < galaxies.size(); j++) {
                result += Math.abs(galaxies.get(i).enlargedX - galaxies.get(j).enlargedX) + Math.abs(galaxies.get(i).enlargedY - galaxies.get(j).enlargedY);
            }
        }

        System.out.println(result/2);
    }
}