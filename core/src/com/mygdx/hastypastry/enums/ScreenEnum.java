package com.mygdx.hastypastry.enums;

import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.views.BaseView;
import com.mygdx.hastypastry.views.DrawView;
import com.mygdx.hastypastry.views.LobbyView;
import com.mygdx.hastypastry.views.MainMenuView;
import com.mygdx.hastypastry.views.PlayView;


public enum ScreenEnum {

    MAIN_MENU {
        public BaseView getScreen(Assets assets, Object... params) {
            return new MainMenuView(assets);
        }
    },
    DRAW {
        public BaseView getScreen(Assets assets, Object... params) {
            return new DrawView(assets);
        }
    },
    PLAY {
        public BaseView getScreen(Assets assets, Object... params) {
            return new PlayView(assets);
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
