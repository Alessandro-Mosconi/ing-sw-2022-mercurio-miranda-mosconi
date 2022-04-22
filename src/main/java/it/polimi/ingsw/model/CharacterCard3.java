package it.polimi.ingsw.model;

public class CharacterCard3 implements CardBehavior{
/*
    Eff: scegli un'isola e calcola l'infuenza
 */
    private SchoolBoard[] schoolBoards;
    private Island island;

    public CharacterCard3(SchoolBoard[] schoolBoards, Island island) {
        this.schoolBoards = schoolBoards;
        this.island = island;
    }//????????

    @Override
    public void Effect(){//devo dargli come input le schoolboard
        island.assignInfluence(schoolBoards);
    }
}
