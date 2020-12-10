package com.example.flappybird.activities;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.flappybird.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_high_scrores);

        renderHighScores();

    }

    public List<Integer> loadScore() {
        try {
            FileInputStream fis = getBaseContext().openFileInput("score.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text;
            List<Integer> scoreList = new ArrayList<>();

            while ((text = br.readLine()) != null) {
                System.out.println(text);
                if (!text.equals("")) {
                    scoreList.add(Integer.valueOf(text));
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

    private void renderHighScores() {
        List<Integer> scoreArray = loadScore();
        Collections.reverse(scoreArray);

        int[] scores = {R.id.score_1, R.id.score_2, R.id.score_3, R.id.score_4, R.id.score_5};

        for (int i = 0; i < scoreArray.size(); i++) {
            TextView tv = findViewById(scores[i]);
            String text = "Patrik: " + scoreArray.get(i);
            tv.setText(text);
        }
    }

    public void openMenu(View view) {
        finish();
    }
}