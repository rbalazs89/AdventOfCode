package year2016.day24;

import main.ReadLines;
import java.util.*;

public class Day24 {

    // setup:
    private final ReadLines reader = new ReadLines(2016, 24, 1);
    private List<String> fileLines; // lines from txt file
    private boolean prepared = false;

    // puzzle:
    private char[][] grid; // digits mean passages, '.' is empty space, '#' means walls, initialized once, never modified
    private int maxX, maxY; // initialized once, never modified
    private final ArrayList<Passage> passages = new ArrayList<>(); // initialized once, never modified
    private int part1Solution = Integer.MAX_VALUE;
    private int part2Solution = Integer.MAX_VALUE;

    private void readData(){
        fileLines = reader.readFile();
    }

    private void prepare(){
        if(prepared){
            return;
        }

        // prepare the grid & fill passages arraylist
        readData();
        maxX = fileLines.getFirst().length();
        maxY = fileLines.size();
        grid = new char[maxY][maxX];
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                char c = fileLines.get(i).charAt(j);
                grid[i][j] = c;
                if(Character.isDigit(c)){
                    passages.add(new Passage(c, j, i));
                }
            }
        }
        prepared = true;
    }

    public void part1(){
        prepare();

        // get each distances passages
        for (int i = 0; i < passages.size(); i++) {
            searchingDistances(passages.get(i));
        }

        // route must start with 0:
        // add all passages other than 0 to a collection:
        ArrayList<Passage> others = new ArrayList<>();
        for (int i = 0; i < passages.size(); i++) {
            if(passages.get(i).getName() != '0'){
                others.add(passages.get(i));
            }
        }
        boolean[] used = new boolean[others.size()];
        generateAndProcessRoutes(others, new ArrayList<>(), used, false);

        //print result:
        System.out.println(part1Solution);
    }

    private void generateAndProcessRoutes(ArrayList<Passage> psgs, ArrayList<Passage> current, boolean[] used, boolean returnToZero){
        if(current.size() == psgs.size()){
            if(!returnToZero){
                investigateRoute(current);
            } else {
                investigateRoutePart2(current);
            }
            return;
        }

        for (int i = 0; i < psgs.size(); i++) {
            if(!used[i]){
                //one step
                used[i] = true;
                current.add(psgs.get(i));

                // recursive
                generateAndProcessRoutes(psgs, current, used, returnToZero);

                // backtrack
                used[i] = false;
                current.removeLast();
            }
        }
    }

    // sets field variable part1Result to minimum steps in path
    private void investigateRoute(ArrayList<Passage> route){
        int distance = 0;

        // new arraylist for calculation, to avoid modifying the original
        ArrayList<Passage> calculation = new ArrayList<>(route);

        // add zeroth passage as first element
        for (int i = 0; i < passages.size(); i++) {
            if(passages.get(i).getName() == '0'){
                calculation.addFirst(passages.get(i));
                break;
            }
        }
        for (int i = 0; i < calculation.size() - 1; i++) {
            distance += calculation.get(i).getDistanceFromPassage(calculation.get(i + 1));
        }

        // print solution
        part1Solution = Math.min(distance, part1Solution);
    }

    private void investigateRoutePart2(ArrayList<Passage> route){
        int distance = 0;
        // new arraylist for calculation, to avoid modifying the original
        ArrayList<Passage> calculation = new ArrayList<>(route);

        // add zeroth passage as first element
        for (int i = 0; i < passages.size(); i++) {
            if(passages.get(i).getName() == '0'){
                calculation.addFirst(passages.get(i));
                calculation.addLast(passages.get(i));
                break;
            }
        }
        for (int i = 0; i < calculation.size() - 1; i++) {
            distance += calculation.get(i).getDistanceFromPassage(calculation.get(i + 1));
        }

        part2Solution = Math.min(distance, part2Solution);
    }

    public void part2(){
        prepare();

        // get each distances passages
        for (int i = 0; i < passages.size(); i++) {
            searchingDistances(passages.get(i));
        }

        // route must start with 0:
        // add all passages other than 0 to a collection:
        ArrayList<Passage> others = new ArrayList<>();
        for (int i = 0; i < passages.size(); i++) {
            if(passages.get(i).getName() != '0'){
                others.add(passages.get(i));
            }
        }
        boolean[] used = new boolean[others.size()];
        generateAndProcessRoutes(others, new ArrayList<>(), used, true);

        //print result:
        System.out.println(part2Solution);
    }

    // search distances from a passage to all other passages
    private void searchingDistances(Passage psg){
        Set<Integer> visited = new HashSet<>(); // format is "y,x" (eg: "13,4")
        Queue<int[]> queue = new LinkedList<>(); // two elements in array, first element is y, second is x
        queue.add(new int[]{psg.getY(), psg.getX()});
        visited.add(psg.getY() * maxX + psg.getX());

        int counter = 0;
        while(!queue.isEmpty()){
            counter ++;
            int queueSizeAtThisStep = queue.size();

            for (int i = 0; i < queueSizeAtThisStep; i++) {
                int[] current = queue.poll();
                int y = current[0];
                int x = current[1];

                // step up:
                if(y - 1 >= 0){
                    char c = grid[y - 1][x];
                    if(c != '#'){
                        if(visited.add((y - 1) * maxX + x)){
                            queue.add(new int[]{y - 1, x});
                            if(Character.isDigit(c)){
                                addDistance(psg, c, counter);
                            }
                        }
                    }
                }

                // step right:
                if(x + 1 < maxX){
                    char c = grid[y][x + 1];
                    if(c != '#'){
                        if(visited.add(y * maxX + (x + 1))){
                            queue.add(new int[]{y, x + 1});
                            if(Character.isDigit(c)){
                                addDistance(psg, c, counter);
                            }
                        }
                    }
                }

                // step down:
                if(y + 1 < maxY){
                    char c = grid[y + 1][x];
                    if(c != '#'){
                        if(visited.add((y + 1) * maxX + x)){
                            queue.add(new int[]{y + 1, x});
                            if(Character.isDigit(c)){
                                addDistance(psg, c, counter);
                            }
                        }
                    }
                }

                // step left:
                if(x - 1 >= 0){
                    char c = grid[y][x - 1];
                    if(c != '#'){
                        if(visited.add(y * maxX + x - 1)){
                            queue.add(new int[]{y, x - 1});
                            if(Character.isDigit(c)){
                                addDistance(psg, c, counter);
                            }
                        }
                    }
                }
            }

            if(counter > maxX * maxY){
                System.out.println("too many steps");
                break;
            }
        }
    }

    private void addDistance(Passage p, char c, int distance){
        Distance dist = new Distance(p, getPassage(c), distance);
        p.addDistance(dist); // the method inside passage class checks if this is already added;
    }

    /**
     * DEBUG METHOD FOR VISUALIZATION
     */
    private void drawGrid(){
        if(!prepared){
            System.out.println("Prepare grid first from input file");
            return;
        }
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(j == maxX - 1){
                    System.out.print(grid[i][j]);
                } else {
                    System.out.print(grid[i][j] + " ");
                }

            }
            System.out.println();
        }
    }

    private Passage getPassage(char c){
        for (int i = 0; i < passages.size(); i++) {
            Passage current = passages.get(i);
            if(current.getName() == c){
                return current;
            }
        }
        System.out.println("Passage with name " + c + " not found.");
        return null;
    }
}
