package com.mygdx.hastypastry.enums;

import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.views.BaseView;
import com.mygdx.hastypastry.views.CompletedLevelView;
import com.mygdx.hastypastry.views.DrawView;
import com.mygdx.hastypastry.views.FailedLevelView;
import com.mygdx.hastypastry.views.HighScoreListView;
import com.mygdx.hastypastry.views.LevelSelectView;
import com.mygdx.hastypastry.views.LobbyView;
import com.mygdx.hastypastry.views.MainMenuView;
import com.mygdx.hastypastry.views.PlayView;


public enum ScreenEnum {

    MAIN_MENU {
        public BaseView getScreen(Object... params) {
            return new MainMenuView();
        }
    },
    PLAY {
        public BaseView getScreen(Object... params) {
            return new PlayView((Game)params[0]);
        }
    },
    LOBBY {
        public BaseView getScreen(Object... params) {
            return new LobbyView((String)params[0]);
        }
    },
    DRAW {
        public BaseView getScreen(Object... params) {
            return new DrawView((Game)params[0]);
        }
    },
    COMPLETED_LEVEL {
        public BaseView getScreen(Object... params) { return new CompletedLevelView((Game)params[0]); }
    },
    FAILED_lEVEL {
        public BaseView getScreen(Object... params) { return new FailedLevelView(); }
    },
    HIGHSCORE {
        public BaseView getScreen(Object... params) { return new HighScoreListView((Game)params[0]); }
    },
    LEVELSELECT {
        public BaseView getScreen(Object... params) {return new LevelSelectView(); }
    };

    public abstract BaseView getScreen(Object... params);
}
