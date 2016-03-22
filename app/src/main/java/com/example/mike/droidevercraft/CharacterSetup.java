package com.example.mike.droidevercraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

public class CharacterSetup extends AppCompatActivity {

    //TODO this screen doesn't seem to handle the device back button at the bottom of the device

    Gson gs;
    EverCraftCharacter everChar;
    private EditText characterName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gs = new Gson();
        Intent intent = getIntent();
        String evercharSerialized2 = (String)intent.getStringExtra("characterAny");
        everChar = gs.fromJson(evercharSerialized2, EverCraftCharacter.class);
        characterName = (EditText) findViewById(R.id.character_name_input);
        characterName.setText(everChar.getName());
    }


    @Override
    public void onBackPressed(){

        Intent intent = new Intent();
        String characterSerialized = gs.toJson(everChar);
        if (everChar.getPlayerNumber() == 1){
            intent.putExtra("characterOne", characterSerialized);
        } else if (everChar.getPlayerNumber() == 2){
            intent.putExtra("characterTwo", characterSerialized);
        }
        setResult(RESULT_OK, intent);
        super.onBackPressed();


        //TODO also I will need some kind of lifecycle method that knows we awoke the parent again and transfer the
        //TODO character object back to MainActivity overwriting it's object reference.
    }

}
