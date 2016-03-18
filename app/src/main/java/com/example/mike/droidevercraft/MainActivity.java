package com.example.mike.droidevercraft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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
        textInput = (EditText) findViewById(R.id.character1_name_input);
        everChar1 = saveCharacter(textInput);
    }

    public void saveCharacter2(View view){
        textInput = (EditText) findViewById(R.id.character2_name_input);
        everChar2 = saveCharacter(textInput);
    }

    public EverCraftCharacter saveCharacter(EditText editText){
        inputStr = editText.getText().toString();
        return new EverCraftCharacter(inputStr, EverEnum.Alignment.Good);
    }

    public void goToPlay(View view) {

        EverCraftCharacter everChar2;

        everChar2 = new EverCraftCharacter(inputStr, EverEnum.Alignment.Good);

        Intent intent = new Intent(this, PlayGame.class);
        String character1Serialized = gs.toJson(everChar1);
        String character2Serialized = gs.toJson(everChar2);
        intent.putExtra("characterOne", character1Serialized);
        intent.putExtra("characterTwo", character2Serialized);
        startActivity(intent);
    }


}
