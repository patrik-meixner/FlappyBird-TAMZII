package com.example.flappybird.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.flappybird.R;
import com.example.flappybird.application.MyApplication;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        String sharedPrefsUserName = sharedPreferences.getString("userName", "");

        if (sharedPrefsUserName.isEmpty()) {
            String userName = ((MyApplication) this.getApplicationContext().getApplicationContext()).getUserName();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userName", userName);
            editor.apply();
        }

        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View view) {
        Intent gameIntent = new Intent(MainMenuActivity.this, GameActivity.class);
        MainMenuActivity.this.startActivity(gameIntent);
    }

    public void openHighScores(View view) {
        Intent highScoresIntent = new Intent(MainMenuActivity.this, HighScoresActivity.class);
        MainMenuActivity.this.startActivity(highScoresIntent);
    }
}