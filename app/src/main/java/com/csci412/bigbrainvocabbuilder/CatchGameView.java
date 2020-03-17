package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
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


    // Auto size text to fit given width (size)
    public void setTextSize(String text, Paint p, int size) {
        float testSize = 48f;
        p.setTextSize(testSize);
        Rect bounds = new Rect();
        p.getTextBounds(text, 0, text.length(), bounds);

        float newTextSize = testSize * size / bounds.width();
        if (newTextSize > 60) {
            newTextSize = 60;
        }
        p.setTextSize(newTextSize);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapePaint.setColor(getResources().getColor(R.color.gold));
        canvas.drawRect(0, 0, width, height, shapePaint);
        shapePaint.setColor(getResources().getColor(R.color.blueish));
        if (game.isGameOver()) {
            // Draw the end screen with user score and back button
            int score = game.getScore();

            setTextSize(String.valueOf(score), defPaint, width / 10);
            canvas.drawText("Score: " + score, width / 2 - width / 10, height/2 - width / 8, defPaint);

            setTextSize("Tap anywhere to continue", defPaint, width / 2);
            canvas.drawText("Tap anywhere to continue", width / 2 - width / 4, height/2, defPaint);
        } else {
            // Draw the game, with position of each word, the catcher, and definition
            String[] words = game.getWords();
            Point[] wordPos = game.getPositions();
            Rect catchRect = game.getCatcherRect();
            String definition = game.getDefinition();

            canvas.drawRect(catchRect, shapePaint);

            setTextSize(definition, defPaint, width - 100);
            Rect textRect = new Rect();
            defPaint.getTextBounds(definition, 0, definition.length(), textRect);
            if (textRect.width() > width - 100) {
                Log.i("Catch", "Definition too big");
                Log.i("Catch", "Definition size is " + defPaint.getTextSize());
            }
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
