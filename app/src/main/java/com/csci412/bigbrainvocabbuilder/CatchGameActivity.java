package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;

import java.util.Timer;

public class CatchGameActivity extends AppCompatActivity {

    private CatchGameView gameView;
    private CatchGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        int statusBarHeight = 0;
        int statusBarId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0)
            statusBarHeight = res.getDimensionPixelSize(statusBarId);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new CatchGameView(this, size.x, size.y - statusBarHeight);
        game = gameView.getGame();
        setContentView(gameView);

        Timer gameTimer = new Timer();
        gameTimer.schedule(new CatchGameTimerTask(gameView), 0 , 100);
    }
}
