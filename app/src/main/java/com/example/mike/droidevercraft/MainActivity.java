package com.example.mike.droidevercraft;

import android.media.MediaMetadataRetriever;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Play play;
    EverCraftCharacter everChar1;
    EverCraftCharacter everChar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = new Play();


    }

    public void roll(View view){

        EditText textInput = (EditText) findViewById(R.id.name_input);
        Editable nameInput = textInput.getText();
        String nameInputStr = nameInput.toString();
        everChar1 = new EverCraftCharacter(nameInputStr, Enum.Alignment.Good);
        everChar2 = new EverCraftCharacter("name2", Enum.Alignment.Good);
        int hp = everChar1.getHitPoints();
        String hpStr = Integer.toString(hp);
        Log.d("before", hpStr);

        play.roll(everChar1, everChar2, 20);

        hp = everChar1.getHitPoints();
        hpStr = Integer.toString(hp);
        Log.d("after", hpStr);
        String nameStr = everChar1.getName();
        Log.d("name", nameStr);
    }


}
