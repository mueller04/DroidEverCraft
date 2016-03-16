import com.example.mike.droidevercraft.yay.*;
import com.example.mike.droidevercraft.yay.Enum;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeaponTest {

    EverCraftCharacter everCharacter;

    @Test
    public void canAssignWeapon() {
        //Arrange
        com.example.mike.droidevercraft.yay.Enum.Weapon expectedWeapon = Enum.Weapon.DAGGER;
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(expectedWeapon);

        //Act
        Enum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(expectedWeapon, result);
    }

    @Test
    public void canAssignOnly1Weapon() {
        //Arrange
        Enum.Weapon expectedWeapon = Enum.Weapon.DAGGER;
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.KNIFEOFOGRESLAYING);
        everCharacter.setWeapon(expectedWeapon);

        //Act
        Enum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(expectedWeapon, result);
    }

    @Test
    public void defaultIsNoWeapon() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);

        //Act
        Enum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(Enum.Weapon.NOWEAPON, result);
    }

    @Test
    public void DaggerAdds1ToDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.DAGGER);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(2, result);
    }

    @Test
    public void longswordAdds5ToDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.LONGSWORD);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(6, result);
    }

    @Test
    public void warAxeAdds2ToDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.WARAXE);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(3, result);
    }

    @Test
    public void warAxeAdds2ToModifiedAttack() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.WARAXE);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void warAxeDoesTripleDamageOnCritical() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.WARAXE);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(true);

        //Assert
        assertEquals(9, result);
    }

    @Test
    public void warAxeDoesQuadrupleDamageOnCriticalWithRogue() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Neutral);
        everCharacter.setWeapon(Enum.Weapon.WARAXE);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.ROGUE);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(true);

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void longswordAdds2ToDamageForElf() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.LONGSWORD);
        everCharacter.setRace(Enum.RaceEnum.ELF);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(8, result);
    }

    @Test
    public void longswordAdds2ToModifiedRollForElf() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.LONGSWORD);
        everCharacter.setRace(Enum.RaceEnum.ELF);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(12, result);
    }

    @Test
    public void nunChucksAdds6ToAttackDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.NUNCHUCKS);
        everCharacter.setRace(Enum.RaceEnum.ELF);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(7, result);
    }

    @Test
    public void nunChucksUsedByNonMonk4PenaltyToModifiedAttackRoll() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.NUNCHUCKS);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(6, result);
    }

    @Test
    public void nunChucksUsedByMonkResultsInRegularAttackRoll() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setWeapon(Enum.Weapon.NUNCHUCKS);
        everCharacter.setCharacterClass(Enum.CharacterClassEnum.MONK);

        //Act
        int result = everCharacter.getModifiedRollNumber(10);

        //Assert
        assertEquals(10, result);
    }

    @Test
    public void KNIFEOFOGRESLAYINGAdds10ToAttackDamage() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setRace(Enum.RaceEnum.DWARF);
        everCharacter.setWeapon(Enum.Weapon.KNIFEOFOGRESLAYING);

        //Act
        int result = everCharacter.calculateHitPointsAndAttackStrength(false);

        //Assert
        assertEquals(11, result);
    }

    @Test
    public void humanCannotWieldKNIFEOFOGRESLAYING() {
        //Arrange
        everCharacter = new EverCraftCharacter("Example Name", Enum.Alignment.Good);
        everCharacter.setRace(Enum.RaceEnum.HUMAN);
        everCharacter.setWeapon(Enum.Weapon.KNIFEOFOGRESLAYING);

        //Act
        Enum.Weapon result = everCharacter.getWeapon();

        //Assert
        assertEquals(Enum.Weapon.NOWEAPON, result);
    }
}
