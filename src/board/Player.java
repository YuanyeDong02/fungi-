package board;

import cards.Card;
import cards.CardType;
import cards.Pan;
import cards.Stick;

import java.util.ArrayList;

public class Player {
    private final Hand h;
    private final Display d;
    private final int score;
    private int handlimit;
    private int sticks;

    public Player() {
        h = new Hand();
        d = new Display();
        score = 0;
        handlimit = 8;
        sticks = 0;
        addCardtoDisplay(new Pan());

    }

    public int getScore() {
        return score;
    }

    public int getHandLimit() {
        return handlimit;
    }

    public int getStickNumber() {
        return sticks;
    }

    public void addSticks(int number) {
        for (int i = 0; i < number; i++) {
            addCardtoDisplay(new Stick());
        }
        this.sticks += number;
    }

    public void removeSticks(int number) {
        for (int a = 0; a < getDisplay().size(); a++) {
            if (getDisplay().getElementAt(a).getType().equals(CardType.STICK)) {
                getDisplay().removeElement(a);
                break;
            }
        }
        this.sticks -= number;
    }

    public Hand getHand() {
        return h;
    }

    public Display getDisplay() {
        return d;
    }

    public void addCardtoHand(Card card) {
        this.h.add(card);
    }


    public void addCardtoDisplay(Card card) {
        this.d.add(card);
    }


    public boolean takeCardFromTheForest(int number) {
        if (getHandLimit() != getHand().size() || (Board.getForest().getElementAt(8 - number).getType().equals(CardType.BASKET) &&
                Board.getForest().getElementAt(8 - number).getType().equals(CardType.STICK))) {
            if (number > 2 && (number - 2) > sticks) {
                return false;
            } else {
                if (number > 2) {
                    removeSticks(number - 2);
                }
                if (Board.getForest().getElementAt(8 - number).getType().equals(CardType.BASKET)) {
                    handlimit += 2;
                    addCardtoDisplay(Board.getForest().removeCardAt(number));
                } else if (((Board.getForest().getElementAt(8 - number).getType().equals(CardType.STICK)))) {
                    addSticks(1);
                    Board.getForest().removeCardAt(number);
                } else {
                    addCardtoHand((Board.getForest().removeCardAt(8 - number)));
                }
            }
        }
        return true;
    }

    public boolean takeFromDecay() {
        int decaysize = Board.getDecayPile().size();
        int numberbasket = 0;
        for (int i = 0; i < decaysize; i++) {
            if (Board.getDecayPile().get(i).getType() == (CardType.BASKET)) {
                numberbasket += 1;
            }
        }
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println(getHand().getElementAt(i));
        }
        System.out.println("size:" + getHand().size());
        System.out.println("limit:"+getHandLimit());
        if (getHand().size() >= getHandLimit()) {
            System.out.println("111");
            if (numberbasket == 0) {
                return false;
            }
            if (numberbasket == 1 && Board.getDecayPile().size() ==4) {
                return false;
            }
            if (numberbasket == 1 ) {
                handlimit += 2;
            }
            if (numberbasket == 2) {
                handlimit += 4;
            }
            if (numberbasket == 3) {
                handlimit += 6;
            }
            if (numberbasket == 4) {
                handlimit += 8;
            }
            return false;
        }
        for (int i = 0; i < decaysize; i++) {
            if (Board.getDecayPile().get(i).getType() == (CardType.BASKET)) {
                addCardtoDisplay(Board.getDecayPile().remove(i));
            }if(Board.getDecayPile().get(i).getType() == (CardType.STICK)) {
                addSticks(1);
                Board.getDecayPile().remove(i);
            }else{
                addCardtoHand(Board.getDecayPile().remove(i));
            }
        }
        return true;
    }


    public boolean cookMushrooms(ArrayList<Card> card) {

        return false;


    }

    public boolean sellMushrooms(String a, int number) {

        return false;


    }

    public boolean putPanDown() {
        boolean f = false;
        int a;
        for (a = 0; a < getHand().size(); a++)
            if (getHand().getElementAt(a).getType() == (CardType.PAN)) {
                f = true;

            }
        if (f == false) {
            return false;
        }
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().getElementAt(i).getType().equals(CardType.PAN)) {
                addCardtoDisplay(getHand().removeElement(i));
                break;
            }
        }

        return true;
    }

}
