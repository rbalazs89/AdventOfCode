package year2024.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    private final ReadLines reader = new ReadLines(2024, 10, 2);
    private static Step[][] field;
    private static int maxX = 0;
    private static int maxY = 0;
    private static int part2Result = 0;

    private void prepare(){
        List<String> input = reader.readFile();

        maxY = input.size();
        maxX = input.getFirst().length();
        field = new Step[maxY][maxX];

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                Step step = new Step();
                field[i][j] = step;
                step.posX = j;
                step.posY = i;
                step.value = Integer.parseInt(input.get(i).substring(j, j + 1));
            }
        }
    }

    public void part1(){
        prepare();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(field[i][j].value == 0){
                    stepNext(field[i][j], j, i, 0);
                }
            }
        }

        int result = 0;
        for (Step[] steps : field) {
            for (int j = 0; j < field[0].length; j++) {
                result = result + steps[j].results.size();
            }
        }
        System.out.println(result);
        System.out.println(part2Result);
    }

    public void part2(){
        // nothing to do here, already calculated in part 1
    }

    private static void stepNext(Step step, int currentX, int currentY, int fieldValue){
        if(field[currentY][currentX].value == 9){
            part2Result ++;
            if(!step.results.contains(field[currentY][currentX])){
                step.results.add(field[currentY][currentX]);
            }
            return;
        }
        fieldValue = fieldValue + 1;

        int[][] pathSteps = {{-1,0},{1,0},{0,1},{0,-1}};
        for (int[] pathStep : pathSteps) {
            int nextX = currentX + pathStep[0];
            int nextY = currentY + pathStep[1];
            if (nextX >= 0 && nextX < maxX && nextY >= 0 && nextY < maxY && field[nextY][nextX].value == fieldValue) {
                stepNext(step, nextX, nextY, fieldValue);
            }
        }
    }

    private static class Step {
        public int value;
        public ArrayList<Step> results = new ArrayList<>();
        public int posX;
        public int posY;
    }
}
