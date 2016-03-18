package com.example.mike.droidevercraft;

import java.io.Serializable;

public class Abilities implements Serializable {

    private MapScoreToModifier strength = new MapScoreToModifier();
    private MapScoreToModifier dexterity = new MapScoreToModifier();
    private MapScoreToModifier constitution = new MapScoreToModifier();
    private MapScoreToModifier wisdom = new MapScoreToModifier();
    private MapScoreToModifier intelligence = new MapScoreToModifier();
    private MapScoreToModifier charisma = new MapScoreToModifier();

    private int strengthScore = 10;
    private int dexterityScore = 10;
    private int constitutionScore = 10;
    private int wisdomScore = 10;
    private int intelligenceScore = 10;
    private int charismaScore = 10;

//    private int testInt;
//
//    public int getTestInt(){
//        return testInt;
//    }


    //Getters and Setters
    public int getStrengthModifier(int strengthScore){
        return strength.scoreToModifier.get(strengthScore);
    }

    public int getDexterityModifier(int dexterityScore){
        return dexterity.scoreToModifier.get(dexterityScore);
    }

    public int getConstitutionModifier(int constitutionScore){
        return constitution.scoreToModifier.get(constitutionScore);
    }

    public int getWisdomModifier(int wisdomScore){
        return wisdom.scoreToModifier.get(wisdomScore);
    }

    public int getIntelligenceModifier(int intelligenceScore){
        return intelligence.scoreToModifier.get(intelligenceScore);
    }

    public int getCharismaModifier(int charismaScore){
        return charisma.scoreToModifier.get(charismaScore);
    }

    public int getStrengthScore(){
        return strengthScore;
    }

    public void setStrengthScore(int strengthScore){
        this.strengthScore = strengthScore;
    }

    public int getDexterityScore(){
        return dexterityScore;
    }

    public void setDexterityScore(int dexterityScore){
        this.dexterityScore = dexterityScore;
    }

    public int getConstitutionScore(){
        return constitutionScore;
    }

    public void setConstitutionScore(int constitutionScore){
        this.constitutionScore = constitutionScore;
    }

    public int getWisdomScore(){
        return wisdomScore;
    }

    public void setWisdomScore(int wisdomScore) { this.wisdomScore = wisdomScore; }

    public int getIntelligenceScore(){
        return intelligenceScore;
    }

    public int getCharismaScore(){
        return charismaScore;
    }

}
