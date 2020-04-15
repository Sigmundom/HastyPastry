package com.mygdx.hastypastry.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


import java.util.ArrayList;



public class FileReader {

    //Creates an array with all lines in a local or internal text file
    public ArrayList<String> getLocalFileData(String file) {

        FileHandle handle = Gdx.files.local(file);

        ArrayList<String> fileData = new ArrayList<>();

        if (!handle.exists()) {
            FileHandle internalFile = Gdx.files.internal(file);
            internalFile.copyTo(handle);
        }

        String text = handle.readString();
        String[] split = text.split("\\r?+\\n");
        for (String line : split){
            fileData.add(line);
        }
        return fileData;
    }

    public ArrayList<String> getInternalFileData(String file){
        FileHandle handle = Gdx.files.internal(file);
        ArrayList<String> fileData = new ArrayList<>();
        String text = handle.readString();
        String[] split = text.split("\\r?+\\n");
        for (String line : split){
            fileData.add(line);
        }
        return fileData;

    }

    public void updateLocalFile(String file, String level, String line){
        ArrayList<String> fileStrings = new ArrayList<>();
        fileStrings = getLocalFileData(file);
        ArrayList<String> newFileStrings = new ArrayList<>();
        for (int i = 0; i<fileStrings.size(); i++){
            newFileStrings.add(fileStrings.get(i));
            if (fileStrings.get(i).equals(level)){
                i++;
                newFileStrings.add(line);
            }
        }
        FileHandle handle = Gdx.files.local(file);
        handle.delete();
        for(String newLine : newFileStrings){
            handle.writeString(newLine+"\n", false);
        }

    }

    public void rebootLocalFile(String file) {
        FileHandle handle = Gdx.files.local(file);
        if (!handle.exists()) {
            FileHandle fileSetup = Gdx.files.internal(file);
            fileSetup.copyTo(handle);
        }
        else {
            handle.delete();
            FileHandle fileSetup = Gdx.files.internal(file);
            fileSetup.copyTo(handle);
        }
    }

}
