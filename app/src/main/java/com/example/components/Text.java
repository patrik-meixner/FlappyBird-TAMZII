package com.example.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;

import com.example.flappybird.R;

public class Text {

    private String text;
    private Paint strokePaint;
    private Paint fillPaint;
    private int x, y;

    public Text(Context context, int x, int y, String text) {
        setCommonTextProperties(context, x, y, text);
        this.strokePaint.setTextSize(130);
        this.fillPaint.setTextSize(130);
    }

    public Text(Context context, int x, int y, String text, int size) {
        setCommonTextProperties(context, x, y, text);
        this.strokePaint.setTextSize(size);
        this.fillPaint.setTextSize(size);
    }

    public void setCommonTextProperties(Context context, int x, int y, String text) {
        Typeface font = ResourcesCompat.getFont(context, R.font.flappybird);
        this.strokePaint = new Paint();
        this.fillPaint = new Paint();

        this.x = x;
        this.y = y;

        this.text = text;

        this.strokePaint.setStyle(Paint.Style.STROKE);
        this.strokePaint.setStrokeWidth(15);
        this.strokePaint.setTypeface(font);
        this.strokePaint.setARGB(255, 0, 0, 0);

        this.fillPaint.setStyle(Paint.Style.FILL);
        this.fillPaint.setTypeface(font);
        this.fillPaint.setARGB(255, 255, 255, 255);
        this.fillPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void draw(Canvas canvas) {
        canvas.drawText(this.text, this.x, this.y, this.strokePaint);
        canvas.drawText(this.text, this.x, this.y, this.fillPaint);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
