package year2018.day14;

import main.ReadLines;

import java.util.*;

public class Day14 {
    private final ReadLines reader = new ReadLines(2018, 14, 2);

    // first two recipe values from task description:
    private static final int RECIPE_ONE = 3;
    private static final int RECIPE_TWO = 7;
    private static final int RECIPE_NUMBER = 10; // task requires to print 10 after the input


    public void part1(){
        int numberOfRecipes = Integer.parseInt(reader.readFile().getFirst());
        List<Integer> recipes = createRecipes();

        // extract the 10 recipes starting at index numberOfRecipes
        StringBuilder sb = new StringBuilder();
        for (int i = numberOfRecipes; i < numberOfRecipes + RECIPE_NUMBER; i++) {
            sb.append(recipes.get(i));
        }
        System.out.println(sb);
    }


    public void part2(){
        String recipeString = reader.readFile().getFirst();
        int numberOfRecipes = Integer.parseInt(recipeString);

        List<Integer> recipes = createRecipes();
        int recipeLength = recipeString.length();

    }

    private List<Integer> createRecipes(){
        int numberOfRecipes = Integer.parseInt(reader.readFile().getFirst());
        List<Integer> recipes = new ArrayList<>();
        recipes.add(RECIPE_ONE);
        recipes.add(RECIPE_TWO);

        int elf1 = 0;
        int elf2 = 1;

        while (recipes.size() < numberOfRecipes + RECIPE_NUMBER) {
            int sum = recipes.get(elf1) + recipes.get(elf2);
            if (sum >= 10) {
                recipes.add(1);
            }
            recipes.add(sum % 10);
            elf1 = (elf1 + recipes.get(elf1) + 1) % recipes.size();
            elf2 = (elf2 + recipes.get(elf2) + 1) % recipes.size();
        }
        return recipes;
    }
}
