package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

public class Deck {
    private final Set<AssistantCard> cards;
    private WizardType wizard;

    public Deck(){
        this.cards = null;
        this.wizard = null;
    }

    public Deck(Set<AssistantCard> cards)
    {
        this.cards = new HashSet<AssistantCard>(cards);
        this.wizard = null;
    }

    public Deck(Set<AssistantCard> cards, WizardType wizard)
    {
        this.cards = new HashSet<AssistantCard>(cards);
        this.wizard = wizard;
    }

    public WizardType getWizard() {
        return wizard;
    }

    public Set<AssistantCard> getCards() {
        return cards;
    }

    public void setWizard(WizardType wizard) {
        this.wizard = wizard;
    }

}
