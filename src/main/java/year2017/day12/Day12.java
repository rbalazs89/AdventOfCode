package year2017.day12;

import main.ReadLines;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

    private final ReadLines reader = new ReadLines(2017, 12, 2);
    private List<String> fileLines; // lines from txt file
    private final ArrayList<Program> allPrograms = new ArrayList<>();

    private void readData() {
        fileLines = reader.readFile();
    }

    private void connectPrograms(){
        readData();
        allPrograms.clear();
        Pattern p = Pattern.compile("\\d+");
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            Matcher m = p.matcher(line);

            boolean firstFound = false;
            Program first = null;
            Program other;
            while (m.find()){
                if(!firstFound){
                    first = getOrCreateProgram(Integer.parseInt(m.group()));
                    firstFound = true;
                    addToAllPrograms(first);
                } else {
                    other = getOrCreateProgram(Integer.parseInt(m.group()));
                    first.addConnection(other);
                    other.addConnection(first);
                    addToAllPrograms(other);
                }
            }
        }
    }

    // find how many programs are connected to program zero
    public void part1(){
        // set up the network, not all programs are connected to the same network
        connectPrograms();
        Program zero = getOrCreateProgram(0);

        // set up queue and visited set for graph search
        Set<Integer> visited = new HashSet<>();
        Queue<Program> queue = new LinkedList<>();
        visited.add(zero.getValue());
        queue.add(zero);

        // search algorithm until every connection visited
        while (!queue.isEmpty()){
            Program current = queue.poll();
            for (int i = 0; i < current.getConnected().size(); i++) {
                Program addThis = current.getConnected().get(i);

                // add to queue if not visited yet
                if(visited.add(addThis.getValue())){
                    queue.add(addThis);
                }
            }
        }
        System.out.println(visited.size());

    }

    // find out how many clusters there are
    public void part2(){
        // set up the network, not all programs are connected to the same network
        connectPrograms();

        int clusters = 0;

        // same graph searching algorithm, set up the first in queue from a loop
        // keep shared visited for the whole process
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < allPrograms.size(); i++) {
            Program programToInvestigate = allPrograms.get(i);
            if(!visited.add(programToInvestigate.getValue())){
                continue;
            }

            Queue<Program> queue = new LinkedList<>();
            queue.add(programToInvestigate);

            while (!queue.isEmpty()){
                Program current = queue.poll();
                for (int j = 0; j < current.getConnected().size(); j++) {
                    Program addThis = current.getConnected().get(j);

                    // add to queue if not visited yet
                    if(visited.add(addThis.getValue())){
                        queue.add(addThis);
                    }
                }
                // add one to solution if there are no more programs in the same cluster
                if(queue.isEmpty()){
                    clusters ++;
                }
            }
        }
        System.out.println(clusters);
    }

    private Program getOrCreateProgram(int name){
        for (int i = 0; i < allPrograms.size(); i++) {
            Program current = allPrograms.get(i);
            if(current.getValue() == name){
                return  current;
            }
        }
        return new Program(name);
    }

    private void addToAllPrograms(Program program){
        if(!allPrograms.contains(program)){
            allPrograms.add(program);
        }
    }
}
