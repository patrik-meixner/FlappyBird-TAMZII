package com.example.components;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.flappybird.R;

import java.util.concurrent.ThreadLocalRandom;

public class Pipe {
    private Bitmap pipeUp;
    private Bitmap pipeDown;

    private int opening;
    private int x;
    private int y;
    private int resetPos;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Pipe(Resources res, int x, int screenWidth, int screenHeight) {
        this.x = x;
        this.y = screenHeight - 50;
        this.resetPos = screenWidth;
        this.opening = this.generateRandom(300, 600);
        this.pipeUp = BitmapFactory.decodeResource(res, R.drawable.pipe_up);

        int type = generateRandom(0, 4);

//        this.pipeDown = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, R.drawable.pipe_down_thick_tall), 90, generateRandom(100, 800), true);

        if (type == 1) {
            this.pipeDown = BitmapFactory.decodeResource(res, R.drawable.pipe_down_thick_tall);
        } else if (type == 2) {
            this.pipeDown = BitmapFactory.decodeResource(res, R.drawable.pipe_down_slim_small);
        } else if (type == 3) {
            this.pipeDown = BitmapFactory.decodeResource(res, R.drawable.pipe_down_slim_tall);
        } else {
            this.pipeDown = BitmapFactory.decodeResource(res, R.drawable.pipe_down_thick_small);
        }

        this.y -= this.pipeDown.getHeight();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.pipeDown, this.x, this.y, null);
        canvas.drawBitmap(this.pipeUp, this.x, this.y - this.opening - this.pipeUp.getHeight(), null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void move() {
        if (this.x <= -400) {
            this.x = this.resetPos;
            this.opening -= generateRandom(50, 100);
        }
        this.x -= 10;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int generateRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getPipeDownY() {
        return this.y;
    }

    public int getPipeUpperY() {
        return this.y - this.opening;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setOpening() {
        this.opening = this.generateRandom(300, 600);
    }

}
