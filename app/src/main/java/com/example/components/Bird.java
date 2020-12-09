package com.example.components;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.flappybird.R;

public class Bird {
    private Bitmap birdWingsUp;
    private Bitmap birdWingsDown;
    private Bitmap birdDead;

    private boolean birdRendered;

    private int x;
    private int y;

    private boolean climb;
    private int climbing;
    private boolean dead;
    private int wingFlap;

    public Bird(Resources res, int x, int y) {
        this.birdWingsDown = BitmapFactory.decodeResource(res, R.drawable.wing_down);
        this.birdWingsUp = BitmapFactory.decodeResource(res, R.drawable.wing_up);
        this.birdDead = BitmapFactory.decodeResource(res, R.drawable.dead_bird);

        this.x = x;
        this.y = y;

        this.dead = false;
        this.climb = false;
        this.climbing = 0;
        this.wingFlap = 0;
        this.birdRendered = true;
    }

    public void draw(Canvas canvas) {
        if (!this.dead) {
            if (this.birdRendered) {
                canvas.drawBitmap(this.birdWingsUp, this.x, this.y, null);
            } else {
                canvas.drawBitmap(this.birdWingsDown, this.x, this.y, null);
            }
            wingFlap++;
            if (wingFlap % 4 == 0) {
                this.birdRendered = !this.birdRendered;
            }
        } else {
            canvas.drawBitmap(this.birdDead, this.x, this.y, null);
        }

    }

    public void fly() {
        if (this.y >= 854) {
            this.climb = true;
        }
        if (this.y <= 654 && this.climb) {
            this.climb = false;
        }
        if (!this.climb) {
            this.y += 8;
        } else {
            this.y -= 8;
        }
    }

    public void fall(int speed) {
        this.y += speed;
    }

    public void climb() {
        if (this.climbing == 4) {
            this.climbing = 0;
        } else {
            this.climbing += 1;
            this.y -= 25;
        }
    }

    public int getClimbing() {
        return this.climbing;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return this.birdWingsDown.getHeight();
    }

    public void toggleLife() {
        this.dead = !this.dead;
    }

}
