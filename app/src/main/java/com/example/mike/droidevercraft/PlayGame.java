package com.example.mike.droidevercraft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private int initialHitPoints;
    TextView turnTextView;
    TextView rollNumberTextView;
    TextView summaryTitleTextView;
    TextView hitPointTextView;
    TextView rollTurnDetailTextPlayer1;
    TextView rollTurnDetailTextPlayer2;


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
        summaryTitleTextView = (TextView) findViewById(R.id.playsummarytitle_view);
        hitPointTextView = (TextView) findViewById(R.id.playsummary_view);
        rollTurnDetailTextPlayer1 = (TextView) findViewById(R.id.player1detail_view);
        rollTurnDetailTextPlayer2 = (TextView) findViewById(R.id.player2detail_view);
    }

    public void roll(View view){
        String turnSummary;
        String rollTurnSummaryText;
        String hitPointText;

        rollDie();

        if (turnCounter % 2 == 0) {
            turnSummary = turnSummary(everChar2);
            rollTurnSummaryText = rollTurn(everChar1, everChar2);
            hitPointText = hitPointText(everChar1, everChar2);

        } else {
            turnSummary = turnSummary(everChar1);
            rollTurnSummaryText = rollTurn(everChar2, everChar1);
            hitPointText = hitPointText(everChar2, everChar1);

        }
        turnTextView.setText(turnSummary);
        summaryTitleTextView.setText(rollTurnSummaryText);
        hitPointTextView.setText((hitPointText));
        String detailTextPlayer1 = rollTurnDetail(everChar1);
        String detailTextPlayer2 = rollTurnDetail(everChar2);
        rollTurnDetailTextPlayer1.setText(detailTextPlayer1);
        rollTurnDetailTextPlayer2.setText(detailTextPlayer2);
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
        initialHitPoints = defendingChar.getHitPoints();
        String rollResult = play.roll(defendingChar, attackingChar, 20);
       return rollResult;
    }

    public String hitPointText(EverCraftCharacter attackingChar, EverCraftCharacter defendingChar){
        String summaryText = '\n' + String.valueOf(attackingChar.getName());
        summaryText += " attacked ";
        summaryText += String.valueOf(defendingChar.getName());
        summaryText += " causing ";
        summaryText += String.valueOf(initialHitPoints - defendingChar.getHitPoints());
        summaryText += " damage";
        return summaryText;
    }

    public String rollTurnDetail(EverCraftCharacter everChar){
        String detailText = everChar.getName();
        detailText += "\n hit points: ";
        detailText += everChar.getHitPoints();
        detailText += "\n level: ";
        detailText += everChar.getLevel();
        detailText += "\n armor: ";
        detailText += everChar.getArmor();
        return detailText;
    }


    public int getRollNumber(){
        double minimumRoll = 1;
        double rollNumber = minimumRoll + Math.random() * 19;
        int returnNumber = (int)Math.round(rollNumber);
        return returnNumber;
    }

    public void rollDie(){
        int rollNumber = getRollNumber();
        populateRollNumber(rollNumber);
    }

    public void populateRollNumber(int rollNum){
        rollNumberTextView.setText(String.valueOf(rollNum));
    }

    //Getters and Setters
    public void setTurnCounter(int turnCounter){
        this.turnCounter = turnCounter;
    }

}
