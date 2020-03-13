package com.mygdx.hastypastry;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    //The asset manager
    private AssetManager manager = new AssetManager();

    public AssetManager getManager() {
        return manager;
    }

    //The atlas, I renamed .txt to pack (just a habit).
    public static final AssetDescriptor<TextureAtlas> uiAtlas =
            new AssetDescriptor<>("ui/uiskin.pack", TextureAtlas.class);

    //The skin
    public static final AssetDescriptor<Skin> uiSkin =
            new AssetDescriptor<>("ui/uiskin.json", Skin.class,
                    new SkinLoader.SkinParameter("ui/uiskin.pack"));

    //Obstacles and waffle
    //Loads a text-file with reference to a collection of textures ("textures.png"), and
    //information about size and coordinates in collection.
    public static final AssetDescriptor<TextureAtlas> gameTextures =
            new AssetDescriptor<>("textures.pack", TextureAtlas.class);

    //Method for loading the assets into the manager
    public void load()
    {
        manager.load(uiAtlas);
        manager.load(uiSkin);
        manager.load(gameTextures);
    }

    //Easy asset disposing, whenever you are done with it just dispose the manager instead of many files.
    public void dispose()
    {
        manager.dispose();
    }
}
