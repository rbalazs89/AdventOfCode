package year2015.day22;

class Boss {
    private final int damage;
    private int health;
    private int poisonRemainingRounds;
    private boolean isDead;

    // constants: logically shouldn't belong to boss class in a real game, but only boss can get poisoned in this puzzle:
    final static int poisonDamagePerRound = 3;
    private final static int poisonDuration = 6;

    Boss(int health, int damage){
        this.health = health;
        this.damage = damage;
        isDead = false;
        poisonRemainingRounds = 0;
    }

    void advancePoisonStatus(){
        if(poisonRemainingRounds > 0){
            poisonRemainingRounds --;
            loseHealth(poisonDamagePerRound);
        }
    }

    // damage not applied the same round
    void applyPoison(){
        if(poisonRemainingRounds != 0){
            // this checked already when deciding next spell
            throw new IllegalStateException("Rules state ongoing effect cannot be applied, poison reapplied");
        } else {
            poisonRemainingRounds = poisonDuration;
        }
    }

    void loseHealth(int value){
        health = health - value;
        if(health <= 0){
            isDead = true;
        }
    }

    static Boss deepCopyBoss(Boss boss){
        Boss copiedBoss = new Boss(boss.health, boss.damage);
        copiedBoss.poisonRemainingRounds = boss.poisonRemainingRounds;
        return copiedBoss;
    }

    int getHealth(){
        return health;
    }

    int getDamage(){
        return damage;
    }

    boolean confirmIfDead(){
        return isDead;
    }

    int getPoisonRemainingRounds(){
        return poisonRemainingRounds;
    }
}