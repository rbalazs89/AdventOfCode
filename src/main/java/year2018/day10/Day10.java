package year2018.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    private final ReadLines reader = new ReadLines(2018, 10, 2);
    private static final Pattern PARTICLE_PATTERN = Pattern.compile("-?\\d+");

    private ArrayList<Particle> getParticles(){
        List<String> lines = reader.readFile();
        ArrayList<Particle> particles = new ArrayList<>();

        // example input line: "position=< 9,  1> velocity=< 0,  2>"
        for (String line : lines) {
            Matcher m = PARTICLE_PATTERN.matcher(line);
            ArrayList<Integer> particleData = new ArrayList<>();
            while (m.find()){
                particleData.add(Integer.parseInt(m.group()));
            }
            particles.add(new Particle(particleData.get(0), particleData.get(1), particleData.get(2), particleData.get(3)));
        }
        return particles;
    }

    public void part1(){
        List<Particle> particles = getParticles();

        int counter = 0;
        int boundingBox = getBoundingArea(particles);
        int passedSeconds = 0;
        while(true){
            // move one step forward in time
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).incrementParticlePosition();
            }
            passedSeconds ++;

            // calculate bounding box and keep track on increase/decrease
            int newArea = getBoundingArea(particles);
            if(newArea > boundingBox){
                counter ++;
            } else {
                counter = 0;
            }

            // if bounding box keeps increasing 4 times in a row, then step back and print
            if(counter > 3){
                for (int i = 0; i <= 3; i++) {
                    for (Particle p : particles) {
                        p.decrementParticlePosition();
                    }
                    passedSeconds--;
                }
                System.out.println(passedSeconds); // part 2 result
                printParticles(particles);
                break;
            }
            boundingBox = newArea;
        }
        // output must be red from printed terminal
    }

    private int getBoundingArea(List<Particle> particles){
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        for (Particle p : particles) {
            minX = Math.min(p.getPx(), minX);
            minY = Math.min(p.getPy(), minY);
            maxX = Math.max(p.getPx(), maxX);
            maxY = Math.max(p.getPy(), maxY);
        }
        int deltaX = maxX - minX;
        int deltaY = maxY - minY;
        return deltaX * deltaY;
    }

    private void printParticles(List<Particle> particles){
        int minY = Integer.MAX_VALUE;
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        for (Particle p : particles) {
            minX = Math.min(p.getPx(), minX);
            minY = Math.min(p.getPy(), minY);
            maxX = Math.max(p.getPx(), maxX);
            maxY = Math.max(p.getPy(), maxY);
        }

        int height = maxY - minY + 1;
        int width = maxX - minX + 1;

        char[][] textPattern = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                textPattern[i][j] = '.';
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int i = 0; i < particles.size(); i++) {
                    Particle p = particles.get(i);
                    if(p.getPy() - minY == y && p.getPx() - minX == x){
                        textPattern[y][x] = '#';
                        break;
                    }
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(textPattern[y][x]);
            }
            System.out.println();
        }
    }

    public void part2(){
        // nothing to do here, calculated in part one
    }
}
