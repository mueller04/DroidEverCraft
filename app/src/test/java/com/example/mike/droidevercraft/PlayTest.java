package com.example.mike.droidevercraft;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class PlayTest {

    Play play;
    EverCraftCharacter mockDefendingChar;
    EverCraftCharacter mockAttackingChar;
    Abilities mockAbilities;

    @Before
    public void BeforeMethodClass() {
        play = new Play();
        mockDefendingChar = Mockito.mock(EverCraftCharacter.class);
        mockAttackingChar = Mockito.mock(EverCraftCharacter.class);
        mockAbilities = Mockito.mock(Abilities.class);
    }

    @Test
    public void rollHitsIfMeetsOpponentsArmorClass(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);

        //Act
        String result = play.roll(mockDefendingChar, mockAttackingChar, 10);

        //Assert
        assertEquals("it's a hit", result);
    }

    @Test
    public void rollFailsIfLessThanOpponentsArmorClass(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);

        //Act
        String result = play.roll(mockDefendingChar, mockAttackingChar, 9);

        //Assert
        assertEquals("attack glanced off the armor", result);
    }


    @Test
    public void higherRollBeatsModifiedArmor(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(9);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(8)).thenReturn(10);

        //Act
        String result = play.roll(mockDefendingChar, mockAttackingChar, 8);

        //Assert
        assertEquals("it's a hit", result);
    }


    @Test
    public void successfulAttackEarns10ExperiencePoints(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);

        //Act
        play.roll(mockDefendingChar, mockAttackingChar, 10);

        //Assert
        verify(mockAttackingChar).addExperiencePoints(10);
    }

    @Test
    public void adds1ToCriticalRangeForCriticalHits(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);
        Mockito.when(mockAttackingChar.getCriticalRange()).thenReturn(1);

        //Act
        boolean result = play.isCritical(19, mockAttackingChar);

        //Assert
        assertEquals(true, result);
    }

    @Test
    public void twentyIsCriticalHitWithRangeOf0(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);
        Mockito.when(mockAttackingChar.getCriticalRange()).thenReturn(0);

        //Act
        boolean result = play.isCritical(20, mockAttackingChar);

        //Assert
        assertEquals(true, result);
    }

    @Test
    public void rangeofZeroRoll19NotCriticalHit(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);
        Mockito.when(mockAttackingChar.getCriticalRange()).thenReturn(0);

        //Act
        boolean result = play.isCritical(19, mockAttackingChar);

        //Assert
        assertEquals(false, result);
    }

    @Test
    public void rangeof2Roll18IsCriticalHit(){
        //Arrange
        Mockito.when(mockAttackingChar.getAbilities()).thenReturn(mockAbilities);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);
        Mockito.when(mockAttackingChar.getCriticalRange()).thenReturn(2);

        //Act
        boolean result = play.isCritical(18, mockAttackingChar);

        //Assert
        assertEquals(true, result);
    }

    @Test
    public void attackingCharWeaponWarAxeAgainstOrcCallsAttackAgainstOrcFlag(){
        //Arrange
        Mockito.when(mockAttackingChar.getWeapon()).thenReturn(com.example.mike.droidevercraft.EverEnum.Weapon.LONGSWORD);
        Mockito.when(mockDefendingChar.getRace()).thenReturn(EverEnum.RaceEnum.ORC);
        Mockito.when(mockDefendingChar.getModifiedArmor()).thenReturn(10);
        Mockito.when(mockAttackingChar.getModifiedRollNumber(anyInt())).thenReturn(10);

        //Act
        play.roll(mockDefendingChar, mockAttackingChar, 10);

        //Assert
        verify(mockAttackingChar).setWarAxeAgainstOrcFlag();
    }



}


