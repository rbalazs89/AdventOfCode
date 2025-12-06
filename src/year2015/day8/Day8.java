package year2015.day8;

import main.ReadLines;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {

    List<String> fileLines;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void part1(){
        readData();
        int[] memorySize = new int[fileLines.size()];
        int[] actualSize = new int[fileLines.size()];
        int result = 0;

        for (int i = 0; i < fileLines.size(); i++) {
            memorySize[i] = fileLines.get(i).length();

            String line = fileLines.get(i);

            line = line.replaceAll("\\\\\"", "\"");
            line = line.replaceAll("\\\\{2}","\\\\");

            Pattern p = Pattern.compile("(\\\\x)([0-9A-Fa-f]{2})");
            Matcher m = p.matcher(line);
            StringBuilder sb = new StringBuilder();
            while (m.find()) {
                int hex = Integer.parseInt(m.group(2), 16);
                String replace1 = Character.toString((char) hex);
                m.appendReplacement(sb,Matcher.quoteReplacement(replace1));
            }
            m.appendTail(sb);
            line = sb.toString();

            actualSize[i] = line.length() - 2;

            result = result + memorySize[i] - actualSize[i];
        }
        System.out.println(result);
    }

    public void part2(){
        readData();
        int result = 0;

        for (int i = 0; i < fileLines.size(); i++) {

            String line = fileLines.get(i);
            int currentSize = line.length();

            Pattern p2 = Pattern.compile("\\\\");
            Matcher m2 = p2.matcher(line);
            int count2 = 0;
            while (m2.find()){
                count2 ++;
            }

            Pattern p3 = Pattern.compile("\"");
            Matcher m3 = p3.matcher(line);
            int count3 = 0;
            while (m3.find()){
                count3 ++;
            }

            int newSize = currentSize + count3 + count2 + 2;
            result = result + newSize - currentSize;
        }
        System.out.println(result);
    }
}
