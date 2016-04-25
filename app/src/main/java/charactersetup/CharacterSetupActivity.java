package charactersetup;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mike.droidevercraft.EverCraftCharacter;
import com.example.mike.droidevercraft.EverEnum;
import com.example.mike.droidevercraft.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CharacterSetupActivity extends AppCompatActivity {

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

    public CharacterSetupActivity(){
    }

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
        String evercharSerialized = (String)intent.getStringExtra("characterAny");

        everChar = gs.fromJson(evercharSerialized, EverCraftCharacter.class);
        if (everChar == null){
            everChar = new EverCraftCharacter("test character", EverEnum.Alignment.Neutral);
        }

        characterName = (EditText) findViewById(R.id.character_name_input);
        String name = everChar.getName();
        characterName.setText(name);

        raceOnScreenLoad = everChar.getRace();

        createRaceSpinner();
        createClassSpinner();
        createAlignmentSpinner();
        createWeaponSpinner();
        createArmorSpinner();
    }

    private void invalidSpinnerSelection(CharacterSetupRulesReturnObject returnObj, Spinner spinner, int currentSpinnerIndex){
        int resetPositionToCurrentSelection = currentSpinnerIndex;
        spinner.setSelection(resetPositionToCurrentSelection);
        String message = returnObj.getMessage();
        spinnerAlertDialog(message);
    }

    private void spinnerAlertDialog(String message){
        alertBuilder.setTitle("Alert!");
        alertBuilder.setMessage(message);
        dialog = alertBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
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
        ArrayAdapter<EverEnum.RaceEnum>adapter = new ArrayAdapter<EverEnum.RaceEnum>(CharacterSetupActivity.this,
                android.R.layout.simple_spinner_item, raceSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceSpinner.setAdapter(adapter);
        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.RaceEnum currentRaceSelection = (EverEnum.RaceEnum) parent.getItemAtPosition(position);
                CharacterSetupRulesReturnObject returnObj = everChar.setRace(currentRaceSelection);

                if (!returnObj.getIsValid()){
                    invalidSpinnerSelection(returnObj, raceSpinner, raceEnumIndex);
                }  else {
                    raceEnumIndex = raceSpinner.getSelectedItemPosition();
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
        ArrayAdapter<EverEnum.CharacterClassEnum>adapter = new ArrayAdapter<EverEnum.CharacterClassEnum>(CharacterSetupActivity.this,
                android.R.layout.simple_spinner_item, classSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                EverEnum.CharacterClassEnum currentClassSelection = (EverEnum.CharacterClassEnum) parent.getItemAtPosition(position);
                CharacterSetupRulesReturnObject returnObj = everChar.setCharacterClass(currentClassSelection);


                if (!returnObj.getIsValid()){
                    invalidSpinnerSelection(returnObj, classSpinner, classEnumIndex);
                } else {
                    classEnumIndex = classSpinner.getSelectedItemPosition();
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
        ArrayAdapter<EverEnum.Alignment>adapter = new ArrayAdapter<EverEnum.Alignment>(CharacterSetupActivity.this,
                android.R.layout.simple_spinner_item, alignmentSpinnerOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alignmentSpinner.setAdapter(adapter);
        alignmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.Alignment currentAlignmentSelection = (EverEnum.Alignment) parent.getItemAtPosition(position);
                CharacterSetupRulesReturnObject returnObj = everChar.setAlignment(currentAlignmentSelection);

                if (!returnObj.getIsValid()){
                    invalidSpinnerSelection(returnObj, alignmentSpinner, alignmentEnumIndex);
                } else {
                alignmentEnumIndex = alignmentSpinner.getSelectedItemPosition();
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
        ArrayAdapter<EverEnum.Weapon>adapter = new ArrayAdapter<EverEnum.Weapon>(CharacterSetupActivity.this,
                android.R.layout.simple_spinner_item, alignmentWeaponOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weaponSpinner.setAdapter(adapter);
        weaponSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                EverEnum.Weapon currentWeaponSelection = (EverEnum.Weapon) parent.getItemAtPosition(position);
                CharacterSetupRulesReturnObject returnObj = everChar.setWeapon(currentWeaponSelection);

                if (!returnObj.getIsValid()){
                    invalidSpinnerSelection(returnObj, weaponSpinner, weaponEnumIndex);
                } else {
                weaponEnumIndex = weaponSpinner.getSelectedItemPosition();
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
        ArrayAdapter<EverEnum.Armor>adapter = new ArrayAdapter<EverEnum.Armor>(CharacterSetupActivity.this,
                android.R.layout.simple_spinner_item, alignmentArmorOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        armorSpinner.setAdapter(adapter);
        armorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                EverEnum.Armor currentArmorSelection = (EverEnum.Armor) parent.getItemAtPosition(position);
                CharacterSetupRulesReturnObject returnObj = everChar.setArmor(currentArmorSelection);

                if (!returnObj.getIsValid()){
                    invalidSpinnerSelection(returnObj, armorSpinner, armorEnumIndex);
                } else {
                    armorEnumIndex = armorSpinner.getSelectedItemPosition();
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

}
