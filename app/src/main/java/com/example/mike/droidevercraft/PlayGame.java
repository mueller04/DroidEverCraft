package com.example.mike.droidevercraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.ExtractedText;
import android.widget.TextView;

import com.google.gson.Gson;

public class PlayGame extends AppCompatActivity {

    Gson gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        gs = new Gson();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String everchar1Serialized = (String)intent.getStringExtra("characterOne");
        EverCraftCharacter newTest = gs.fromJson(everchar1Serialized, EverCraftCharacter.class);
//
       TextView priceTextView = (TextView) findViewById(R.id.activitymain_view);

            priceTextView.setText(newTest.getName());


    }

}
