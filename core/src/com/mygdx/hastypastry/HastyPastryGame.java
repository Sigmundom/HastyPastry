package com.mygdx.hastypastry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class HastyPastryGame extends Game {
	@Override
	public void create () {
		Assets.instance.init(new AssetManager());
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
	}

//	@Override
//	public void resume() {
//		//Adding this is VITAL to prevent android application crashing on onResume().  When context is lost
//		// the memory is cleared and references to assets lost.  Unless this is added a crash will occur as
//		//there are no assets in memory
//		Assets.instance.init(new AssetManager());
//	}

	@Override
	public void dispose() {
		// Disposing AssetManager disposes everything. Easy =)
		Assets.instance.dispose();
	}
}