package com.example.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import com.example.flappybird.R;

import java.io.IOException;

public class Score {
    private final Bitmap[] numbers;
    private int score;
    private final int x1;
    private final int x2;
    private final int y;

    public Score(Context context, int x1, int x2, int y) {
        this.numbers = new Bitmap[10];
        this.score = 0;
        this.x1 = x1;
        this.x2 = x2;
        this.y = y;

        for (int i = 0; i < 10; i++) {
            try {
                this.numbers[i] = BitmapFactory.decodeStream(context.getAssets().open("numbers/" + i + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Canvas canvas, Context context) throws IOException {
        if (this.score == -1) {
            canvas.drawBitmap(BitmapFactory.decodeStream(context.getAssets().open("numbers/blank.png")), this.x1, this.y, null);
        } else {
            if (this.score >= 10) {
                String scoreString = Integer.toString(this.score);

                int i0 = Character.getNumericValue(scoreString.charAt(0));
                int i1 = Character.getNumericValue(scoreString.charAt(1));

                canvas.drawBitmap(this.numbers[i0], this.x1, this.y, null);
                canvas.drawBitmap(this.numbers[i1], this.x2, this.y, null);
            } else {
                canvas.drawBitmap(this.numbers[this.score], this.x1, this.y, null);
            }
        }
    }

    public void makeInvisible(Canvas canvas) {
        this.score = -1;
    }

    public void reset() {
        this.score = 0;
    }


    public void increase(Context context) {
        final MediaPlayer mp = MediaPlayer.create(context, R.raw.point);
        mp.start();
        this.score++;
    }

    public int getScore() {
        return this.score;
    }
}
