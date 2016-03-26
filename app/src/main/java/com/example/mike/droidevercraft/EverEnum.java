package com.example.mike.droidevercraft;

import java.io.Serializable;

public class EverEnum {

    public enum Alignment implements Serializable {
        Good, Evil, Neutral
    }

    public enum LifeStatus implements Serializable {
        Alive, Dead
    }

    public enum CharacterClassEnum implements Serializable  {
        DEFAULT, DEFENDER, WARLORD, ROGUE, MONK
    }

    public enum RaceEnum implements Serializable  {
        HUMAN, ORC, DWARF, ELF, HALFLING
    }

    public enum Weapon implements Serializable  {
        NOWEAPON, LONGSWORD, DAGGER, KNIFEOFOGRESLAYING, WARAXE, NUNCHUCKS
    }

    public enum Armor implements Serializable {
        NONE, LEATHER, PLATE, CHAINMAIL, SHIELD
    }

}
