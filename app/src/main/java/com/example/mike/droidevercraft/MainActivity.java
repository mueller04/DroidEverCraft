package com.example.mike.droidevercraft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public final static String packageName = "com.example.mike.droidevercraft.";

    Play play;
    EverCraftCharacter everChar1;
    EverCraftCharacter everChar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = new Play();


    }

    public void saveCharacter1(View view){

        EditText textInput = (EditText) findViewById(R.id.name_input);
        String nameInputStr = textInput.getText().toString();
        everChar1 = new EverCraftCharacter(nameInputStr, EverEnum.Alignment.Good);
        everChar2 = new EverCraftCharacter("name2", EverEnum.Alignment.Good);
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

    public void goToPlay(View view) {
        Intent intent = new Intent(this, PlayGame.class);
        intent.putExtra("characterOne", "hi");
        startActivity(intent);
    }


}
