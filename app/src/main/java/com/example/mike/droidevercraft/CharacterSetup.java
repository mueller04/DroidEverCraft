package com.example.mike.droidevercraft;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CharacterSetup extends AppCompatActivity {

    Gson gs;
    EverCraftCharacter everChar;
    private EditText characterName;
    AlertDialog.Builder alertBuilder;
    AlertDialog dialog;
    private int raceEnumIndex;
    private int classEnumIndex;
    private int alignmentEnumIndex;
    private int weaponEnumIndex;
    private int armorEnumIndex;
    private EverEnum.RaceEnum raceOnScreenLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Warning");

        gs = new Gson();
        Intent intent = getIntent();
        String evercharSerialized2 = (String)intent.getStringExtra("characterAny");
        everChar = gs.fromJson(evercharSerialized2, EverCraftCharacter.class);
        characterName = (EditText) findViewById(R.id.character_name_input);
        characterName.setText(everChar.getName());

        raceOnScreenLoad = everChar.getRace();

        createRaceSpinner();
        createClassSpinner();
        createAlignmentSpinner();
        createWeaponSpinner();
        createArmorSpinner();
    }


    private void createRaceSpinner(){
        ArrayList<EverEnum.RaceEnum> raceSpinnerOptions = new ArrayList<>();
        int index = 0;
        for (EverEnum.RaceEnum e : EverEnum.RaceEnum.values()){
            raceSpinnerOptions.add(e);
            if (e == everChar.getRace()){
                raceEnumIndex = index;
            }
            index++;
        }

        final Spinner raceSpinner = (Spinner)findViewById(R.id.race_spinner);
        ArrayAdapter<EverEnum.RaceEnum>adapter = new ArrayAdapter<EverEnum.RaceEnum>(CharacterSetup.this,
                android.R.layout.simple_spinner_item, raceSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(adapter);
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.RaceEnum currentRaceSelection = (EverEnum.RaceEnum) parent.getItemAtPosition(position);
                try {
                    everChar.setRace(currentRaceSelection);
                } catch (IllegalArgumentException e) {
                    ExceptionDialog(e, raceSpinner, raceEnumIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        raceSpinner.setSelection(raceEnumIndex);
    }

    private void createClassSpinner(){
        ArrayList<EverEnum.CharacterClassEnum> classSpinnerOptions = new ArrayList<>();
        int index = 0;
        for (EverEnum.CharacterClassEnum e : EverEnum.CharacterClassEnum.values()){
            classSpinnerOptions.add(e);
            if (e == everChar.getCharacterClass()){
                classEnumIndex = index;
            }
            index++;
        }

        final Spinner classSpinner = (Spinner)findViewById(R.id.class_spinner);
        ArrayAdapter<EverEnum.CharacterClassEnum>adapter = new ArrayAdapter<EverEnum.CharacterClassEnum>(CharacterSetup.this,
                android.R.layout.simple_spinner_item, classSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.CharacterClassEnum currentClassSelection = (EverEnum.CharacterClassEnum) parent.getItemAtPosition(position);
                try {
                    everChar.setCharacterClass(currentClassSelection);
                } catch (IllegalArgumentException e) {
                    ExceptionDialog(e, classSpinner, raceEnumIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        classSpinner.setSelection(classEnumIndex);
    }

    private void createAlignmentSpinner(){
        ArrayList<EverEnum.Alignment> alignmentSpinnerOptions = new ArrayList<>();
        int index = 0;
        for (EverEnum.Alignment e : EverEnum.Alignment.values()){
            alignmentSpinnerOptions.add(e);
            if (e == everChar.getAlignment()){
                alignmentEnumIndex = index;
            }
            index++;
        }

        final Spinner alignmentSpinner = (Spinner)findViewById(R.id.alignment_spinner);
        ArrayAdapter<EverEnum.Alignment>adapter = new ArrayAdapter<EverEnum.Alignment>(CharacterSetup.this,
                android.R.layout.simple_spinner_item, alignmentSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alignmentSpinner.setAdapter(adapter);
        alignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.Alignment currentAlignmentSelection = (EverEnum.Alignment) parent.getItemAtPosition(position);
                try {
                    everChar.setAlignment(currentAlignmentSelection);
                } catch (IllegalArgumentException e) {
                    ExceptionDialog(e, alignmentSpinner, alignmentEnumIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        alignmentSpinner.setSelection(alignmentEnumIndex);
    }

    private void createWeaponSpinner(){
        ArrayList<EverEnum.Weapon> alignmentWeaponOptions = new ArrayList<>();
        int index = 0;
        for (EverEnum.Weapon e : EverEnum.Weapon.values()){
            alignmentWeaponOptions.add(e);
            if (e == everChar.getWeapon()){
                weaponEnumIndex = index;
            }
            index++;
        }

        final Spinner weaponSpinner = (Spinner)findViewById(R.id.weapon_spinner);
        ArrayAdapter<EverEnum.Weapon>adapter = new ArrayAdapter<EverEnum.Weapon>(CharacterSetup.this,
                android.R.layout.simple_spinner_item, alignmentWeaponOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weaponSpinner.setAdapter(adapter);
        weaponSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.Weapon currentWeaponSelection = (EverEnum.Weapon) parent.getItemAtPosition(position);
                try {
                    everChar.setWeapon(currentWeaponSelection);
                } catch (IllegalArgumentException e) {
                    ExceptionDialog(e, weaponSpinner, weaponEnumIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        weaponSpinner.setSelection(weaponEnumIndex);
    }

    private void createArmorSpinner(){
        ArrayList<EverEnum.Armor> alignmentArmorOptions = new ArrayList<>();
        int index = 0;
        for (EverEnum.Armor e : EverEnum.Armor.values()){
            alignmentArmorOptions.add(e);
            if (e == everChar.getArmor()){
                armorEnumIndex = index;
            }
            index++;
        }

        final Spinner armorSpinner = (Spinner)findViewById(R.id.armor_spinner);
        ArrayAdapter<EverEnum.Armor>adapter = new ArrayAdapter<EverEnum.Armor>(CharacterSetup.this,
                android.R.layout.simple_spinner_item, alignmentArmorOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        armorSpinner.setAdapter(adapter);
        armorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.Armor currentArmorSelection = (EverEnum.Armor) parent.getItemAtPosition(position);
                try {
                    everChar.setArmor(currentArmorSelection);
                } catch (IllegalArgumentException e) {
                    ExceptionDialog(e, armorSpinner, armorEnumIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        armorSpinner.setSelection(armorEnumIndex);
    }

    public void PressBack(){
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
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            PressBack();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                PressBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ExceptionDialog(IllegalArgumentException e, Spinner spinner, int index){
        alertBuilder.setMessage(e.getMessage());
        dialog = alertBuilder.create();
        spinner.setSelection(index);
        dialog.show();
    }


}
