package year2015.day9;

import main.ReadLines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day9 {
    List<String> fileLines;
    ArrayList<Vertex> nodes = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>();
    int edgesNumberPerCity;
    int highestResult = 0;

    public void readData(){
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(2);
    }

    public void clearData(){
        fileLines.clear();
        nodes.clear();
        edges.clear();
        edgesNumberPerCity = 0;
    }

    public void processData(){
        readData();

        HashSet<String> duplicate = new HashSet<>();
        edgesNumberPerCity = ((1 + (int) Math.round(Math.sqrt(1.0 + 8.0 * fileLines.size()))) / 2) - 1;
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);

            Edge edge = new Edge();

            String[] parts = line.split(" ");
            if(duplicate.add(parts[0])){
                Vertex city1 = new Vertex();
                city1.name = parts[0];
                nodes.add(city1);
            }

            if(duplicate.add(parts[2])){
                Vertex city1 = new Vertex();
                city1.name = parts[2];
                nodes.add(city1);
            }

            edge.vertex1 = findVertexByName(parts[0]);
            edge.vertex2 = findVertexByName(parts[2]);
            edge.value = Integer.parseInt(parts[4]);

            edges.add(edge);
        }

        for (int i = 0; i < nodes.size(); i++) {
            Vertex current = findVertexByName(nodes.get(i).name);
            current.edges = new Edge[edgesNumberPerCity];

            int counter = 0;
            for (int j = 0; j < edges.size(); j++) {
                if(edges.get(j).vertex1.name.equals(current.name) || edges.get(j).vertex2.name.equals(current.name)){
                    current.edges[counter] = edges.get(j);
                    counter++;
                }
            }
        }
    }

    Vertex findVertexByName(String city){
        for (int i = 0; i < nodes.size(); i++) {
            if(nodes.get(i).name.equals(city)){
                return nodes.get(i);
            }
        }
        System.out.println("problem");
        return null;
    }

    public void resetVisited(){
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).visited = false;
        }

        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).visited = false;
        }
    }

    public void part1(){
        processData();

        int[] results = new int [edgesNumberPerCity + 1];
        //select starting city
        for (int i = 0; i < results.length; i++) {
            resetVisited();
            Vertex currentCity = nodes.get(i);

            //select destination (nodes - 1 times)
            for (int m = 0; m < results.length - 1; m++) {
                int lowest = Integer.MAX_VALUE;
                int lowestIndex = -1;
                for (int j = 0; j < currentCity.edges.length; j++) {
                    if(currentCity.edges[j].value < lowest  && !currentCity.edges[j].visited){
                        lowest = currentCity.edges[j].value;
                        lowestIndex = j;
                    }
                }
                for (int j = 0; j < currentCity.edges.length; j++) {
                    currentCity.edges[j].visited = true;
                }

                // add value to result table
                results[i] = results[i] + currentCity.edges[lowestIndex].value;

                // set the new current city
                if(currentCity.edges[lowestIndex].vertex1.name.equals(currentCity.name)){
                    currentCity = currentCity.edges[lowestIndex].vertex2;
                } else {
                    currentCity = currentCity.edges[lowestIndex].vertex1;
                }
            }
        }

        // find lowest overall
        int part1Result = Integer.MAX_VALUE;
        for (int i = 0; i < results.length; i++) {
            if(results[i] < part1Result){
                part1Result = results[i];
            }
        }
        System.out.println(part1Result);
    }

    public void part2(){

        clearData();
        processData();

        boolean[] used = new boolean[nodes.size()];
        generatePermutations(nodes, new ArrayList<>(), used);

        System.out.println(highestResult);

    }

    void generatePermutations(ArrayList<Vertex> cities, ArrayList<Vertex> current, boolean[] used) {
        if (current.size() == cities.size()) {
            processPath(current);
            return;
        }

        for (int i = 0; i < cities.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(cities.get(i));

                generatePermutations(cities, current, used);

                // backtrack
                used[i] = false;
                current.remove(current.size() - 1);
            }
        }
    }

    void processPath(ArrayList<Vertex> onePath){
        int onePathDistance = 0;
        for (int i = 0; i < onePath.size() - 1; i++) {
            onePathDistance += getValueBetweenTwoCity(onePath.get(i), onePath.get(i + 1));
        }

        if(onePathDistance > highestResult){
            highestResult = onePathDistance;
        }
    }

    int getValueBetweenTwoCity(Vertex city1, Vertex city2){
        for (int i = 0; i < city1.edges.length; i++) {
            if(city1.edges[i].vertex1.name.equals(city2.name) || city1.edges[i].vertex2.name.equals(city2.name)){
                return city1.edges[i].value;
            }
        }
        return 0;
    }


    public void part2didntwork(){
        clearData();
        processData();

        int[] results = new int [edgesNumberPerCity + 1];
        //select starting city
        for (int i = 0; i < results.length; i++) {
            resetVisited();
            Vertex currentCity = nodes.get(i);

            //select destination (nodes - 1 times)
            for (int m = 0; m < results.length - 1; m++) {
                int highest = 0;
                int highestIndex = -1;
                for (int j = 0; j < currentCity.edges.length; j++) {
                    if(currentCity.edges[j].value > highest  && !currentCity.edges[j].visited){
                        highest = currentCity.edges[j].value;
                        highestIndex = j;
                    }
                }
                // set all edges visited so it doesnt visit the same city again
                for (int j = 0; j < currentCity.edges.length; j++) {
                    currentCity.edges[j].visited = true;
                }

                // add value to result table
                results[i] = results[i] + currentCity.edges[highestIndex].value;

                // set the new current city
                if(currentCity.edges[highestIndex].vertex1.name.equals(currentCity.name)){
                    currentCity = currentCity.edges[highestIndex].vertex2;
                } else {
                    currentCity = currentCity.edges[highestIndex].vertex1;
                }
            }
        }

        // find highest overall
        int part2Result = 0;
        for (int i = 0; i < results.length; i++) {
            if(results[i] > part2Result){
                part2Result = results[i];
            }
        }
        System.out.println(part2Result);
    }
}
