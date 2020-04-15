package com.mygdx.hastypastry.singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String LOGTAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    //Singleton - prevents instantiation from other classes
    private Assets(){
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        //set asset manager error handler
        assetManager.setErrorListener(this);
        //load texture atlas
        load();
        //start loading assets and wait until finished
        assetManager.finishLoading();

        Gdx.app.debug(LOGTAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(LOGTAG, "asset: " + a);
        }
    }

    public AssetManager getManager() {
        return assetManager;
    }

    public static final AssetDescriptor<TextureAtlas> orangeUiAtlas =
            new AssetDescriptor<>("ui/orange/skin/uiskin.atlas", TextureAtlas.class);

    public static final AssetDescriptor<Skin> orangeUiSkin =
            new AssetDescriptor<>("ui/orange/skin/uiskin.json", Skin.class,
                    new SkinLoader.SkinParameter("ui/orange/skin/uiskin.atlas"));

    //The atlas, I renamed .txt to pack (just a habit).
    public static final AssetDescriptor<TextureAtlas> uiAtlas =
            new AssetDescriptor<>("ui/uiskin.atlas", TextureAtlas.class);

    //The skin
    public static final AssetDescriptor<Skin> uiSkin =
            new AssetDescriptor<>("ui/uiskin.json", Skin.class,
                    new SkinLoader.SkinParameter("ui/uiskin.atlas"));

    //Obstacles and waffle
    //Loads a text-file with reference to a collection of textures ("textures.png"), and
    //information about size and coordinates in collection.
    public static final AssetDescriptor<TextureAtlas> gameTextures =
            new AssetDescriptor<>("textures.pack", TextureAtlas.class);

    //Method for loading the assets into the manager
    private void load()
    {
        assetManager.load(orangeUiAtlas);
        assetManager.load(orangeUiSkin);
        assetManager.load(uiAtlas);
        assetManager.load(uiSkin);
        assetManager.load(gameTextures);
    }

    //Easy asset disposing, whenever you are done with it just dispose the manager instead of many files.
    @Override
    public void dispose()
    {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(LOGTAG, "Could not load asset '" + asset.fileName +"'", (Exception)throwable );
    }

    public void error(String filename, Class type, Throwable throwable) {
        Gdx.app.error(LOGTAG, "Could not load asset '" + filename +"'", (Exception)throwable );
    }
}
