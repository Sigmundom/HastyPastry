package com.mygdx.hastypastry.models;
/** The Inkbar will contain the amount of ink that'f left and how much we have drawn
 * @author sigmundhh */
public class Inkbar {
    private int maxAmount;
    private float inkLeft;

    Inkbar(int maxAmount){
        this.maxAmount = maxAmount;
        inkLeft = maxAmount;
    }

    boolean inkbarCheck(){
        return inkLeft > 0;
    }

    void useInk(float amount){
        inkLeft -= amount;
    }

    void refillInk(float amount){
        inkLeft += amount;
    }

    public float getInkLeft() {
        return inkLeft;
    }

    void reset() {
        inkLeft = maxAmount;
    }
}
