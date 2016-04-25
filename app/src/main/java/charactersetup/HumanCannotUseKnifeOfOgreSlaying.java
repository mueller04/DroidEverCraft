package charactersetup;

import com.example.mike.droidevercraft.EverEnum;

public class HumanCannotUseKnifeOfOgreSlaying implements SetupRule {

    CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();

    @Override
    public CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                                   EverEnum.RaceEnum race,
                                                   EverEnum.Alignment alignment,
                                                   EverEnum.Weapon weapon,
                                                   EverEnum.CharacterClassEnum characterClass) {
        if (race == EverEnum.RaceEnum.HUMAN && weapon == EverEnum.Weapon.KNIFEOFOGRESLAYING){
            returnObject.setIsValid(false);
            returnObject.setMessage("Human cannot use Knife of Ogre Slaying");
        }
        return returnObject;
    }
}
