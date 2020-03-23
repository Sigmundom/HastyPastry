package com.mygdx.hastypastry.models;

public class Player {
    private String name;
    private Drawing drawing;
    private Waffle waffle;

//    private InkBar inkBar;

    public Player(String name, Waffle waffle) {
        this.name = name;
        this.waffle = waffle;
        drawing = new Drawing();
    }

    public Player(Waffle waffle) {
        this.name = "Player 1";
        this.waffle = waffle;
        drawing = new Drawing();
    }

    public Waffle getWaffle() {
        return waffle;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public String getName() {
        return name;
    }
}
