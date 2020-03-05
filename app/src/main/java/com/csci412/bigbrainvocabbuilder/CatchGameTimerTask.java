package com.csci412.bigbrainvocabbuilder;

import java.util.TimerTask;

class CatchGameTimerTask extends TimerTask {

    private CatchGame game;
    private CatchGameView gameView;

    public CatchGameTimerTask(CatchGameView gameView) {
        this.gameView = gameView;
        game = gameView.getGame();
    }

    public void run() {
        game.moveWordsDown();
        gameView.postInvalidate();
    }
}
