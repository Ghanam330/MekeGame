package com.example.mekegame;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundEffects {
    private AudioAttributes audio;
    final int MAX = 2;
    private static int collectedSound;
    private static int loseSound;
    private static SoundPool sound;

    public SoundEffects(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audio = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            sound = new SoundPool.Builder()
                    .setAudioAttributes(audio)
                    .build();
        } else {
            sound = new SoundPool(MAX, AudioManager.STREAM_MUSIC, 0);
        }
        collectedSound = sound.load(context, R.raw.collect, 1);
        loseSound = sound.load(context, R.raw.lose, 1);
    }

    public void CollectedSound() {
        sound.play(collectedSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void loseSound() {
        sound.play(loseSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}