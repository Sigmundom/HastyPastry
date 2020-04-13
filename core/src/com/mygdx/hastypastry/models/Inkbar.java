package com.mygdx.hastypastry.models;
/** The Inkbar will contain the amount of ink that'f left and how much we have drawn
 * @author sigmundhh */
public class Inkbar {
    private int maxAmount;
    private float inkLeft;

    public Inkbar(int maxAmount){
        this.maxAmount = maxAmount;
        inkLeft = maxAmount;
    }

    public boolean inkbarCheck(){
        return inkLeft > 0;
    }

    public void useInk(float inkRequired){
        inkLeft -= inkRequired;
    }

    public void refillInk(float amount){
        inkLeft += amount;
    }

    public float getInkLeft() {
        return inkLeft;
    }

    public void reset() {
        inkLeft = maxAmount;
    }
}
