package com.mygdx.hastypastry;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    //The asset manager
    public static AssetManager manager = new AssetManager();
    //The atlas, I renamed .txt to pack (just a habit).
    public static final AssetDescriptor<TextureAtlas> uiAtlas =
            new AssetDescriptor<TextureAtlas>("ui/uiskin.pack", TextureAtlas.class);
    //The skin
    public static final AssetDescriptor<Skin> uiSkin =
            new AssetDescriptor<Skin>("ui/uiskin.json", Skin.class,
                    new SkinLoader.SkinParameter("ui/uiskin.pack"));

    //Method for loading the assets into the manager
    public static void load()
    {
        manager.load(uiAtlas);
        manager.load(uiSkin);
    }

    //Easy asset disposing, whenever you are done with it just dispose the manager instead of many files.
    public void dispose()
    {
        manager.dispose();
    }
}
