package com.example.mike.droidevercraft;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbilitiesTest {

    @Test
    public void AbilitiesDefaultTo10(){
        //Arrange

        //Act
        Abilities abilities = new Abilities();

        //Assert
        assertEquals(10, abilities.getStrengthScore());
        assertEquals(10, abilities.getDexterityScore());
        assertEquals(10, abilities.getConstitutionScore());
        assertEquals(10, abilities.getWisdomScore());
        assertEquals(10, abilities.getIntelligenceScore());
        assertEquals(10, abilities.getCharismaScore());
    }


    @Test
    public void scoresMapToModifiers(){
        //Arrange

        //Act
        Abilities abilities = new Abilities();

        //Assert
        assertEquals(-5, abilities.getStrengthModifier(1), 0);
        assertEquals(-4, abilities.getDexterityModifier(2), 0);
        assertEquals(0, abilities.getConstitutionModifier(11), 0);
        assertEquals(0, abilities.getWisdomModifier(12), 1);
        assertEquals(0, abilities.getIntelligenceModifier(15), 3);
        assertEquals(4, abilities.getCharismaModifier(19), 0);
    }





}
