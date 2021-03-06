package com.example.mike.droidevercraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

public class PlayGame extends AppCompatActivity implements DialogInterface.OnDismissListener {

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
    TextView experienceEarnTextView;
    AlertDialog.Builder alertBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alertBuilder = new AlertDialog.Builder(this);

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
        turnTextView.setText(turnSummary);

        rollNumberTextView = (TextView) findViewById(R.id.rollnumber_view);
        summaryTitleTextView = (TextView) findViewById(R.id.playsummarytitle_view);
        hitPointTextView = (TextView) findViewById(R.id.playsummary_view);
        rollTurnDetailTextPlayer1 = (TextView) findViewById(R.id.player1detail_view);
        rollTurnDetailTextPlayer2 = (TextView) findViewById(R.id.player2detail_view);
        experienceEarnTextView = (TextView) findViewById(R.id.earnexperience_view);
        Log.d("experience points 1", String.valueOf(everChar1.getExperiencePoints()));
        Log.d("experience points 2", String.valueOf(everChar2.getExperiencePoints()));

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
            generateEndOfTurnResults(everChar1, everChar2, turnSummary, rollTurnSummaryText, hitPointText);
        } else {
            turnSummary = turnSummary(everChar1);
            rollTurnSummaryText = rollTurn(everChar2, everChar1);
            hitPointText = hitPointText(everChar2, everChar1);
            generateEndOfTurnResults(everChar2, everChar1, turnSummary, rollTurnSummaryText, hitPointText);
        }

    }

    private void generateEndOfTurnResults(EverCraftCharacter attackingChar, EverCraftCharacter defendingChar,
                                          String turnSummary, String rollTurnSummaryText, String hitPointText){
        if (defendingChar.getLifeStatus() == EverEnum.LifeStatus.Dead){
            clearTextViews();
            attackingChar.addExperiencePoints(150);
            showPlayerRoundEndAlert(attackingChar, defendingChar, hitPointText);
            everChar1.beginNextRound();
            everChar2.beginNextRound();
        } else {
            setSummaryText(turnSummary, rollTurnSummaryText, hitPointText);
            experienceEarnTextView.setText("+ 100 XP");
            setDetailText();
        }

    }

    private void showPlayerRoundEndAlert(EverCraftCharacter attackingChar, EverCraftCharacter defendingChar,
                                         String hitPointText){
        String alertTitle = attackingChar.getName() + " Won the Battle!";
        alertBuilder.setTitle(alertTitle);

        String alertMessage = hitPointText + "\n";
        alertMessage += defendingChar.getName() + " fell!";
        alertMessage += "\n\n" + attackingChar.getName() + "earned 250 XP";

        alertBuilder.setMessage(alertMessage);
        AlertDialog dialog = alertBuilder.create();
        dialog.setOnDismissListener(this);
        dialog.show();
    }

    private void clearTextViews(){
        turnCounter = 1;
        turnTextView.setText("");
        summaryTitleTextView.setText("");
        hitPointTextView.setText("");
        experienceEarnTextView.setText("");
        rollTurnDetailTextPlayer1.setText("");
        rollTurnDetailTextPlayer2.setText("");
    }

    private void setSummaryText(String turnSummary, String rollTurnSummaryText, String hitPointText){
        turnTextView.setText(turnSummary);
        summaryTitleTextView.setText(rollTurnSummaryText);
        hitPointTextView.setText((hitPointText));
    }

    private void setDetailText(){
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

    @Override
    public void onDismiss(DialogInterface dialogInterface){
        String turnSummary = turnSummary(everChar1);
        turnTextView.setText(turnSummary);
    }

    public void PressBack(){
        Intent intent = new Intent();
        String characterSerialized1 = gs.toJson(everChar1);
        String characterSerialized2 = gs.toJson(everChar2);
        intent.putExtra("characterOne", characterSerialized1);
        intent.putExtra("characterTwo", characterSerialized2);
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

    //Getters and Setters
    public void setTurnCounter(int turnCounter){
        this.turnCounter = turnCounter;
    }

}
