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
    EverCraftCharacter everChar1;
    EverCraftCharacter everChar2;
    Play play;
    private int turnCounter;
    TextView turnTextView;
    TextView rollNumberTextView;
    TextView hitPointTextView;
    TextView rollTurnDetailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        gs = new Gson();
        play = new Play();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String everchar1Serialized = (String)intent.getStringExtra("characterOne");
        everChar1 = gs.fromJson(everchar1Serialized, EverCraftCharacter.class);
        String everchar2Serialized = (String)intent.getStringExtra("characterTwo");
        everChar2 = gs.fromJson(everchar2Serialized, EverCraftCharacter.class);

        turnCounter = 1;

        turnTextView = (TextView) findViewById(R.id.turnsummary_view);
        String turnSummary = turnSummary(everChar1);
        turnTextView.setText(String.valueOf(turnSummary));

        rollNumberTextView = (TextView) findViewById(R.id.rollnumber_view);
        hitPointTextView = (TextView) findViewById(R.id.playsummary_view);
        rollTurnDetailText = (TextView) findViewById(R.id.playerdetail_view);
    }

    public void roll(View view){
        String turnSummary;
        String rollTurn;
        int rollNum = getRollNumber();

        if (turnCounter % 2 == 0){
            turnSummary = turnSummary(everChar2);
            rollTurn = rollTurn(everChar1, everChar2);
            populateRollNumber(rollNum);
        } else {
            turnSummary = turnSummary(everChar1);
            rollTurn = rollTurn(everChar2, everChar1);
            populateRollNumber(rollNum);
        }
        String detailText = rollTurnDetail();
        hitPointTextView.setText(String.valueOf(rollTurn));
        turnTextView.setText(String.valueOf(turnSummary));
        rollTurnDetailText.setText(detailText);
    }

    public String turnSummary(EverCraftCharacter attackingChar){
        String turnString = "Turn ";
        turnString += String.valueOf(turnCounter);
        turnString += " - " + attackingChar.getName();
        turnString += " to attack";

        turnCounter++;
        return turnString;
    }

    public String rollTurn(EverCraftCharacter attackingChar, EverCraftCharacter defendingChar){
        int initialHitPoints = defendingChar.getHitPoints();
        play.roll(defendingChar, attackingChar, 20);
        String summaryText = String.valueOf(attackingChar.getName());
        summaryText += " attacked ";
        summaryText += String.valueOf(defendingChar.getName());
        summaryText += " causing ";
        summaryText += String.valueOf(initialHitPoints - defendingChar.getHitPoints());
        summaryText += " damage";
        return summaryText;
    }

    public String rollTurnDetail(){

        String detailText = everChar1.getName();
        detailText += "\n hit points: ";
        detailText += everChar1.getHitPoints();
        detailText += "\n level: ";
        detailText += everChar1.getLevel();
        detailText += "\n armor: ";
        detailText += everChar1.getArmor();

        detailText += "'\n\n ";
        detailText += everChar2.getName();
        detailText += "\n hit points: ";
        detailText += everChar2.getHitPoints();
        detailText += "\n level: ";
        detailText += everChar2.getLevel();
        detailText += "\n armor: ";
        detailText += everChar2.getArmor();
        return detailText;
    }

    public int getRollNumber(){
        double minimumRoll = 1;
        double rollNumber = minimumRoll + Math.random() * 19;
        int returnNumber = (int)Math.round(rollNumber);
        return returnNumber;
    }

    public void populateRollNumber(int rollNum){
        rollNumberTextView.setText(String.valueOf(rollNum));
    }

    //Getters and Setters
    public void setTurnCounter(int turnCounter){
        this.turnCounter = turnCounter;
    }

}
