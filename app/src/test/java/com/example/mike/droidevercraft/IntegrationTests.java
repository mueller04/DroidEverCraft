package com.example.mike.droidevercraft;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IntegrationTests {

    Play play;
    EverCraftCharacter everCharacter;
    EverCraftCharacter attackingEverCharacter;

    @Before
    public void beforeEachMethod(){
        everCharacter = new EverCraftCharacter("Defending Character", com.example.mike.droidevercraft.EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        play = new Play();
    }

    @Test
    public void rollModifierAddsToAttackRole(){
        //Arrange
        //Add enough experience points to level up to level 4 which adds additional 2 to attack (1 for each even level achieved)
        attackingEverCharacter.addExperiencePoints(3000);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 10);

        //Assert
        assertEquals(2, everCharacter.getHitPoints());
    }


    @Test
    public void addDexterityModifierToArmorClassMeetArmorPointsResultsInHit(){
        //Arrange
        everCharacter.getAbilities().setDexterityScore(14);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 12);

        //Assert
        assertEquals("it's a hit", result);
    }

    @Test
    public void addDexterityModifierToArmorClassLessPointsResultsInMiss(){
        //Arrange
        everCharacter.getAbilities().setDexterityScore(12);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 10);

        //Assert
        assertEquals("attack glanced off the armor", result);
    }

    //Can this realistically be mocked or should this be moved to integration tests?
    @Test
    public void successfulRollLessThan20Takes1HitPoint(){
        //Arrange

        //Act
        play.roll(everCharacter, attackingEverCharacter, 15);

        //Assert
        assertEquals(4, everCharacter.getHitPoints());

    }

    //Can this realistically be mocked or should this be moved to integration tests?
    @Test
    public void failedrollDoesNotChangeHitPoints(){
        //Arrange

        //Act
        play.roll(everCharacter, attackingEverCharacter, 2);

        //Assert
        assertEquals(5, everCharacter.getHitPoints());
    }

    //Can this realistically be mocked or should this be moved to integration tests?
    @Test
    public void roll20TakesDoublePoints(){
        //Arrange
        everCharacter.setHitPoints(5);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 20);

        //Assert
        assertEquals(3, everCharacter.getHitPoints());
    }

    //Can this realistically be mocked or should this be moved to integration tests?
    //I do have a unitTest in EverCraftCharacterTest for UpdateLifeStatus, is it necessary to have an integration test?
    @Test
    public void afterRollingCharacterWith0HitPointsIsDead(){
        //Arrange
        everCharacter.setHitPoints(2);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 20);

        //Assert
        assertEquals(EverEnum.LifeStatus.Dead, everCharacter.getLifeStatus());
    }

    //same as above
    @Test
    public void afterRollingCharacterWithLessThan0HitPointsIsDead(){
        //Arrange
        everCharacter.setHitPoints(1);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 20);

        //Assert
        assertEquals(EverEnum.LifeStatus.Dead, everCharacter.getLifeStatus());
    }

    @Test
    public void afterRollingCharacterWithMoreThan0HitPointsIsAlive(){
        //Arrange
        everCharacter.setHitPoints(3);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 20);

        //Assert
        assertEquals(EverEnum.LifeStatus.Alive, everCharacter.getLifeStatus());
    }

    @Test
    public void defenderClassIgnoresAttackersStrengthModifier(){
        //Arrange
        everCharacter.setCharacterClass(EverEnum.CharacterClassEnum.DEFENDER);
        attackingEverCharacter.getAbilities().setStrengthScore(20);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 11);

        //Assert
        assertEquals(9, everCharacter.getHitPoints());
    }

    @Test
    public void warlordClassIgnoresDefendersDexterityModifier(){
        //Arrange
        attackingEverCharacter.setCharacterClass(EverEnum.CharacterClassEnum.WARLORD);
        everCharacter.getAbilities().setDexterityScore(20);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 14);

        //Assert
        assertEquals("it's a hit", result);
    }

    @Test
    public void rogueHitAgainstEvilAdds2ToAttack(){
        //Arrange
        attackingEverCharacter.setCharacterClass(EverEnum.CharacterClassEnum.ROGUE);
        everCharacter.setAlignment(EverEnum.Alignment.Evil);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 14);

        //Assert
        assertEquals(2, everCharacter.getHitPoints());
    }

    @Test
    public void rogueHitAgainstGoodAddsRegularAttack(){
        //Arrange
        attackingEverCharacter.setCharacterClass(EverEnum.CharacterClassEnum.ROGUE);
        everCharacter.setAlignment(EverEnum.Alignment.Good);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 14);

        //Assert
        assertEquals(4, everCharacter.getHitPoints());
    }

    @Test
    public void rogueHitAgainstEvilAdds2AndMultiplesBy3ToAttackWhenCrit(){
        //Arrange
        attackingEverCharacter.setCharacterClass(EverEnum.CharacterClassEnum.ROGUE);
        everCharacter.setAlignment(EverEnum.Alignment.Evil);
        everCharacter.setHitPoints(20);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 20);

        //Assert
        assertEquals(11, everCharacter.getHitPoints());
    }

    @Test
    public void dwarfHitAgainstOrcAdds2ToRegularAttack(){
        //Arrange
        everCharacter = new EverCraftCharacter("Defending Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter.setCharacterClass(EverEnum.CharacterClassEnum.DEFAULT);
        everCharacter.setRace(EverEnum.RaceEnum.ORC);
        attackingEverCharacter.setRace(EverEnum.RaceEnum.DWARF);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 12);

        //Assert
        assertEquals(2, everCharacter.getHitPoints());
    }

    @Test
    public void halflingPlus2ToArmorWhenNotBeingHitByHalfling(){
        //Arrange
        everCharacter = new EverCraftCharacter("Defending Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        everCharacter.setRace(EverEnum.RaceEnum.HALFLING);
        attackingEverCharacter.setRace(EverEnum.RaceEnum.DWARF);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 11);

        //Assert
        assertEquals("attack glanced off the armor", result);
    }

    @Test
    public void nonHalflingNoAdditionalArmorWhenAttackedByHalfling(){
        //Arrange
        everCharacter = new EverCraftCharacter("Defending Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);
        attackingEverCharacter.setRace(EverEnum.RaceEnum.DWARF);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 11);

        //Assert
        assertEquals("it's a hit", result);
    }

    @Test
    public void halflingAttackedByOtherHalflingNoAdditionalArmor(){
        //Arrange
        everCharacter = new EverCraftCharacter("Defending Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        everCharacter.setRace(EverEnum.RaceEnum.HALFLING);
        attackingEverCharacter.setRace(EverEnum.RaceEnum.HALFLING);

        //Act
        String result = play.roll(everCharacter, attackingEverCharacter, 11);

        //Assert
        assertEquals("it's a hit", result);
    }

    @Test
    public void longSwordAttackAgainstOrcAdds2ToDamage(){
        //Arrange
        everCharacter = new EverCraftCharacter("Defending Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        everCharacter.setRace(EverEnum.RaceEnum.ORC);
        everCharacter.setHitPoints(20);
        attackingEverCharacter.setRace(EverEnum.RaceEnum.HUMAN);
        attackingEverCharacter.setWeapon(EverEnum.Weapon.LONGSWORD);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 12);
        int result = everCharacter.getHitPoints();

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void longSwordElfAttackAgainstOrcAdds5ToDamage(){
        //Arrange
        everCharacter = new EverCraftCharacter("Defending Character", EverEnum.Alignment.Neutral);
        attackingEverCharacter = new EverCraftCharacter("Attacking Character", EverEnum.Alignment.Neutral);
        everCharacter.setRace(EverEnum.RaceEnum.ORC);
        everCharacter.setHitPoints(20);
        attackingEverCharacter.setRace(EverEnum.RaceEnum.ELF);
        attackingEverCharacter.setWeapon(EverEnum.Weapon.LONGSWORD);

        //Act
        play.roll(everCharacter, attackingEverCharacter, 10);
        int result = everCharacter.getHitPoints();

        //Assert
        assertEquals(7, result);
    }



}
