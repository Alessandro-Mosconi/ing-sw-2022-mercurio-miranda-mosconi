package it.polimi.ingsw.network;


public enum MessageType {
    ERROR,
    LOGIN,
    LOBBY_WAITING,
    LOGIN_SUCCESSFUL,
    RECONNECTED,
    DISCONNECTED,
    QUIT,
    GAME_MODE,
    NUM_PLAYER,
    GAME_STARTED,
    LOBBY_CREATED,
    LOBBY_JOINED,
    OTHER_USER_JOINED,
    NEXT_STATE,
    GAME_ENDED,
    PLANNING,
    ACTION,
    CREATE_MATCH,
    READY,
    JOIN_MATCH,
    END_ACTION_TURN,
    ChoseWizard,
    Planning,
    MoveToIsland,
    MoveToHall,
    MoveMN,
    ChoseCloudTile,
    BuyCharacterCard,
    SchoolBoardChanged,
    HallChanged,
    EntranceChanged,
    ProfTableChanged,
    CloudChanged,
    IslandChanged,
    CharacterCardChanged,
    WalletChanged,
    DeckChanged
}