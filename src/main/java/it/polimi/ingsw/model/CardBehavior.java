package it.polimi.ingsw.model;

public interface CardBehavior {

     /**
      * Activates the character card's effect.
      * @param parameter contains the Game in which the card is activated, the player who activates it and all the user choices that are necessary for the effect to be applied.
      */
     void startEffect(Parameter parameter);

     /**
      * Initializes those cards who need to be initialized.
      * @param parameter contains the Game in which the card is activated.
      */
     void initializeCard(Parameter parameter);

     /**
      * Resets those Game attributes modified by the effect of the card when it would persist otherwise.
      * @param parameter contains the Game in which the card was activated, the player who activated it and all the user choices used to start the effect itself.
      */
     void endEffect(Parameter parameter);
}
