package it.polimi.ingsw.network;

import it.polimi.ingsw.model.PawnColor;

import java.util.Map;

public interface VirtualViewListener {

    /**
     * Performs the client's request.
     */
    void performAction();

    /**
     * Activates a character card's effect.
     * @param characterCardID character card to be activated.
     * @param playerUsername player who wants to activate it.
     * @param color parameter - could be null.
     * @param islandID parameter - could be null.
     * @param map1 parameter - could be null.
     * @param map2 parameter - could be null.
     */
    void activateCardEffect(Integer characterCardID, String playerUsername, PawnColor color, Integer islandID, Map<PawnColor,Integer> map1, Map<PawnColor,Integer> map2);

    /**
     * Makes the controller listener of a new virtual view.
     * @param virtualView virtual view to be listened to.
     */
    void addVirtualView(VirtualView virtualView);
}
