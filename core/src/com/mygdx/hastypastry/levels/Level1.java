package com.mygdx.hastypastry.levels;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;

public class Level1 extends Level {
    public Level1(){
        super();
        waffle = new Waffle(Config.WORLD_WIDTH/2, Config.WORLD_HEIGHT - 2);
        obstacles.add(new RoundObstacle(Config.WORLD_WIDTH/2, 2, 2, false));
        obstacles.add(new SquareObstacle(3, 8, 2, 4, true));
        obstacles.add(new TriangularObstacle(10,10,6,3, false));
    }
}
