package year2015.day21;

class Hero {
    int armor;
    int health;
    int damage;

    public void resetHero(){
        armor = 0;
        health = 0;
        damage = 0;
    }

    public void setupHero(){
        health = 100;
    }
}
