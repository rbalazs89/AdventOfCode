package year2015.day21;

class Boss {
    int health;
    int damage;
    int armor;

    public void resetBoss(){
        health = 100;
    }

    public void setupBoss(){
        damage = 8;
        health = 100;
        armor = 2;
    }
}
