package board;

import cards.Card;

import java.util.ArrayList;

public class Hand implements Displayable {
    private static ArrayList<Card> handList;


    public void add(Card card) {
        handList.add(card);

    }


    public int size() {
        return handList.size();
    }


    public Card getElementAt(int index) {
        return handList.get(index);
    }


    public Card removeElement(int index) {
        return handList.remove(index);
    }
}