package year2018.day3;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private final ReadLines reader = new ReadLines(2018, 3, 2);
    private static final Pattern CLAIM_PATTERN =
            Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

    private List<Fabric> getFabricList(){
        // there are 1265 lines and fabric dimensions are below 1001
        List<String> lines = reader.readFile();

        List<Fabric> fabrics = new ArrayList<>(lines.size()); // pre-size

        for (String line : lines) {
            Matcher m = CLAIM_PATTERN.matcher(line);
            if (!m.matches()) {
                throw new IllegalArgumentException("Invalid line: " + line);
            }
            int id = Integer.parseInt(m.group(1));
            int x = Integer.parseInt(m.group(2));
            int y = Integer.parseInt(m.group(3));
            int w = Integer.parseInt(m.group(4));
            int h = Integer.parseInt(m.group(5));

            fabrics.add(new Fabric(id, x, y, w, h));
        }

        return fabrics;
    }

    public void part1(){
        // fabric areas are low, making array of each square area of the original fabric is feasible:
        List<Fabric> allFabric = getFabricList();

        // find max values of the required rectangle:
        int maxX = 0;
        int maxY = 0;
        for (Fabric f : allFabric) {
            maxX = Math.max(f.getMaxX(), maxX);
            maxY = Math.max(f.getMaxY(), maxY);
        }

        int[][] claimedArea = new int[maxY + 1][maxX + 1];

        // update areas from arraylist:
        for (Fabric f : allFabric){
            for (int i = f.getMinY(); i < f.getMaxY(); i++) {
                for (int j = f.getMinX(); j < f.getMaxX(); j++) {
                    claimedArea[i][j] ++;
                }
            }
        }

        // count parity to get areas:
        int overlaps = 0;
        for (int i = 0; i < maxY + 1; i++) {
            for (int j = 0; j < maxX + 1; j++) {
                if(claimedArea[i][j] >= 2){
                    overlaps ++;
                }
            }
        }

        System.out.println(overlaps);
    }

    public void part2(){
        List<Fabric> allFabric = getFabricList();

        int size = allFabric.size();
        for (int i = 0; i < size; i++) {
            int counter = 0;
            for (int j = 0; j < size; j++) {

                if (i == j){
                    continue;
                }

                // increment if there is no overlap
                if(!Fabric.isThereOverlap(allFabric.get(i), allFabric.get(j))){
                    counter ++;
                } else {
                    break;
                }
            }

            // no overlaps other than itself
            if(counter == size - 1){
                System.out.println(allFabric.get(i).getID());
                return;
            }
        }
        System.out.println("No fabric with unique only claims found.");
    }
}
