package com.example.mike.droidevercraft;


public class CharacterSetupRules {

    public static CharacterSetupRulesReturnObject validate(EverEnum.Armor armor,
                                                           EverEnum.RaceEnum race,
                                                           EverEnum.Alignment alignment,
                                                           EverEnum.Weapon weapon,
                                                           EverEnum.CharacterClassEnum characterClass){

        CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();
        
        if (race != EverEnum.RaceEnum.DWARF && armor == EverEnum.Armor.PLATE){
            returnObject.setIsValid(false);
            returnObject.setMessage("Only Dwarf can wear Plate armor");
        } else if (alignment == EverEnum.Alignment.Evil && race == EverEnum.RaceEnum.HALFLING){
            returnObject.setIsValid(false);
            returnObject.setMessage("Halfling cannot have evil alignment");
        } else if (race != EverEnum.RaceEnum.DWARF && armor == EverEnum.Armor.PLATE){
            returnObject.setIsValid(false);
            returnObject.setMessage("Only Dwarf can wear Plate armor");
        } else if (alignment == EverEnum.Alignment.Evil && race == EverEnum.RaceEnum.HALFLING) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Halfling cannot have evil alignment");
        } else if (race == EverEnum.RaceEnum.HUMAN && weapon == EverEnum.Weapon.KNIFEOFOGRESLAYING){
            returnObject.setIsValid(false);
            returnObject.setMessage("Human cannot use Knife of Ogre Slaying");
        } else if (race == EverEnum.RaceEnum.HUMAN && weapon == EverEnum.Weapon.KNIFEOFOGRESLAYING){
            returnObject.setIsValid(false);
            returnObject.setMessage("Human cannot use Knife of Ogre Slaying");
        } else if (alignment == EverEnum.Alignment.Evil && characterClass == EverEnum.CharacterClassEnum.DEFENDER) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Defender Class cannot have evil alignment");
        } else if (alignment == EverEnum.Alignment.Good && characterClass == EverEnum.CharacterClassEnum.WARLORD) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Warlord Class cannot have good alignment");
        } else if ((alignment == EverEnum.Alignment.Good || alignment == EverEnum.Alignment.Evil) && characterClass == EverEnum.CharacterClassEnum.ROGUE) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Rogue Class cannot have good or evil alignment");
        }
        return returnObject;
    }

}
