package it.polimi.ingsw.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EnumTest {

    @Test
    public void PawnColor(){
        ArrayList<Color> colors = new ArrayList<>();

        for(PawnColor color : PawnColor.values())
            colors.add(PawnColor.getColor(PawnColor.getColorIndex(color)));

        for(int i=0; i<5; i++)
            assertEquals(colors.get(i), PawnColor.getColorFromPawnColor(PawnColor.getIndex(i)));

        assertNull(PawnColor.getIndex(10));
        assertEquals(PawnColor.getColor(10), Color.TRANSPARENT);
    }

    @Test
    public void TowerColor(){
        assertEquals(Color.GREY, TowerColor.getColor(TowerColor.grey));
        assertEquals(Color.BLACK, TowerColor.getColor(TowerColor.black));
        assertEquals(Color.WHITE, TowerColor.getColor(TowerColor.white));
    }

    @Test
    public void WizardType(){

        assertEquals(1, WizardType.getIndex(WizardType.wizard1));
        assertEquals(2, WizardType.getIndex(WizardType.wizard2));
        assertEquals(3, WizardType.getIndex(WizardType.wizard3));
        assertEquals(4, WizardType.getIndex(WizardType.wizard4));
    }
}