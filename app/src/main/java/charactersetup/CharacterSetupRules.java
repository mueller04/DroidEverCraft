package charactersetup;


import com.example.mike.droidevercraft.EverEnum;

import java.util.ArrayList;

public class CharacterSetupRules {

    private ArrayList<SetupRule> setupRuleList;

    public CharacterSetupRules(){
        setupRuleList = new ArrayList<SetupRule>();
        setupRuleList.add(new DefenderCannotBeEvil());
        setupRuleList.add(new HalflingCannotBeEvil());
        setupRuleList.add(new HumanCannotUseKnifeOfOgreSlaying());
        setupRuleList.add(new OnlyDwarfCanWearPlateArmor());
        setupRuleList.add(new RogueCanOnlyBeNeutral());
        setupRuleList.add(new WarlordCannotBeGood());
    }

    public CharacterSetupRulesReturnObject validate(EverEnum.Armor armor,
                                                           EverEnum.RaceEnum race,
                                                           EverEnum.Alignment alignment,
                                                           EverEnum.Weapon weapon,
                                                           EverEnum.CharacterClassEnum characterClass){

        CharacterSetupRulesReturnObject returnObject = new CharacterSetupRulesReturnObject();
        
        for (SetupRule rule : setupRuleList){
            if (returnObject.getIsValid()){
                returnObject = rule.execute(armor, race, alignment, weapon, characterClass);
            }
        }
        return returnObject;
    }


}
