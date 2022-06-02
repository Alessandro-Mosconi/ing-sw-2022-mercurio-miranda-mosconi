package it.polimi.ingsw.network;

import it.polimi.ingsw.virtualview.VirtualView;

public interface VirtualViewListener {
     void performAction();

    void addVirtualView(VirtualView virtualView);
}
