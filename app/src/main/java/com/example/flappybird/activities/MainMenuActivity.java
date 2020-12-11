package com.example.flappybird.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.flappybird.R;
import com.example.flappybird.application.MyApplication;

public class MainMenuActivity extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View view) {
        userName = ((MyApplication) getApplication()).getUserName();
        Intent gameIntent = new Intent(MainMenuActivity.this, GameActivity.class);
        MainMenuActivity.this.startActivity(gameIntent);
    }

    public void openHighScores(View view) {
        Intent highScoresIntent = new Intent(MainMenuActivity.this, HighScoresActivity.class);
        MainMenuActivity.this.startActivity(highScoresIntent);
    }

    public String getUserName() {
        return userName;
    }
}