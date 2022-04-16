package board;

import cards.*;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Player {
    private final Hand h;
    private final Display d;
    private int score;
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
        for (int i = 0; i < number; i++) {
            for (int a = 0; a < getDisplay().size(); a++) {
                if (getDisplay().getElementAt(a).getType().equals(CardType.STICK)) {
                    getDisplay().removeElement(a);
                    break;
                }
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
        if (card.getType() == CardType.BASKET) {
            addCardtoDisplay(card);
            handlimit += 2;
        } else {
            this.h.add(card);
        }

    }


    public void addCardtoDisplay(Card card) {
        this.d.add(card);
    }


    public boolean takeCardFromTheForest(int number) {
        if (getHand().size() == handlimit) {
            if (Board.getForest().getElementAt(8 - number).getType() != CardType.BASKET && Board.getForest().getElementAt(8 - number).getType() != CardType.STICK) {
                return false;
            }
        }
        if (number > 2 && (number - 2) > sticks) {
            return false;
        }
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
        return true;
    }


    public boolean takeFromDecay() {

        int numberbasket = 0;
        for (int i = 0; i < Board.getDecayPile().size(); i++) {
            if (Board.getDecayPile().get(i).getType() == (CardType.BASKET)) {
                numberbasket += 1;
            }
        }


        if (getHand().size() == getHandLimit()) {

            if (numberbasket == 0) {
                return false;
            }
            if (numberbasket == 1 && Board.getDecayPile().size() == 4) {
                return false;
            }
            if (numberbasket == 1 && Board.getDecayPile().size() < 4) {
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

        }
        if (getHand().size() > handlimit) {
            return false;
        }

        if (getHand().size() <= getHandLimit()) {
            if (numberbasket == 0) {
                if ((handlimit - getHand().size()) < Board.getDecayPile().size()) {
                    return false;

                }
            }
        }

        for (int i = 0; i < Board.getDecayPile().size(); i++) {
            if (Board.getDecayPile().get(0).getType() == (CardType.BASKET)) {
                addCardtoDisplay(Board.getDecayPile().remove(0));
                break;
            }
            if (Board.getDecayPile().get(0).getType() == (CardType.STICK)) {
                addSticks(1);
                Board.getDecayPile().remove(0);
                break;
            } else {
                addCardtoHand(Board.getDecayPile().remove(0));
            }
        }

        return true;
    }


    public boolean cookMushrooms(ArrayList<Card> card) {
        boolean pangot = false;
        int daymushroom = 0;
        int nightmushroom = 0;
        int butter = 0;
        int cider = 0;

        Card mushroom = null;
        int pan = 0;
        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).getType() == (CardType.DAYMUSHROOM) || card.get(i).getType() == CardType.NIGHTMUSHROOM) {
                if (mushroom == null) {
                    mushroom = card.get(i);
                } else {
                    if (!Objects.equals(card.get(i).getName(), mushroom.getName())) {
                        return false;
                    }
                }
            }
            if (card.get(i).getType() == (CardType.DAYMUSHROOM)) {
                daymushroom += 1;
            }
            if (card.get(i).getType() == (CardType.NIGHTMUSHROOM)) {
                nightmushroom += 1;
            }
            if (card.get(i).getType() == (CardType.BUTTER)) {
                butter += 1;
            }
            if (card.get(i).getType() == (CardType.CIDER)) {
                cider += 1;
            }
            if (card.get(i).getType() == CardType.PAN) {
                pan += 1;
                pangot = true;
            }
            if(card.get(i).getType() == CardType.BASKET){
                return false;
            }

        }
        for (int i = 0; i < getDisplay().size(); i++) {
            if ((getDisplay().getElementAt(i).getType() == (CardType.PAN))) {
                pangot = true;
            }
        }
        if (!pangot) {
            return false;
        }
        if (daymushroom + nightmushroom * 2 < 3) {
            return false;
        }
        if ((daymushroom + nightmushroom * 2) < (cider * 5 + butter * 4)) {
            return false;
        }
        int point = new EdibleItem(mushroom.getType(), mushroom.getName()).getFlavourPoints();

        score += point * (daymushroom + nightmushroom * 2);

        score += butter * 3;

        score += cider * 5;

        for (int i = 0; i < card.size(); i++) {
            for (int a = 0; a < getHand().size(); a++) {
                if (card.get(i) == getHand().getElementAt(i)) {
                    getHand().removeElement(i);
                }
            }

        }
        return true;

    }

    public boolean sellMushrooms(String a, int number) {
        a = a.replace(" ", "").replace("'", " ").toLowerCase();
        int Daymushroomss=0;
        int Nightmushroomss=0;
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().getElementAt(i).getName().equals(a)&&getHand().getElementAt(i).getType()==CardType.DAYMUSHROOM ){
                Daymushroomss+=1;
            }
            if (getHand().getElementAt(i).getName().equals(a)&&getHand().getElementAt(i).getType()==CardType.NIGHTMUSHROOM ){
                Nightmushroomss+=1;
            }
        }
        if ((Daymushroomss+Nightmushroomss*2)<number||number<2){
            return false;
        }
        int point = new Mushroom(CardType.DAYMUSHROOM,a).getSticksPerMushroom();
        if (Nightmushroomss>=number*2) {
            for (int count = 0; count < number; count++) {
                for (int i = 0; i < getHand().size(); i++) {
                    if (getHand().getElementAt(i).getName().equals(a) && getHand().getElementAt(i).getType() == CardType.NIGHTMUSHROOM) {
                        getHand().removeElement(i);
                        addSticks(point * 2);
                    }
                }
            }
        }else{
            for (int i = 0; i < getHand().size(); i++) {
                if (getHand().getElementAt(i).getName().equals(a) && getHand().getElementAt(i).getType() == CardType.NIGHTMUSHROOM) {
                    getHand().removeElement(i);
                    addSticks(point*2);
                }
            }
            for (int count = 0; count < number-Nightmushroomss*2; count++) {
                for (int i = 0; i < getHand().size(); i++) {
                    if (getHand().getElementAt(i).getName().equals(a) && getHand().getElementAt(i).getType() == CardType.DAYMUSHROOM) {
                        getHand().removeElement(i);
                        addSticks(point);
                    }
                }
            }

        }

        return true;
    }

    public boolean putPanDown() {
        boolean f = false;
        int a;
        for (a = 0; a < getHand().size(); a++)
            if (getHand().getElementAt(a).getType() == (CardType.PAN)) {
                f = true;

            }
        if (!f) {
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
