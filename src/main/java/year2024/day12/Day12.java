package year2024.day12;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day12 {
    private final ReadLines reader = new ReadLines(2024, 12, 2);
    private static Plot[][] field;
    private static int maxX = 0;
    private static int maxY = 0;
    private static final ArrayList<Patch> patches = new ArrayList<>();

    private void prepare(){
        List<String> input = reader.readFile();
        maxX = input.getFirst().length();
        maxY = input.size();
        field = new Plot[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                Plot plot = new Plot();
                plot.type = String.valueOf(input.get(i).charAt(j));
                plot.positionX = j;
                plot.positionY = i;
                field[i][j] = plot;
            }
        }
    }

    public void part1(){
        prepare();
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(!field[i][j].visited){
                    Patch patch = new Patch();
                    patches.add(patch);
                    checkOneField(patch, j, i, field[i][j].type);
                }
            }
        }
        int result  = 0;

        for (int i = 0; i < patches.size(); i++) {
            int perimeter = 0;
            for (int j = 0; j < patches.get(i).plots.size(); j++) {
                perimeter = perimeter + 4 - patches.get(i).plots.get(j).similarNeighbour;
            }
            result = result + patches.get(i).plots.size() * perimeter;
        }
        System.out.println(result);
    }

    // requires part1
    public void part2(){
        for (int i = 0; i < patches.size(); i++) {
            int result = 0;
            Patch currentPatch = patches.get(i);
            for (int j = 0; j < currentPatch.plots.size(); j++) {
                Plot currentPlot = currentPatch.plots.get(j);
                int corners = howManyCorners(currentPatch, currentPlot.positionX, currentPlot.positionY);
                currentPlot.sides = corners;
                result = result + corners;
            }
        }
        for (int i = 0; i < patches.size(); i++) {
            int sideSum = 0;
            for (int j = 0; j < patches.get(i).plots.size(); j++) {
                sideSum = sideSum + patches.get(i).plots.get(j).sides;
            }
            patches.get(i).sides = sideSum;
        }
        int part2result = 0;
        for (int i = 0; i < patches.size(); i++) {
            part2result = part2result + patches.get(i).sides * patches.get(i).plots.size();
        }

        System.out.println(part2result);
    }

    private static int howManyCorners(Patch currentPatch, int x, int y){
        int sides = 0;

        // outside corners:
        //check if bottom right is an "outside" corner:
        if(y + 1 < maxY && x + 1 < maxX){
            if(!currentPatch.plots.contains(field[y + 1][x]) && !currentPatch.plots.contains(field[y][x + 1])){
                sides ++;
            }
        }
        else if(y + 1 >= maxY && x + 1 >= maxX){
            sides ++;
        }
        else if(x + 1  < maxX){
            if(y + 1 >= maxY && !currentPatch.plots.contains(field[y][x + 1])){
                sides ++;
            }
        }
        else if(y + 1 < maxY){
            if(x + 1 >= maxX && !currentPatch.plots.contains(field[y + 1][x])){
                sides ++;
            }
        }

        //check if top right is an "outside" corner
        if(x + 1 < maxX && y - 1 >= 0){
            if(!currentPatch.plots.contains(field[y][x + 1]) && !currentPatch.plots.contains(field[y - 1][x])){
                sides ++;
            }
        }
        else if(x + 1 >= maxX &&  y - 1 < 0){
            sides ++;
        }
        else if(x + 1 < maxX){
            if(y - 1 <= 0 && !currentPatch.plots.contains(field[y][x + 1])){
                sides ++;
            }
        }
        else if(y - 1 >= 0){
            if(x + 1 >= maxX && !currentPatch.plots.contains(field[y - 1][x])){
                sides ++;
            }
        }

        //check if top left corner is an "outside" corner
        if(x - 1 >= 0 && y - 1 >= 0) {
            if (!currentPatch.plots.contains(field[y - 1][x]) && !currentPatch.plots.contains(field[y][x - 1])) {
                sides++;
            }
        }
        else if(x - 1 < 0 && y - 1 < 0){
            sides ++;
        }
        else if(x - 1 >= 0){
            if (y - 1 < 0 && !currentPatch.plots.contains(field[y][x - 1])){
                sides ++;
            }
        }
        else {
            if (!currentPatch.plots.contains(field[y - 1][x])){
                sides++;
            }
        }

        //check if bottom left is an outside corner
        if(x - 1 >= 0 && y + 1 < maxY){
            if(!currentPatch.plots.contains(field[y][x - 1]) && !currentPatch.plots.contains(field[y + 1][x])){
                sides ++;
            }
        }
        else if(x - 1 < 0 && y + 1 >= maxY){
            sides ++;
        }
        else if(x - 1 >= 0){
            if(!currentPatch.plots.contains(field[y][x - 1]) && y + 1 >= maxY){
                sides ++;
            }
        }
        else if(y + 1 < maxY){
            if(!currentPatch.plots.contains(field[y + 1][x])){
                sides ++;
            }
        }

        //inside corners:
        //bottom right inside corner:
        if(y + 1 < maxY && x + 1 < maxX){
            if(currentPatch.plots.contains(field[y + 1][x]) && currentPatch.plots.contains(field[y][x + 1]) &&
                    !currentPatch.plots.contains(field[y + 1][x + 1])){
                sides ++;
            }
        }
        //top right inside corner:
        if(x + 1 < maxX && y - 1 >= 0){
            if(currentPatch.plots.contains(field[y][x + 1]) && currentPatch.plots.contains(field[y - 1][x]) &&
                    !currentPatch.plots.contains(field[y - 1][x + 1])){
                sides ++;
            }
        }
        //top left inside corner:
        if(x - 1 >= 0 && y - 1 >= 0) {
            if (currentPatch.plots.contains(field[y - 1][x]) && currentPatch.plots.contains(field[y][x - 1]) &&
                    !currentPatch.plots.contains(field[y - 1][x - 1])){
                sides++;
            }
        }
        //bottom left inside corner:
        if(x - 1 >= 0 && y + 1 < maxY){
            if(currentPatch.plots.contains(field[y][x - 1]) && currentPatch.plots.contains(field[y + 1][x]) &&
                    !currentPatch.plots.contains(field[y + 1][x - 1])){
                sides ++;
            }
        }
        return sides;
    }

    private static void checkOneField(Patch currentPatch, int currentX, int currentY, String type) {
        field[currentY][currentX].visited = true;

        if(!currentPatch.plots.contains(field[currentY][currentX])){
            currentPatch.plots.add(field[currentY][currentX]);
        }

        int[][] pathSteps = {{-1,0},{1,0},{0,1},{0,-1}};
        for (int[] pathStep : pathSteps) {
            int nextX = currentX + pathStep[0];
            int nextY = currentY + pathStep[1];
            if (nextX >= 0 && nextX < maxX && nextY >= 0 && nextY < maxY) {
                if (field[nextY][nextX].type.equals(type)) {
                    field[nextY][nextX].similarNeighbour++;
                    if (!currentPatch.plots.contains(field[nextY][nextX])) {
                        currentPatch.plots.add(field[nextY][nextX]);
                    }
                }
            }
        }

        for (int[] pathStep : pathSteps) {
            int nextX = currentX + pathStep[0];
            int nextY = currentY + pathStep[1];
            if (nextX >= 0 && nextX < maxX && nextY >= 0 && nextY < maxY) {
                if (field[nextY][nextX].type.equals(type) && !field[nextY][nextX].visited) {
                    checkOneField(currentPatch, nextX, nextY, type);
                }
            }
        }
    }
}
