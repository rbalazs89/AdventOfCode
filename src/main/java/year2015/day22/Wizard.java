package year2015.day22;

class Wizard {
    private int mana;
    private int health;
    private boolean isDead;
    private int totalManaSpent;

    // only player has shield and recharge spells:
    private int rechargingRemaining;
    private final static int RECHARGE_VALUE = 101;
    private final static int RECHARGE_SPELL_DURATION = 5;

    private int armor;
    private int shieldRemainingRounds;
    private final static int SHIELD_ARMOR_VALUE = 7;
    private final static int SHIELD_DURATION = 6; // 6 originally

    Wizard(){
        mana = 500;
        health = 50;
        rechargingRemaining = 0;
        shieldRemainingRounds = 0;
        armor = 0;
    }

    Wizard(int mana, int health, int rechargingRemaining, int shieldRemainingRounds, int totalManaSpent, int armor){
        this.mana = mana;
        this.health = health;
        this.rechargingRemaining = rechargingRemaining;
        this.shieldRemainingRounds = shieldRemainingRounds;
        this.totalManaSpent = totalManaSpent;
        this.armor = armor;
    }

    void applyShield(){
        if(shieldRemainingRounds != 0){
            throw new IllegalStateException("Shield was already applied.");
        } else {
            armor = SHIELD_ARMOR_VALUE;
        }
        shieldRemainingRounds = SHIELD_DURATION;
    }

    void advanceShieldStatus(){
        if(shieldRemainingRounds > 0){
            shieldRemainingRounds--;
        }
        if(shieldRemainingRounds == 0){
            armor = 0;
        }
    }

    void advanceRechargeStatus(){
        if(rechargingRemaining > 0){
            rechargingRemaining --;
            mana = mana + RECHARGE_VALUE;
        }
    }

    void applyRecharge(){
        if(rechargingRemaining != 0){
            throw new IllegalStateException("Recharge was already applied");
        }
        rechargingRemaining = RECHARGE_SPELL_DURATION;
    }

    void loseLife(int value){

        health = health - Math.max(value - armor, 1);
        if(health <= 0){
            isDead = true;
        }
    }

    void winHealth(int value){
        health = health + value;
    }

    void loseMana(int value){
        mana = mana - value;
        totalManaSpent = totalManaSpent + value;

        // this is checked already when deciding the next available spell
        if(mana < 0){
            throw new IllegalStateException("Mana below 0");
        }
    }

    int getMana(){
        return mana;
    }

    static Wizard deepCopyWizard(Wizard original){
        return new Wizard(original.mana, original.health, original.rechargingRemaining, original.shieldRemainingRounds, original.totalManaSpent, original.armor);
    }

    boolean confirmIfDead(){
        return isDead;
    }

    int getTotalManaSpent(){
        return totalManaSpent;
    }

    int getRechargeRemainingRounds(){
        return rechargingRemaining;
    }

    int getShieldRemainingRounds(){
        return shieldRemainingRounds;
    }
}
