# Communication Protocol
## Premise

The communication protocol used is **JSON** based. 
## Message Structure
All messages share the following structure:

- id (String) :
  - Client->Server: User of the Client that is sending the msg;
  - Server->Client: User of the destination Client;
- messageType (enum)
- payload (String)
    - if messageType is an _ACTION_ -> then payload is a JSON representation of the objects interested;
    - if messageType is a _MODEL_UPDATE_ -> then payload is a JSON representation of the objects that contains the changes made to the model;
    - if messageType == ERROR -> then payload is a JSON representation of the error enumeration.


## Communication Examples

### Checking connection
When connection is established server and client set a socket timeout. Then client and server keep sending each other periodical messages to keep the timeout from expiring (Ping message sent every _timeout/2 sec_)


### Player connection with match creation

Example of new game creation:

```json
{
   "id": "userClient",
   "messageType": "CREATE_MATCH",
   "payload": "GameID"
}
```

Example of a message from server asking for th settings:

```json
{
   "id": "userClient",
   "messageType": "SETTINGS",
   "payload": null
}
```

Example of a message from client to specify the number of players:

```json
{
   "id": "userClient",
   "messageType": "SETTINGS",
   "payload": 
   {
     "players_number": "2",
     "difficulty": "easy"
   }
}
```


### Player connection joining an existing match

Example of a login message to join an existing match:

```json
{
   "id": "userClient",
   "messageType": "JOIN_MATCH",
   "payload": "idGame"
}
```

### Ask to perform an action

Example of a request to perform an action to use/buy a character card:

```json
{
   "username": "userClient",
   "messageType": "BUY_CHARACTER_CARD",
   "payload":
   {
      "idCharacterCard": "1",
      "price" : "3"
   }
}
```
Example of a possible error message during login (when you try to create a new Game with an id already existing for another match":

```json
{
   "username": "userClient",
   "messageType": "ERROR",
   "payload": "GAME_ALREADY_EXISTING"
}
```

## Message Tables

| Source     | Message Type  | payload content    |  description
| :----:     |    :----:   |          :----:  |     :----:  |
| Server     | LOGIN_SUCCESSFUL       | null    | login procedure has been succesfull completed
| Server     | RECONNECTED  | player username | client has been successfully reconnected to server
| Server     | DISCONNECTED   |   player username   | inform clients that a player has disconnected
| Server     | QUIT |  player username  | sent by the server to notify that a user quitted the game
| Server     | ERROR  | an error type  | notifies the client that an error occured
| Server     | SETTINGS       | null     | tell the client that server is in a state where he expects the number of players and the game difficulty
| Server     | GAME_STARTED | List\<String\> of the players' usernames | indicates to all clients that game has started
| Server     | LOBBY_CREATED | number of remaining required players (int) | confirms to the client that he joined the lobby succesfully
| Server     | OTHER_USER_JOINED |   number of remaining required players (int)   | tells every client that a new player joined
| Server     | SERVER_DOWN |  null    | notifies the client that the server is crashed
| Server     | NEXT_STATE | State object | updates the client about the new turn (planning -> action / my action -> next player action)
| Server     | GAME_ENDED | "user", "motivation"| tells every client who's the winner
| Server     | PLANNING        | null     | the server notify the beginning of the planning phase
| Server     | ACTION        | null     | the server notify the beginning of the action phase
| Server/Client     | PING |  null  | sent to notify that server/clients are still working
| Client     | SETTINGS |  int for number of player and String for GameMode    | the chosen number of players    
| Client     | CREATE_MATCH | the match ID     | sent by client to create a new match
| Client     | JOIN_MATCH |  the match ID    | sent by client to join an existing match
| Client     | QUIT |  null  | sent by the client to the server to QUIT the game
| Client     | END_ACTION_TURN      | null     | this message notify the server that a player has ended its action turn
| Client     | _IN_GAME_ACTION_        | classes needed for the action     | this message contains the different possible decision a Player could do
| Server     | _MODEL_UPDATE_        | classes updated     | this message contains the classes tha are changed after the actions

### In Game Action Table
| Action Type | parameters |description |
| :----: | :----: | :----: |
| ChoseWizard | `Wizard` | choose on deck from the 4 possible
| Planning | `AssistantCard` | choose the Assistant Card
| MoveToIsland |`color`, `IslandManager position`| move a student from the Entrance to an Island
| MoveToHall | `color`| move a student from the Entrance to a Hall
| MoveMN |`shift`| player decided how many steps the MN does
| ChoseCloudTile |`CloudTile Array position`| choose a cloud to take students from
| BuyCharacterCard |`characterCard`| buy, if enough money, a Character Card and use its effect

### Model Update
| Action Type | parameters |description |
| :----: | :----: | :----: |
| SchoolBoardChanged | `user`, `schoolboard` | the schoolboard of user changed
| HallChanged | `user`, `map<PawnColor, int>` | the hall of user changed
| EntranceChanged |`user`, `map<PawnColor, int>` | the entrance of user changed
| ProfTableChanged |`user`, `map<PawnColor, boolean>` | the professor table cof user hanged
| CloudChanged |`cloud`, `CloudTile Array position`| the cloud changed
| IslandChanged |`island`, `IslandManager position` | an Island changed
| CharacterCardChanged |`newPrice`, `CardId` | a CharacterCard changed its value
| WalletChanged |`wallet`, `user` | the wallet of a user changed
| DeckChanged |`deck`, `user` | the deck of a user changed

### Errors Tables
#### Generic Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_ERROR |
| USERNAME_NOT_FOUND | a player is trying to rejoin a match with a user that doesnt match with anyone of the Player List
| INVALID_SETTINGS | the selected number of players is invalid
| GAME_ALREADY_STARTED | a player tries to JOIN a game already started 
| GAME_ALREADY_EXISTING | a player tries to CREATE a game with an idGame of a game already existing
| INVALID_LOGIN_USERNAME | username is empty or null
| INVALID_NUM_PLAYER | num_player is empty, null, <2 or >4 
| INVALID_DIFFICULTY | difficulty is empty, null, != easy or expert


#### Controller Errors
| Error Type | description |
| :----: | :----: |
| UNKNOWN_CONTROLLER_ERROR |
| INVALID_ACTION | action not correctly initialized
| WRONG_ACTION | action can't be performed now
| WRONG_PLAYER | not their turn

#### Model Errors
| Error Type | description |
| :----: | :----: |
| NOT_ENOUGH_MONEY | trying to buy a CharacterCard, but with not enough money
| INVALID_REMOVAL_HALL_STUDENT | trying to remove a student that doesn't exists from the hall
| INVALID_REMOVAL_ENTRANCE_STUDENT | trying to remove a student that doesn't exists from the entrance
| INVALID_REMOVAL_CLOUD_STUDENT | trying to remove a student that doesn't exists from the cloud
| INVALID_REMOVAL_ISLAND_STUDENT | trying to remove a student from that doesn't exists  the island
| INVALID_MN_SHIFT | trying to shift mn more than the AssistantCard possibility (or empty, or null)


