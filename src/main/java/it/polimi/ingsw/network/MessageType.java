package it.polimi.ingsw.network;


public enum MessageType {
    ERROR,
    LOGIN,
    WAIT,
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
    DeckChanged,
    AssistantCard,
    ModelUpdate, Color, Island, ChosenCharacterCard, OK_ASSISTANT_CARD, OK_PAWNS_MOVED, OK_MN_MOVED, OK_CLOUTILE_COLLECTED, START_YOUR_TURN, OK_EFFECT_CARD, WAITING, NOW_IS_YOUR_TURN, SETTINGS, PAWN_MOVE, CHOSEN_CHARACTER_CARD, MN_SHIFT, CHOSEN_CT, ACK, MODEL_UPDATE,
}
