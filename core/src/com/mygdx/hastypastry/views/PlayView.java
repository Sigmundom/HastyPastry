package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.listeners.MyContactListener;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

import java.text.DecimalFormat;
import java.util.List;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class PlayView extends BaseView {
    protected Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();;
    protected ShapeRenderer shapeRenderer = new ShapeRenderer();
    protected World world;
    protected MenuButton menuButton;
    protected BitmapFont font;
    protected double elapsedTime = 0.0;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private Table table;
    protected Label timeLabel;
    protected Game game;

    public PlayView(Game game) {
        super();
        world = new World(new Vector2(0, -9.81f), false);
        world.setContactListener(new MyContactListener());
        this.game = game;
        game.initPlayView(world);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(); // world step and update positions.

        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
//        know what it need (position and size).
        for (WorldObject object: game.getWorldObjects()){
            object.getSprite().draw(batch);
        }

        // Implementing font generator from BaseView.
        elapsedTime += (double)delta;
        //timeLabel.setText(df.format(elapsedTime));
        //timeLabel.setText(String.format("%6s", df.format(elapsedTime)).replace(' ', '0'));
        timeLabel.setText(df.format(elapsedTime));

        batch.end();


        // Renders the shape of the bodies. Remove in production.
        debugRenderer.render(world, batch.getProjectionMatrix());

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLACK);
        // Renders players drawing
        for (List<Vector2> line: game.getFinalLines()) {
            for(int i = 0; i < line.size()-1; ++i) {
                shapeRenderer.line(line.get(i), line.get(i+1));
            }
        }
        shapeRenderer.end();
    }

    protected void update() {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            game.update();
    }

    @Override
    public void buildStage() {
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(10, Config.UI_HEIGHT - menuButton.getHeight() - 10);

        font = generateFont("pixelfont.TTF", 24);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        timeLabel = new Label("Time", labelStyle);
        table = new Table();
        table.top().padLeft(Config.UI_WIDTH/2 - 40).left().padTop(10);
        table.setFillParent(true);
        table.add(timeLabel);

        this.ui.addActor(table);

        this.ui.addActor(menuButton);
    }

}
