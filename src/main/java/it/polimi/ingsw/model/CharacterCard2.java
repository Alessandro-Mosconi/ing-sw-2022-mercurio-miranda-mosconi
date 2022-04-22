package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CharacterCard2 implements CardBehavior{
/*
    Eff: durante il turno, prendi il controllo dei prof anche se nella tua sala hai lo stesso numero di studenti del giocatore che controlla i prof in quel momento
 */
    private ArrayList<SchoolBoard> schoolBoards;

    @Override
    public void Effect(){
        for(SchoolBoard s: schoolBoards){
            //TODO
        }

    }
}
