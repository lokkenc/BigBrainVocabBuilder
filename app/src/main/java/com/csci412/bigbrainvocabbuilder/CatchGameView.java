package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class CatchGameView extends View {

    int width;
    int height;
    Paint shapePaint;
    Paint textPaint;
    Paint defPaint;
    CatchGame game;

    public CatchGameView(Context context, int width, int height) {
        super(context);
        this.height = height;
        this.width = width;
        game = new CatchGame(context, width, height);
        game.startWordPositions(3);

        shapePaint = new Paint();
        shapePaint.setColor(getResources().getColor(R.color.blueish));
        shapePaint.setAntiAlias(true);
        shapePaint.setStrokeWidth(10.0f);
        shapePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(48f);

        defPaint = new Paint();
        defPaint.setColor(getResources().getColor(R.color.blueish));
        defPaint.setAntiAlias(true);
        defPaint.setTextSize(48f);
    }

    public void setTextSize(String text, Paint p, int size) {
        float testSize = 48f;
        textPaint.setTextSize(testSize);
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        float newTextSize = testSize * width / 5 / bounds.width();
        if (newTextSize > 70) {
            newTextSize = 70;
        }
        textPaint.setTextSize(newTextSize);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (game.isGameOver()) {
            Rect backRect = game.getBackRect();
            Point scorePos = game.getScorePos();
            int score = game.getScore();
            canvas.drawRect(backRect, shapePaint);
            setTextSize("Back", textPaint, (int)(backRect.width() * 0.9f));
            textPaint.setColor(getResources().getColor(R.color.gold));
            canvas.drawText("Back", backRect.left, (backRect.bottom + backRect.top)/2, textPaint);

            setTextSize(String.valueOf(score), defPaint, width / 10);
            canvas.drawText("Score: " + score, scorePos.x, scorePos.y, defPaint);
        } else {
            String[] words = game.getWords();
            Point[] wordPos = game.getPositions();
            Rect catchRect = game.getCatcherRect();
            String definition = game.getDefinition();

            canvas.drawRect(catchRect, shapePaint);

            setTextSize(definition, defPaint, width - 100);
            canvas.drawText(definition, 50, height / 2, defPaint);

            for (int i = 0; i < words.length; i++) {
                Point pos = wordPos[i];
                setTextSize(words[i], textPaint, width / 5);
                canvas.drawText(words[i], pos.x, pos.y, textPaint);
            }
        }

    }

    public CatchGame getGame() {
        return game;
    }

}
