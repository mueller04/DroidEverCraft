package com.example.mike.droidevercraft;

public class EverCraftCharacter {

    private String name;
    private Enum.Weapon weapon = Enum.Weapon.NOWEAPON;
    private Enum.Alignment alignment;
    private Enum.LifeStatus lifeStatus;
    private int experiencePoints = 0;
    private int hitPoints = 5;
    private Abilities abilities;
    private Enum.CharacterClassEnum characterClassEnum;
    private Enum.RaceEnum raceEnum;
    private Enum.Armor armorEnum = Enum.Armor.NONE;

    //Class Related
    private boolean rogueHitAgainstEvilFlag = false;

    //Race Related
    private boolean dwarfHitAgainstOrcFlag = false;
    private boolean halflingIncreasedArmorFlag = false;

    //Weapon Related
    private boolean warAxeAgainstOrcFlag = false;

    public EverCraftCharacter(String name, Enum.Alignment alignment){
        this.name = name;
        this.alignment = alignment;
        this.lifeStatus = Enum.LifeStatus.Alive;
        this.abilities = new Abilities();
        this.characterClassEnum = Enum.CharacterClassEnum.DEFAULT;
        this.raceEnum = Enum.RaceEnum.HUMAN;
    }

    public void setCharacterClass(Enum.CharacterClassEnum characterClass){
        if (alignment == Enum.Alignment.Evil && characterClass == Enum.CharacterClassEnum.DEFENDER) {
            throw new IllegalArgumentException("Defender Class cannot have evil alignment");
        } else if (alignment == Enum.Alignment.Good && characterClass == Enum.CharacterClassEnum.WARLORD) {
            throw new IllegalArgumentException("Warlord Class cannot have good alignment");
        } else if ((alignment == Enum.Alignment.Good || alignment == Enum.Alignment.Evil) && characterClass == Enum.CharacterClassEnum.ROGUE) {
            throw new IllegalArgumentException("Rogue Class cannot have good or evil alignment");
        } else {
            this.characterClassEnum = characterClass;
            setClassModifiers();
        }
    }

    public void setRace(Enum.RaceEnum raceEnum) {
        if (alignment == Enum.Alignment.Evil && raceEnum == Enum.RaceEnum.HALFLING) {
            throw new IllegalArgumentException("Halfling cannot have evil alignment");
        }
        this.raceEnum = raceEnum;
    }

    private void setClassModifiers(){
        if (characterClassEnum == Enum.CharacterClassEnum.DEFENDER) {
            hitPoints *= 2;
        }

    }

    public void updateLifeStatus(){
        if (this.getHitPoints() <= 0){
            this.setLifeStatus(Enum.LifeStatus.Dead);
        }
    }

    public int getModifiedArmor(){
        int armor = 10;

        if (raceEnum == Enum.RaceEnum.ORC || halflingIncreasedArmorFlag){
            armor += 2;
        }

        if (armorEnum ==Enum.Armor.LEATHER){
            armor += 2;
        }

        if (armorEnum == Enum.Armor.PLATE){
            armor += 8;
        }

        if (armorEnum == Enum.Armor.CHAINMAIL){
            armor += 5;
        }

        if (armorEnum == Enum.Armor.SHIELD){
            armor += 3;
        }

        if (armorEnum == Enum.Armor.CHAINMAIL && raceEnum == Enum.RaceEnum.ELF){
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

        if (characterClassEnum == Enum.CharacterClassEnum.DEFENDER){
            if (dexterityModifier >= 0) {
                dexterityModifier *=2;
            }
        }
        if (raceEnum == Enum.RaceEnum.ELF || raceEnum == Enum.RaceEnum.HALFLING) {
            dexterityModifier += 1;
        }
        return dexterityModifier;
    }

    private int calculcateWisdomModifier() {
        int value = 0;
        if (characterClassEnum == Enum.CharacterClassEnum.MONK){
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

        if (weapon == Enum.Weapon.WARAXE ||
                (weapon == Enum.Weapon.LONGSWORD && raceEnum == Enum.RaceEnum.ELF) ){
            rollNumber += 2;
        }

        if (weapon == Enum.Weapon.NUNCHUCKS && characterClassEnum != Enum.CharacterClassEnum.MONK){
            rollNumber -= 4;
        }

        if (armorEnum == Enum.Armor.SHIELD){
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
        if (raceEnum == Enum.RaceEnum.ORC) {
            modifiedAttackScore += 2;
        }
        if (rogueHitAgainstEvilFlag) {
            modifiedAttackScore += 2;
        }
        if (dwarfHitAgainstOrcFlag) {
            modifiedAttackScore += 2;
        }
        if (armorEnum == Enum.Armor.CHAINMAIL && raceEnum == Enum.RaceEnum.ELF){
            modifiedAttackScore += 1;
        }
        return modifiedAttackScore;
    }

    private int calculateStandardAttack(){
        if (characterClassEnum == Enum.CharacterClassEnum.MONK) {
            return 3;
        } else {
            return 1;
        }
    }

    private int calculateWeaponAttack(){
        int weaponAttack = 0;
        if (weapon == Enum.Weapon.DAGGER) {
            weaponAttack += 1;
        }
        if (weapon == Enum.Weapon.LONGSWORD && raceEnum == Enum.RaceEnum.ELF){
            weaponAttack += 2;
        }
        if (weapon == Enum.Weapon.LONGSWORD) {
            weaponAttack += 5;
        }
        if (weapon == Enum.Weapon.WARAXE) {
            weaponAttack += 2;
        }
        if (raceEnum == Enum.RaceEnum.ELF && warAxeAgainstOrcFlag){
            weaponAttack += 5;
        } else if (warAxeAgainstOrcFlag){
            weaponAttack += 2;
        }
        if (weapon == Enum.Weapon.NUNCHUCKS) {
            weaponAttack += 6;
        }

        if (weapon == Enum.Weapon.KNIFEOFOGRESLAYING) {
            weaponAttack += 10;
        }

        return weaponAttack;
    }

    public int calculateStrengthModifier(boolean isCritical){
        int strengthModifier = 0;

        if (characterClassEnum == Enum.CharacterClassEnum.ROGUE) {
            int dexterityScore = this.getAbilities().getDexterityScore();
            strengthModifier = this.getAbilities().getDexterityModifier(dexterityScore);
        } else if (characterClassEnum == Enum.CharacterClassEnum.WARLORD) {
            int strengthScore = this.getAbilities().getStrengthScore();
            strengthModifier = (this.getAbilities().getStrengthModifier(strengthScore) * 2);
        } else {
            int strengthScore = this.getAbilities().getStrengthScore();
            strengthModifier = this.getAbilities().getStrengthModifier(strengthScore);
        }

        if (raceEnum == Enum.RaceEnum.HALFLING){
            strengthModifier -= 1;
        }

        if (isCritical) {
            strengthModifier *= 2;
        }
        return strengthModifier;
    }

    private int criticalHit(){
        int value = 0;
        if (characterClassEnum == Enum.CharacterClassEnum.WARLORD || rogueHitAgainstEvilFlag) {
            value += 3;
        }

        if (weapon == Enum.Weapon.WARAXE && characterClassEnum == Enum.CharacterClassEnum.ROGUE){
            value += 4;
        } else if (weapon == Enum.Weapon.WARAXE) {
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
            if (characterClassEnum == Enum.CharacterClassEnum.WARLORD) {
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
        if (raceEnum == Enum.RaceEnum.DWARF){
            return (base + (constitutionModifier * 2));
        } else {
            return (base + constitutionModifier);
        }
    }


    private int calculateRaceConsitutionModifier(int constitutionModifier) {
        if (raceEnum == Enum.RaceEnum.DWARF) {
            return constitutionModifier += 1;
        } else if (raceEnum == Enum.RaceEnum.ELF) {
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
        if (raceEnum == Enum.RaceEnum.ELF){
            value += 1;
        }
        if (characterClassEnum == Enum.CharacterClassEnum.WARLORD){
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

    //Getters and Setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Enum.Alignment getAlignment(){
        return alignment;
    }

    public void setAlignment(Enum.Alignment alignment){
        this.alignment = alignment;
    }

    public Enum.LifeStatus getLifeStatus(){
        return lifeStatus;
    }

    public void setLifeStatus(Enum.LifeStatus lifeStatus){
        this.lifeStatus = lifeStatus;
    }

    public Abilities getAbilities() { return abilities; }

    public void setExperiencePoints(int experiencePoints){
        this.experiencePoints = experiencePoints;
    }

    public int getHitPoints(){
        return hitPoints;
    }

    public void setHitPoints(int hitPoints){
        this.hitPoints = hitPoints;
    }

    public Enum.CharacterClassEnum getCharacterClass() { return characterClassEnum; }

    public Enum.RaceEnum getRace() { return raceEnum; }

    public void setRogueHitAgainstEvilFlag() { rogueHitAgainstEvilFlag = true; }

    public void setDwarfHitAgainstOrcFlag() { dwarfHitAgainstOrcFlag = true; }

    public void setHalflingIncreasedArmorFlag() { this.halflingIncreasedArmorFlag = true; }

    public Enum.Weapon getWeapon(){
        return weapon;
    }

    public void setWeapon(Enum.Weapon weapon){
        if (raceEnum == Enum.RaceEnum.HUMAN && weapon == Enum.Weapon.KNIFEOFOGRESLAYING){
            //DO NOTHING
        } else {
            this.weapon = weapon;
        }
    }

    public void setWarAxeAgainstOrcFlag(){
        this.warAxeAgainstOrcFlag = true;
    }

    public Enum.Armor getArmor(){
        return armorEnum;
    }

    public void setArmor(Enum.Armor armor){
        if (raceEnum != Enum.RaceEnum.DWARF && armor == Enum.Armor.PLATE){
            //DO NOTHING
        } else {
            this.armorEnum = armor;
        }
    }
}
