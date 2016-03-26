package com.example.mike.droidevercraft;

import com.google.gson.Gson;

import java.io.Serializable;

public class EverCraftCharacter  {

    private String name;
    private int playerNumber;
    private EverEnum.Weapon weapon = EverEnum.Weapon.NOWEAPON;
    private EverEnum.Alignment alignment;
    private EverEnum.LifeStatus lifeStatus;
    private int experiencePoints;
    private int hitPoints = 5;
    private Abilities abilities;
    private EverEnum.CharacterClassEnum characterClassEnum;
    private EverEnum.RaceEnum raceEnum;
    private EverEnum.Armor armorEnum = EverEnum.Armor.NONE;

    //Class Related
    private boolean rogueHitAgainstEvilFlag = false;

    //Race Related
    private boolean dwarfHitAgainstOrcFlag = false;
    private boolean halflingIncreasedArmorFlag = false;

    //Weapon Related
    private boolean warAxeAgainstOrcFlag = false;



    public EverCraftCharacter(String name, EverEnum.Alignment alignment){
        //this.abilities = abilities;
        abilities = new Abilities();
        this.name = name;
        this.alignment = alignment;
        this.lifeStatus = EverEnum.LifeStatus.Alive;
        this.abilities = new Abilities();
        this.characterClassEnum = EverEnum.CharacterClassEnum.DEFAULT;
        this.raceEnum = EverEnum.RaceEnum.HUMAN;
    }

    public void setCharacterClass(EverEnum.CharacterClassEnum characterClass){
        if (alignment == EverEnum.Alignment.Evil && characterClass == EverEnum.CharacterClassEnum.DEFENDER) {
            throw new IllegalArgumentException("Defender Class cannot have evil alignment");
        } else if (alignment == EverEnum.Alignment.Good && characterClass == EverEnum.CharacterClassEnum.WARLORD) {
            throw new IllegalArgumentException("Warlord Class cannot have good alignment");
        } else if ((alignment == EverEnum.Alignment.Good || alignment == EverEnum.Alignment.Evil) && characterClass == EverEnum.CharacterClassEnum.ROGUE) {
            throw new IllegalArgumentException("Rogue Class cannot have good or evil alignment");
        } else {
            this.characterClassEnum = characterClass;
            setClassModifiers();
        }
    }

    public void setRace(EverEnum.RaceEnum raceEnum) {
        if (alignment == EverEnum.Alignment.Evil && raceEnum == EverEnum.RaceEnum.HALFLING) {
            throw new IllegalArgumentException("Halfling cannot have evil alignment");
        }
        this.raceEnum = raceEnum;
    }

    private void setClassModifiers(){
        if (characterClassEnum == EverEnum.CharacterClassEnum.DEFENDER) {
            hitPoints *= 2;
        }

    }

    public void updateLifeStatus(){
        if (this.getHitPoints() <= 0){
            this.setLifeStatus(EverEnum.LifeStatus.Dead);
        }
    }

    public int getModifiedArmor(){
        int armor = 10;

        if (raceEnum == EverEnum.RaceEnum.ORC || halflingIncreasedArmorFlag){
            armor += 2;
        }

        if (armorEnum == EverEnum.Armor.LEATHER){
            armor += 2;
        }

        if (armorEnum == EverEnum.Armor.PLATE){
            armor += 8;
        }

        if (armorEnum == EverEnum.Armor.CHAINMAIL){
            armor += 5;
        }

        if (armorEnum == EverEnum.Armor.SHIELD){
            armor += 3;
        }

        if (armorEnum == EverEnum.Armor.CHAINMAIL && raceEnum == EverEnum.RaceEnum.ELF){
            armor += 3;
        }

        int dexterityModifier = calculateDexterityModifier();
        int wisdomModifier = calculcateWisdomModifier();

        armor += (dexterityModifier + wisdomModifier);
        return armor;
    }

    private int calculateDexterityModifier() {
        int dexterityScore = abilities.getDexterityScore();
        int dexterityModifier = abilities.getDexterityModifier(dexterityScore);

        if (characterClassEnum == EverEnum.CharacterClassEnum.DEFENDER){
            if (dexterityModifier >= 0) {
                dexterityModifier *=2;
            }
        }
        if (raceEnum == EverEnum.RaceEnum.ELF || raceEnum == EverEnum.RaceEnum.HALFLING) {
            dexterityModifier += 1;
        }
        return dexterityModifier;
    }

    private int calculcateWisdomModifier() {
        int value = 0;
        if (characterClassEnum == EverEnum.CharacterClassEnum.MONK){
            int wisdomScore = abilities.getWisdomScore();
            int wisdomModifier = abilities.getWisdomModifier(wisdomScore);
            if (wisdomModifier > 0) {
                value = wisdomModifier;
            }
        }
        return value;
    }

    public int getModifiedRollNumber(int rollNumber) {
        int strengthScore = this.getAbilities().getStrengthScore();
        int strengthModifier = this.getAbilities().getStrengthModifier(strengthScore);

        if (weapon == EverEnum.Weapon.WARAXE ||
                (weapon == EverEnum.Weapon.LONGSWORD && raceEnum == EverEnum.RaceEnum.ELF) ){
            rollNumber += 2;
        }

        if (weapon == EverEnum.Weapon.NUNCHUCKS && characterClassEnum != EverEnum.CharacterClassEnum.MONK){
            rollNumber -= 4;
        }

        if (armorEnum == EverEnum.Armor.SHIELD){
            rollNumber -= 3;
        }

        return rollNumber + strengthModifier;
    }


    public int calculateHitPointsAndAttackStrength(boolean isCritical) {
        int level = getLevel();
        calculateHitPoints(level);

        int standardAttack = calculateStandardAttack();
        int strengthModifier = calculateStrengthModifier(isCritical);
        int attackRollLevelModifier = getAttackRollModifier(level);
        int attackWeapon = calculateWeaponAttack();
        int raceModifiedAttackScore = raceAttackModifier();
        int totalAttackScore = standardAttack + attackRollLevelModifier
                + strengthModifier + attackWeapon + raceModifiedAttackScore;


        if (isCritical) {
            totalAttackScore *= criticalHit();
        }

        if (totalAttackScore < 1) {
            totalAttackScore = 1;
        }

        return totalAttackScore;
    }

    private int raceAttackModifier(){
        int modifiedAttackScore = 0;
        if (raceEnum == EverEnum.RaceEnum.ORC) {
            modifiedAttackScore += 2;
        }
        if (rogueHitAgainstEvilFlag) {
            modifiedAttackScore += 2;
        }
        if (dwarfHitAgainstOrcFlag) {
            modifiedAttackScore += 2;
        }
        if (armorEnum == EverEnum.Armor.CHAINMAIL && raceEnum == EverEnum.RaceEnum.ELF){
            modifiedAttackScore += 1;
        }
        return modifiedAttackScore;
    }

    private int calculateStandardAttack(){
        if (characterClassEnum == EverEnum.CharacterClassEnum.MONK) {
            return 3;
        } else {
            return 1;
        }
    }

    private int calculateWeaponAttack(){
        int weaponAttack = 0;
        if (weapon == EverEnum.Weapon.DAGGER) {
            weaponAttack += 1;
        }
        if (weapon == EverEnum.Weapon.LONGSWORD && raceEnum == EverEnum.RaceEnum.ELF){
            weaponAttack += 2;
        }
        if (weapon == EverEnum.Weapon.LONGSWORD) {
            weaponAttack += 5;
        }
        if (weapon == EverEnum.Weapon.WARAXE) {
            weaponAttack += 2;
        }
        if (raceEnum == EverEnum.RaceEnum.ELF && warAxeAgainstOrcFlag){
            weaponAttack += 5;
        } else if (warAxeAgainstOrcFlag){
            weaponAttack += 2;
        }
        if (weapon == EverEnum.Weapon.NUNCHUCKS) {
            weaponAttack += 6;
        }

        if (weapon == EverEnum.Weapon.KNIFEOFOGRESLAYING) {
            weaponAttack += 10;
        }

        return weaponAttack;
    }

    public int calculateStrengthModifier(boolean isCritical){
        int strengthModifier = 0;

        if (characterClassEnum == EverEnum.CharacterClassEnum.ROGUE) {
            int dexterityScore = this.getAbilities().getDexterityScore();
            strengthModifier = this.getAbilities().getDexterityModifier(dexterityScore);
        } else if (characterClassEnum == EverEnum.CharacterClassEnum.WARLORD) {
            int strengthScore = this.getAbilities().getStrengthScore();
            strengthModifier = (this.getAbilities().getStrengthModifier(strengthScore) * 2);
        } else {
            int strengthScore = this.getAbilities().getStrengthScore();
            strengthModifier = this.getAbilities().getStrengthModifier(strengthScore);
        }

        if (raceEnum == EverEnum.RaceEnum.HALFLING){
            strengthModifier -= 1;
        }

        if (isCritical) {
            strengthModifier *= 2;
        }
        return strengthModifier;
    }

    private int criticalHit(){
        int value = 0;
        if (characterClassEnum == EverEnum.CharacterClassEnum.WARLORD || rogueHitAgainstEvilFlag) {
            value += 3;
        }

        if (weapon == EverEnum.Weapon.WARAXE && characterClassEnum == EverEnum.CharacterClassEnum.ROGUE){
            value += 4;
        } else if (weapon == EverEnum.Weapon.WARAXE) {
            value += 3;
        }

        if (value < 2){
            value = 2;
        }

       return value;
    }


    public void addExperiencePoints(int experiencePoints) {
        this.experiencePoints += experiencePoints;
    }


    public int getLevel() {
        int level = 1;

        if (experiencePoints == 0) {
            experiencePoints = 1;
        }

        int fractionalLevel = (experiencePoints / 1000) + 1;
        level = (int) Math.floor(fractionalLevel);

        return level;
    }


    public void calculateHitPoints(int level){
        int constitutionModifier = abilities.getConstitutionModifier(abilities.getConstitutionScore());

        constitutionModifier = calculateRaceConsitutionModifier(constitutionModifier);

        hitPoints += constitutionModifier;

        for (int i = 1; i < level; i++) {
            if (characterClassEnum == EverEnum.CharacterClassEnum.WARLORD) {
                hitPoints += calculateLevelUpConstitutionModifier(6, constitutionModifier);
            } else {
                hitPoints += calculateLevelUpConstitutionModifier(5, constitutionModifier);
            }
        }
        if (hitPoints < 1) {
            hitPoints = 1;
        }
    }


    private int calculateLevelUpConstitutionModifier(int base, int constitutionModifier){
        if (raceEnum == EverEnum.RaceEnum.DWARF){
            return (base + (constitutionModifier * 2));
        } else {
            return (base + constitutionModifier);
        }
    }


    private int calculateRaceConsitutionModifier(int constitutionModifier) {
        if (raceEnum == EverEnum.RaceEnum.DWARF) {
            return constitutionModifier += 1;
        } else if (raceEnum == EverEnum.RaceEnum.ELF) {
            return constitutionModifier -= 1;
        } else {
            return constitutionModifier;
        }
    }


    private int getAttackRollModifier(int level) {
        int fractionalLevel = (level / 2);
        int attackRollModifier = (int)Math.floor(fractionalLevel);
        return attackRollModifier;
    }


    public int getCriticalRange(){
        int value = 0;
        if (raceEnum == EverEnum.RaceEnum.ELF){
            value += 1;
        }
        if (characterClassEnum == EverEnum.CharacterClassEnum.WARLORD){
            value += 2;
        }
        return value;
    }

    public void clearFlags(){
        rogueHitAgainstEvilFlag = false;
        dwarfHitAgainstOrcFlag = false;
        halflingIncreasedArmorFlag = false;
        warAxeAgainstOrcFlag = false;
    }

    public void beginNextRound(){
        setHitPoints(5);
        setLifeStatus(EverEnum.LifeStatus.Alive);
    }

    //Getters and Setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public EverEnum.Alignment getAlignment(){
        return alignment;
    }

    public void setAlignment(EverEnum.Alignment alignment){
        if (alignment == EverEnum.Alignment.Evil && raceEnum == EverEnum.RaceEnum.HALFLING) {
            throw new IllegalArgumentException("Halfling cannot have evil alignment");
        }
        this.alignment = alignment;
    }

    public EverEnum.LifeStatus getLifeStatus(){
        return lifeStatus;
    }

    public void setLifeStatus(EverEnum.LifeStatus lifeStatus){
        this.lifeStatus = lifeStatus;
    }

    public Abilities getAbilities() { return abilities; }

    public int getExperiencePoints(){
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints){
        this.experiencePoints = experiencePoints;
    }

    public int getHitPoints(){
        if (hitPoints < 0) {
            hitPoints = 0;
        }
        return hitPoints;
    }

    public void setHitPoints(int hitPoints){
        this.hitPoints = hitPoints;
    }

    public EverEnum.CharacterClassEnum getCharacterClass() { return characterClassEnum; }

    public EverEnum.RaceEnum getRace() { return raceEnum; }

    public void setRogueHitAgainstEvilFlag() { rogueHitAgainstEvilFlag = true; }

    public void setDwarfHitAgainstOrcFlag() { dwarfHitAgainstOrcFlag = true; }

    public void setHalflingIncreasedArmorFlag() { this.halflingIncreasedArmorFlag = true; }

    public EverEnum.Weapon getWeapon(){
        return weapon;
    }

    public void setWeapon(EverEnum.Weapon weapon){
        if (raceEnum == EverEnum.RaceEnum.HUMAN && weapon == EverEnum.Weapon.KNIFEOFOGRESLAYING){
            //DO NOTHING
        } else {
            this.weapon = weapon;
        }
   }

    public void setWarAxeAgainstOrcFlag(){
        this.warAxeAgainstOrcFlag = true;
    }

    public EverEnum.Armor getArmor(){
        return armorEnum;
    }

    public void setArmor(EverEnum.Armor armor){
        if (raceEnum != EverEnum.RaceEnum.DWARF && armor == EverEnum.Armor.PLATE){
            //DO NOTHING
       } else {
            this.armorEnum = armor;
        }
    }
    public int getPlayerNumber(){
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }



}
