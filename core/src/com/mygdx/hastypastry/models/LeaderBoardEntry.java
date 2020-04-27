package com.mygdx.hastypastry.models;

/**
 * LeaderBoardEntry creates an entry with name and time to be used
 * in the leaderboard arraylist.
 * The entries are sorted based on time through implementing
 * the Comparable interface with the compareTo method.
 */

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
