import com.example.mike.droidevercraft.yay.*;
import com.example.mike.droidevercraft.yay.Enum;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EverCraftCharacterTest {

    EverCraftCharacter everCharacter;

    @Before
    public void beforeAllTests() {
        everCharacter = new EverCraftCharacter("Example Name", com.example.mike.droidevercraft.yay.Enum.Alignment.Good);

    }

    @Test
    public void canGetCharacterName(){
        //Arrange
        String expectedName = "Example Name";
        everCharacter = new EverCraftCharacter(expectedName, Enum.Alignment.Good);

        //Act
        String result = everCharacter.getName();

        //Assert
        assertEquals(expectedName, result);
    }

    @Test
    public void canSetCharacterName(){
        //Arrange

        //Act
        String expectedName = "Joe";
        everCharacter.setName(expectedName);
        String result = everCharacter.getName();

        //Assert
        assertEquals(expectedName, result);
    }

    @Test
    public void canGetAlignment(){
        //Arrange
        Enum.Alignment expectedAlignment = Enum.Alignment.Good;

        //Act
        Enum.Alignment result = everCharacter.getAlignment();

        //Assert
        assertEquals(expectedAlignment, result);
    }

    @Test
    public void canSetAlignment(){
        //Arrange

        //Act
        Enum.Alignment expectedAlignment = Enum.Alignment.Evil;
        everCharacter.setAlignment(expectedAlignment);

        //Assert
        Enum.Alignment result = everCharacter.getAlignment();
        assertEquals(expectedAlignment, result);
    }

    @Test
    public void armorDefaultsTo10(){
        //Arrange

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(10, result);
    }

    @Test
    public void hitPointsDefaultsTo5(){
        //Arrange

        //Act
        int result = everCharacter.getHitPoints();

        //Assert
        assertEquals(5, result);
    }



    //This is where I should use mocking to say it's expected i'm handed back an abilities object and i'm done..I believe
    //OR would I want to do an object compare between a mocked object and the object handed back from getAbilities?
    @Test
    public void charachterHasAbilitiesScoresIncludingStrengthModifier(){
        //Arrange
        int expectedStrengthScore = 0;

        //Act
        int strengthScore = everCharacter.getAbilities().getStrengthScore();
        int result = everCharacter.getAbilities().getStrengthModifier(strengthScore);

        //Assert
        assertEquals(expectedStrengthScore, result);
    }


    @Test
    public void constitutionAddsToHitPoints(){
        //Arrange
        everCharacter.getAbilities().setConstitutionScore(14);

        //Act
        everCharacter.calculateHitPoints(0);

        //Assert
        int result = everCharacter.getHitPoints();
        assertEquals(7, result);
    }

    @Test
    public void constitutionAddsToHitPoints1IsMinimum(){
        //Arrange
        everCharacter.getAbilities().setConstitutionScore(1);

        //Act
        everCharacter.setHitPoints(3);
        everCharacter.calculateHitPoints(0);

        //Assert
        int result = everCharacter.getHitPoints();
        assertEquals(1, result);
    }

    @Test
    public void levelDefaultsTo1(){
        //Arrange

        //Act
        int level = everCharacter.getLevel();

        //Assert
        assertEquals(1, level);
    }

    @Test
    public void reaching1000xpUpsToLevel2(){
        //Arrange
        everCharacter.setExperiencePoints(990);

        //Act
        everCharacter.addExperiencePoints(10);
        int level = everCharacter.getLevel();

        //Assert
        assertEquals(2, level);
    }

    @Test
    public void reaching2000xpUpsToLevel3(){
        //Arrange
        everCharacter.setExperiencePoints(10);

        //Act
        everCharacter.addExperiencePoints(1990);
        int level = everCharacter.getLevel();

        //Assert
        assertEquals(3, level);
    }

    @Test
    public void reaching3058xpUpsToLevel4(){
        //Arrange
        everCharacter.setExperiencePoints(2000);

        //Act
        everCharacter.addExperiencePoints(1058);
        int level = everCharacter.getLevel();

        //Assert
        assertEquals(4, level);
    }

    @Test
    public void eachAdditionalLevelAfterLevel1Adds5HitPointsPlusConstitutionModifier(){
        //Arrange
        everCharacter.getAbilities().setConstitutionScore(12);

        //Act
        everCharacter.addExperiencePoints(3058);
        everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        int hitPoints = everCharacter.getHitPoints();
        assertEquals(24, hitPoints);
    }

    @Test
    public void oneIsAddedToAttackRollForEveryEvenLevelAchieved() {
        //Arrange

        //Act
        //4058 gives you level 5 which means you earn additional 2 for having achieved level 4
        everCharacter.addExperiencePoints(4058);

        //Assert
        assertEquals(3, everCharacter.calculateHitPointsAndAttackStrength(false));
    }

    @Test
    public void addDexterityModifierToArmorClass(){
        //Arrange
        everCharacter.getAbilities().setDexterityScore(14);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void whenUpdateLifeStatusIsCalledAndZeroHPCharacterIsDead(){
        //Arrange
        everCharacter.setHitPoints(0);
        everCharacter.setLifeStatus(Enum.LifeStatus.Alive);

        //Act
        everCharacter.updateLifeStatus();

        //Assert
        assertEquals(Enum.LifeStatus.Dead, everCharacter.getLifeStatus());
    }

    @Test
    public void whenUpdateLifeStatusIsCalledAndBelowZeroHPCharacterIsDead(){
        //Arrange
        everCharacter.setHitPoints(-3);
        everCharacter.setLifeStatus(Enum.LifeStatus.Alive);

        //Act
        everCharacter.updateLifeStatus();

        //Assert
        assertEquals(Enum.LifeStatus.Dead, everCharacter.getLifeStatus());
    }

    @Test
    public void afterRolling20StrengthModifierIsAddedAndDoubledForAttackDamageAndAttackIsDoubled(){
        //Arrange
        everCharacter.getAbilities().setStrengthScore(15);
        boolean rolled20 = true;

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(rolled20);

        //Assert
        assertEquals(10, result);
    }

    @Test
    public void strengthScoreOf1ReturnsAttackDamageOf1(){
        //Arrange
        everCharacter.getAbilities().setStrengthScore(1);
        boolean rolled20 = true;

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(rolled20);

        //Assert
        assertEquals(1, result);
    }

    @Test
    public void strengthModifierIsAddedToAttack(){
        //Arrange
        everCharacter.getAbilities().setStrengthScore(15);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(3, result);
    }

}
