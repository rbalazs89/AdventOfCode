package year2015.day22;

import main.ReadLines;

import java.util.*;

public class Day22 {

    private final ReadLines reader = new ReadLines(2015, 22, 2);
    private List<String> fileLines;

    // spell constants from task description:
    private static final int MAGIC_MISSILE_DAMAGE = 4;
    private static final int MAGIC_MISSILE_MANA_COST = 53;
    private static final int DRAIN_MANA_COST = 73;
    private static final int DRAIN_VALUE = 2;
    private static final int SHIELD_MANA_COST = 113;
    private static final int POISON_SPELL_MANA_COST = 173;
    private static final int RECHARGE_MANA_COST = 229;

    // unique id for spells:
    private static final int MAGIC_MISSILE = 0;
    private static final int DRAIN = 1;
    private static final int SHIELD = 2;
    private static final int POISON = 3;
    private static final int RECHARGE = 4;

    public void readData(){
        fileLines = reader.readFile();
    }

    private GameState prepareStateFromInput(){
        readData();
        Wizard wizard = new Wizard();

        String[] parts = fileLines.get(0).split(" ");
        int health = Integer.parseInt(parts[2].trim());
        String[] parts2 = fileLines.get(1).split(" ");
        int damage = Integer.parseInt(parts2[1].trim());

        Boss boss = new Boss(health, damage);
        return new GameState(boss, wizard);
    }

    public void part1(){
        System.out.println(findMinimalMana(prepareStateFromInput(), false));
    }

    public void part2(){
        System.out.println(findMinimalMana(prepareStateFromInput(), true));
    }

    private int findMinimalMana(GameState gameState, boolean hardMode){
        PriorityQueue<GameState> queue = new PriorityQueue<>(new Comparator<GameState>(){
            @Override
            public int compare(GameState o1, GameState o2){
                return Integer.compare(o1.getWizard().getTotalManaSpent(), o2.getWizard().getTotalManaSpent());
            }
        });

        queue.add(gameState);

        int maxNumberOfSpells = 5;
        int solution = -1;

        while(!queue.isEmpty()){
            GameState current = queue.poll();

            for (int i = 0; i < maxNumberOfSpells; i++) {
                GameState nextState = GameState.deepCopyGameState(current);

                if(!isSpellAvailable(nextState, i)){
                    continue;
                }

                if(hardMode){
                    nextState.getWizard().loseLife(1); // part 2 hard difficulty
                }

                doOneFullRoundFight(nextState, i);

                if(nextState.getBoss().confirmIfDead()){
                    solution = nextState.getWizard().getTotalManaSpent();
                    return solution;
                }

                if(nextState.getWizard().confirmIfDead()){
                    continue;
                }
                queue.offer(nextState);
            }
        }
        return solution;
    }

    private void doOneFullRoundFight(GameState gameState, int chosenSpell){
        // hero's round:
        // advance state:
        gameState.getBoss().advancePoisonStatus();
        gameState.getWizard().advanceShieldStatus();
        gameState.getWizard().advanceRechargeStatus();

        // hero casts some spell:
        // note: in the last round when boss is dead, player could be casting spell against a dead boss from poison tick
        // this is fine according to task description, player must be able to cast spell every round
        switch (chosenSpell){
            case MAGIC_MISSILE: {castMagicMissile(gameState);break;}
            case DRAIN: {castDrain(gameState);break;}
            case SHIELD: {castShield(gameState);break;}
            case POISON: {castPoison(gameState);break;}
            case RECHARGE: {castRecharge(gameState);break;}
            default: {throw new IllegalStateException("Player has to cast a spell each round.");}
        }

        // boss turn:
        gameState.getBoss().advancePoisonStatus();
        gameState.getWizard().advanceShieldStatus();
        gameState.getWizard().advanceRechargeStatus();

        // after player's round check if boss is dead
        if(gameState.getBoss().confirmIfDead()){
            return;
        }
        bossHitsWizard(gameState);
    }

    private void bossHitsWizard(GameState gameState){
        int damage = gameState.getBoss().getDamage();
        gameState.getWizard().loseLife(damage);
    }

    private void castRecharge(GameState gameState){
        gameState.getWizard().applyRecharge();
        gameState.getWizard().loseMana(RECHARGE_MANA_COST);
    }

    private void castPoison(GameState gameState){
        gameState.getBoss().applyPoison();
        gameState.getWizard().loseMana(POISON_SPELL_MANA_COST);
    }

    private void castShield(GameState gameState){
        gameState.getWizard().applyShield();
        gameState.getWizard().loseMana(SHIELD_MANA_COST);
    }

    private void castDrain(GameState gameState){
        gameState.getWizard().loseMana(DRAIN_MANA_COST);
        gameState.getBoss().loseHealth(DRAIN_VALUE);
        gameState.getWizard().winHealth(DRAIN_VALUE);
    }

    private void castMagicMissile(GameState gameState){
        gameState.getWizard().loseMana(MAGIC_MISSILE_MANA_COST);
        gameState.getBoss().loseHealth(MAGIC_MISSILE_DAMAGE);
    }

    private boolean isSpellAvailable(GameState gameState, int chosenSpell){
        // prune by mana cost:
        int cost = getManaCost(chosenSpell);
        if(gameState.getWizard().getMana() < cost)
            return false;

        // pruning possibility for depth + 1
        // because player must be able to cast spell every round, even if the boss would be killed by poison in the same round
        int maxDamageDonePerRound = Boss.poisonDamagePerRound + MAGIC_MISSILE_DAMAGE;
        if(gameState.getWizard().getMana() - cost < MAGIC_MISSILE_MANA_COST && gameState.getWizard().getRechargeRemainingRounds() == 0 && gameState.getBoss().getHealth() > maxDamageDonePerRound){
            return false;
        }

        if(chosenSpell == MAGIC_MISSILE || chosenSpell == DRAIN){
            return true;
        }

        if(chosenSpell == SHIELD){
            return gameState.getWizard().getShieldRemainingRounds() <= 1;
        }
        if(chosenSpell == POISON){
            return gameState.getBoss().getPoisonRemainingRounds() <= 1;

        }
        if(chosenSpell == RECHARGE){
            return gameState.getWizard().getRechargeRemainingRounds() <= 1;
        }

        throw new IllegalStateException("Spell not recognized");
    }

    private static int getManaCost(int chosenSpell){
        switch (chosenSpell){
            case MAGIC_MISSILE:{return MAGIC_MISSILE_MANA_COST;}
            case DRAIN:{return DRAIN_MANA_COST;}
            case SHIELD:{return SHIELD_MANA_COST;}
            case POISON:{return POISON_SPELL_MANA_COST;}
            case RECHARGE:{return RECHARGE_MANA_COST;}
            default:{throw new IllegalStateException("invalid spell");}
        }
    }
}