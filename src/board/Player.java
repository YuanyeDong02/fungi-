package board;

import cards.Card;

import java.util.ArrayList;

public class Player {
    private Hand h;
    private Display d;
    private int score;
    private int handlimit;
    private int sticks;
    public Player(){
        h = new Hand();
        d = new Display();
        score = 0;
        handlimit = 8;
        sticks = 0;

    }
    public int getScore(){
        return score;
    }
    public int getHandLimit(){
        return handlimit;
    }
    public int  getStickNumber(){
        return sticks;
    }
    public void addSticks(int number){
        this.sticks+=number;
    }
    public void removeSticks(int number){
        this.sticks-=number;
    }
    public Hand getHand(){
        return h;
    }
    public Display getDisplay(){
        return d;
    }
    public void addCardtoHand(Card card){
        this.h.add(card);

    }


    public void addCardtoDisplay(Card card){
        this.d.add(card);
    }
    public boolean takeCardFromTheForest(int number){


        return false;
    }
    public boolean takeFromDecay(){

        return false;
    }
    public boolean cookMushrooms(ArrayList<Card> card){

        return false;
    }
    public boolean sellMushrooms(String a,int number){

        return false;
    }
    public boolean putPanDown(){

        return false;
    }

}
