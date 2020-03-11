package com.mygdx.hastypastry.enums;

import com.mygdx.hastypastry.views.BaseView;
import com.mygdx.hastypastry.views.DrawView;
import com.mygdx.hastypastry.views.LobbyView;
import com.mygdx.hastypastry.views.MainMenuView;
import com.mygdx.hastypastry.views.PlayView;


public enum ScreenEnum {

    MAIN_MENU {
        public BaseView getScreen(Object... params) {
            return new MainMenuView();
        }
    },
    DRAW {
        public BaseView getScreen(Object... params) {
            return new DrawView();
        }
    },
    PLAY {
        public BaseView getScreen(Object... params) {
            return new PlayView();
        }
    },
    LOBBY {
        public BaseView getScreen(Object... params) {
            return new LobbyView();
        }
    }
    ;

    public abstract BaseView getScreen(Object... params);
}
