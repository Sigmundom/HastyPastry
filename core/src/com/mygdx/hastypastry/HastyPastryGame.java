package com.mygdx.hastypastry;

import com.badlogic.gdx.Game;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class HastyPastryGame extends Game {

	@Override
	public void create () {
		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);

	}
}