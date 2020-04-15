package com.mygdx.hastypastry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.levels.LevelData;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.ScreenManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HastyPastryGame extends Game {

	public HastyPastryGame(HastyPastryDatabase db) {
		super();
		db.checkVersion();
		DBManager.instance.init(db);
	}

	@Override
	public void create () {
		Assets.instance.init(new AssetManager());
		MusicAndSound.instance.init();
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);

		uploadAllLevelsToFB();
	}


	@Override
	public void dispose() {
		// Disposing AssetManager disposes everything. Easy =)
		Assets.instance.dispose();
	}

	private void uploadAllLevelsToFB() {
		List<LevelData> levels = new ArrayList<>();
		for (int i=1; i<=18; i++) {
			Level level = new Level("Level "+i);
			levels.add(level.getLevelData());
		}
		DBManager.instance.getDB().uploadLevels(levels);
	}
}