package com.mygdx.hastypastry.models;

public class LeaderBoardEntry implements Comparable<LeaderBoardEntry> {
    String name;
    float time;

    public LeaderBoardEntry(String name, float time) {
        this.name = name;
        this.time = time;
    }

    public String getName() { return name; }

    public float getTime() { return time; }

    @Override
    public int compareTo(LeaderBoardEntry leaderBoardEntry) {
        return Float.compare(this.getTime(), leaderBoardEntry.getTime());
    }
}
