package charactersetup;

import com.example.mike.droidevercraft.EverEnum;

public class DefenderCannotBeEvil implements SetupRule {

    CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();

    @Override
    public CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                                   EverEnum.RaceEnum race,
                                                   EverEnum.Alignment alignment,
                                                   EverEnum.Weapon weapon,
                                                   EverEnum.CharacterClassEnum characterClass) {
        if (alignment == EverEnum.Alignment.Evil && characterClass == EverEnum.CharacterClassEnum.DEFENDER) {
            returnObject.setIsValid(false);
            returnObject.setMessage("Defender Class cannot have evil alignment");
        }
        return returnObject;
    }
}