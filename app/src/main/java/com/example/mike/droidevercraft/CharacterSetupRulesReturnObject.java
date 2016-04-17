package com.example.mike.droidevercraft;

/**
 * Created by Mike on 4/15/16.
 */
public class CharacterSetupRulesReturnObject {

    private boolean isValid;
    private String message;

    public CharacterSetupRulesReturnObject(){
        isValid = true;
        message = "";
    }

    public boolean getIsValid(){
        return isValid;
    }

    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
