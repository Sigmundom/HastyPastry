package com.mygdx.hastypastry.models;
/** The Inkbar will contain the amount of ink that'f left and how much we have drawn
 * @author sigmundhh */
public class Inkbar {
    private int maxAmount;
    private int pointsUsed;

    public Inkbar(int maxAmount){
        this.maxAmount = maxAmount;
        pointsUsed = 0;
    }

    public boolean inkbarCheck(){
        if(pointsUsed < maxAmount){
            return true;
        }
        else{
            return false;
        }
    }

    public void useInk(){
        pointsUsed++;
    }

    public void refillInk(int amount){
        pointsUsed -= amount;
    }



    public float getPercent(){
        return (float)pointsUsed/maxAmount * 100;
    }
}
