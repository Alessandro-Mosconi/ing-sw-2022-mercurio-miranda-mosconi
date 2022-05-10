package it.polimi.ingsw.virtualview;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class VirtualView {
    //private ArrayList<Player> players;
    //private GameMode chosenGameMode;

    //todo compito della virtual view è di prendere i player dal network handler.
    /*public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
*/
    public Player askForPlayerData() {
        Player player= new Player();
        //todo deve chiedere ad ogni client di inserire tutti i dati richiesti (username, colore e mago)
        return player;
    }

    public AssistantCard askForAssistantCard() {
        AssistantCard chosenAssistantCard = new AssistantCard();
        //todo chiede, riceve e dejsonizza la scelta
        return chosenAssistantCard;
    }
}
