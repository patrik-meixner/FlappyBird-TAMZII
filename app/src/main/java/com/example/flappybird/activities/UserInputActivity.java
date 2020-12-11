package com.example.flappybird.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flappybird.R;
import com.example.flappybird.application.MyApplication;

public class UserInputActivity extends AppCompatActivity {
    private EditText userNameInput;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_input);

        userNameInput = findViewById(R.id.userNameInput);
        userNameInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    ((MyApplication) getApplication()).setUserName(userNameInput.getText().toString());
                    userName = ((MyApplication) getApplication()).getUserName();
                    Toast.makeText(UserInputActivity.this, "Hello " + userName, Toast.LENGTH_SHORT).show();

                    Intent mainMenuIntent = new Intent(UserInputActivity.this, MainMenuActivity.class);
                    UserInputActivity.this.startActivity(mainMenuIntent);

                    return true;
                }

                return false;
            }
        });
    }
}