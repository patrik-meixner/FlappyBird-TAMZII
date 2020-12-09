package com.example.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Floor {

    private Bitmap floorAlpha;
    private Bitmap floorBeta;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int outOfScreen;

    public Floor(Context context, int x, int y, int width) {
        Bitmap floor = null;

        try {
            floor = BitmapFactory.decodeStream(context.getAssets().open("floor.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (floor != null) {
            this.floorAlpha = Bitmap.createScaledBitmap(floor, width, 70, true);
            this.floorBeta = Bitmap.createScaledBitmap(floor, width + 6, 70, true);
        }
        this.x1 = x;
        this.y1 = y;
        this.x2 = width - 12;
        this.y2 = y;
        this.outOfScreen = width;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.floorAlpha, this.x1, this.y1, null);
        canvas.drawBitmap(this.floorBeta, this.x2, this.y2, null);
    }

    public void move() {
        if (this.x2 <= 0 && this.x1 <= -133) {
            this.x1 = this.outOfScreen - 12;
        }
        if (this.x1 <= 0 && this.x2 <= -133) {
            this.x2 = this.outOfScreen - 12;
        }
        this.x1 -= 8;
        this.x2 -= 8;
    }
}
