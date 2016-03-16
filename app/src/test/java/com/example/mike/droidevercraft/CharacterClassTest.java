import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacterClassTest {

    EverCraftCharacter everCharacter;

    @Test
    public void defenderClassIncreasesHitPoints(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.DEFENDER);
        int expectedHitPoints = 10;

        //Act
        int result = everCharacter.getHitPoints();

        //Assert
        assertEquals(expectedHitPoints, result);
    }

    @Test
    public void defenderClassAlignmentCannotBeEvil(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Evil);
        try {
            //Act
            everCharacter.setCharacterClass(Enum.CharacterClassEnum.DEFENDER);
        } catch (Exception e) {
            //Assert
            assertEquals(e.getMessage(), "Defender Class cannot have evil alignment");
        }
    }

    @Test
    public void defaultClassAlignmentCanBeEvil(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Evil);

        //Act
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.DEFAULT);

        //Assert
        assertEquals(Enum.CharacterClassEnum.DEFAULT, everCharacter.getCharacterClass());
    }

    @Test
    public void defenderClassDoublesDexterityModifierIfPositive(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.getAbilities().setDexterityScore(14);

        //Act
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.DEFENDER);

        //Assert
        assertEquals(14, everCharacter.getModifiedArmor());
    }

    @Test
    public void defenderClassRegularDexterityModifierIfNegative(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.getAbilities().setDexterityScore(5);

        //Act
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.DEFENDER);

        //Assert
        assertEquals(6, everCharacter.getModifiedArmor());
    }

    @Test
    public void warlordClassTripleDamageOnCriticalHits(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Evil);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.WARLORD);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(true);

        //Assert
        assertEquals(3, result);
    }

    @Test
    public void warlordClassAlignmentCannotBeGood(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        try {
            //Act
            everCharacter.setCharacterClass(Enum.CharacterClassEnum.WARLORD);
        } catch (Exception e) {
            //Assert
            assertEquals(e.getMessage(), "Warlord Class cannot have good alignment");
        }
    }

    @Test
    public void rogueClassAddsDexterityModifierToStrength(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.ROGUE);
        everCharacter.getAbilities().setDexterityScore(20);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(6, result);
    }

    @Test
    public void rogueClassAlignmentCannotBeGood(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        try {
            //Act
            everCharacter.setCharacterClass(Enum.CharacterClassEnum.ROGUE);
        } catch (Exception e) {
            //Assert
            assertEquals(e.getMessage(), "Rogue Class cannot have good or evil alignment");
        }
    }

    @Test
    public void rogueClassAlignmentCannotBeEvil(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Evil);
        try {
            //Act
            everCharacter.setCharacterClass(Enum.CharacterClassEnum.ROGUE);
        } catch (Exception e) {
            //Assert
            assertEquals(e.getMessage(), "Rogue Class cannot have good or evil alignment");
        }
    }

    @Test
    public void warlordClassDoublesStrengthModifier(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.WARLORD);
        everCharacter.getAbilities().setStrengthScore(20);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(11, result);
    }

    @Test
    public void eachAdditionalLevelAfterLevel1Adds6HitPointsPlusConstitutionModifierForWarlordClass(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Evil);
        everCharacter.getAbilities().setConstitutionScore(12);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.WARLORD);

        //Act
        everCharacter.addExperiencePoints(3058);
        everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        int hitPoints = everCharacter.getHitPoints();
        assertEquals(27, hitPoints);
    }

    @Test
    public void warlordClassAdd2ToCriticalRange(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.WARLORD);

        //Act
        int result = everCharacter.getCriticalRange();

        //Assert
        assertEquals(2, result);
    }


    @Test
    public void monkClassDoes3DamageWhenAttacking(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.MONK);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(3, result);
    }

    @Test
    public void monkAddsPositiveWisdomToArmor(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.MONK);
        everCharacter.getAbilities().setWisdomScore(17);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(13, result);
    }

    @Test
    public void monkDoesNotAddNegativeWisdomToArmor(){
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.MONK);
        everCharacter.getAbilities().setWisdomScore(8);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(10, result);
    }

}
