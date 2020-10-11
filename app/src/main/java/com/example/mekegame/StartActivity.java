package com.example.mekegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;

public class StartActivity extends AppCompatActivity {
    ImageButton play, more, shop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starty);

        shop = findViewById(R.id.shop);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        play = (ImageButton) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, ShopActivity.class));
                finish();
            }
        });

        more = (ImageButton) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(StartActivity.this, more);
                popupMenu.getMenuInflater().inflate(R.menu.pop, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.feecbook) {
                            Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                            Uri data = Uri.parse("mailto:?subject=" + "subject text" + "&body=" + "body text" + "&to=" + "yghnam334455@gmail.com");
                            mailIntent.setData(data);
                            startActivity(Intent.createChooser(mailIntent, "Send mail..."));

                        }
                        if (id == R.id.share) {
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("text/plain");
                            i.putExtra(Intent.EXTRA_SUBJECT, "تطبيق ");
                            i.putExtra(Intent.EXTRA_TEXT, "جرب هذا التطبيق اكثر من رائع\n" +
                                    "https://play.google.com/store/apps/details?id=com.example.introslider");
                            startActivity(Intent.createChooser(i, "اختر التطبيق"));
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    // disable return button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }

        }
        return super.dispatchKeyEvent(event);
    }

}