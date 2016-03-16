package com.example.mike.droidevercraft;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArmorTest {

    EverCraftCharacter everCharacter;

    @Test
    public void leatherArmorAdds2ToArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setArmor(EverEnum.Armor.LEATHER);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void defaultArmorIsNone() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);

        //Act
        EverEnum.Armor result = everCharacter.getArmor();

        //Assert
        assertEquals(EverEnum.Armor.NONE, result);
    }

    @Test
    public void onlyDwarfsCanWearPlateArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.DWARF);
        everCharacter.setArmor(EverEnum.Armor.PLATE);

        //Act
        EverEnum.Armor result = everCharacter.getArmor();

        //Assert
        assertEquals(EverEnum.Armor.PLATE, result);
    }

    @Test
    public void ElvesCannotWearPlateArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);
        everCharacter.setArmor(EverEnum.Armor.PLATE);

        //Act
        EverEnum.Armor result = everCharacter.getArmor();

        //Assert
        assertEquals(EverEnum.Armor.NONE, result);
    }

    @Test
    public void plateArmorAdds8ToArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.DWARF);
        everCharacter.setArmor(EverEnum.Armor.PLATE);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(18, result);
    }

    @Test
    public void chainMailArmorAdds5ToArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.DWARF);
        everCharacter.setArmor(EverEnum.Armor.CHAINMAIL);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(15, result);
    }

    @Test
    public void chainMailArmorAdds8ToElfsArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);
        everCharacter.setArmor(EverEnum.Armor.CHAINMAIL);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(19, result);
    }

    @Test
    public void chainMailArmorAdds1ToElfsAttack() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);
        everCharacter.setArmor(EverEnum.Armor.CHAINMAIL);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(2, result);
    }

    @Test
    public void sheildAdds3ToArmor() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.HUMAN);
        everCharacter.setArmor(EverEnum.Armor.SHIELD);

        //Act
        int result = everCharacter.getModifiedArmor();

        //Assert
        assertEquals(13, result);
    }

    @Test
    public void sheildPenaltyOf3ToAttackModifier() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.HUMAN);
        everCharacter.setArmor(EverEnum.Armor.SHIELD);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(7, result);
    }

}
