package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.mygdx.hastypastry.singletons.Assets;

public class ComposedImage extends Stack {

    public ComposedImage(String imageName) {
        super();
        Image imageBackground = new Image(Assets.instance.getManager().get(Assets.orangeUiAtlas).findRegion("button-orange"));
        imageBackground.setSize(15, 15);
        Image image = new Image(Assets.instance.getManager().get(Assets.uiAtlas).findRegion(imageName));
        image.setSize(15,15);


        this.add(imageBackground);
        this.add(image);
    }
}
