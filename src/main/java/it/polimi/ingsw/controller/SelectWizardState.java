package it.polimi.ingsw.controller;

public class GeneratePlayerState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        gameController.getGame().addPlayer(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForPlayerData());
        //aggiunge all'array di player del model il player i cui dati vengono chiesti dalla currentVirtualView
        /*
        model update -> tutte le virtualView sono observer e il loro metodo update che viene chiamato setta a ModelUpdate tutti i prossimi messaggi
        i client inviano la conferma della ricezione del modelUpdate
        le VV leggono che il client ha ricevuto correttamente il modelUpdate e settano il msg_out ad:
        1. okAspetta per tutte
        2. chiediDati alla prossima vV
         */
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getGame().getPlayers().size()!=gameController.getClientHandlerArrayList().size()){
            gameController.setNextState(new SelectWizardState());
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
        gameController.setPreviousState(this);
    }
}
