package com.example.mike.droidevercraft;


import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;

public class PlayGameTest {

    PlayGame playGame;
    Play mockPlay;
    EverCraftCharacter mockDefendingChar;
    EverCraftCharacter mockAttackingChar;
    Bundle mockBundle;
    Abilities mockAbilities;

    @Before
    public void BeforeMethodClass() {
        playGame = new PlayGame();
        mockPlay = Mockito.mock(Play.class);
        mockDefendingChar = Mockito.mock(EverCraftCharacter.class);
        mockAttackingChar = Mockito.mock(EverCraftCharacter.class);
        mockAbilities = Mockito.mock(Abilities.class);
        mockBundle = Mockito.mock(Bundle.class);
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
    }

    @Test
    public void turnSummaryStringIsCorrect(){
        //Arrange
        Mockito.when(mockAttackingChar.getName()).thenReturn("Larry");
        playGame.setTurnCounter(1);

        //Act
        String result = playGame.turnSummary(mockAttackingChar);

        //Assert
        assertEquals("Turn 1 - Larry to attack", result);
    }

    //If I could mock this stuff, that would be great.  or use espresso
//    @Test
//    public void rollSummaryStringIsCorrect(){
//        //Arrange
//        Mockito.when(mockAttackingChar.getName()).thenReturn("Larry");
//        Mockito.when(mockDefendingChar.getName()).thenReturn("Eddy");
//        Mockito.when(mockDefendingChar.getHitPoints()).thenReturn(10);
//        Mockito.when(mockAttackingChar.getHitPoints()).thenReturn(10);
//        Mockito.when(mockPlay.roll(mockDefendingChar, mockAttackingChar, 20)).thenReturn(null);
//
//        //WHAT is going on here?
//        //Mockito.when(PlayGame.super.onCreate(mockBundle)).thenReturn(null);
//
//
//        playGame.onCreate(mockBundle);
//
//        //Act
//        String result = playGame.rollTurn(mockDefendingChar, mockAttackingChar);
//
//        //Assert
//        assertEquals("Larry attacked Eddy for 2 damage", result);
//    }


}
