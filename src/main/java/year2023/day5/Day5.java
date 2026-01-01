package year2023.day5;

import main.ReadLines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Day5 {
    private final ReadLines reader = new ReadLines(2023, 5, 2);
    private final ArrayList<Long> seedToSoil = new ArrayList<>();
    private final ArrayList<Long> soilToFertilizer = new ArrayList<>();
    private final ArrayList<Long> fertilizerToWater = new ArrayList<>();
    private final ArrayList<Long> waterToLight = new ArrayList<>();
    private final ArrayList<Long> lightToTemperature = new ArrayList<>();
    private final ArrayList<Long> temperatureToHumidity = new ArrayList<>();
    private final ArrayList<Long> humidityToLocation = new ArrayList<>();
    private final ArrayList<Long> seeds = new ArrayList<>();

    private void prepare(){
        List<String> input = reader.readFile();
        ArrayList<Integer> mapping = getList(input);

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);

            if(i == 0){
                for (int j = 0; j < line.length(); j++) {
                    if (Character.isDigit(line.charAt(j))) {
                        int k = j;
                        while (k < line.length() && Character.isDigit(line.charAt(k))) {
                            k++;
                        }
                        seeds.add(Long.valueOf(line.substring(j, k)));
                        j = k;
                    }
                }
            }
            if(mapping.get(0).equals(i)){
                i++;
                for (int l = i; l < mapping.get(1) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            seedToSoil.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(1).equals(i)){
                i++;
                for (int l = i; l < mapping.get(2) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            soilToFertilizer.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(2).equals(i)){
                i++;
                for (int l = i; l < mapping.get(3) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            fertilizerToWater.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(3).equals(i)){
                i++;
                for (int l = i; l < mapping.get(4) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            waterToLight.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(4).equals(i)){
                i++;
                for (int l = i; l < mapping.get(5) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            lightToTemperature.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(5).equals(i)){
                i++;
                for (int l = i; l < mapping.get(6) - 1; l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            temperatureToHumidity.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
            if(mapping.get(6).equals(i)){
                i++;
                for (int l = i; l < mapping.get(7); l++) {
                    for (int j = 0; j < input.get(l).length(); j++) {
                        if (Character.isDigit(input.get(l).charAt(j))) {
                            int k = j;
                            while (k < input.get(l).length() && Character.isDigit(input.get(l).charAt(k))) {
                                k++;
                            }
                            humidityToLocation.add(Long.valueOf(input.get(l).substring(j, k)));
                            j = k;
                        }
                    }
                }
            }
        }
    }

    private static ArrayList<Integer> getList(List<String> input) {
        ArrayList<Integer> mapping = new ArrayList<>();
        for(int i = 0; i < input.size(); i ++){
            if(Objects.equals(input.get(i), "seed-to-soil map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "soil-to-fertilizer map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "fertilizer-to-water map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "water-to-light map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "light-to-temperature map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "temperature-to-humidity map:")){
                mapping.add(i);
            }
            if(Objects.equals(input.get(i), "humidity-to-location map:")){
                mapping.add(i);
            }
        }
        mapping.add(input.size());
        return mapping;
    }

    public void part1(){
        prepare();
        for(int i = 0 ; i < seeds.size(); i ++){
            for(int j = 0 ; j < seedToSoil.size(); j = j + 3){
                if(seeds.get(i) < seedToSoil.get(j + 1) + seedToSoil.get(j + 2) && seeds.get(i) >= seedToSoil.get(j + 1)){
                    seeds.set(i, (seeds.get(i) - seedToSoil.get(j + 1)) + seedToSoil.get(j));
                    break;
                }
            }

            // For soilToFertilizer ArrayList
            for (int j = 0; j < soilToFertilizer.size(); j = j + 3) {
                if (seeds.get(i) < soilToFertilizer.get(j + 1) + soilToFertilizer.get(j + 2)
                        && seeds.get(i) >= soilToFertilizer.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - soilToFertilizer.get(j + 1)) + soilToFertilizer.get(j));
                    break;
                }
            }

            // For fertilizerToWater ArrayList
            for (int j = 0; j < fertilizerToWater.size(); j = j + 3) {
                if (seeds.get(i) < fertilizerToWater.get(j + 1) + fertilizerToWater.get(j + 2)
                        && seeds.get(i) >= fertilizerToWater.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - fertilizerToWater.get(j + 1)) + fertilizerToWater.get(j));
                    break;
                }
            }

            // For waterToLight ArrayList
            for (int j = 0; j < waterToLight.size(); j = j + 3) {
                if (seeds.get(i) < waterToLight.get(j + 1) + waterToLight.get(j + 2)
                        && seeds.get(i) >= waterToLight.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - waterToLight.get(j + 1)) + waterToLight.get(j));
                    break;
                }
            }

            // For lightToTemperature ArrayList
            for (int j = 0; j < lightToTemperature.size(); j = j + 3) {
                if (seeds.get(i) < lightToTemperature.get(j + 1) + lightToTemperature.get(j + 2)
                        && seeds.get(i) >= lightToTemperature.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - lightToTemperature.get(j + 1)) + lightToTemperature.get(j));
                    break;
                }
            }

            // For temperatureToHumidity ArrayList
            for (int j = 0; j < temperatureToHumidity.size(); j = j + 3) {
                if (seeds.get(i) < temperatureToHumidity.get(j + 1) + temperatureToHumidity.get(j + 2)
                        && seeds.get(i) >= temperatureToHumidity.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - temperatureToHumidity.get(j + 1)) + temperatureToHumidity.get(j));
                    break;
                }
            }

            // For humidityToLocation ArrayList
            for (int j = 0; j < humidityToLocation.size(); j = j + 3) {
                if (seeds.get(i) < humidityToLocation.get(j + 1) + humidityToLocation.get(j + 2)
                        && seeds.get(i) >= humidityToLocation.get(j + 1)) {
                    seeds.set(i, (seeds.get(i) - humidityToLocation.get(j + 1)) + humidityToLocation.get(j));
                    break;
                }
            }
        }
        Collections.sort(seeds);
        System.out.println(seeds.getFirst());
    }

    public void part2(){
        Long solution = Long.MAX_VALUE;

        for (int m = 0; m < seeds.size() ; m = m + 2) {

            Long rangeMin = seeds.get(m);
            Long rangeMax = seeds.get(m) + seeds.get(m+1);
            while (rangeMin < rangeMax) {

                long processedNumber = rangeMin;
                rangeMin ++;

                for (int j = 0; j < seedToSoil.size(); j = j + 3) {
                    if (processedNumber < seedToSoil.get(j + 1) + seedToSoil.get(j + 2) && processedNumber >= seedToSoil.get(j + 1)) {
                        processedNumber = (processedNumber - seedToSoil.get(j + 1)) + seedToSoil.get(j);
                        break;
                    }
                }

                // For soilToFertilizer ArrayList
                for (int j = 0; j < soilToFertilizer.size(); j = j + 3) {
                    if (processedNumber < soilToFertilizer.get(j + 1) + soilToFertilizer.get(j + 2)
                            && processedNumber >= soilToFertilizer.get(j + 1)) {
                        processedNumber = (processedNumber - soilToFertilizer.get(j + 1)) + soilToFertilizer.get(j);
                        break;
                    }
                }

                // For fertilizerToWater ArrayList
                for (int j = 0; j < fertilizerToWater.size(); j = j + 3) {
                    if (processedNumber < fertilizerToWater.get(j + 1) + fertilizerToWater.get(j + 2)
                            && processedNumber >= fertilizerToWater.get(j + 1)) {
                        processedNumber = (processedNumber - fertilizerToWater.get(j + 1)) + fertilizerToWater.get(j);
                        break;
                    }
                }

                // For waterToLight ArrayList
                for (int j = 0; j < waterToLight.size(); j = j + 3) {
                    if (processedNumber < waterToLight.get(j + 1) + waterToLight.get(j + 2)
                            && processedNumber >= waterToLight.get(j + 1)) {
                        processedNumber = (processedNumber - waterToLight.get(j + 1)) + waterToLight.get(j);
                        break;
                    }
                }

                // For lightToTemperature ArrayList
                for (int j = 0; j < lightToTemperature.size(); j = j + 3) {
                    if (processedNumber < lightToTemperature.get(j + 1) + lightToTemperature.get(j + 2)
                            && processedNumber >= lightToTemperature.get(j + 1)) {
                        processedNumber = (processedNumber - lightToTemperature.get(j + 1)) + lightToTemperature.get(j);
                        break;
                    }
                }

                // For temperatureToHumidity ArrayList
                for (int j = 0; j < temperatureToHumidity.size(); j = j + 3) {
                    if (processedNumber < temperatureToHumidity.get(j + 1) + temperatureToHumidity.get(j + 2)
                            && processedNumber >= temperatureToHumidity.get(j + 1)) {
                        processedNumber = (processedNumber - temperatureToHumidity.get(j + 1)) + temperatureToHumidity.get(j);
                        break;
                    }
                }

                // For humidityToLocation ArrayList
                for (int j = 0; j < humidityToLocation.size(); j = j + 3) {
                    if (processedNumber < humidityToLocation.get(j + 1) + humidityToLocation.get(j + 2)
                            && processedNumber >= humidityToLocation.get(j + 1)) {
                        processedNumber = (processedNumber - humidityToLocation.get(j + 1)) + humidityToLocation.get(j);
                        break;
                    }
                }
                if(solution > processedNumber) {
                    solution = processedNumber;
                }
            }
        }
        System.out.println(solution);
    }
}