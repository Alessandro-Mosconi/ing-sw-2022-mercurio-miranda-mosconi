package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;

public class GeneratePlayersState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        gameController.getGame().addPlayer(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForPlayerData());
        //aggiunge all'array di player del model il player i cui dati vengono chiesti dalla currentVirtualView
        updateNextState(gameController);
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getGame().getPlayers().size()!=gameController.getClientHandlerArrayList().size()){
            gameController.setNextState(new GeneratePlayersState());
            gameController.setCurrentVirtualView((gameController.getCurrentVirtualView()+1));
        }//se non sono finiti i player da inizializzare, torna in questo stato ma incrementa l'indice di scorrimento dell'array delle view
        //se uniamo le classi virtualView e clientHandler bisogna sistemare un po' la chiamata ai metodi get e set
        else{
            gameController.setNextState(new SetupState());
            gameController.setCurrentVirtualView(0);
        }
    }

    @Override
    public void endState(GameController gameController) {
    }
}
