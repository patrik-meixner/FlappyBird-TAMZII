package com.example.flappybird.activities;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flappybird.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_high_scrores);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        ConstraintLayout lLayout = (ConstraintLayout) findViewById(R.id.high_scores_layout);
        for (int i = 0; i < scoreArray.size(); i++) {
            TextView tv = new TextView(this);
            tv.setText(scoreArray.get(i));
            tv.setId(i);
            lLayout.addView(tv);
        }
    }

    public void openMenu(View view) {
        finish();
    }
}