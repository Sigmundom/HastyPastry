package com.mygdx.hastypastry.enums;

import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Drawing;
import com.mygdx.hastypastry.models.GameInfo;
import com.mygdx.hastypastry.views.BaseView;
import com.mygdx.hastypastry.views.DrawView;
import com.mygdx.hastypastry.views.LobbyView;
import com.mygdx.hastypastry.views.MainMenuView;
import com.mygdx.hastypastry.views.SingleplayerView;


public enum ScreenEnum {

    MAIN_MENU {
        public BaseView getScreen(Assets assets, Object... params) {
            return new MainMenuView(assets);
        }
    },
    PLAY {
        public BaseView getScreen(Assets assets, Object... params) {
            return new SingleplayerView(assets, (Level)params[0], (Drawing)params[1]);
        }
    },
    LOBBY {
        public BaseView getScreen(Assets assets, Object... params) {
            return new LobbyView(assets, (String)params[0]);
        }
    },
    DRAW {
        public BaseView getScreen(Assets assets, Object... params) {
            return new DrawView(assets, params);
        }
    };

    public abstract BaseView getScreen(Assets assets, Object... params);
}
