package com.example.mike.droidevercraft;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AbilitiesTest.class,
        EverCraftCharacterTest.class,
        PlayTest.class,
        CharacterClassTest.class,
        RaceTest.class,
        IntegrationTests.class,
        WeaponTest.class,
        ArmorTest.class,
        PlayGameTest.class})

    public class AllTests {
    }