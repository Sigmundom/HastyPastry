package com.mygdx.hastypastry.enums;

import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.views.BaseView;
import com.mygdx.hastypastry.views.LobbyView;
import com.mygdx.hastypastry.views.MainMenuView;
import com.mygdx.hastypastry.views.GameView;


public enum ScreenEnum {

    MAIN_MENU {
        public BaseView getScreen(Assets assets, Object... params) {
            return new MainMenuView(assets);
        }
    },
    PLAY {
        public BaseView getScreen(Assets assets, Object... params) {
            return new GameView(assets);
        }
    },
    LOBBY {
        public BaseView getScreen(Assets assets, Object... params) {
            return new LobbyView(assets);
        }
    }
    ;

    public abstract BaseView getScreen(Assets assets, Object... params);
}
