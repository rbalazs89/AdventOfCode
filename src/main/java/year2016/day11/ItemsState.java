package year2016.day11;

import java.util.ArrayList;

// helper class to save elevator position too

public class ItemsState {
    int elevator;
    ArrayList<Item> itemList;

    ItemsState(ArrayList<Item> itemList, int elevator){
        this.itemList = itemList;
        this.elevator = elevator;
    }
}