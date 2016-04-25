package charactersetup;

import com.example.mike.droidevercraft.EverEnum;

public class WarlordCannotBeGood implements SetupRule {

    CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();

    @Override
    public CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                                   EverEnum.RaceEnum race,
                                                   EverEnum.Alignment alignment,
                                                   EverEnum.Weapon weapon,
                                                   EverEnum.CharacterClassEnum characterClass) {
        if (alignment == EverEnum.Alignment.Good && characterClass == EverEnum.CharacterClassEnum.WARLORD) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Warlord Class cannot have good alignment");
        }
        return returnObject;
    }
}
