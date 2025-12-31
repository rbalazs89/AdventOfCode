package year2018.day15;

import main.ReadLines;

public class Day15 {
    private final ReadLines reader = new ReadLines(2018, 15, 2);
    private static final int BASE_ELF_DAMAGE = 3;

    public void part1(){
        Battlefield battlefield = new Battlefield(reader.readFile(), BASE_ELF_DAMAGE);

        int counter = 0;
        while (true) {
            for (int i = 0; i < battlefield.fighters.size(); i++) {
                battlefield.fighters.get(i).doOneTurn(battlefield);

                int index = battlefield.removeDeadFighter();
                if(index != -1){
                    if(index <= i){
                        i--;
                    }
                }

                battlefield.updateMap();
                if (battlefield.isBattleOver()) {
                    if(i == battlefield.fighters.size() - 1){
                        counter ++;
                    }
                    System.out.println(battlefield.getAllHitPoints() * (counter));
                    return;
                }
            }
            battlefield.orderFighters();
            counter ++;
        }
    }

    public void part2(){
        // 200 damage limit, equals to original hp value
        for (int dmg = BASE_ELF_DAMAGE + 1; dmg < 200; dmg ++) {
            Battlefield bf = new Battlefield(reader.readFile(), dmg);
            int originalElfNumber = getElfNumbers(bf);

            int counter = 0;
            battleLoop:
            while (true) {
                for (int i = 0; i < bf.fighters.size(); i++) {
                    bf.fighters.get(i).doOneTurn(bf);

                    int index = bf.removeDeadFighter();
                    if(index != -1){
                        if(index <= i){
                            i--;
                        }
                    }

                    bf.updateMap();
                    if (bf.isBattleOver()) {
                        if(getElfNumbers(bf) == originalElfNumber){
                            if(i == bf.fighters.size() - 1){
                                counter ++;
                            }
                            System.out.println(bf.getAllHitPoints() * (counter));

                            return;
                        } else {

                            // restart everything if an elf died
                            break battleLoop;
                        }
                    }
                }
                bf.orderFighters();
                counter ++;
            }
        }
    }

    private int getElfNumbers(Battlefield bf){
        int elfNumber = 0;
        for (int i = 0; i < bf.fighters.size(); i++) {
            if(bf.fighters.get(i) instanceof Elf){
                elfNumber ++;
            }
        }
        return elfNumber;
    }
}
