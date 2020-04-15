package com.mygdx.hastypastry.enums;

import com.badlogic.gdx.audio.Music;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.views.BaseView;
import com.mygdx.hastypastry.views.CompletedLevelView;
import com.mygdx.hastypastry.views.CompletedMultiplayerView;
import com.mygdx.hastypastry.views.DrawView;
import com.mygdx.hastypastry.views.FailedLevelView;
import com.mygdx.hastypastry.views.HighScoreListView;
import com.mygdx.hastypastry.views.InvalidVersionView;
import com.mygdx.hastypastry.views.LevelSelectView;
import com.mygdx.hastypastry.views.LobbyView;
import com.mygdx.hastypastry.views.LoginView;
import com.mygdx.hastypastry.views.MainMenuView;
import com.mygdx.hastypastry.views.PlayView;
import com.mygdx.hastypastry.views.PreferenceView;


public enum ScreenEnum {

    MAIN_MENU {
        public BaseView getScreen(Object... params) {
            return new MainMenuView();
        }
    },
    PLAY {
        public BaseView getScreen(Object... params) { return new PlayView((Game)params[0]); }
    },
    LOBBY {
        public BaseView getScreen(Object... params) { return new LobbyView((Lobby) params[0]); }
    },
    DRAW {
        public BaseView getScreen(Object... params) { return new DrawView((Game)params[0]); }
    },
    COMPLETED_LEVEL {
        public BaseView getScreen(Object... params) { return new CompletedLevelView((Game)params[0]); }
    },
    FAILED_LEVEL {
        public BaseView getScreen(Object... params) { return new FailedLevelView((Game)params[0]); }
    },
    LOGIN {
        public BaseView getScreen(Object... params) { return new LoginView(); }
    },
    HIGHSCORE {
        public BaseView getScreen(Object... params) { return new HighScoreListView((Game)params[0]); }
    },
    LEVELSELECT {
        public BaseView getScreen(Object... params) {return new LevelSelectView(); }
    },
    PREFERENCES {
        public BaseView getScreen(Object... params) { return new PreferenceView();        }
    },
    COMPLETED_MULTIPLAYER {
        public BaseView getScreen(Object... params) { return new CompletedMultiplayerView((Game)params[0]); }
    },
    INVALID_VERSION {
        public BaseView getScreen(Object... params) { return new InvalidVersionView(); }
    };


    public abstract BaseView getScreen(Object... params);
}
