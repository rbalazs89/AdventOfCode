package year2016.day17;

import main.ReadLines;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day17 {
    private List<String> fileLines; // lines from txt file

    private String passCode = "";
    private final int maxX = 4;
    private final int maxY = 4;
    private final int goalX = maxX - 1;
    private final int goalY = maxY - 1;
    private final List<Character> openChar = Arrays.asList('b', 'c', 'd', 'e', 'f');
    private final int codePositionsLength = 4;
    private final int codePositionUp = 0;
    private final int codePositionDown = 1;
    private final int codePositionLeft = 2;
    private final int codePositionRight = 3;
    private final ReadLines reader = new ReadLines(2016, 17, 2);

    private void readData(){
        // READ INPUT
        fileLines = reader.readFile();
    }

    private void processData(){
        readData();
        passCode = fileLines.getFirst();
    }

    public void part1(){
        processData();

        // set up start
        PathTaken zerothStep = new PathTaken(0, 0, "");

        // set up queue
        Queue<PathTaken> paths = new LinkedList<>();
        paths.add(zerothStep);

        //loop as bfs:
        while(!paths.isEmpty()){

            // size set up to make sure only one step is taken for each path
            PathTaken currentPath = paths.poll();

            // result found:
            if(currentPath.x == goalX && currentPath.y == goalY){
                System.out.println("solution found: " + currentPath.pathString);
                break;
            }
            String currentCode = md5Convert(passCode + currentPath.pathString).substring(0,codePositionsLength);

            //up
            if(openChar.contains(currentCode.charAt(codePositionUp))){
                if(currentPath.y - 1 >= 0){
                    PathTaken path = new PathTaken(currentPath.x, currentPath.y - 1, currentPath.pathString + "U");
                    paths.add(path);
                }
            }

            //down
            if(openChar.contains(currentCode.charAt(codePositionDown))){
                if(currentPath.y + 1 < maxY){
                    PathTaken path = new PathTaken(currentPath.x, currentPath.y + 1, currentPath.pathString + "D");
                    paths.add(path);
                }
            }

            //left
            if(openChar.contains(currentCode.charAt(codePositionLeft))){
                if(currentPath.x - 1 >= 0){
                    PathTaken path = new PathTaken(currentPath.x - 1, currentPath.y, currentPath.pathString + "L");
                    paths.add(path);
                }
            }

            //right
            if(openChar.contains(currentCode.charAt(codePositionRight))){
                if(currentPath.x + 1 < maxX){
                    PathTaken path = new PathTaken(currentPath.x + 1, currentPath.y, currentPath.pathString + "R");
                    paths.add(path);
                }
            }

            if(paths.isEmpty()){
                System.out.println("No valid path found");
            }
        }
    }

    public void part2(){
        // set up start
        PathTaken zerothStep = new PathTaken(0, 0, "");

        // set up queue
        Queue<PathTaken> paths = new LinkedList<>();
        paths.add(zerothStep);

        int longestSolution = Integer.MIN_VALUE;

        //loop as bfs:
        while(!paths.isEmpty()){

            // size set up to make sure only one step is taken for each path
            PathTaken currentPath = paths.poll();

            // result found:
            if(currentPath.x == goalX && currentPath.y == goalY){
                longestSolution = Math.max(longestSolution, currentPath.pathString.length());
                continue;
            }
            String currentCode = md5Convert(passCode + currentPath.pathString).substring(0,codePositionsLength);

            //up
            if(openChar.contains(currentCode.charAt(codePositionUp))){
                if(currentPath.y - 1 >= 0){
                    PathTaken path = new PathTaken(currentPath.x, currentPath.y - 1, currentPath.pathString + "U");
                    paths.add(path);
                }
            }

            //down
            if(openChar.contains(currentCode.charAt(codePositionDown))){
                if(currentPath.y + 1 < maxY){
                    PathTaken path = new PathTaken(currentPath.x, currentPath.y + 1, currentPath.pathString + "D");
                    paths.add(path);
                }
            }

            //left
            if(openChar.contains(currentCode.charAt(codePositionLeft))){
                if(currentPath.x - 1 >= 0){
                    PathTaken path = new PathTaken(currentPath.x - 1, currentPath.y, currentPath.pathString + "L");
                    paths.add(path);
                }
            }

            //right
            if(openChar.contains(currentCode.charAt(codePositionRight))){
                if(currentPath.x + 1 < maxX){
                    PathTaken path = new PathTaken(currentPath.x + 1, currentPath.y, currentPath.pathString + "R");
                    paths.add(path);
                }
            }

            if(paths.isEmpty()){
                System.out.println("All paths explored");
            }
        }
        System.out.println("Part 2 solution: " + longestSolution);
    }

    private static String md5Convert(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());

            StringBuilder hex = new StringBuilder(digest.length * 2);
            for (int i = 0; i < digest.length; i++) {
                byte b = digest[i];
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}
