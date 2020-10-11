package com.example.mekegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Short2;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {
    RelativeLayout shopP1, shopP2, shopP3, shopP4;
    RelativeLayout unlock2, unlock3, unlock4;
    ImageButton goHome;
    TextView tv_Coins;
    boolean shop2, shop3, shop4;
    int coins, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //      fullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        shopP1 = (RelativeLayout) findViewById(R.id.shopP1);
        shopP2 = (RelativeLayout) findViewById(R.id.shopP2);
        shopP3 = (RelativeLayout) findViewById(R.id.shopP3);
        shopP4 = (RelativeLayout) findViewById(R.id.shopP4);


        unlock2 = (RelativeLayout) findViewById(R.id.unLock2);
        unlock3 = (RelativeLayout) findViewById(R.id.unLock3);
        unlock4 = (RelativeLayout) findViewById(R.id.unLock4);

        goHome = (ImageButton) findViewById(R.id.home);
        tv_Coins = (TextView) findViewById(R.id.tv_coins);
        final SharedPreferences setting = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        coins = setting.getInt("COINS", 0);
        action = setting.getInt("ACTION", 1);
        shop2 = setting.getBoolean("SHOP2", false);
        shop3 = setting.getBoolean("SHOP3", false);
        shop4 = setting.getBoolean("SHOP4", false);

        tv_Coins.setText("" + coins);
        if (shop2 == true) {
            unlock2.setVisibility(View.GONE);
        }
        if (shop3 == true) {
            unlock3.setVisibility(View.GONE);
        }
        if (shop4 == true) {
            unlock4.setVisibility(View.GONE);
        }
        shopP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = 1;
                SharedPreferences.Editor editor = setting.edit();
                editor.putInt("ACTION", action);
                editor.commit();
                startActivity(new Intent(ShopActivity.this, StartActivity.class));
            }
        });

               shopP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop2 == true) {
                    action = 2;
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putInt("ACTION", action);
                    editor.commit();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));
                } else if (coins >= 30) {
                    shop2 = true;
                    action = 2;
                    coins = coins - 30;

                    tv_Coins.setText(""+coins);
                    unlock2.setVisibility(View.GONE);

                    SharedPreferences.Editor editor =setting.edit();
                    editor.putInt("ACTION",action);
                    editor.putInt("COINS",coins);
                    editor.putBoolean("SHOP2", shop2);
                    editor.commit();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));

                }else {
                    Toast.makeText(ShopActivity.this, "no enough coins", Toast.LENGTH_SHORT).show();
                }
            }
        });



        shopP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop3 == true) {
                    action = 3;
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putInt("ACTION", action);
                    editor.commit();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));
                } else if (coins >= 50) {
                    shop3 = true;
                    action = 3;
                    coins = coins - 50;

                    tv_Coins.setText(""+coins);
                    unlock3.setVisibility(View.GONE);
                    SharedPreferences.Editor editor =setting.edit();
                    editor.putInt("ACTION",action);
                    editor.putInt("COINS",coins);
                    editor.putBoolean("SHOP3", shop3);
                    editor.commit();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));

                }else {
                    Toast.makeText(ShopActivity.this, "no enough coins", Toast.LENGTH_SHORT).show();
                }
            }
        });


        shopP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shop4 == true) {
                    action = 4;
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putInt("ACTION", action);
                    editor.commit();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));
                } else if (coins >= 80) {
                    shop4 = true;
                    action = 4;
                    coins = coins - 80;

                    tv_Coins.setText(""+coins);
                    unlock4.setVisibility(View.GONE);
                    SharedPreferences.Editor editor =setting.edit();
                    editor.putInt("ACTION",action);
                    editor.putInt("COINS",coins);
                    editor.putBoolean("SHOP4", shop4);
                    editor.commit();
                    startActivity(new Intent(ShopActivity.this, StartActivity.class));

                }else {
                    Toast.makeText(ShopActivity.this, "no enough coins", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopActivity.this,StartActivity.class));
            }
        });

    }
}