package com.mygdx.hastypastry.models;
/** The Inkbar will contain the amount of ink that'f left and how much we have drawn
 * @author sigmundhh */
public class Inkbar {
    private int maxAmount;
    private int pointsLeft;

    public Inkbar(int maxAmount){
        this.maxAmount = maxAmount;
        pointsLeft = maxAmount;
    }

    public boolean inkbarCheck(){
        return pointsLeft > 0;
    }

    public void useInk(){
        pointsLeft--;
    }

    public void refillInk(int amount){
        pointsLeft += amount;
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

}
