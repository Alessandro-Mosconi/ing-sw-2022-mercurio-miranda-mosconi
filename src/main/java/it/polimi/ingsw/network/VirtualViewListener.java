package it.polimi.ingsw.network;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Parameter;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualview.VirtualView;

import java.util.Map;

public interface VirtualViewListener {
    void performAction();
    void activateCardEffect(Integer characterCardID, String playerUsername, PawnColor color, Integer islandID, Map<PawnColor,Integer> map1, Map<PawnColor,Integer> map2);
    //void endCardEffect();
    void addVirtualView(VirtualView virtualView);
}
