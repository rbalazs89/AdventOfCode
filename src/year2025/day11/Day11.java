package year2025.day11;

import main.ReadLines;

import java.util.*;

public class Day11 {
    // part2 98318535 too low
    List<String> fileLines;
    int inputFileIndex = 2;
    ArrayList<Device> devices = new ArrayList<>();
    int part1Result = 0;
    int part2Result = 0;
    int dacReached = 0;
    int highestStepDec = 0;

    public void readData() {
        // READ INPUT
        ReadLines reader = new ReadLines();
        fileLines = reader.readFile(inputFileIndex);
    }

    public void processData(){
        for (int i = 0; i < fileLines.size(); i++) {
            Device device = new Device();
            device.name = fileLines.get(i).substring(0,3);
            devices.add(device);
        }

        for (int i = 0; i < devices.size(); i++) {

        }

        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");
            Device current = findDevice(line.substring(0,3));
            for (int j = 1; j < parts.length; j++) {
                Device addThis = findDevice(parts[j]);
                current.out.add(addThis);
                addThis.in.add(current);
            }
        }
    }

    Device findDevice(String name){
        for (int i = 0; i < devices.size(); i++) {
            if(devices.get(i).name.equals(name)){
                return devices.get(i);
            }
        }
        Device d = new Device();
        d.name = name;
        devices.add(d);
        return d;
    }

    public void part1(){
        readData();
        processData();
        doOneStep(findDevice("you"), 0);
        System.out.println("part1: " + part1Result);
    }

    void doOneStep(Device d, int stepNumber){
        for (int i = 0; i < d.out.size(); i++) {
            if(d.out.get(i).name.equals("out")){
                part1Result ++;
            } else {
                doOneStep(d.out.get(i), stepNumber + 1);
            }
        }
    }

    /**
     * from out to dac backwards 12215 ways, always 8 steps
     * from svr to fft 8049 8 or 9 step (?)
     *
     */

    public void part2(){
        long scenario1 = 0;
        long result1 = seeking("svr", "fft");
        resetGraph();
        long result2 = seeking("fft", "dac");
        resetGraph();
        long result3 = seeking("dac", "out");

        scenario1 = result1 * result2 * result3;

        resetGraph();
        result1 = seeking("svr", "dac");
        resetGraph();
        result2 = seeking("dac", "fft");
        resetGraph();
        result3 = seeking("fft", "out");
        long scenario2 = result1 * result2 * result3;
        System.out.println("part 2 result: " + Math.max(scenario2, scenario1));
    }

    public long seeking(String startName, String endName){
        Device start = findDevice(startName);
        Device end = findDevice(endName);

        for (Device d : devices) {
            d.paths = 0L;
            d.visited = 0;           // number of incoming edges seen from reachable predecessors
            d.delay = 0;             // how many levels we have been waiting
        }

        start.paths = 1L;
        start.visited = 1;

        ArrayList<Device> queue = new ArrayList<>();
        queue.add(start);
        int depth = 0;
        int max_delay = 12;

        ArrayList<Device> delayed = new ArrayList<>();
        while(!queue.isEmpty() || !delayed.isEmpty()){
            depth ++;

            Set<Device> nextLevel = new HashSet<>();  // use set to store

            for (int i = 0; i < queue.size(); i++) {
                Device current = queue.get(i);
                for (int j = 0; j < current.out.size(); j++) {
                    Device next = current.out.get(j);
                    next.visited ++;
                    next.paths += current.paths;

                    if(next.visited == next.in.size()){
                        nextLevel.add(next);
                        delayed.remove(next); // remove from delayed if all inputs arrive in the meantime
                    } else {
                        if (!delayed.contains(next) && !nextLevel.contains(next) && next != start) {
                            delayed.add(next);
                        }
                    }
                }
            }

            // prepare for next round:
            queue.clear();
            queue.addAll(nextLevel);

            // force queue even if not all inputs arrived
            // this is for devices that are part of graph, but not part of current branch, so cannot receive all inputs
            Iterator<Device> it = delayed.iterator();
            while (it.hasNext()) {
                Device d = it.next();
                d.delay++;
                if (d.delay >= max_delay) {
                    queue.add(d); // force queue
                    it.remove();  // remove from delayed
                }
            }

            if (depth > 200) {
                System.out.println("Infinite loop detected");
                break;
            }
        }
        return end.paths;
    }

    public void doOneStep(Device d, boolean foundDac, boolean foundFft, int step){
        for (int i = 0; i < d.out.size(); i++) {
            Device next = d.out.get(i);
            if(next.name.equals("out") && foundDac && foundFft){

                System.out.println("result");
            } else if(next.name.equals("dac")){
                System.out.println("dacstep: " + step);
                highestStepDec = Math.max(highestStepDec, step + 1);
                dacReached ++ ;
                //doOneStep2(next, true, foundFft, step + 1);
            } else if(next.name.equals("fft")){
                System.out.println("fft" + step);
                part2Result ++;
                //doOneStep(next, foundDac, true);
            } else {
                if(step <= 19){
                    doOneStep(next, foundDac, foundFft, step + 1);
                }
            }
        }
    }

    public void stepBackwards(Device d, boolean foundDac, boolean foundFft, int step){
        for (int i = 0; i < d.in.size(); i++) {
            Device next = d.in.get(i);
            if(next.name.equals("you") && foundDac && foundFft){
                part2Result ++;
            } else if(next.name.equals("dac")){
                System.out.println("dacstep: " + step);
                //highestStepDec = Math.max(highestStepDec, step);
                //dacReached ++ ;
                stepBackwards(next, true, foundFft, step + 1);
            } else if(next.name.equals("fft")){
                //stoppls = true;
                System.out.println("fftstep: " + step);
                stepBackwards(next, foundDac, foundFft, step + 1);;
            } else {
                if(step < 25){
                    stepBackwards(next, foundDac, foundFft, step + 1);
                }
            }
        }
    }

    public void resetGraph(){
        for (int i = 0; i < devices.size(); i++) {
            devices.get(i).visited = 0;
            devices.get(i).paths = 0;
        }
    }
}
