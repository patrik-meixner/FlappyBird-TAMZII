package com.example.flappybird.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.components.Bird;
import com.example.components.Floor;
import com.example.components.Pipe;
import com.example.components.Score;
import com.example.components.Text;
import com.example.components.User;
import com.example.flappybird.activities.GameActivity;
import com.example.flappybird.activities.UserInputActivity;
import com.example.flappybird.application.MyApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static android.content.Context.MODE_PRIVATE;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread thread;
    private final Context context;

    private final Pipe[] pipeList = new Pipe[4];
    private Bird bird;
    private Floor floor;

    private Text gameOverText;
    private Text restartText;
    private Text bestScoreText;

    private Bitmap background;
    private Queue<Integer> pipeIndexQueue;
    private Score score;
    private final String userName;

    private boolean start = false;
    private boolean tap = false;
    private boolean end = false;
    private boolean initComponents = false;

    private int gravity;
    private int bestScore;

    public Game(Context context) {
        super(context);
        this.context = context;
        userName = ((MyApplication) context.getApplicationContext()).getUserName();
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void renderGameOver() {
        this.end = true;

        this.gameOverText.setX(getWidth() / 4);
        this.gameOverText.setY(getHeight() / 3);
        this.gameOverText.setText("Game Over");

        this.bird.toggleLife();

        if (this.score.getScore() > this.bestScore) {
            this.bestScore = this.score.getScore();
        }

        this.bestScoreText.setText("Best: " + this.bestScore);

        try {
            List<User> scoreList = loadScore();
            FileOutputStream fos = context.openFileOutput("score.csv", MODE_PRIVATE);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);
            Collections.sort(scoreList);


            if (scoreList.size() < 5) {
                scoreList.add(new User(userName, this.score.getScore()));
                Collections.sort(scoreList);

                for (int i = 0; i < scoreList.size(); i++) {
                    User user = scoreList.get(i);

                    myOutWriter.append(user.getName()).append(",").append(String.valueOf(user.getScore())).append("\n");
                }
            } else {
                User lowestScore = Collections.min(scoreList);

                if (lowestScore.getScore() < this.score.getScore()) {
                    scoreList.set(scoreList.indexOf(lowestScore), new User(userName, this.score.getScore()));
                    Collections.sort(scoreList);
                }

                for (int i = 0; i < scoreList.size(); i++) {
                    User user = scoreList.get(i);
                    myOutWriter.append(user.getName()).append(",").append(String.valueOf(user.getScore())).append("\n");
                }
            }

            myOutWriter.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> loadScore() {
        try {
            FileInputStream fis = context.openFileInput("score.csv");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text;
            List<User> scoreList = new ArrayList<>();

            while ((text = br.readLine()) != null) {
                if (!text.equals("")) {
                    User user = new User(text.split(",")[0], Integer.valueOf(text.split(",")[1]));
                    scoreList.add(user);
                }
            }

            return scoreList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void update() {
        if (!this.end) {
            if (this.start) {

                for (Pipe pipe : this.pipeList) {
                    pipe.move();
                }

                this.updatePipeQueue();

                if (this.checkCollision() || this.bird.getY() >= getHeight() - 150) {
                    renderGameOver();
                }
            }

            if (!this.start) {
                this.bird.fly();
            } else if (!this.tap && !this.end) {
                this.bird.fall(this.gravity);
                this.gravity += 4;
            } else {
                this.bird.climb();
                if (this.bird.getClimbing() == 0) {
                    this.tap = false;
                }
            }

            this.floor.move();
        }

    }

    public void drawCanvas(Canvas canvas) throws IOException {
        canvas.drawBitmap(this.background, 0, 0, null);
        this.floor.draw(canvas);

        if (!this.start) {
            this.gameOverText.draw(canvas);
        } else {
            for (Pipe p : this.pipeList) {
                p.draw(canvas);
            }
        }

        this.bird.draw(canvas);
        this.score.draw(canvas, context);

        if (this.end) {
            this.gameOverText.draw(canvas);
            this.restartText.draw(canvas);
            this.bestScoreText.draw(canvas);
            this.score.makeInvisible(canvas);

            if (this.bird.getY() < getHeight() - 150) {
                this.bird.fall(this.gravity);
                this.gravity += 5;
            }
        }
    }

    private void updatePipeQueue() {
        int frontPipe = this.pipeIndexQueue.peek();

        if (this.pipeList[frontPipe].getX() <= -10) {
            frontPipe = this.pipeIndexQueue.remove();
            this.pipeIndexQueue.add(frontPipe);
            this.score.increase();
        }
    }

    private boolean checkCollision() {
        int frontPipe = this.pipeIndexQueue.peek();
        Pipe pipe = this.pipeList[frontPipe];
        frontPipe = pipe.getX();

        if (frontPipe >= 0 && frontPipe <= 150) {
            if (this.bird.getY() + this.bird.getHeight() >= pipe.getPipeDownY()) {
                return true;
            } else return this.bird.getY() <= pipe.getPipeUpperY();

        }
        return false;
    }


    public boolean getInitComponents() {
        return this.initComponents;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initComponents() {
        try {
            Bitmap backgroundBuffer = BitmapFactory.decodeStream(this.context.getAssets().open("background.jpg"));

            float backgroundScale = (float) backgroundBuffer.getHeight() / (float) getHeight();
            int newWidth = Math.round(backgroundBuffer.getWidth() / backgroundScale);
            int newHeight = Math.round(backgroundBuffer.getHeight() / backgroundScale);

            this.background = Bitmap.createScaledBitmap(backgroundBuffer, newWidth + 15, newHeight, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.pipeIndexQueue = new LinkedList<>();
        int x = getWidth();

        for (int i = 0; i < this.pipeList.length; i++) {
            this.pipeIndexQueue.add(i);
            this.pipeList[i] = new Pipe(getResources(), x, getWidth(), getHeight());
            x += 400;
        }

        this.score = new Score(getContext(), getWidth() / 2 - 30, getWidth() / 2 + 30, getHeight() / 5);
        this.bird = new Bird(getResources(), 55, (getHeight() / 2) - 150);
        this.floor = new Floor(this.context, 0, getHeight() - 50, getWidth());

        this.gameOverText = new Text(context, getWidth() / 5, getHeight() / 3, "Tap to start", 160);
        this.restartText = new Text(context, getWidth() / 6, (getHeight() / 3) + 150, "Tap to Restart", 160);
        this.bestScoreText = new Text(context, getWidth() / 3, getHeight() / 3 + 300, "Best: ");
        this.initComponents = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent tap) {
        if (!this.end && tap.getActionMasked() == MotionEvent.ACTION_DOWN) {
            this.tap = true;
            this.gravity = 0;
        }

        if (!this.start) {
            this.start = true;
        }

        if (this.end && this.bird.getY() >= getHeight() - 150) {
            this.pipeIndexQueue.clear();
            this.start = true;
            this.end = false;
            this.gravity = 0;

            this.bird.toggleLife();
            this.bird.setY((getHeight() / 2) - 150);

            int position = getWidth();
            this.score.reset();

            for (int i = 0; i < this.pipeList.length; i++) {
                this.pipeList[i].setX(position);
                this.pipeList[i].setOpening();
                this.pipeIndexQueue.add(i);
                position += 400;
            }
        }
        return true;
    }

}
