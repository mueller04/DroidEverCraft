package com.example.mike.droidevercraft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import charactersetup.CharacterSetupActivity;


public class MainActivity extends AppCompatActivity {

    public final static String packageName = "com.example.mike.droidevercraft.";

    private Gson gs;
    private EverCraftCharacter everChar2;
    private EverCraftCharacter everChar1;
    private EditText textInputOne;
    private EditText textInputTwo;
    private Button button;

    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gs = new Gson();
        button = (Button) findViewById(R.id.goToPlayButton);
        button.setEnabled(false);
        textInputOne = (EditText) findViewById(R.id.character1_name_input);
        textInputTwo = (EditText) findViewById(R.id.character2_name_input);
        textInputOne.addTextChangedListener(textWatcher);
        textInputTwo.addTextChangedListener(textWatcher);
        everChar1 = new EverCraftCharacter("Player One", EverEnum.Alignment.Neutral);
        everChar2 = new EverCraftCharacter("Player Two", EverEnum.Alignment.Neutral);
        everChar1.setPlayerNumber(1);
        everChar2.setPlayerNumber(2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 200){
                String evercharSerialized1 = (String)intent.getStringExtra("characterOne");
                String evercharSerialized2 = (String)intent.getStringExtra("characterTwo");
            if (evercharSerialized1 != null){
                everChar1 = gs.fromJson(evercharSerialized1, EverCraftCharacter.class);
            }
            if (evercharSerialized2 != null) {
                everChar2 = gs.fromJson(evercharSerialized2, EverCraftCharacter.class);
            }
            setEditTextFields();
        }
    }

    private void setEditTextFields(){
        if (!everChar1.getName().equalsIgnoreCase("Player One")){
            textInputOne.setText(everChar1.getName());
        }
        if (!everChar2.getName().equalsIgnoreCase("Player Two")){
            textInputTwo.setText(everChar2.getName());
        }
    }

    public void checkFieldsForEmptyValues(){
        boolean String1IsEmpty = textInputOne.getText().toString().equalsIgnoreCase("");
        boolean String2IsEmpty = textInputTwo.getText().toString().equalsIgnoreCase("");

        if (String1IsEmpty || String2IsEmpty) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

    public void goToCharacter1Setup(View view){
        characterSetup(everChar1, textInputOne);
    }

    public void goToCharacter2Setup(View view){
        characterSetup(everChar2, textInputTwo);
    }

    public void characterSetup(EverCraftCharacter everChar, EditText textInput){
        if (!textInput.getText().toString().equalsIgnoreCase("Player One")
                || !textInput.getText().toString().equalsIgnoreCase("Player Two")){
            everChar.setName(textInput.getText().toString());
        }
        Intent intent = new Intent(this, CharacterSetupActivity.class);
        String characterSerialized = gs.toJson(everChar);
        intent.putExtra("characterAny", characterSerialized);
        startActivityForResult(intent, 200);
    }



    public void goToPlay(View view) {
        everChar1.setName(textInputOne.getText().toString());
        everChar2.setName(textInputTwo.getText().toString());
        Intent intent = new Intent(this, PlayGame.class);
        String character1Serialized = gs.toJson(everChar1);
        String character2Serialized = gs.toJson(everChar2);
        intent.putExtra("characterOne", character1Serialized);
        intent.putExtra("characterTwo", character2Serialized);
        startActivityForResult(intent, 200);
    }


}
