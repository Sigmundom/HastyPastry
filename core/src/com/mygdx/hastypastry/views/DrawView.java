package com.mygdx.hastypastry.views;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.DrawingInputProcessor;

public class DrawView extends BaseView {

    public DrawView(Assets assets) {
        super(assets, new DrawingInputProcessor());
    }

    @Override
    public void buildStage() {
    }
}
