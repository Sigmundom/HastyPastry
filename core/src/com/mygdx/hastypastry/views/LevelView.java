package com.mygdx.hastypastry.views;

import com.mygdx.hastypastry.controllers.FileReader;

import java.util.ArrayList;

public class LevelView {
    FileReader reader = new FileReader();
    ArrayList<String> levelData = new ArrayList<>();

    public void levelCreator(String level){
        levelData = reader.getFileData("levels.txt");
        for(String line : levelData){

        }
    }
}
