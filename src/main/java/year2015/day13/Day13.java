package year2015.day13;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 {
    private final ReadLines reader = new ReadLines(2015, 13, 2);
    private List<String> fileLines;
    private Vertex[] table;
    private int people;
    private int highestScore = 0;

    private void readData(){
        fileLines = reader.readFile();
    }

    // sets people variable
    private void getPPL(){
        String firstName = fileLines.get(0).split(" ")[0];
        for (int i = 0; i < fileLines.size(); i++) {
            if(!fileLines.get(i).split(" ")[0].equals(firstName)){
                people = i + 1;
                break;
            }
        }
    }

    private void processFile(){
        table = new Vertex[people];

        for (int i = 0; i < fileLines.size(); i++) {
            String[] parts = fileLines.get(i).split(" ");
            Vertex v1;
            Vertex v2;
            if(findByName(parts[0]) != null){ // name found from list
                v1 = findByName(parts[0]);
            } else {
                v1 = new Vertex();
                v1.name = parts[0];
                insertName(v1);
            }

            String secondName = parts[parts.length - 1];
            secondName = secondName.substring(0, secondName.length() - 1);
            if(findByName(secondName) != null){
                v2 = findByName(secondName);
            } else {
                v2 = new Vertex();
                v2.name = secondName;
                insertName(v2);
            }

            int value;
            if(parts[2].equals("gain")){
                value = Integer.parseInt(parts[3]);
            } else {
                value = Integer.parseInt(parts[3]) * -(1);
            }

            Edge e = new Edge();
            e.vertex1 = v1;
            e.vertex2 = v2;
            if(v1.edges == null){
                v1.edges = new Edge[people - 1];
            }
            e.value = value;
            insertEdge(v1, e);
        }
    }

    private void insertEdge(Vertex v, Edge e){
        for (int i = 0; i < v.edges.length; i++) {
            if(v.edges[i] == null){
                v.edges[i] = e;
                return;
            }
        }
    }

    private Vertex findByName(String name){
        for (int i = 0; i < table.length; i++) {
            if(table[i] != null){
                if(table[i].name.equals(name)){
                    return table[i];
                }
            }
        }
        return null;
    }

    private void insertName(Vertex v){
        for (int i = 0; i < table.length; i++) {
            if(table[i] == null){
                table[i] = v;
                return;
            }
        }
    }

    public void part1(){
        readData();
        getPPL();
        processFile();

        ArrayList<Vertex> nodes = new ArrayList<>(Arrays.asList(table));
        boolean[] used = new boolean[nodes.size()];
        generatePermutations(nodes, new ArrayList<>(), used);
        System.out.println(highestScore);
    }

    public void part2(){
        highestScore = 0;
        readData();
        getPPL();
        processFile();

        String firstName = fileLines.get(0).split(" ")[0];
        for (int i = 0; i < fileLines.size(); i++) {
            if(!fileLines.get(i).split(" ")[0].equals(firstName)){
                people = i + 2;
                break;
            }
        }

        addBalazs();

        ArrayList<Vertex> nodes = new ArrayList<>(Arrays.asList(table));
        boolean[] used = new boolean[nodes.size()];
        generatePermutations(nodes, new ArrayList<>(), used);
        System.out.println(highestScore);
    }

    private void generatePermutations(ArrayList<Vertex> guests, ArrayList<Vertex> current, boolean[] used) {
        if (current.size() == guests.size()) {
            processSeating(current);
            return;
        }

        for (int i = 0; i < guests.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(guests.get(i));

                generatePermutations(guests, current, used);

                // backtrack
                used[i] = false;
                current.remove(current.size() - 1);
            }
        }
    }

    private void processSeating(ArrayList<Vertex> seating){
        int score = 0;
        for (int i = 0; i < seating.size(); i++) {
            Vertex current = seating.get(i);
            Vertex right;
            Vertex left;
            if(i == seating.size() - 1){
                right = seating.get(0);
            } else {
                right = seating.get(i + 1);
            }
            if(i == 0){
                left = seating.get(seating.size() - 1);
            } else {
                left = seating.get(i - 1);
            }

            for (int j = 0; j < current.edges.length; j++) {
                if(current.edges[j].vertex2.name.equals(right.name)){
                    score = score + current.edges[j].value;
                }
                if(current.edges[j].vertex2.name.equals(left.name)){
                    score = score +  current.edges[j].value;
                }
            }
        }
        highestScore = Math.max(highestScore, score);
    }

    private void addBalazs(){
        Vertex Balazs = new Vertex();
        Edge[] edges = new Edge[people - 1];
        Balazs.name = "Balazs";

        for (int i = 0; i < edges.length; i++) {
            Edge e = new Edge();
            e.value = 0;
            e.vertex1 = Balazs;
            e.vertex2 = table[i];
            edges[i] = e;
        }
        Balazs.edges = edges;
        table[table.length - 1] = Balazs;

        for (int i = 0; i < table.length - 1; i++) {
            Edge e2 = new Edge();
            e2.value = 0;
            e2.vertex1 = table[i];
            e2.vertex2 = Balazs;
            table[i].edges[table.length - 2] = e2;
        }
    }
}
