package com.csci412.bigbrainvocabbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Point;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class CatchGameActivity extends AppCompatActivity {

    private CatchGameView gameView;
    private CatchGame game;
    private int statusBarHeight = 0;
    private SoundPool soundPool;
    private int thunkSoundId;
    private int musicSoundId;

    private boolean musicStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        int statusBarId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0)
            statusBarHeight = res.getDimensionPixelSize(statusBarId);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new CatchGameView(this, size.x, size.y - statusBarHeight);
        game = gameView.getGame();
        setContentView(gameView);

        SoundPool.Builder poolBuilder = new SoundPool.Builder();
        poolBuilder.setMaxStreams(2);
        soundPool = poolBuilder.build();
        thunkSoundId = soundPool.load(this, R.raw.thunk, 1);
        musicSoundId = soundPool.load(this, R.raw.catch_music, 1);

        Timer gameTimer = new Timer();
        gameTimer.schedule(new CatchGameTimerTask(gameView), 0 , 33);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        game.moveInput((int)ev.getX());
        if (game.backInput((int)ev.getX(),(int)ev.getY() - statusBarHeight)) {
            Statistics stats = MainActivity.profile.getStats();
            stats.addGamesCompleted();
            MainActivity.profile.setPreferences(this);
            goBack();
        }
        return true;
    }

    public void goBack() {
        soundPool.autoPause();
        super.onBackPressed();
    }

    public void playThunkSound() {
        soundPool.play(thunkSoundId, 1, 1, 1, 0, 1);
    }

    public void tryPlayMusic() {
        if (!musicStarted) {
            int check = soundPool.play(musicSoundId, 1, 1, 1, -1, 1);
            if (check != 0) {
                musicStarted = true;
            }
        }
    }

}
