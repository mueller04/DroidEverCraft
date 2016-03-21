package com.example.mike.droidevercraft;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;


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
        everChar1 = new EverCraftCharacter("Character One", EverEnum.Alignment.Neutral);
        everChar2 = new EverCraftCharacter("Character Two", EverEnum.Alignment.Neutral);
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

    public void saveCharacter1(View view){
        //everChar1 = saveCharacter(textInputOne);
    }

    public void saveCharacter2(View view){
        //everChar2 = saveCharacter(textInputTwo);
    }



    public void goToPlay(View view) {
        everChar1.setName(textInputOne.getText().toString());
        everChar2.setName(textInputTwo.getText().toString());
        Intent intent = new Intent(this, PlayGame.class);
        String character1Serialized = gs.toJson(everChar1);
        String character2Serialized = gs.toJson(everChar2);
        intent.putExtra("characterOne", character1Serialized);
        intent.putExtra("characterTwo", character2Serialized);
        startActivity(intent);
    }


}
