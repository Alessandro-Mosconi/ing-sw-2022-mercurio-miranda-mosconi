package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
        private Game game;
       // private VirtualView view;

    public Controller(Game game/*, VirtualView view*/) {
        this.game = game;
        //this.view = view;
    }

    public Controller() {
            this.game = new Game();
            //this.view = new VirtualView();
        }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
