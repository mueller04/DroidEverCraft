package com.example.mike.droidevercraft;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MapScoreToModifier implements Serializable {

    public Map<Integer,Integer> scoreToModifier = new HashMap<Integer, Integer>();

    public MapScoreToModifier(){
        scoreToModifier.put(1, -5);
        scoreToModifier.put(2, -4);
        scoreToModifier.put(3, -4);
        scoreToModifier.put(4, -3);
        scoreToModifier.put(5, -4);
        scoreToModifier.put(6, -2);
        scoreToModifier.put(7, -2);
        scoreToModifier.put(8, -1);
        scoreToModifier.put(9, -1);
        scoreToModifier.put(10, 0);
        scoreToModifier.put(11, 0);
        scoreToModifier.put(12, 1);
        scoreToModifier.put(13, 1);
        scoreToModifier.put(14, 2);
        scoreToModifier.put(15, 2);
        scoreToModifier.put(16, 3);
        scoreToModifier.put(17, 3);
        scoreToModifier.put(18, 4);
        scoreToModifier.put(19, 4);
        scoreToModifier.put(20, 5);
    }

}