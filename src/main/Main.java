package main;

import java.lang.reflect.InvocationTargetException;

public class Main {

    static int YEAR = 2025;
    static int DAY = 11;

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = "year%d.day%d.Day%d".formatted(YEAR, DAY, DAY);

        Class<?> dayClass = Class.forName(className);
        Object dayInstance = dayClass.getDeclaredConstructor().newInstance();

        dayClass.getMethod("part1").invoke(dayInstance);
        dayClass.getMethod("part2").invoke(dayInstance);
    }
}