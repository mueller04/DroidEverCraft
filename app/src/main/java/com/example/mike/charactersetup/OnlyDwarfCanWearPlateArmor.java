package com.example.mike.charactersetup;

import com.example.mike.droidevercraft.EverEnum;


public class OnlyDwarfCanWearPlateArmor implements SetupRule {

    CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();

    public OnlyDwarfCanWearPlateArmor(){
    }

    @Override
    public CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                                   EverEnum.RaceEnum race,
                                                   EverEnum.Alignment alignment,
                                                   EverEnum.Weapon weapon,
                                                   EverEnum.CharacterClassEnum characterClass) {
        if (race != EverEnum.RaceEnum.DWARF && armor == EverEnum.Armor.PLATE) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Only Dwarf can wear Plate armor");
        }
        return returnObject;
    }
}
