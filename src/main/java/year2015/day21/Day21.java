package year2015.day21;

import java.util.ArrayList;

public class Day21 {

    // 0...4 are weapons
    // 5...9 are armors
    // 10...15 are rings
    Item[] itemList;
    Boss boss;
    Hero hero;

    // combinations container
    ArrayList<boolean[]> allCombinations = new ArrayList<>();

    public void part1(){
        setup(); // sets up hero, boss and items
        recursiveItemCombinations(new boolean[itemList.length], 0); // fills allCombinations arraylist

        int minCost = Integer.MAX_VALUE;

        for (int i = 0; i < allCombinations.size(); i++) {
            boolean[] current = allCombinations.get(i);
            applyItemListToHero(current);
            if(heroAndBossHaveAFight()){
                int currentCost = getCostOfItems(current);
                minCost = Math.min(currentCost, minCost);
            }
            resetHeroAndBoss(); // remove items and heal up
        }

        System.out.println(minCost);
    }
    public void recursiveItemCombinations(boolean[] oneCombination, int index){

        // prune invalid combinations (eq: 2 weapons, 3 rings, 2 armors, important: 0 weapon is still OK to detect future branches)
        if(!checkIfTooManyTruesInCombination(oneCombination)){
            return;
        }

        // end
        if(index == oneCombination.length){
            registerCombinations(oneCombination);
            return;
        }

        // branch 1:
        recursiveItemCombinations(oneCombination, index + 1);

        // branch 2:
        oneCombination[index] = true;
        recursiveItemCombinations(oneCombination, index + 1);
        oneCombination[index] = false;
    }
    public void part2(){
        int maxCost = 0;

        // already filled from part 1
        // allCombinations.clear();
        // recursiveItemCombinations(new boolean[itemList.length], 0); // fills allCombinations arraylist

        for (int i = 0; i < allCombinations.size(); i++) {
            boolean[] current = allCombinations.get(i);
            applyItemListToHero(current);
            if(!heroAndBossHaveAFight()){
                int currentCost = getCostOfItems(current);
                maxCost = Math.max(currentCost, maxCost);
            }
            resetHeroAndBoss(); // remove items and heal up
        }

        System.out.println(maxCost);
    }
    // return true if hero wins
    public boolean heroAndBossHaveAFight(){
        int counter = 0;
        int maxRounds = Math.max(hero.health, boss.health) + 1;
        while(true){
            // hero hits the boss
            int damage1 = Math.max(hero.damage - boss.armor, 1);
            boss.health = boss.health - damage1;
            if(boss.health <= 0){
                return  true;
            }

            // boss hits the hero
            int damage2 = Math.max(boss.damage - hero.armor, 1);
            hero.health = hero.health - damage2;
            if(hero.health <= 0){
                return false;
            }

            counter ++;
            if(counter > maxRounds){
                System.out.println("Something went wrong, too many rounds");
                break;
            }
        }
        return false;
    }
    public void registerCombinations(boolean[] combination){
        if(checkIfValidCombination(combination)){
            allCombinations.add(combination.clone());
        }
    }
    public boolean checkIfValidCombination(boolean[] combination){
        // check weapon:
        int points = 0;
        for (int i = 0; i < 5; i++) { // incl 0....4
            if(combination[i]){
                points ++;
            }
        }
        if(points != 1){
            return false;
        }

        // check armors:
        points = 0;
        for (int i = 5; i < 10; i++) { // incl 5....9
            if(combination[i]){
                points ++;
            }
        }
        if(!(points == 0 || points == 1)){
            return false;
        }

        // check rings:
        points = 0;
        for (int i = 10; i < 16; i++) { // incl 10....15
            if(combination[i]){
                points ++;
            }
        }
        return points == 0 || points == 1 || points == 2;
    }
    public boolean checkIfTooManyTruesInCombination(boolean[] combination){
        // check weapon:
        int points = 0;
        for (int i = 0; i < 5; i++) { // incl 0....4
            if(combination[i]){
                points ++;
            }
        }
        if(points > 1){
            return false;
        }

        // check armors:
        points = 0;
        for (int i = 5; i < 10; i++) { // incl 5....9
            if(combination[i]){
                points ++;
            }
        }
        if(points > 1){
            return false;
        }

        // check rings:
        points = 0;
        for (int i = 10; i < 16; i++) { // incl 10....15
            if(combination[i]){
                points ++;
            }
        }
        return points <= 2;
    }
    public void setup(){
        setupItems();
        setupBoss();
        setupHero();
    }
    public void setupItems(){
        itemList = new Item[16];
        // weapons:
        itemList[0] = new Item(0, 4, 8);
        itemList[1] = new Item(0, 5, 10);
        itemList[2] = new Item(0,6, 25);
        itemList[3] = new Item(0, 7, 40);
        itemList[4] = new Item(0, 8, 74);

        // armors:
        itemList[5] = new Item(1, 0, 13);
        itemList[6] = new Item(2, 0, 31);
        itemList[7] = new Item(3, 0, 53);
        itemList[8] = new Item(4, 0, 75);
        itemList[9] = new Item(5, 0, 102);

        //rings:
        itemList[10]= new Item(0, 1, 25);
        itemList[11] = new Item(0, 2, 50);
        itemList[12] = new Item(0, 3, 100);
        itemList[13] = new Item(1, 0, 20);
        itemList[14] = new Item(2, 0, 40);
        itemList[15] = new Item(3, 0, 80);
    }
    public void setupBoss(){
        // from unique input:
        boss = new Boss();
        boss.setupBoss();
    }
    public void setupHero(){
        hero = new Hero();
        hero.setupHero();
    }
    public void resetHeroAndBoss(){
        boss.resetBoss(); // resets health
        hero.resetHero(); // resets health and removes additional items effect
    }
    private void applyOneItemToHero(Item item){
        hero.armor += item.armor;
        hero.damage += item.damage;
    }
    public void applyItemListToHero(boolean[] equipped){
        for (int i = 0; i < equipped.length; i++) {
            if(equipped[i]){
                applyOneItemToHero(itemList[i]);
            }
        }
    }
    public int getCostOfItems(boolean[] equipped){
        int allCost = 0;
        for (int i = 0; i < equipped.length; i++) {
            if(equipped[i]){
                allCost += itemList[i].cost;
            }
        }
        return allCost;
    }
}