package charactersetup;


import com.example.mike.droidevercraft.EverEnum;

public interface SetupRule {

    CharacterSetupRulesReturnObject execute(EverEnum.Armor armor,
                                            EverEnum.RaceEnum race,
                                            EverEnum.Alignment alignment,
                                            EverEnum.Weapon weapon,
                                            EverEnum.CharacterClassEnum characterClass);

}
