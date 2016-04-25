package com.example.mike.charactersetup;

import com.example.mike.droidevercraft.EverEnum;


public class RogueCanOnlyBeNeutral implements SetupRule {

    CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();

    public RogueCanOnlyBeNeutral(){
    }

    @Override
    public CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                                   EverEnum.RaceEnum race,
                                                   EverEnum.Alignment alignment,
                                                   EverEnum.Weapon weapon,
                                                   EverEnum.CharacterClassEnum characterClass) {
        if ((alignment == EverEnum.Alignment.Good || alignment == EverEnum.Alignment.Evil) && characterClass == EverEnum.CharacterClassEnum.ROGUE) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Rogue Class cannot have good or evil alignment");
        }
        return returnObject;
    }
}
