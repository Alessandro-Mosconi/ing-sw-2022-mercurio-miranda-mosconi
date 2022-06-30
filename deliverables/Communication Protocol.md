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
    - if messageType == ERROR -> then payload is a JSON representation of the error enumeration.
    - else payload is a JSON representation of the objects to send


## Communication Examples

### Checking connection
When connection is established server and client set a socket timeout. Then client and server keep sending each other periodical messages to keep the timeout from expiring (Ping message sent every _timeout/2 sec_)

![ping](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliveries/Sequence_Diagram_Examples/ping_message.png)

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

Example of a message from client to specify the settings:

```json
{
   "id": "userClient",
   "messageType": "SETTINGS",
   "payload": 
   {
     "difficulty": "easy"
   }
}
```

![create match](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliveries/Sequence_Diagram_Examples/create_match.png)

### Player connection joining an existing match

Example of a login message to join an existing match:

```json
{
   "id": "userClient",
   "messageType": "JOIN_MATCH",
   "payload": "idGame"
}
```

![join match](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliveries/Sequence_Diagram_Examples/join_match.png)

### Ask to perform an action

Example of a request to start the planning phase

```json
{
  "username": "userClient",
  "messageType": "PLANNING",
  "payload": "null"
}
```

Example of the choice of an Assistant Card

```json
{
  "username": "userClient",
  "messageType": "PLANNING",
  "payload":
  {
    "id": "3",
    "value": "5",
    "motherMovement": "4",
    "consumed": "true"
  }
}
```

![planning phase](https://github.com/michelelorenzo/ing-sw-2022-mercurio-miranda-mosconi/blob/main/deliveries/Sequence_Diagram_Examples/planning_phase.png)

Example of a request to use/buy a character card:

```json
{
   "username": "userClient",
   "messageType": "CHOSEN_CHARACTER_CARD",
   "payload":
   {
      "idCharacterCard": "3",
      "price" : "2",
      "islandID" : "7"
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
| Server     | LOBBY WAITING | gameMode, numOfPlayer, (userList)    | you are waiting in lobby, if the player is the creator, there isn't userList
| Server     | WAIT  | null | notify the client to wait untile next message
| Server     | IS_YOUR_TURN   | null | notify the client that now it's player turn
| Server     | CARD_ACTIVATED |  null  | notify the client that that the player chosen character card effect is active
| Server     | LOBBY_UPDATED  | null  | notify another player joined the lobby
| Server     | AVAILABLE_WIZARDS       | List\<WizardType\> of available wizards  | tell the player they have to chose a wizard from the list 
| Server     | AVAILABLE_TOWER_COLORS | List\<TowerColor\> of available tower colors | tell the player they have to chose a tower color from the list
| Server     | UPDATE_ASSISTANT_CARD | nickname,  idAssistantCard| tell the client that the player with that nickname chose tha assistant card with that id
| Server     | UPDATE_ISLAND |   islandID,  studentMap  | update the student map of the island with that id
| Server     | UPDATE_TOWERS_NUM |   nickname, towerNumber   | update the tower number of the player with that nickname
| Server     | UPDATE_SCHOOL_BOARD_ENTRANCE |  nickname, studentMap    | update the entrance schoolboard of the client with that nickname
| Server     | UPDATE_SCHOOL_BOARD_HALL |  nickname, studentMap    | update the hall schoolboard of the client with that nickname
| Server     | UPDATE_PROFESSORS |  nickname, professorMap    | update the professorTable of the client with that nickname
| Server     | UPDATE_ISLAND_LIST| List\<Island\>  islandList   | update the the main board with the given islandList
| Server     | UPDATE_CLOUDTILES| List\<CloudTile\>  cloudList  | update the the main board with the given cloudList
| Server     | SETUP_PLAYERS | List\<Player\>  cloudList     | for each player setup the view player settings
| Server     | MODEL_CREATED   | null     | notify the model is correctly created
| Server     | GAME_ENDED | nickname     | notify the client tha the player with that nickname won
| Server     | UPDATE_WALLET  | nickname, currentWallet     | update the player with that nickname with the new amount of the wallet
| Server     | UPDATE_MAX_SHIFT | nickname| update the maximum shift of mother nature of the player with that nickname (character card effect)
| Server     | PRICE_INCREASE | cardID, newPrice     | notify the client with the new price of the character card witg cardID
| Server     | INIT_CHARACTER_CARDS | List\<cardID, price, parameters\>| notify the clients with the three initial character cards attribute to initialize them
| Server     | UPDATE_CARD_STUDENTS | cardID, studentMap     | notify the clients that the character card with that id changed its map
| Server     | SOMEONE_ACTIVATED_AN_EFFECT | nickname, cardID | notify the clients that tha aplayer with that nickname activated the cardID effect
| Server     | EFFECT_ENDED  | null| notify the client that the current character card effect has ended
| Server/Client     | -- |  "ping"  | sent to notify that server/clients are still working (it is only a string "ping")
| Client     | SETTINGS |  wizardType, towerColor   | communicate to the server the chosen wizardType and towerColor   
| Client     | CREATE_MATCH | username, gameID, playerNumber, gameMode     | send the settings to create the match
| Client     | ASSISTANT_CARD |  chosenAssistantID | tell the ID of the chosen assistant card
| Client     | JOIN_MATCH |  nickname, gameID    | sent by client to join an existing match
| Client     | PAWN_MOVE |  colorStudentToMove, destination | message to communicate a single move of pawn
| Client     | MN_SHIFT      | shift     | message to communicate a shift of Mother Nature
| Client     | CHOSEN_CT        | chosenCloudID     | message to communicate the ID of the chosen cloud
| Client     | CHOSEN_CHARACTER_CARD        | characterCardID, parameters     | message to communicate the ID of the chosen character card

### Character Card Parameters
| CharacterCardID | parameters |
| :----: | :----: | 
| 1 | `PawnColor`, `islandID` | 
| 3, 5 | `islandID` | 
| 7, 10 |`map1`, `map2`|
N.B. map1 e map2 are sent addding to payload `PawnColor`, `numberOfPanws` for each color of each map

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
| INVALID_GAME_MODE | the selected game mode is invalid
| GAME_ALREADY_STARTED | a player tries to JOIN a game already started 
| GAME_ALREADY_EXISTING | a player tries to CREATE a game with an idGame of a game already existing
| GAME_NOT_FOUND | a player tries to JOIN a game with an idGame that existing doesn't match with any idGame
| INVALID_USERNAME | username is empty or null
| INVALID_NUM_PLAYER | num_player is empty, null, <2 or >4 
| INVALID_DIFFICULTY | difficulty is empty, null, != easy or expert
| FULL_LOBBY| you are trying to join a game with more than 3 player in the lobby

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


