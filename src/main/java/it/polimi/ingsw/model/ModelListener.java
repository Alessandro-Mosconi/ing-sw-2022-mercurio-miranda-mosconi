package it.polimi.ingsw.model;

import java.util.ArrayList;

public interface ModelListener {

    void updateModel(Game game);
    void updateLastAssistantCard(Player player);
    void updateIsland (Island island);
    void updatePlayers(ArrayList<Player> players);
    void updateIslandList(ArrayList<Island> islandList);
    void updateCT (CloudTile ct);
    void updateCharacterCardUsed (CharacterCard charCard);
    void updateCardStudents (CharacterCard charCard);
    void updateBonus2InfluencePoints (Player player);
    void updateMaxShift(Player player);
    void updateKeptOut (PawnColor keptOutColor);
    void updateAvailableWizards(ArrayList<WizardType> wizards);

    void updateAvailableTowerColors(ArrayList<TowerColor> availableTowerColors);

    void updateSchoolBoardEntrance(Player player);

    void updateSchoolBoardHall(Player player);

    void updateProfessorTables(ArrayList<Player> players);
}
