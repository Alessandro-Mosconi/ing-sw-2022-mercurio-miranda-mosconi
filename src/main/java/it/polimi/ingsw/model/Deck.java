package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<AssistantCard> cards;
    private WizardType wizard;

    public Deck(){
        this.cards = new ArrayList<AssistantCard>(){{
            add(new AssistantCard(1,1));
            add(new AssistantCard(2,1));
            add(new AssistantCard(3,2));
            add(new AssistantCard(4,2));
            add(new AssistantCard(5,3));
            add(new AssistantCard(6,3));
            add(new AssistantCard(7,4));
            add(new AssistantCard(8,4));
            add(new AssistantCard(9,5));
            add(new AssistantCard(10,5));
        }};
        this.wizard = null;
    }

    public Deck(ArrayList<AssistantCard> cards)
    {
        this.cards = new ArrayList<AssistantCard>(cards);
        this.wizard = null;
    }

    public Deck(WizardType wizard)
    {
        this.cards = new ArrayList<AssistantCard>(){{
            add(new AssistantCard(1,1));
            add(new AssistantCard(2,1));
            add(new AssistantCard(3,2));
            add(new AssistantCard(4,2));
            add(new AssistantCard(5,3));
            add(new AssistantCard(6,3));
            add(new AssistantCard(7,4));
            add(new AssistantCard(8,4));
            add(new AssistantCard(9,5));
            add(new AssistantCard(10,5));
        }};
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
