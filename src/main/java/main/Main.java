package main;

public class Main {

    private static final int YEAR = 2023;
    private static final int DAY = 17;

    public static void main(String[] args) throws Exception {
        String className = String.format("year%d.day%d.Day%d", YEAR, DAY, DAY);

        Class<?> dayClass = Class.forName(className);
        Object dayInstance = dayClass.getDeclaredConstructor().newInstance();

        dayClass.getMethod("part1").invoke(dayInstance);
        dayClass.getMethod("part2").invoke(dayInstance);
    }
}