package year2016.day11;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
    // setup:
    private final ReadLines reader = new ReadLines(2016, 11, 2);
    private List<String> fileLines; // lines from txt file

    // puzzle:
    private static final int maxFloor = 4; // task description specifies 1...4 floors
    private static final int GENERATOR = 1; // arbitrary identification
    private static final int MICROCHIP = 2; // arbitrary identification
    private static int uniqueIDAdder = 0; // to ease comparison
    private static final int elevatorCapacity = 2; // given from task description

    // class variables for easier method handling:
    private final Queue<ItemsState> queue = new LinkedList<>(); // used only for part1 bfs search
    private final ArrayList<ArrayList<Integer>> combinations = new ArrayList<>(); // filled by generateCombinations method
    private final Set<Long> detectDuplicates = new HashSet<>(); // part 1 detect duplicates

    /** RULES:
     * elevator starts at floor one
     * two variants: Generator, Microchip
     * minimum one, but maximum two items per move
     * cant have: Microchip cant be the same area as other Generator if it is not connected to its own Generator
     */

    private void readData() {
        fileLines = reader.readFile();
    }

    private ArrayList<Item> prepare(){
        readData();

        ArrayList<Item> items = new ArrayList<>();

        Pattern microchipPattern = Pattern.compile("([A-Za-z]+)-compatible");
        Pattern generatorPattern = Pattern.compile("([A-Za-z]+) generator");

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);

            if(line.contains("nothing")){
                continue;
            }

            Matcher microchipMatcher = microchipPattern.matcher(line);
            Matcher generatorMatcher = generatorPattern.matcher(line);

            // Floors start with 1 and goes until 4
            int currentFloor = i + 1;
            while(microchipMatcher.find()){
                items.add(new Item(microchipMatcher.group(1), MICROCHIP, currentFloor, assignUniqueID()));
            }

            while (generatorMatcher.find()){
                items.add(new Item(generatorMatcher.group(1), GENERATOR, currentFloor, assignUniqueID()));
            }
        }

        addPairs(items);
        return items;
    }

    public void part1(){
        clearClassVariables();

        ArrayList<Item> items = prepare();
        int elevator = 1; // elevator starts at 1
        ItemsState state = new ItemsState(items, elevator);

        System.out.println(BFS(state));
    }

    /**
     *  TODO: Optimize BSF to prune paths that try to move items downwards (currently takes ~3-5 minutes to run)
     *  Status: never, unless someone pays me.
     */
    public void part2(){
        clearClassVariables();

        ArrayList<Item> items = prepare();
        int elevator = 1; // elevator starts at 1
        ItemsState state = new ItemsState(items, elevator);

        // no input file, these 4 items are from task description, so I add them manually:
        state.itemList.add(new Item("elerium", GENERATOR, 1, assignUniqueID()));
        state.itemList.add(new Item("elerium", MICROCHIP, 1, assignUniqueID()));
        state.itemList.add(new Item("dilithium", GENERATOR, 1, assignUniqueID()));
        state.itemList.add(new Item("dilithium", MICROCHIP, 1, assignUniqueID()));

        // make sure all pairs added for new items:
        addPairs(state.itemList);

        System.out.println(BFS(state));
    }

    private int BFS(ItemsState state){
        queue.add(state);
        detectDuplicates.add(makeLongForDuplicate(state));

        int solution = 0;

        whileLoop:
        while(!queue.isEmpty()){
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                ItemsState currentState = queue.poll();

                if(isASolution(currentState)){
                    break whileLoop;
                }

                //the method below gets possible branches, validates and adds it back to the queue
                generatePossibleBranches(currentState);
            }
            solution ++;
        }
        return solution;
    }

    private ArrayList<Item> deepCopyArray(ArrayList<Item> original){
        ArrayList<Item> deepCopy = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            deepCopy.add(Item.deepCopy(original.get(i)));
        }
        addPairs(deepCopy);
        return deepCopy;
    }

    private ItemsState deepCopyItemsState(ItemsState state){
        return new ItemsState(deepCopyArray(state.itemList), state.elevator);
    }

    // generates possible branches from current item state
    private void generatePossibleBranches(ItemsState itemsState){
        // same levels as the elevator
        ArrayList<Integer> uniqueIDsOnThisLevel = new ArrayList<>();
        for (int i = 0; i < itemsState.itemList.size(); i++) {
            Item current = itemsState.itemList.get(i);
            if(current.currentFloor == itemsState.elevator){
                uniqueIDsOnThisLevel.add(current.uniqueID);
            }
        }

        // fills class variable "combinations" with two move possibilities
        combinations.clear();
        generateCombinations(uniqueIDsOnThisLevel, 0, new ArrayList<>());

        // ----------------------
        // TWO ITEM MOVE ELEVATOR
        // ----------------------
        // two possible directions to move for each combination
        for (int i = 0; i < combinations.size(); i++) {
            ArrayList<Integer> current1 = combinations.get(i);

            // check if it's possible to move up:
            if(itemsState.elevator < maxFloor){

                // move items that are marked by the possible combination up one level
                ItemsState copied = deepCopyItemsState(itemsState);
                for (int j = 0; j < copied.itemList.size(); j++) {
                    if(current1.contains(copied.itemList.get(j).uniqueID)){
                        copied.itemList.get(j).currentFloor ++;
                    }
                }
                copied.elevator ++;

                // this method validates and adds to class queue
                checkerClassPartOneAfterMoveUp(copied);

            }
            // check if possible to move down:
            if(itemsState.elevator > 1){
                ItemsState copied = deepCopyItemsState(itemsState);
                for (int j = 0; j < copied.itemList.size(); j++) {
                    if(current1.contains(copied.itemList.get(j).uniqueID)){
                        copied.itemList.get(j).currentFloor --;
                    }
                }
                copied.elevator --;

                // this method validates and adds to class queue
                checkerClassPartOneAfterMoveDown(copied);
            }
        }

        // ----------------------
        // ONE ITEM MOVE ELEVATOR
        // ----------------------
        for (int i = 0; i < uniqueIDsOnThisLevel.size(); i++) {

            // move up:
            if(itemsState.elevator < maxFloor){
                ItemsState copied = deepCopyItemsState(itemsState);
                for (int j = 0; j < copied.itemList.size(); j++) {
                    if(copied.itemList.get(j).uniqueID == uniqueIDsOnThisLevel.get(i)){
                        copied.itemList.get(j).currentFloor ++;
                        break; // one per iteration guaranteed
                    }
                }
                copied.elevator ++;
                // this method validates and adds to class queue
                checkerClassPartOneAfterMoveUp(copied);
            }

            // move down:
            if(itemsState.elevator > 1){
                ItemsState copied = deepCopyItemsState(itemsState);
                for (int j = 0; j < copied.itemList.size(); j++) {
                    if(copied.itemList.get(j).uniqueID == uniqueIDsOnThisLevel.get(i)){
                        copied.itemList.get(j).currentFloor --;
                        break; // one per iteration guaranteed
                    }
                }
                copied.elevator --;
                // this method validates and adds to class queue
                checkerClassPartOneAfterMoveDown(copied);
            }
        }
    }

    private void generateCombinations(ArrayList<Integer> possiblePicks, int nthPick, ArrayList<Integer> currentPicked){
        if(currentPicked.size() == elevatorCapacity){
            combinations.add(new ArrayList<>(currentPicked));
            return; // combinations class variable
        }

        if(nthPick >= possiblePicks.size()){
            return;
        }

        generateCombinations(possiblePicks, nthPick + 1, currentPicked);
        currentPicked.add(possiblePicks.get(nthPick));
        generateCombinations(possiblePicks, nthPick + 1, currentPicked);
        currentPicked.removeLast();
    }

    // Try adding all available pairs:
    // Item class handles verification
    private void addPairs(ArrayList<Item> items){
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                items.get(i).addPair(items.get(j));
            }
        }
    }

    /**
     * DEBUG METHOD
     * represents current location of the items
     * not used in the final version
     */
    private void printCurrentStatus(ArrayList<Item> items, int elevator){
        for (int i = maxFloor; i >= 1; i--) {
            System.out.print("F" + i + " ") ;
            if(elevator == i){
                System.out.print("E   ");
            } else {
                System.out.print(".   ");
            }
            for (int j = 0; j < items.size(); j++) {
                if(items.get(j).currentFloor == i){
                    System.out.print(items.get(j).namePrefix.substring(0,2).toLowerCase());
                    if(items.get(j).type == GENERATOR){
                        System.out.print("G ");
                    } else {
                        System.out.print("M ");
                    }
                } else {
                    System.out.print(".   ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    static private int assignUniqueID(){
        uniqueIDAdder ++;
        return uniqueIDAdder;
    }

    // check if items state is valid, and has been visited yet, then add to part 1 queue if
    private void checkerClassPartOneAfterMoveDown(ItemsState copied){
        // check if moved state is valid:
        if(!isFloorValid(copied.itemList, copied.elevator)){
            return;
        }

        // check previous floor
        if(!isFloorValid(copied.itemList, copied.elevator + 1)){
            return;
        }

        // check if state already visited by hashset
        if(detectDuplicates.add(makeLongForDuplicate(copied))){
            queue.add(copied);
        }
    }

    private void checkerClassPartOneAfterMoveUp(ItemsState copied){
        // check if moved state is valid:
        if(!isFloorValid(copied.itemList, copied.elevator)){
            return;
        }
        // check previous floor
        if(!isFloorValid(copied.itemList,copied.elevator - 1)){
            return;
        }

        // check if state already visited by hashset
        if(detectDuplicates.add(makeLongForDuplicate(copied))){
            queue.add(copied);
        }
    }

    private boolean isASolution(ItemsState itemsState){
        for (int i = 0; i < itemsState.itemList.size(); i++) {
            if(itemsState.itemList.get(i).currentFloor != 4){
                return false;
            }
        }
        return true;
    }

    private long makeLongForDuplicate(ItemsState state){
        long key = state.elevator;
        for (int i = 0; i < state.itemList.size(); i++) {
            key += state.itemList.get(i).currentFloor * (long)Math.pow(10L,i + 1);
        }
        return key;
    }

    private void clearClassVariables(){
        queue.clear();
        combinations.clear();
        detectDuplicates.clear();
    }

    private boolean isFloorValid(ArrayList<Item> currentSetup, int floor){
        // Get items in the specified floor
        ArrayList<Item> currentFloor = new ArrayList<>();
        for (int j = 0; j < currentSetup.size(); j++) {
            Item current1 = currentSetup.get(j);
            if(current1.currentFloor == floor){
                currentFloor.add(current1);
            }
        }

        // Investigate whether current floor is valid:
        for (int k = 0; k < currentFloor.size(); k++) {
            Item current2 = currentFloor.get(k);
            if(current2.type == MICROCHIP){
                for (int l = 0; l < currentFloor.size(); l++) {
                    Item current3 = currentFloor.get(l);
                    if(current3.type == GENERATOR && !current3.namePrefix.equals(current2.namePrefix) && current2.pair.currentFloor != floor){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}