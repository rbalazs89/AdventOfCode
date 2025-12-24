package year2015.day22;

public class GameState {
    // container class to save status of both fighters
    private final Boss boss;
    private final Wizard wizard;

    GameState(Boss boss, Wizard wizard){
        this.boss = boss;
        this.wizard = wizard;
    }

    static GameState deepCopyGameState(GameState original){
        Boss copiedBoss = Boss.deepCopyBoss(original.boss);
        Wizard copiedWizard = Wizard.deepCopyWizard(original.wizard);
        return new GameState(copiedBoss, copiedWizard);
    }

    Wizard getWizard() {
        return wizard;
    }

    Boss getBoss() {
        return boss;
    }
}
