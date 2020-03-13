package com.mygdx.hastypastry.levels;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.Assets;
import com.badlogic.gdx.physics.box2d.World;

public class Level1 extends Level {
    public Level1(Assets assets, World world){
        super();
        waffle = new Waffle(assets, world, Config.WORLD_WIDTH/2, Config.WORLD_HEIGHT - 2);
        obstacles.add(new RoundObstacle(assets, world, Config.WORLD_WIDTH/2, 2, 2, false));
        obstacles.add(new SquareObstacle(assets, world, 3, 8, 2, 4, true));
        obstacles.add(new TriangularObstacle(assets, world, 10,10,6,3, false));
    }
}
