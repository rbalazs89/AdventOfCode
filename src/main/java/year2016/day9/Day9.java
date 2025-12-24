package year2016.day9;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {
    private List<String> fileLines;
    private final ArrayList<Marker> markers = new ArrayList<>();
    private final ArrayList<Marker> markersNoSkip = new ArrayList<>();

    private final ReadLines reader = new ReadLines(2016, 9, 2);
    private void readData(){
        fileLines = reader.readFile();
    }

    public void part1(){
        readData();
        setupMarkers();
        String line = fileLines.getFirst();

        StringBuilder resultString = new StringBuilder();
        resultString.append(line, 0, markers.getFirst().start);
        for (int i = 0; i < markers.size(); i++) {
            Marker marker = markers.get(i);
            String repeatThis = line.substring(marker.end, marker.end + marker.theseChars);
            resultString.append(repeatThis.repeat(Math.max(0, marker.times)));

            if(i == markers.size() - 1){
                resultString.append(line.substring(marker.end + marker.theseChars));
            } else {
                resultString.append(line, marker.end + marker.theseChars, markers.get(i + 1).start);
            }
        }
        System.out.println(resultString.length());
    }

    private void setupMarkers2(){
        String line = fileLines.getFirst();
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '('){
                for (int j = i; j < line.length(); j++) {
                    if(line.charAt(j) == ')'){
                        String investigate = line.substring(i, j + 1);
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(investigate);
                        ArrayList<Integer> numbers = new ArrayList<>();
                        while (m.find()){
                            numbers.add(Integer.valueOf(m.group()));
                        }
                        Marker marker = new Marker();
                        marker.end = j + 1;
                        marker.theseChars = numbers.get(0);
                        marker.times = numbers.get(1);
                        marker.start = i;
                        markersNoSkip.add(marker);
                        break;
                    }
                }
            }
        }
    }

    private void setupMarkers(){
        String line = fileLines.getFirst();
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '('){
                for (int j = i; j < line.length(); j++) {
                    if(line.charAt(j) == ')'){
                        String investigate = line.substring(i, j + 1);
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(investigate);
                        ArrayList<Integer> numbers = new ArrayList<>();
                        while (m.find()){
                            numbers.add(Integer.valueOf(m.group()));
                        }
                        Marker marker = new Marker();
                        marker.end = j + 1;
                        marker.theseChars = numbers.get(0);
                        marker.times = numbers.get(1);
                        marker.start = i;
                        i = j + marker.theseChars;
                        markers.add(marker);
                        break;
                    }
                }
            }
        }
    }

    public void part2(){
        setupMarkers2();
        String line = fileLines.getFirst();
        long result = 0;

        outerLoop:
        for (int i = 0; i < line.length(); i++) {
            int multiplier = 1;
            for (int j = 0; j < markersNoSkip.size(); j++) {
                // check if index i is part of any marker string
                Marker currentMarker = markersNoSkip.get(j);
                if(!(i < currentMarker.start || i >= currentMarker.end)){
                    continue outerLoop; // if part of any marker text, then this is not counted -> go to investigate next
                }

                // find all the multiplier markers
                if(i >= currentMarker.end && i < currentMarker.end + currentMarker.theseChars){
                    multiplier = multiplier * currentMarker.times;
                }
            }
            result = result + multiplier;
        }
        System.out.println(result);
    }
}
