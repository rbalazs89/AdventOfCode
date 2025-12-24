package year2016.day6;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {

    private List<String> fileLines;
    private final ReadLines reader = new ReadLines(2016, 6, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        ArrayList<HashMap<Character, Integer>> maps = new ArrayList<>();

        for (int i = 0; i < fileLines.getFirst().length(); i++) {
            HashMap<Character, Integer> frequencyMap = new HashMap<>();
            maps.add(frequencyMap);
        }

        for (int i = 0; i < fileLines.size(); i++) {
            for (int j = 0; j < fileLines.get(i).length(); j++) {
                String line = fileLines.get(i);
                Character currentChar = line.charAt(j);
                HashMap<Character, Integer> currentMap = maps.get(j);
                if(currentMap.containsKey(currentChar)){
                    currentMap.put(currentChar, currentMap.get(currentChar) + 1);
                } else {
                    currentMap.put(currentChar, 1);
                }
            }
        }

        StringBuilder constructedString = getConstructedString(maps);
        System.out.println(constructedString);
    }

    private static StringBuilder getConstructedString(ArrayList<HashMap<Character, Integer>> maps) {
        StringBuilder constructedString = new StringBuilder();
        for (int i = 0; i < maps.size(); i++) {
            HashMap<Character, Integer> currentMap = maps.get(i);
            Character mostFrequent = ' ';
            int freq = 0;
            for (Map.Entry<Character, Integer> entry : currentMap.entrySet()){
                if(entry.getValue() > freq){
                    freq = entry.getValue();
                    mostFrequent = entry.getKey();
                }
            }
            constructedString.append(mostFrequent);
        }
        return constructedString;
    }

    public void part2(){
        readData();
        ArrayList<HashMap<Character, Integer>> maps = new ArrayList<>();

        for (int i = 0; i < fileLines.getFirst().length(); i++) {
            HashMap<Character, Integer> frequencyMap = new HashMap<>();
            maps.add(frequencyMap);
        }

        for (int i = 0; i < fileLines.size(); i++) {
            for (int j = 0; j < fileLines.get(i).length(); j++) {
                String line = fileLines.get(i);
                Character currentChar = line.charAt(j);
                HashMap<Character, Integer> currentMap = maps.get(j);
                if(currentMap.containsKey(currentChar)){
                    currentMap.put(currentChar, currentMap.get(currentChar) + 1);
                } else {
                    currentMap.put(currentChar, 1);
                }
            }
        }

        StringBuilder constructedString = getStringBuilder(maps);
        System.out.println(constructedString);
    }

    private static StringBuilder getStringBuilder(ArrayList<HashMap<Character, Integer>> maps) {
        StringBuilder constructedString = new StringBuilder();
        for (int i = 0; i < maps.size(); i++) {
            HashMap<Character, Integer> currentMap = maps.get(i);
            Character mostFrequent = ' ';
            int freq = Integer.MAX_VALUE;
            for (Map.Entry<Character, Integer> entry : currentMap.entrySet()){
                if(entry.getValue() < freq){
                    freq = entry.getValue();
                    mostFrequent = entry.getKey();
                }
            }
            constructedString.append(mostFrequent);
        }
        return constructedString;
    }
}
