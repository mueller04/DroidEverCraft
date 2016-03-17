package com.example.mike.droidevercraft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {

    public final static String packageName = "com.example.mike.droidevercraft.";

    Play play;
    EverCraftCharacter everChar2;
    Gson gs;
    EverCraftCharacter everChar1;
    public String inputStr;
    public EditText textInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = new Play();
        gs = new Gson();
    }

    public void saveCharacter1(View view){


        //everChar2 = new EverCraftCharacter("name2", EverEnum.Alignment.Good);
        //int hp = everChar1.getHitPoints();
        //String hpStr = Integer.toString(hp);
        //Log.d("before", hpStr);

        textInput = (EditText) findViewById(R.id.name_input);
        inputStr = textInput.getText().toString();

        everChar1 = new EverCraftCharacter("testname", EverEnum.Alignment.Good);

        //play.roll(everChar1, everChar2, 20);

        //hp = everChar1.getHitPoints();
        //hpStr = Integer.toString(hp);
        //Log.d("after", hpStr);
        //String nameStr = everChar1.getName();
        //Log.d("name", nameStr);
    }

    public void goToPlay(View view) {

        TestClass testName = new TestClass("bwahaha", EverEnum.Weapon.DAGGER);

        EverCraftCharacter everChar2;



        everChar2 = new EverCraftCharacter(inputStr, EverEnum.Alignment.Good);

        Intent intent = new Intent(this, PlayGame.class);
        String characterSerialized = gs.toJson(everChar2);
        intent.putExtra("characterOne", characterSerialized);
        startActivity(intent);
    }


}
