package year2015.day15;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    List<String> fileLines;
    int inputFileIndex = 2;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    int finalScore = 0;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void processData(){
        ingredients.clear();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);

            Pattern p = Pattern.compile("-?\\d+");
            Matcher m = p.matcher(line);

            ArrayList<Integer> thisLine = new ArrayList<>();
            while(m.find()){
                thisLine.add(Integer.parseInt(m.group()));
            }
            ingredients.add(new Ingredient(thisLine.get(0), thisLine.get(1), thisLine.get(2), thisLine.get(3), thisLine.get(4)));
        }
    }

    public void part1(){
        readData();
        processData();

        long maxScore = 0;

        for (int a = 0; a <= 100; a++) {
            for (int b = 0; b <= 100 - a; b++) {
                for (int c = 0; c <= 100 - a - b; c++) {
                    int d = 100 - a - b - c;
                    ingredients.get(0).spoons = a;
                    ingredients.get(1).spoons = b;
                    ingredients.get(2).spoons = c;
                    ingredients.get(3).spoons = d;

                    long currentScore = score();
                    if (currentScore > maxScore) {
                        maxScore = currentScore;
                    }
                }
            }
        }
        System.out.println("part1 result: " + maxScore);
    }

    public void part2(){

        readData();
        processData();

        long maxScore = 0;

        for (int a = 0; a <= 100; a++) {
            for (int b = 0; b <= 100 - a; b++) {
                for (int c = 0; c <= 100 - a - b; c++) {
                    int d = 100 - a - b - c;

                    ingredients.get(0).spoons = a;
                    ingredients.get(1).spoons = b;
                    ingredients.get(2).spoons = c;
                    ingredients.get(3).spoons = d;

                    int calories = 0;
                    for (int i = 0; i < ingredients.size(); i++) {
                        calories += ingredients.get(i).calories * ingredients.get(i).spoons;
                    }
                    if(calories != 500){
                        continue;
                    }

                    long currentScore = score();
                    if (currentScore > maxScore) {
                        maxScore = currentScore;
                    }
                }
            }
        }
        System.out.println("part2 result: " + maxScore);

    }

    long score(){
        int[] score = new int[4];
        for (int i = 0; i < ingredients.size(); i++) {
            score[0] += ingredients.get(i).capacity * ingredients.get(i).spoons;
            score[1] += ingredients.get(i).durability * ingredients.get(i).spoons;
            score[2] += ingredients.get(i).flavor * ingredients.get(i).spoons;
            score[3] += ingredients.get(i).texture * ingredients.get(i).spoons;
        }

        for (int i = 0; i < score.length; i++) {
            score[i] = Math.max(score[i], 0);
        }

        finalScore = 1;
        for (int i = 0; i < score.length; i++) {
            finalScore = finalScore * score[i];
        }

        return finalScore;
    }
}
