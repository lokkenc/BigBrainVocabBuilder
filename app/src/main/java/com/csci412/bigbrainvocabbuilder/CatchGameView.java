package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class CatchGameView extends View {

    int width;
    int height;
    Paint shapePaint;
    Paint textPaint;
    CatchGame game;

    public CatchGameView(Context context, int width, int height) {
        super(context);
        this.height = height;
        this.width = width;
        game = new CatchGame(context);

        shapePaint = new Paint();
        shapePaint.setColor(0xFF000000);
        shapePaint.setAntiAlias(true);
        shapePaint.setStrokeWidth(10.0f);

        textPaint = new Paint();
        textPaint.setColor(0xFF000000);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(48f);
    }

    public void setTextSize(String text) {
        float testSize = 48f;
        textPaint.setTextSize(testSize);
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        float newTextSize = testSize * width / 5 / bounds.width();
        textPaint.setTextSize(newTextSize);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setTextSize("Hello");
        canvas.drawText("Hello", width/2, height/2, textPaint);
        setTextSize("World");
        canvas.drawText("World", width/2, height/2 + 100, textPaint);
    }

    // Redraw the view
    public void postInvalidate() {

    }

    public CatchGame getGame() {
        return game;
    }

}
