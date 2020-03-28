package com.mygdx.hastypastry.levels;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.models.Goal;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;

import static com.mygdx.hastypastry.Config.WORLD_HEIGHT;

public class Level1 extends Level {
    public Level1(){
        super();
        waffle = new Waffle(Config.WORLD_WIDTH/2, WORLD_HEIGHT - 2);
        goal = new Goal(2,WORLD_HEIGHT - 20, 3,3);
        obstacles.add(new RoundObstacle(Config.WORLD_WIDTH/2, 2, 2, false));
        obstacles.add(new SquareObstacle(3, 8, 2, 4, true));
        obstacles.add(new TriangularObstacle(10,10,6,3, false));

        // Starvalues based on seconds from start to goal.
        starValues.add(3.0f);
        starValues.add(4.0f);
        starValues.add(5.0f);
    }
}
