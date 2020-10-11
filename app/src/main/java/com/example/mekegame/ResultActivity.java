package com.example.mekegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    int hightScrore;
    ImageView medal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //      fullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView hightScoreLabel = (TextView) findViewById(R.id.hightScoreLabel);
        TextView gamePlayedLabel = (TextView) findViewById(R.id.gamePlayedLabel);
        medal =(ImageView)findViewById(R.id.medal);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText("" + score);


        if (score< 50){
            medal.setImageResource(R.drawable.bronze);
        }else if (score>=150){
            medal.setImageResource(R.drawable.gold);
        }else {
            medal.setImageResource(R.drawable.silver);
        }

        SharedPreferences preferences = getSharedPreferences("HIGHTSCORE", Context.MODE_PRIVATE);
        hightScrore = preferences.getInt("HIGHTSCORE", 0);
        if (score > hightScrore) {
            hightScoreLabel.setText("Height Score : " + score);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("HIGHTSCORE", score);
            editor.commit();
        } else
            hightScoreLabel.setText("Hight Score :" + hightScrore);


        SharedPreferences preferencesGames = getSharedPreferences("GAMES", Context.MODE_PRIVATE);
        int games = preferencesGames.getInt("GAMES", 0);
        gamePlayedLabel.setText("Games Played :" + (games + 1));
        SharedPreferences.Editor editor = preferencesGames.edit();
        editor.putInt("GAMES", (games + 1));
        editor.commit();

    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),StartActivity.class));
        finish();
    }



    // disable return button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }

        }
        return super.dispatchKeyEvent(event);
    }
}