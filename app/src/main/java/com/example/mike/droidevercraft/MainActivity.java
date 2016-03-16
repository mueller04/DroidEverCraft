package com.example.mike.droidevercraft;

import android.media.MediaMetadataRetriever;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    Play play;
    EverCraftCharacter everChar1;
    EverCraftCharacter everChar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = new Play();
        everChar1 = new EverCraftCharacter("name1", Enum.Alignment.Good);
        everChar2 = new EverCraftCharacter("name2", Enum.Alignment.Good);
        int hp = everChar1.getHitPoints();
        String hpStr = Integer.toString(hp);
        Log.d("before", hpStr);

        play.roll(everChar1, everChar2, 20);

        hp = everChar1.getHitPoints();
        hpStr = Integer.toString(hp);
        Log.d("after", hpStr);
    }
}
