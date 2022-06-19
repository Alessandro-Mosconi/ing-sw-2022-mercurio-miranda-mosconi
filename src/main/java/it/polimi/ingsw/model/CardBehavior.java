package it.polimi.ingsw.model;

public interface CardBehavior {
     void startEffect(Parameter parameter);
     void initializeCard(Parameter parameter);
     void endEffect(Parameter parameter);
}
