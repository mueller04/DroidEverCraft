package com.example.mike.droidevercraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CharacterSetup extends AppCompatActivity {

    Gson gs;
    EverCraftCharacter everChar;
    private EditText characterName;
    private int raceEnumIndex;

    private Spinner raceSpinner;

    private ArrayList<EverEnum.RaceEnum> raceSpinnerOptions;


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



        createRaceSpinner();
    }


    private void createRaceSpinner(){
        raceSpinnerOptions = new ArrayList<>();
        int index = 0;
        for (EverEnum.RaceEnum e : EverEnum.RaceEnum.values()){
            raceSpinnerOptions.add(e);
            if (e == everChar.getRace()){
                raceEnumIndex = index;
            }
            index++;
        }

        raceSpinner = (Spinner)findViewById(R.id.race_spinner);
        ArrayAdapter<EverEnum.RaceEnum>adapter = new ArrayAdapter<EverEnum.RaceEnum>(CharacterSetup.this,
                android.R.layout.simple_spinner_item, raceSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(adapter);
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.RaceEnum currentRaceSelection = (EverEnum.RaceEnum)parent.getItemAtPosition(position);
                everChar.setRace(currentRaceSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //we aren't even hitting this method, how is this called?  I want it to show the current rance when player hits screen


            }
        });

        raceSpinner.setSelection(raceEnumIndex);
        Log.d("raceEnumIndex", String.valueOf(raceEnumIndex));
    }

    //TODO I'm guessing this is for the navigation bar back button..doesn't fire or do anything
    @Override
    public void onBackPressed(){
        PressBack();
        //super.onBackPressed();
    }

    public void PressBack(){
        //TODO call to another method(s) here that ultimately saves all data on the page
        everChar.setName(characterName.getText().toString());
        Intent intent = new Intent();
        String characterSerialized = gs.toJson(everChar);
        if (everChar.getPlayerNumber() == 1){
            intent.putExtra("characterOne", characterSerialized);
        } else if (everChar.getPlayerNumber() == 2){
            intent.putExtra("characterTwo", characterSerialized);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            PressBack();
        }
        return true;
    }


}
