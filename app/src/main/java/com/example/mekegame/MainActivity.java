package com.example.mekegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView scoreLabel;
    TextView startLabel;
    ImageView player, food, diamond, enemy1, enemy2, pauseLb;
    ImageButton pause, startLb;
    FrameLayout frameLb;

    TextView tv_Coins;

    int frameHeight;
    int playerSize;
    int screenWidth;
    int screenHeight;

    //     postion
    private int playerY;
    private int foodY;
    private int foodX;
    private int diamondY;
    private int diamondX;
    private int enemy1Y;
    private int enemy2Y;
    private int enemy1X;
    private int enemy2X;


    private SoundEffects sound;

    int playerSpeed;
    int foodSpeed;
    int diamondSpeed;
    int enemy1Speed;
    int enemy2Speed;

    private int score = 0;
    private Handler handler = new Handler();
    private Timer timer = new Timer();;


    // Status
    private boolean action_flag = false;
    private boolean start_flag = false;
    private boolean pause_flag = false;

    int action;
    int coins = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);


        action = settings.getInt("ACTION", 1);
        coins = settings.getInt("COINS", 0);   // for test

        //      fullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sound = new SoundEffects(this);

        scoreLabel = (TextView) findViewById(R.id.score_lb);
        startLabel = (TextView) findViewById(R.id.startLb);
        player = (ImageView) findViewById(R.id.player);
        food = (ImageView) findViewById(R.id.food);
        diamond = (ImageView) findViewById(R.id.diamond);
        enemy1 = (ImageView) findViewById(R.id.enemy1);
        enemy2 = (ImageView) findViewById(R.id.enemy2);

        tv_Coins = findViewById(R.id.tv_coins);

        player.setImageResource(getResources().getIdentifier("player" + action + "a", "drawable", getPackageName()));


        pauseLb = (ImageButton) findViewById(R.id.pause_lb);

        startLb = (ImageButton) findViewById(R.id.start_lp);

        frameLb = (FrameLayout) findViewById(R.id.frame_lp);



        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        pauseLb.setEnabled(false);
        frameLb.setVisibility(View.GONE);
        screenWidth = size.x;
        screenHeight = size.y;


        playerSpeed = Math.round(screenWidth / 59);
        foodSpeed = Math.round(screenWidth / 59);
        diamondSpeed = Math.round(screenWidth / 35);
        enemy1Speed = Math.round(screenWidth / 44);
        enemy2Speed = Math.round(screenWidth / 39);


        food.setX(-80f);
        food.setY(-80f);
        diamond.setX(-80f);
        diamond.setY(-80f);
        enemy1.setX(-80f);
        enemy1.setY(-80f);
        enemy2.setX(-80f);
        enemy2.setY(-80f);
        scoreLabel.setText("Score: 0");
        tv_Coins.setText(""+coins);

    }

    public void position() {
        hint();


        //food positin
        foodX -= foodSpeed;
        if (foodX < 0){
            foodX = screenWidth + 20;
            foodY =(int) Math.floor(Math.random() * (frameHeight - food.getHeight()));
        }
        food.setX(foodX);
        food.setY(foodY);



        //enemy1
        enemy1X -= enemy1Speed;
        if (enemy1X < 0){
            enemy1X = screenWidth + 10;
            enemy1Y =(int) Math.floor(Math.random() * (frameHeight - enemy1.getHeight()));
        }
        enemy1.setX(enemy1X);
        enemy1.setY(enemy1Y);

        //enemy2
        enemy2X -= enemy2Speed;
        if (enemy2X < 0){
            enemy2X = screenWidth + 4000;
            enemy2Y =(int) Math.floor(Math.random() * (frameHeight - enemy2.getHeight()));
        }
        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);


        //diamond
        diamondX -= diamondSpeed;
        if (diamondX < 0){
            diamondX = screenWidth + 5000;
            diamondY =(int) Math.floor(Math.random() * (frameHeight - diamond.getHeight()));
        }
        diamond.setX(diamondX);
        diamond.setY(diamondY);


        //player
        if (action_flag == true){

            playerY -= playerSpeed;
            /*if (action == 1){
                player.setImageResource(R.drawable.player1a);
            } else if (action == 2){
                player.setImageResource(R.drawable.player2a);
            }*/
            // I prefer this
            player.setImageResource(getResources().getIdentifier("player"+action+"a", "drawable", getPackageName()));

        } else {
            playerY += playerSpeed;
            player.setImageResource(getResources().getIdentifier("player"+action+"b", "drawable", getPackageName()));
        }

        if (playerY < 0){
            playerY = 0;
        }

        if (playerY > frameHeight - playerSize){
            playerY = frameHeight - playerSize;
        }

        player.setY(playerY);

        scoreLabel.setText("Score: " + score);
        tv_Coins.setText(""+coins);
    }
    public boolean onTouchEvent(MotionEvent me) {
        if (start_flag == false){

            start_flag = true;

            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            playerY = (int) player.getY();

            playerSize = player.getHeight();

            startLabel.setVisibility(View.GONE);

            pauseLb.setEnabled(true);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            position();
                        }
                    });
                }
            }, 0 , 20);
        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN){
                action_flag = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP){
                action_flag = false;
            }
        }

        return true;
    }


    //  food  hit
    public void hint() {

        //food hit
        int foodCenterX = foodX + food.getWidth() / 2;
        int foodCenterY = foodY + food.getHeight() / 2;

        if (0 <= foodCenterX && foodCenterX <= playerSize &&
                playerY <= foodCenterY && foodCenterY <= playerY + playerSize){

            score += 1;
            foodX = -10;
            sound.collectSound();
        }


        //diamond hit
        int diamondCenterX = diamondX + diamond.getWidth() / 2;
        int diamondCenterY = diamondY + diamond.getHeight() / 2;

        if (0 <= diamondCenterX && diamondCenterX <= playerSize &&
                playerY <= diamondCenterY && diamondCenterY <= playerY + playerSize){

            coins++;
            score += 3;
            diamondX = -10;
            sound.collectSound();
        }


        //enemy1 hit
        int enemy1CenterX = enemy1X + enemy1.getWidth() / 2;
        int enemy1CenterY = enemy1Y + enemy1.getHeight() / 2;

        if (0 <= enemy1CenterX && enemy1CenterX <= playerSize &&
                playerY <= enemy1CenterY && enemy1CenterY <= playerY + playerSize){

            timer.cancel();
            timer = null;
            sound.loseSound();

            SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("COINS", coins);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }


        //enemy2 hit
        int enemy2CenterX = enemy2X + enemy2.getWidth() / 2;
        int enemy2CenterY = enemy2Y + enemy2.getHeight() / 2;

        if (0 <= enemy2CenterX && enemy2CenterX <= playerSize &&
                playerY <= enemy2CenterY && enemy2CenterY <= playerY + playerSize){

            timer.cancel();
            timer = null;
            sound.loseSound();

            SharedPreferences settings = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("COINS", coins);
            editor.commit();

            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }
    }

    // disable return button

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case  KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return  super.dispatchKeyEvent(event);
    }

    public void pauseGame(View view){

        if (pause_flag == false){
            pause_flag = true;

            timer.cancel();
            timer = null;

            Drawable d = getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24);
            pauseLb.setBackgroundDrawable(d);

            frameLb.setVisibility(View.VISIBLE);
        } else {
            pause_flag = false;

            Drawable d = getResources().getDrawable(R.drawable.ic_baseline_pause_24);
            pauseLb.setBackgroundDrawable(d);

            frameLb.setVisibility(View.GONE);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            position();
                        }
                    });
                }
            },0 , 20);
        }

    }


}