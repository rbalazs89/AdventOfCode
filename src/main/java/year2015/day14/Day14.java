package year2015.day14;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
    private final ReadLines reader = new ReadLines(2015, 14, 2);
    private List<String> fileLines;
    private final ArrayList<Reindeer> horses = new ArrayList<>();
    private static final int SECONDS = 2503; // given by task description

    private void readData(){
        fileLines = reader.readFile();
    }

    public void processData(){
        readData();
        horses.clear();
        for (int i = 0; i < fileLines.size(); i++) {
            String line = fileLines.get(i);
            String[] parts = line.split(" ");
            Reindeer r = new Reindeer();
            r.name = parts[0];
            r.velocity = Integer.parseInt(parts[3]);
            r.flyTime = Integer.parseInt(parts[6]);
            r.restTime = Integer.parseInt(parts[13]);
            horses.add(r);
        }
    }

    public void part1(){
        int highestDistance = 0;
        processData();
        for (int i = 0; i < horses.size(); i++) {
            Reindeer current = horses.get(i);
            int cycle = current.flyTime + current.restTime;
            int remainder = SECONDS % cycle;
            int completedCycles = SECONDS / cycle;
            current.distance = completedCycles * current.velocity * current.flyTime;
            if(remainder >= current.flyTime){
                current.distance += current.flyTime * current.velocity;
            } else {
                current.distance += remainder * current.velocity;
            }
            highestDistance = Math.max(highestDistance, current.distance);
        }
        System.out.println(highestDistance);
    }

    public void part2(){
        processData();
        int counter = 0;
        while(counter < SECONDS){
            counter ++;
            for (int i = 0; i < horses.size(); i++) {
                Reindeer current = horses.get(i);
                current.currentPhaseTimeSpent ++;

                // flying:
                if(current.isCurrentlyFlying){
                    current.distance += current.velocity;
                    if(current.currentPhaseTimeSpent == current.flyTime){
                        current.isCurrentlyFlying = false;
                        current.currentPhaseTimeSpent = 0;
                    }

                // resting:
                } else {
                    if(current.currentPhaseTimeSpent == current.restTime){
                        current.isCurrentlyFlying = true;
                        current.currentPhaseTimeSpent = 0;
                    }
                }
            }

            //award scores:
            int highestDistance = 0;
            for (int i = 0; i < horses.size(); i++) {
                highestDistance = Math.max(highestDistance, horses.get(i).distance);
            }

            // can be multiple horses in the same second
            for (int i = 0; i < horses.size(); i++) {
                if(horses.get(i).distance == highestDistance){
                    horses.get(i).score ++;
                }
            }
        }

        int hiScore = 0;
        for (int i = 0; i < horses.size(); i++) {
            hiScore = Math.max(hiScore, horses.get(i).score);
        }

        System.out.println(hiScore);
    }
}
