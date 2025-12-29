package year2018.day10;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    private final ReadLines reader = new ReadLines(2018, 10, 2);
    private static final Pattern PARTICLE_PATTERN = Pattern.compile("-?\\d+");
    private static final int AREA_INCREASE_THRESHOLD = 3; // arbitrary number to check whether string pattern string achieved

    private List<Particle> getParticles(){
        List<String> lines = reader.readFile();
        ArrayList<Particle> particles = new ArrayList<>();

        // example input line: "position=< 9,  1> velocity=< 0,  2>"
        for (String line : lines) {
            Matcher m = PARTICLE_PATTERN.matcher(line);
            List<Integer> particleData = new ArrayList<>();
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
            if(counter > AREA_INCREASE_THRESHOLD){
                for (int i = 0; i <= AREA_INCREASE_THRESHOLD; i++) {
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
        // maxX, maxY, minX, minY
        int[] extremes = getExtremes(particles);
        int maxX = extremes[0];
        int maxY = extremes[1];
        int minX = extremes[2];
        int minY = extremes[3];

        int deltaX = maxX - minX;
        int deltaY = maxY - minY;
        return deltaX * deltaY;
    }

    private void printParticles(List<Particle> particles){
        // maxX, maxY, minX, minY
        int[] extremes = getExtremes(particles);
        int maxX = extremes[0];
        int maxY = extremes[1];
        int minX = extremes[2];
        int minY = extremes[3];

        int height = maxY - minY + 1;
        int width = maxX - minX + 1;


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean particleFound = false;

                for (int i = 0; i < particles.size(); i++) {
                    Particle p = particles.get(i);
                    if(p.getPy() - minY == y && p.getPx() - minX == x){
                        particleFound = true;
                        break;
                    }
                }
                if(particleFound){
                    System.out.print('#');
                } else{
                    System.out.print('.');
                }

            }
            System.out.println();
        }
    }

    public void part2(){
        // nothing to do here, calculated in part one
    }

    private int[] getExtremes(List<Particle> particles){
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (Particle p : particles) {
            minX = Math.min(p.getPx(), minX);
            minY = Math.min(p.getPy(), minY);
            maxX = Math.max(p.getPx(), maxX);
            maxY = Math.max(p.getPy(), maxY);
        }
        return new int[]{maxX, maxY, minX, minY};
    }
}
