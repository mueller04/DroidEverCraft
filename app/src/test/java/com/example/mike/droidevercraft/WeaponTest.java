package com.example.mike.droidevercraft;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeaponTest {

    EverCraftCharacter everCharacter;

    @Test
    public void canAssignWeapon() {
        //Arrange
        com.example.mike.droidevercraft.EverEnum.Weapon expectedWeapon = EverEnum.Weapon.DAGGER;
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(expectedWeapon);

        //Act
        EverEnum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(expectedWeapon, result);
    }

    @Test
    public void canAssignOnly1Weapon() {
        //Arrange
        EverEnum.Weapon expectedWeapon = EverEnum.Weapon.DAGGER;
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.KNIFEOFOGRESLAYING);
        everCharacter.setWeapon(expectedWeapon);

        //Act
        EverEnum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(expectedWeapon, result);
    }

    @Test
    public void defaultIsNoWeapon() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);

        //Act
        EverEnum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(EverEnum.Weapon.NOWEAPON, result);
    }

    @Test
    public void DaggerAdds1ToDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.DAGGER);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(2, result);
    }

    @Test
    public void longswordAdds5ToDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.LONGSWORD);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(6, result);
    }

    @Test
    public void warAxeAdds2ToDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.WARAXE);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(3, result);
    }

    @Test
    public void warAxeAdds2ToModifiedAttack() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.WARAXE);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void warAxeDoesTripleDamageOnCritical() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.WARAXE);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(true);

        //Assert
        assertEquals(9, result);
    }

    @Test
    public void warAxeDoesQuadrupleDamageOnCriticalWithRogue() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Neutral);
        everCharacter.setWeapon(EverEnum.Weapon.WARAXE);
        everCharacter.setCharacterClass(EverEnum.CharacterClassEnum.ROGUE);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(true);

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void longswordAdds2ToDamageForElf() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.LONGSWORD);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(8, result);
    }

    @Test
    public void longswordAdds2ToModifiedRollForElf() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.LONGSWORD);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void nunChucksAdds6ToAttackDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.NUNCHUCKS);
        everCharacter.setRace(EverEnum.RaceEnum.ELF);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(7, result);
    }

    @Test
    public void nunChucksUsedByNonMonk4PenaltyToModifiedAttackRoll() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.NUNCHUCKS);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(6, result);
    }

    @Test
    public void nunChucksUsedByMonkResultsInRegularAttackRoll() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setWeapon(EverEnum.Weapon.NUNCHUCKS);
        everCharacter.setCharacterClass(EverEnum.CharacterClassEnum.MONK);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(10, result);
    }

    @Test
    public void KNIFEOFOGRESLAYINGAdds10ToAttackDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.DWARF);
        everCharacter.setWeapon(EverEnum.Weapon.KNIFEOFOGRESLAYING);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(11, result);
    }

    @Test
    public void humanCannotWieldKNIFEOFOGRESLAYING() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", EverEnum.Alignment.Good);
        everCharacter.setRace(EverEnum.RaceEnum.HUMAN);
        everCharacter.setWeapon(EverEnum.Weapon.KNIFEOFOGRESLAYING);

        //Act
        EverEnum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(EverEnum.Weapon.NOWEAPON, result);
    }
}
