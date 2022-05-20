package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Deck {
    private final ArrayList<AssistantCard> cards;
    private WizardType wizard;

    public Deck(){
        this.cards = null;
        this.wizard = null;
    }

    public Deck(ArrayList<AssistantCard> cards)
    {
        this.cards = new ArrayList<AssistantCard>(cards);
        this.wizard = null;
    }

    public Deck(ArrayList<AssistantCard> cards, WizardType wizard)
    {
        this.cards = new ArrayList<AssistantCard>(cards);
        this.wizard = wizard;
    }

    public WizardType getWizard() {
        return wizard;
    }

    public ArrayList<AssistantCard> getCards() {
        return cards;
    }

    public void setWizard(WizardType wizard) {
        this.wizard = wizard;
    }

}
