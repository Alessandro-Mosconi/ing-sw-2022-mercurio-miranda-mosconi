package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeckTest {

    @Test
    void constructor(){
        Deck deck = new Deck(WizardType.wizard1);

        assertEquals(deck.getWizard(), WizardType.wizard1);
        assertEquals(deck.getCards().size(), 10);

        deck.setWizard(WizardType.wizard2);
        assertEquals(deck.getWizard(), WizardType.wizard2);
    }

}