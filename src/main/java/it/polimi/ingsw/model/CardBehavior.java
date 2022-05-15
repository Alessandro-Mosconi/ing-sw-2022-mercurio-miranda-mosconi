package it.polimi.ingsw.model;

public interface CardBehavior {
     void Effect(Parameter parameter);
     void initializeCard(Parameter parameter);
     void endEffect(Parameter parameter);
}
