package com.mygdx.hastypastry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class HastyPastryGame extends Game {

	public HastyPastryGame(HastyPastryDatabase db) {
		super();
		db.checkVersion();
		DBManager.instance.init(db);
		MusicAndSound.instance.init();
	}

	@Override
	public void create () {
		Assets.instance.init(new AssetManager());
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
	}


	@Override
	public void dispose() {
		// Disposing AssetManager disposes everything. Easy =)
		Assets.instance.dispose();
	}
}