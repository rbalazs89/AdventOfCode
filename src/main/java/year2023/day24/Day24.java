package year2023.day24;

import main.ReadLines;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24 {
    private static final ReadLines reader = new ReadLines(2023, 24, 2);
    static long testAreaX1 = 200000000000000L;
    static long testAreaX2 = 400000000000000L;
    static long testAreaY1 = 200000000000000L;
    static long testAreaY2 = 400000000000000L;

    public void part1(){
        List<String> input = reader.readFile();

        ArrayList<ArrayList<Long>> points = new ArrayList<>();
        Pattern pattern = Pattern.compile("-?\\d+");
        for (int i = 0; i < input.size(); i++) {
            Matcher matcher = pattern.matcher(input.get(i));
            ArrayList<Long> point = new ArrayList<>();
            while (matcher.find()) {
                long foundInt = Long.parseLong(matcher.group());
                point.add(foundInt);
            }
            points.add(point);
        }
        int solution = 0;

        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {

                double x0 = points.get(i).get(0);
                double y0 = points.get(i).get(1);
                double v1 = points.get(i).get(3);
                double v2 = points.get(i).get(4);

                double X0 = points.get(j).get(0);
                double Y0 = points.get(j).get(1);
                double V1 = points.get(j).get(3);
                double V2 = points.get(j).get(4);
                if((v2*V1 - V2*v1) == 0){
                    continue;
                }

                double X = (Y0-(V2*X0/V1) - y0 + v2*x0/v1) * V1 * v1 / (v2*V1 - V2*v1);
                double Y = (-v2*x0+ v1 * y0 + v2 * X) / v1;

                double direction1V = getDirection(v1,v2);
                double direction1P = getDirectionCoordinates(x0,y0,X,Y);
                double direction2P = getDirection(V1,V2);
                double direction2V = getDirectionCoordinates(X0,Y0,X,Y);


                if(testAreaX1<X && testAreaX2>X && testAreaY1<Y && testAreaY2>Y && direction1V == direction1P && direction2P == direction2V){
                    solution ++;

                }
            }
        }

        System.out.println(solution);
    }

    public static double getDirection(double v1, double v2){
        if(v1 == 0){
            if(v2 > 0){
                return 0;
            } else if(v2 < 0 ){
                return 4;
            }
        }
        if (v2 == 0){
            if (v1 < 0){
                return 2;
            }else if (v1 > 0) {
                return 6;
            }
        }
        if(v1 > 0){
            if(v2 > 0){
                return 1;
            }if(v2 < 0 ){
                return 3;
            }
        }
        if(v1 < 0){
            if(v2 > 0){
                return 7;
            }
            if (v2 < 0){
                return 5;
            }
        }
        return 0;
    }
    public static double getDirectionCoordinates(double Px, double Py, double x, double y){
        return getDirection(x-Px, y-Py);
    }

    /** Calculating this in java seems impossible for me, I just used matlab to solve equations for the first 3 stones
     * Simple equation system, 9 equations, 9 unknowns
     * if it hits the first 3 lines, it hits all lines

     * 176253337504656, 321166281702430, 134367602892386 @ 190, 8, 338
     * 230532038994496, 112919194224200, 73640306314241 @ 98, 303, 398
     * 326610633825237, 321507930209081, 325769499763335 @ -67, -119, -75

     * eq1 = 176253337504656 + 190 * q == A + a * q;
     * eq2 = 321166281702430 + 8 * q == B + b * q;
     * eq3 = 134367602892386 + 338 * q == C + c * q;
     * eq4 = 230532038994496 + 98 * w == A + a * w;
     * eq5 = 112919194224200 + 303 * w == B + b * w;
     * eq6 = 73640306314241 + 398 * w == C + c * w;
     * eq7 = 326610633825237 - 67 * e == A + a * e;
     * eq8 = 321507930209081 - 119 * e == B + b *e;
     * eq9 = 325769499763335 - 75 * e == C + c * e;
     */
    public void part2(){
        System.out.println(669042940632377L); // matlab solved it
    }
}