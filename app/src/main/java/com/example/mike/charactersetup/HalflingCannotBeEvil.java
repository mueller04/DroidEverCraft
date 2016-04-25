package com.example.mike.charactersetup;

import com.example.mike.droidevercraft.EverEnum;

public class HalflingCannotBeEvil implements SetupRule {

    CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();

    public HalflingCannotBeEvil(){
    }

    @Override
    public CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                                   EverEnum.RaceEnum race,
                                                   EverEnum.Alignment alignment,
                                                   EverEnum.Weapon weapon,
                                                   EverEnum.CharacterClassEnum characterClass) {
        if (alignment == EverEnum.Alignment.Evil && race == EverEnum.RaceEnum.HALFLING) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Halfling cannot have evil alignment");
        }
        return returnObject;
    }
}
