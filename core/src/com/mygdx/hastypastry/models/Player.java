package com.mygdx.hastypastry.models;

public class Player {
    public final String name;
    public final Drawing drawing;
//    private InkBar inkBar;

    public Player(String name) {
        this.name = name;
        drawing = new Drawing();
    }
}
