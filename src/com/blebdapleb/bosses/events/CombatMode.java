package com.blebdapleb.bosses.events;

public class CombatMode {

    public static boolean bossCombat;
    private int combatSecs;

    public int getCombatSecs(){

        return combatSecs;

    }

    public void setCombatSecs(int newCombatSecs){

        this.combatSecs = newCombatSecs;

    }

}
