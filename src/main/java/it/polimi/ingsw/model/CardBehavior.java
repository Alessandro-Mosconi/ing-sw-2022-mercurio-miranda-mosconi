package it.polimi.ingsw.model;

public interface CardBehavior {
     void startEffect(Parameter parameter);
     void initializeCard(Parameter parameter);
     void endEffect(Parameter parameter);
}
//TODO se si fa l'ereditarietà dalla characterCard non serve interfaccia behavior