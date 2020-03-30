package com.mygdx.hastypastry.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files


public class FileReader {
    private ArrayList<String> fileData = new ArrayList<>();
    private String line;

    public ArrayList<String> getFileData(String file) {

        FileHandle handle = Gdx.files.local(file);

        handle.delete();
        if(!handle.exists())
        {
            FileHandle fileSetup = Gdx.files.internal(file);
            fileSetup.copyTo(handle);
        }

        String text = handle.readString();
        String[] split = text.split("\n");
        for (String line : split){
            fileData.add(line);
        }
        System.out.println(fileData);
        return fileData;
    }
}
